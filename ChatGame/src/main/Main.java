package main;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Main {

	public static void main(String[] args) {
		
	    // Creates a JFrame object for the game window
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the program when the window is closed

		window.setResizable(false); // Prevents the window from being resized
		window.setTitle("Life 2d");  // Sets the window title
		
	    // Creates a GamePanel object, which handles game logic and visuals
		GamePanel GamePanel = new GamePanel();
		window.add(GamePanel); // Adds the GamePanel to the window
		window.pack(); // Sizes the window to fit its contents
		window.setLocationRelativeTo(null); // Centers the window on the screen
		window.setVisible(true); // Makes the window visible
	
		MainMenu mainMenu = new MainMenu(GamePanel);
		TextFrame TextFrame = new TextFrame(GamePanel);
		TextFrame.draw();
		
		 // Starts the game thread, initiating the game loop
		GamePanel.startGameThread();

	}

}

