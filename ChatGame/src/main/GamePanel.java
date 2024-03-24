package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamePanel extends JPanel implements Runnable{
//Determines pixel size and screen size
	final int originalTitleSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTitleSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	//fps
	int FPS = 60;
	//input
	KeyHandler keyH = new KeyHandler();
	
	//game clock and animation clock
	Thread gameThread;
	Player player = new Player(this,keyH);
	//player position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		double drawInterval = 1000000000/FPS; //0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		while(gameThread != null) {
	
		
		//Updates character location
		update();
		// Draws the screen 
		repaint();
		
		try {
			double remainingTime = nextDrawTime - System.nanoTime();
			remainingTime = remainingTime/1000000;
			if(remainingTime < 0) {
				remainingTime = 0;
			}
			Thread.sleep((long) remainingTime);
			
			nextDrawTime += drawInterval;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	public void update() {
		player.update();
	
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		player.draw(g2);
		g2.dispose();
	}
}
