package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValue();
		getPlayerImage();
	}
	public void setDefaultValue() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
		
	}
	public void getPlayerImage() {
		try {
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


			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
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
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction) {
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
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

	}
}
