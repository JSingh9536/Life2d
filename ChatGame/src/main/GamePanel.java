package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Player;
//Defines a class(for visual components)
//and implements Runnable (for game loop functionality)
import tile.Tile;
import tile.TileManager;
public class GamePanel extends JPanel implements Runnable{
/**
	 * 
	 */
	private static final long serialVersionUID = -7948366756305049733L;
/**
	 * 
	 */
	//Determines pixel size and screen size
	final int originalTitleSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTitleSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// Frames per second (FPS) for game updates
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	// KeyHandler object for handling user input
	KeyHandler keyH = new KeyHandler();
	
	// Thread for running the game loop
	Thread gameThread;
	// Player object
	Player player = new Player(this, keyH);
	// Initializes the GamePanel object with appropriate settings
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // Enables double buffering for smoother rendering
		this.addKeyListener(keyH); // Adds the KeyHandler for listening to key presses
		this.setFocusable(true); // Allows the panel to receive keyboard input
	}
	// Starts the game thread
	public void startGameThread() {
		gameThread = new Thread(this); // Creates a thread using this GamePanel object
		gameThread.start(); // Starts the thread
	}
	// Main game loop method (executed by the game thread)
	@Override
	public void run() {
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/FPS; //0.01666 seconds; Calculates time interval for each frame
		double nextDrawTime = System.nanoTime() + drawInterval; // Sets initial time for the next frame
		while(gameThread != null) { // Loops continuously until the game thread is stopped
		update(); // Updates game elements
		repaint();// Repaints the screen to reflect changes
		// Controls frame rate
		try {
			double remainingTime = nextDrawTime - System.nanoTime();
			remainingTime = remainingTime/1000000; // Converts remaining time to milliseconds
			if(remainingTime < 0) {
				remainingTime = 0;
			}
			Thread.sleep((long) remainingTime); // Pauses the thread to maintain desired FPS
			nextDrawTime += drawInterval; // Updates next frame time
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	// Updates game elements (currently only updates the player)
	public void update() {
		player.update();
	
	}
	// Draws game elements on the screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Calls the parent class's paintComponent method
		Graphics2D g2 = (Graphics2D)g; // Casts Graphics object to Graphics2D for advanced features
		tileM.draw(g2); //Draws map tile
		player.draw(g2); // Draws the player on the screen
		g2.dispose(); // Disposes of graphics resources
	}
}
