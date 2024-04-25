package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Scanner;


public class MainMenu {
	
	public int commandNum =0;

	GamePanel gp; 
	Font arial_40;
	Graphics2D g2;
	Font maruMonica,purisaB;
	
		public MainMenu(GamePanel gp) {
			this.gp =gp;
			arial_40 = new Font("Arial",Font.PLAIN, 40);
		}
		public void draw(Graphics2D g2) {
			this.g2 =g2;
			
			//System.out.println(gp.scale);
			g2.setFont(maruMonica);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setColor(Color.white);
			
			drawTitleScreen();
		
		}
		
		public void drawTitleScreen(){
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
			String text = "LIFE2D";
		
			g2.setColor(new Color(70,120,80));
			g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
			 
			 //Name of Title
			 g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
			 
			 //Position of title 
			 int x= getXforCenteredText(text);
			 int y = gp.tileSize*3;
		
			 
			 //Shadow for title
			 g2.setColor(Color.black);
			 g2.drawString(text, x+5, y+5);
			 
			 //MAIN COLOR
			 g2.setColor(Color.white);
			 g2.drawString(text,x,y);
			 

			 //MENU
			 g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			 
			 // button / options available
		/*	 text = "JOIN";
			 x=getXforCenteredText(text);
			 y+= gp.tileSize*4;
			 g2.drawString(text,x,y);
			 if(commandNum ==0) {
				 g2.drawString(">", x-gp.tileSize, y);
			 }
			 
			 text = "QUIT";
			 x=getXforCenteredText(text);
			 y+= gp.tileSize;
			 g2.drawString(text,x,y);
			 if(commandNum ==1) {
				 g2.drawString(">", x-gp.tileSize, y);
				
			 }*/
		}

		public int getXforCenteredText(String text) {
		
			int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
			int x = gp.screenWidth/2 - length/2;
			return x;
		}
		

}

