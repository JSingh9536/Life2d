package entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer implements Runnable{
	private int port = 5252;
	
	private ServerSocket server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean listening;
	private ExecutorService threadPool;
	
	public ArrayList<ClientHandler> connections; //made public so ClientHandler can see all connections and players
	public ArrayList<ClientToServer> clientInfo; //list for all clients to store x's and y's
	
	public GameServer() {
		connections = new ArrayList<>();
		clientInfo = new ArrayList<>();
		listening = true;//TODO:ACTUALLY MAKE THE SERVER SHUTDOWN AND FIND A GOOD WAY TO DO SO
	}
	
	public void run() {
		tcpServer();
		//udpServer();
	}
	
	public void udpServer() {
		ReadFromClientsHandlerUDP rfcsU = new ReadFromClientsHandlerUDP();
		WriteToClientsHandlerUDP wtcsU = new WriteToClientsHandlerUDP();
		Thread wtcsThreadUDP = new Thread(wtcsU);
		Thread rfcsThreadUDP = new Thread(rfcsU);
		wtcsThreadUDP.start();
		rfcsThreadUDP.start();
	}
	
	public void tcpServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("Server is now up!");
			threadPool = Executors.newCachedThreadPool();
			while(listening) {//while the server is still listening for connections
				Socket client = server.accept(); //new client accepts and starts server
				System.out.println("Player just joined!");
				DataInputStream in = new DataInputStream(client.getInputStream());
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				ClientHandler handler = new ClientHandler(client, in, out);
				connections.add(handler);//client is added to list of clientHandlers
				
				threadPool.execute(handler);//handler is added to thread Pool
			}
		} catch(IOException ioe) {
			//TODO
		}
	}
	private class ReadFromClientsHandlerUDP implements Runnable{
		private DatagramSocket readSocket;
		public ClientToServer readPlayerInfo;
		public DatagramPacket readPacket;
		public byte[] readBuffer;
		public ByteArrayInputStream byteInput;
		public ObjectInputStream in;
		public boolean inList = false;
		
		public ReadFromClientsHandlerUDP() {
			try {
				readSocket = new DatagramSocket(5252);
				System.out.println("Server: Read Thread is Up!");
				readBuffer = new byte[1400];
				byteInput = new ByteArrayInputStream(readBuffer);
			}catch(Exception e) {
				//nothing
			}
		}
		public void run() {
			while(true) {
				try {
					readPacket = new DatagramPacket(readBuffer, readBuffer.length);//hopefully pulls in the correct length
					readSocket.receive(readPacket);
					byteInput = new ByteArrayInputStream(readBuffer);
					in = new ObjectInputStream(byteInput);
					readPlayerInfo = (ClientToServer)in.readObject();
					System.out.println("Reading: PID_" + readPlayerInfo.getPID() + ", X_" + readPlayerInfo.getX() + ", Y_" + readPlayerInfo.getY());
					if(readPlayerInfo.getIP() == null) {
						readPlayerInfo.setIP(readPacket.getAddress());
					}
					assignInfo();
					Thread.sleep(25);
				}catch(Exception e) {
					//something
				}
			}
		}
		public void assignInfo() {
			inList = false;
			if(clientInfo == null) {//if list is empty and we read in data then make a new player in list
				clientInfo.add(readPlayerInfo);
				System.out.println("Server: Added new player!");
			}else {
				for(ClientToServer i : clientInfo) {
					if(i.getPID() == readPlayerInfo.getPID()) {
						i.setX(readPlayerInfo.getX());
						i.setY(readPlayerInfo.getY());
						inList = true;
					}
				}
				if(inList == false) {//if player is not in the list, then add them to it
					clientInfo.add(readPlayerInfo);
					System.out.println("Server: Added new player!");
				}
			}
		}
	}
	private class WriteToClientsHandlerUDP implements Runnable{
		private DatagramSocket writeSocket;
		public DatagramPacket sPacket;
		public ObjectOutputStream out;
		public ByteArrayOutputStream buffer;
		
		public WriteToClientsHandlerUDP() {
			try {
				writeSocket = new DatagramSocket();
				System.out.println("Server: Write Thread is Up!");
				buffer = new ByteArrayOutputStream();
				out = new ObjectOutputStream(buffer);
			}catch(Exception e) {
				//nothing
			}
		}
		public void run() {
			while(true) {
				try {
					for(ClientToServer i : clientInfo) { //for each client in Server
						for(ClientToServer j : clientInfo) {//write all client info to the specified client
							//System.out.println("Sending to PID_" + i.getPID() + ": PID_" + j.getPID() + ", X_" + j.getX() + ", Y_" + j.getY());
							out.writeObject(j);
							sPacket = new DatagramPacket(buffer.toByteArray(), buffer.size(), i.getIP(), 6262);
							writeSocket.send(sPacket);
							buffer.reset();
						}
					}
					Thread.sleep(25);
				}catch(Exception e) {
					//nothing
				}
			}
		}
	}
	
	//TCP VERSIONS:
	private class ClientHandler implements Runnable{
		
		private Socket clientSocket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		public int ourPID;
		
		public ClientHandler(Socket client, DataInputStream in, DataOutputStream out) {
			ourPID = (int)(Math.random()* 1000) + 111;//creates a PID and makes a new object for player info
			ClientToServer clientInList = new ClientToServer(ourPID, 0, 0);
			clientInfo.add(clientInList);//adds this new 'client' and info into the server clientInfo list
			System.out.println("Player added to list with PID: " + ourPID);
			this.clientSocket = client;
			dataIn = in;
			dataOut = out;
		}
		
		@Override
		public void run() {
			ReadFromClientHandler rtc = new ReadFromClientHandler(ourPID, dataIn);
			WriteToClientHandler wtc = new WriteToClientHandler(ourPID, dataOut);
			Thread rtcThread = new Thread(rtc);
			Thread wtcThread = new Thread(wtc);
			rtcThread.start();
			wtcThread.start();
		}
		
		public void shutdown(){
			try {
				dataIn.close();
				dataOut.close();//close both streams from client
				if(!clientSocket.isClosed()) {
					clientSocket.close();
				}
			} catch(IOException ioe) {
				//ignore because we need to handle this request
			}
		}
	}
	
	private class ReadFromClientHandler implements Runnable{
		private DataInputStream clientIn;
		private int clientPID;
		public int testX;
		public int testY;
		
		public ReadFromClientHandler(int PID, DataInputStream in) {
			clientPID = PID;
			clientIn = in;
		}
		
		public void run() {
			while(true) {
				try {
					for(ClientToServer i : clientInfo) {
						if(i.getPID() == clientPID) { //get the object pid that is the same as this client
							testX = clientIn.readInt();
							testY = clientIn.readInt();
							i.setX(testX); //set that store client's x as the new read in x & y
							i.setY(testY);
						}
					}
					Thread.sleep(25);
				}catch(Exception e) {
					//something
				}
			}
		}
	}
	
	private class WriteToClientHandler implements Runnable{
		private DataOutputStream serverOut;
		private int clientPID;
		
		public WriteToClientHandler(int PID, DataOutputStream out) {
			clientPID = PID;
			serverOut = out;
		}
		
		public void run() {
			while(true) {
				try {
					for(ClientToServer i : clientInfo) {
						if(i.getPID() != clientPID) {//we want to send all client info except this client's info
							try {
								serverOut.writeInt(i.getPID());//send each client PID, X, and Y to client reading
								serverOut.writeInt(i.getX());//client will get the PID and assign that x and y to
								serverOut.writeInt(i.getY());//the other player's x and y that has that same PID on its end
							}catch(IOException ioe) {
								//something
							}
						} else {//still send because otherwise problems for 1st person to join, they can throw it away
							try {
								serverOut.writeInt(-100);
								serverOut.writeInt(-100);
								serverOut.writeInt(-100);
							} catch(Exception e) {
								//nothing
							}
						}
					}
					Thread.sleep(25);
				}catch(Exception e) {
					//nothing
				}
			}
		}
	}
	
	public static void main(String args[]) {
		GameServer gameServer = new GameServer();
		gameServer.run();
	}
}
