package com.game.server;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

public class Client {
	
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private boolean done;
	
	public void run(){
		try {
			client = new Socket("localhost", 7272);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			InputHandler inHandler = new InputHandler();
			Thread t = new Thread(inHandler);
			t.start();
			
			String inMessage;
			while((inMessage = in.readLine()) != null) {
				System.out.println(inMessage);
			}
		}catch (IOException ioe) {
			//shutdown
		}
	}
	
	public void shutdown() {
		done = true;
		try{
			in.close();
			out.close();
			if(!client.isClosed()) {
				client.close();
			}
		} catch(IOException ioe) {
			shutdown();
		}
	}
	
	
	private class InputHandler implements Runnable{

		@Override
		public void run() {
			try {
				BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
				while(!done) {
					String message = inReader.readLine();
					if(message.equals("/quit")) {
						out.println(message);
						inReader.close();
						shutdown();
					} else {
						out.println(message);
					}
				}
			} catch(IOException ioe) {
				shutdown();
			}
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.run();
	}
}

