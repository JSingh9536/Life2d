package tile;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    public boolean collision;

    public Tile() {
    }

    public Tile(BufferedImage image) {
        this.image = image;
        this.collision = false; // Default collision is false
    }

    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public BufferedImage getImage() {
        return image;
    }
}
