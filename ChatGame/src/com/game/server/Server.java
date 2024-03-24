package com.game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public class Server implements Runnable{
	private String host;
	private int port;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean running = false;
	
	public Server(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() {
		try {
			socket = new Socket(host,port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			new Thread(this).start();
		}catch(ConnectException e) {
		System.out.println("Unable to connect:");
	}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			running = false;
			
			in.close();
			out.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void sendObject(Object packet){
		try {
			out.writeObject(packet);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			running = true;
			
			while(running) {
				try {
					Object data = in.readObject();
				}catch(ClassNotFoundException e) {
					
				}catch(SocketException e) {
					close();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
