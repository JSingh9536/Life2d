package entity;
//Imports the BufferedImage class for handling images
import java.awt.image.BufferedImage;
//Defines a class named Entity that serves as a base class for representing objects in a game

public class Entity {
	public int x, y; // Coordinates for the entity's position
	public int speed;	// Movement speed of the entity
	// BufferedImage objects to store sprite images for different directions and animation frames
	public BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2;
	public String direction;
	// Variables for managing animation frames
	public int spriteCounter = 0;
	public int spriteNum = 1; // Specifies the current animation frame
}
