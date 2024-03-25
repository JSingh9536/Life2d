package com.game.server;

import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
	private int port = 7272;
	
	private ServerSocket server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean listening;
	private ExecutorService threadPool;
	
	private ArrayList<ClientHandler> connections;
	
	public Server() {
		connections = new ArrayList<>();//Server starts with a clean slate of clients
		listening = true;
	}
	
	public void run() {
		try {
			server = new ServerSocket(port);
			threadPool = Executors.newCachedThreadPool();
			while(listening) {//while the server is still listening for connections
				Socket client = server.accept(); //new client accepts and starts server
				ClientHandler handler = new ClientHandler(client); //The Client is now the handler for clients
				connections.add(handler);//client is added to list of clients
				threadPool.execute(handler);//handler is added to thread Pool
			}
		} catch(IOException ioe) {
			shutdown();
		}
	}
	
	public void serverBroadcast(String message) {//Sends a Message to all Clients in Server
		for(ClientHandler clh : connections) { //for each client in the connections list
			clh.sendMessage(message);//sends the client a message
		}
	}
	
	public void shutdown(){
		try {
			listening = false;
			threadPool.shutdown();
			if(!server.isClosed()) { //if server still open
				server.close();
			}
			for(ClientHandler clh : connections) {//for each client in list of connections
				clh.shutdown(); //shutdown the client
			}
		} catch(IOException ioe) {
			//ignore because we need to handle this request
		}
	}
	
	/*
	public void sendObject(Object packet){
		try {
			out.writeObject(packet);
		}catch(IOException e) {
			e.printStackTrace();
		}
	} */
	
	
	private class ClientHandler implements Runnable{ //Own Class to handle Clients
		
		private Socket client;
		private BufferedReader in;
		private PrintWriter out;
		
		public ClientHandler(Socket client) {
			this.client = client;
		}
		
		@Override
		public void run() {
			try {
				out = new PrintWriter(client.getOutputStream(), true); //Get Output from client, Auto-Flush set to true
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));//Buffered needs to pass InputStreamReader, gets from Client's InputStream
				//out.println(); To send client message
				//in.readLine(); To read client message
				serverBroadcast("User has entered the chat!");
				String message; //initializes message variable
				while((message = in.readLine()) != null) { //when there is a message in client's input
					if(message.startsWith("/quit")) {
						serverBroadcast("User has left the chat");
						shutdown();
					} else {
						serverBroadcast(message);//send message to everyone in the server
					}
				}
			} catch (IOException ioe) {
				shutdown();
			}
		}
		
		public void sendMessage(String message) {//basic method to message the client
			out.println(message);//sends the client a message
		}
		
		public void shutdown(){
			try {
				in.close();
				out.close();//close both streams from client
				if(!client.isClosed()) {
					client.close();
				}
			} catch(IOException ioe) {
				//ignore because we need to handle this request
			}
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}
}
