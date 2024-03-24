package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Life 2d");
		
	
		GamePanel GamePanel = new GamePanel();
		window.add(GamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		GamePanel.startGameThread();

	}

}
