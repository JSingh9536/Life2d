package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

	// Initializes a Player object with references to a GamePanel and KeyHandler	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValue(); // Sets default values for player attributes
		getPlayerImage(); // Loads player sprites
		run();
	}
	// Sets initial values for player attributes
	public void setDefaultValue() {
		x = 100; // Initial x-coordinate
		y = 100; // Initial y-coordinate
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
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
		if(keyH.upPressed == true) {
			direction = "up";
			y -= speed;
			
		}
		else if(keyH.downPressed == true) {
			direction = "down";
			y += speed;
		}
		else if(keyH.leftPressed == true) {
			direction = "left";
			x -= speed;
		}
		else if(keyH.rightPressed == true) {
			direction = "right";
			x += speed;
		}
        // Updates animation frame

		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1 ) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
	
	}
	}
	// Draws the player's sprite image on the screen
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
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
		
		//Luke - I'm gonna make it draw the other player's here as well
		//we can work on those player animations later.
		if(otherPlayerInfo != null) { //if other players are connected
			for(ClientToServer i : otherPlayerInfo) {
				g2.drawImage(down0, i.getX(), i.getY(), gp.tileSize, gp.tileSize, null); //draw other players based on their x/y
			}
		}
	}
	
	public void run() {
		connectToServer("localhost");
	}
	
	//TODO: MAKE THE serverHandler TWO THREADS FOR READING AND WRITING
	//MAKE A readFromServer CLASS AND RUN THAT THREAD IN connectToServer
	//THEN MAKE A writeToServer CLASS AND RUN THAT THREAD IN connectToServer as well
	
	private void connectToServer(String ip) { //used to have 'name' arg; might implement later
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
		
		public WriteToServerHandler(DataOutputStream out) {
			dataOut = out;
		}
		
		public void run() {
			while(true) {
				try {
					System.out.println("Sending X:" + getX() + " and Y:" + getY());
					dataOut.writeInt(getX());
					dataOut.writeInt(getY());
					dataOut.flush();
					Thread.sleep(25);
				}catch(Exception e){
					//you can catch my balls
				}
			}
		}
	}
}
