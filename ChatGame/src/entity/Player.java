package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
//Defines a class named Player that inherits properties and methods from a class named Entity
public class Player extends Entity implements Runnable{
	GamePanel gp;
	KeyHandler keyH;
	
	//Vars for Network-Connection
	private Socket client;
	private int playerID;
	public ArrayList<ClientToServer> otherPlayerInfo;

	//camera
	public final int screenX;
	public final int screenY;
	// Initializes a Player object with references to a GamePanel and KeyHandler	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);  //returns center of the screen
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		colider = new Rectangle(10,16,32,32);
	
		
		setDefaultValue(); // Sets default values for player attributes
		getPlayerImage(); // Loads player sprites
		run();
	}
	// Sets initial values for player attributes
	public void setDefaultValue() {
		worldX = 100; // Initial x-coordinate
		worldY = 100; // Initial y-coordinate
		speed = 4; // Player's movement speed
		direction = "down"; // Initial facing direction
		
	}
	// Loads player sprite images from files
	public void getPlayerImage() {
		try {
	        // Loads various sprite images for different directions and animation frames
			up0 = ImageIO.read(getClass().getResourceAsStream("/player/usprite_0.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/usprite_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/usprite_2.png"));
			down0 = ImageIO.read(getClass().getResourceAsStream("/player/dsprite_0.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/dsprite_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/dsprite_2.png"));
			left0 = ImageIO.read(getClass().getResourceAsStream("/player/lsprite_0.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/lsprite_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/lsprite_2.png"));
			right0 = ImageIO.read(getClass().getResourceAsStream("/player/rsprite_0.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/rsprite_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/rsprite_2.png"));


			
		}catch(IOException e) {        // Prints error message if image loading fails
			e.printStackTrace();
		}
	}
	// Updates the player's position and animation based on key presses

	public void update() {
	    boolean isMoving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;
	    
	    if (isMoving) {
	        if (keyH.upPressed) {
	            direction = "up";
	        } else if (keyH.downPressed) {
	            direction = "down";
	        } else if (keyH.leftPressed) {
	            direction = "left";
	        } else if (keyH.rightPressed) {
	            direction = "right";
	        }

	        // Collision check
	        collisionOn = false;
	        gp.CChecker.checkTile(this);

	        // Update position only if there's no collision
	        if (!collisionOn) {
	            switch (direction) {
	                case "up":
	                    if (worldY - speed >= 0) { // Ensure not moving out of bounds
	                        worldY -= speed;
	                    }
	                    break;
	                case "down":
	                    if (worldY + speed + colider.height <= gp.maxWorldRow * gp.tileSize) { // Ensure not moving out of bounds
	                        worldY += speed;
	                    }
	                    break;
	                case "left":
	                    if (worldX - speed >= 0) { // Ensure not moving out of bounds
	                        worldX -= speed;
	                    }
	                    break;
	                case "right":
	                    if (worldX + speed + colider.width <= gp.maxWorldCol * gp.tileSize) { // Ensure not moving out of bounds
	                        worldX += speed;
	                    }
	                    break;
	            }
	        }
	        
	        // Updates animation frame
	        spriteCounter++;
	        if (spriteCounter > 12) {
	            if (spriteNum == 1) {
	                spriteNum = 2;
	            } else if (spriteNum == 2) {
	                spriteNum = 1;
	            }
	            spriteCounter = 0;
	        }
	    } else {
	        // Reset spriteCounter when not moving to prevent animation
	        spriteCounter = 0;
	    }
	}	// Draws the player's sprite image on the screen
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction) {     // Selects the appropriate sprite image based on direction and animation frame
		case "up":
			if(spriteNum == 0) {
			image = up0;
			}
			if(spriteNum == 1) {
				image = up1;
				}
			if(spriteNum == 2) {
				image = up2;
				}
			break;
		case "down":
			if(spriteNum == 0) {
			image = down0;
			}
			if(spriteNum == 1) {
				image = down1;
				}
			if(spriteNum == 2) {
				image = down2;
				}
			break;
		case "right":
			if(spriteNum == 0) {
			image = right0;
			}
			if(spriteNum == 1) {
				image = right1;
				}
			if(spriteNum == 2) {
				image = right2;
				}
			break;
		case "left":
			if(spriteNum == 0) {
			image = left0;
			}
			if(spriteNum == 1) {
				image = left1;
				}
			if(spriteNum == 2) {
				image = left2;
				}
			break;
			
		}
	    // Draws the selected image at the player's current position
	    // using tileSize as a guide for dimensions

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

	
		
		//Luke - I'm gonna make it draw the other player's here as well
		//we can work on those player animations later.
		if(otherPlayerInfo != null) { //if other players are connected
			for(ClientToServer i : otherPlayerInfo) {
				g2.drawImage(down0, i.getX(), i.getY(), gp.tileSize, gp.tileSize, null); //draw other players based on their x/y
			}
		}
	}
	
	public void run() {
		connectToServerTCP("localhost");
		//connectToServerUDP();
	}
	//TCP Version:
	private void connectToServerTCP(String ip) { //used to have 'name' arg; might implement later
		try {
				client = new Socket(ip, 5252);
				DataInputStream in = new DataInputStream(client.getInputStream());
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				ReadFromServerHandler rfs = new ReadFromServerHandler(in);
				WriteToServerHandler wts = new WriteToServerHandler(out);
				Thread wtsThread = new Thread(wts);
				Thread rfsThread = new Thread(rfs);
				wtsThread.start();
				rfsThread.start();
			}catch (IOException ioe) {
				//shutdown
			}
	}
	/*UDP Version
	private void connectToServerUDP() {//used to have arg InetAddress serverIP
		playerID = (int)(Math.random()* 1000) + 111;
		ReadFromServerHandlerUDP rfsU = new ReadFromServerHandlerUDP(); //had serverIP arg
		WriteToServerHandlerUDP wtsU = new WriteToServerHandlerUDP(); //had serverIP arg
		Thread wtsThreadUDP = new Thread(wtsU);
		Thread rfsThreadUDP = new Thread(rfsU);
		wtsThreadUDP.start();
		rfsThreadUDP.start();
	} 
	private class ReadFromServerHandlerUDP implements Runnable{
		private DatagramSocket readSocket;
		private InetAddress ipAddress;
		public ClientToServer readPlayerInfo;
		public DatagramPacket readPacket;
		public byte[] readBuffer;
		public ByteArrayInputStream byteInput;
		public ObjectInputStream in;
		public boolean inList = false;
		public int bufLen;
		
		public ReadFromServerHandlerUDP() { //had InetAddress ip arg
			try {
				readSocket = new DatagramSocket(6262);
				//ipAddress = ip;
				ipAddress = InetAddress.getByName("25.5.96.33");
				readBuffer = new byte[1400];
			} catch(Exception e) {
				//something
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
					//System.out.println("PID: " + readPlayerInfo.getPID() + ", X: " + readPlayerInfo.getX() + ", Y: " + readPlayerInfo.getY());
					//THE WRITE BUFFER IS BROKEN BECAUSE BOTH PLAYERS ONLY RECEIVE 1 PID
					assignInfo();
					Thread.sleep(25);
				} catch(Exception e) {
					//something
				}
			}
		}
		public void assignInfo() {
			inList = false;
			if(readPlayerInfo.getPID() != playerID) {
				if(otherPlayerInfo == null) {//if list is empty and we read in data then make a new player in list
					otherPlayerInfo.add(new ClientToServer(readPlayerInfo.getPID(), readPlayerInfo.getX(), readPlayerInfo.getY())); //add new player to info list
					System.out.println("Player: Just added the new player to the list!");
				}else {
					for(ClientToServer i : otherPlayerInfo) {
						if(i.getPID() == readPlayerInfo.getPID()) {
							i.setX(readPlayerInfo.getX());
							i.setY(readPlayerInfo.getY());
							inList = true;
						}
					}
					if(inList == false) {//if player is not in the list, then add them to it
						otherPlayerInfo.add(new ClientToServer(readPlayerInfo.getPID(), readPlayerInfo.getX(), readPlayerInfo.getY()));
						System.out.println("Player: Just added the new player to the list!");
					}
				}
			}
		}
	}
	private class WriteToServerHandlerUDP implements Runnable{
		private DatagramSocket writeSocket;
		private InetAddress address;
		private ClientToServer infoObject;
		public DatagramPacket sPacket;
		public ObjectOutputStream out;
		public ByteArrayOutputStream buffer;
			
		public WriteToServerHandlerUDP(){ //had InetAddress ip arg
			try {
				writeSocket = new DatagramSocket();
				//address = ip;
				address = InetAddress.getByName("25.5.96.33");
				infoObject = new ClientToServer(playerID, getX(), getY());
				buffer = new ByteArrayOutputStream();
				out = new ObjectOutputStream(buffer);
				
			} catch(Exception e) {
				//nothing
			}
			
		}
		public void run(){
			while(true){
				try {
					infoObject.setX(getX());
					infoObject.setY(getY());
					System.out.println("Sending: PID: " + infoObject.getPID() + ", X: " + infoObject.getX() + ", Y: " + infoObject.getY());
					//WE SEND CORRECT INFO BUT RECEIVED INFO IS STUCK AT X:100 Y:100
					out.writeObject(infoObject);
					//System.out.println("Player Sending " +buffer.toByteArray().length + " bytes");
					sPacket = new DatagramPacket(buffer.toByteArray(), buffer.size(), address, 5252);
					writeSocket.send(sPacket);
					//System.out.println("Just sent a packet to:" + address);
					out.flush();
					Thread.sleep(25);
				} catch(Exception e) {
					//something
				}
				
			}
			//out.close(); close these two somewhere
			//buffer.close();
		}
	}*/
	//TCP VERSIONS:
	private class ReadFromServerHandler implements Runnable{
		public DataInputStream dataIn;
		public boolean inList = false;
		public int otherPID;
		public int storedX;
		public int storedY;
		
		public ReadFromServerHandler(DataInputStream in) {
			dataIn = in;
			otherPlayerInfo = new ArrayList<>();
		}
		
		public void run() {
			while(true) {
				try {
					inList = false;
					otherPID = dataIn.readInt();
					storedX = dataIn.readInt();
					storedY = dataIn.readInt();
					if(otherPID != -100) {//this is an error response so that the 1st person to join does not deadlock with server
						if(otherPlayerInfo == null) {//if list is empty and we read in data then make a new player in list
							otherPlayerInfo.add(new ClientToServer(otherPID, storedX, storedY)); //add new player to info list
						}else {
							for(ClientToServer i : otherPlayerInfo) {
								if(i.getPID() == otherPID) {
									System.out.println("Other Player's X:" + storedX + ", Y:" + storedY);
									i.setX(storedX);
									i.setY(storedY);
									inList = true;
								}
							}
							if(inList == false) {//if player is not in the list, then add them to it
								otherPlayerInfo.add(new ClientToServer(otherPID, storedX, storedY));
							}
						}
					}
					Thread.sleep(25);
				}catch(Exception e) {
					//something
				}
			}
		}
	}
	private class WriteToServerHandler implements Runnable{
		DataOutputStream dataOut;
		private int checkX;
		private int checkY;
		
		public WriteToServerHandler(DataOutputStream out) {
			dataOut = out;
		}
		
		public void run() {
			while(true) {//below: only send packets if we move
				try {
					if((checkX != getX()) || (checkY != getY())) {
						checkX = getX();
						checkY = getY();
						dataOut.writeInt(getX());
						dataOut.writeInt(getY());
						System.out.println("Sent a packet!");
						dataOut.flush();
						Thread.sleep(25);
					}
				}catch(Exception e){
					//something
				}
			}
		}
	}
}