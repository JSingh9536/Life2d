package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    private GamePanel gp;
    public Map<Integer, Tile> tile; // Map for faster tile access
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new HashMap<Integer, Tile>(); // Use HashMap for tile storage
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            BufferedImage grass = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass.png"));
            BufferedImage brick = ImageIO.read(getClass().getResourceAsStream("/tiles/Brick.png"));
            BufferedImage cobblestone = ImageIO.read(getClass().getResourceAsStream("/tiles/Cobblestone.png"));
            BufferedImage tree = ImageIO.read(getClass().getResourceAsStream("/tiles/Tree.png"));
            BufferedImage bush = ImageIO.read(getClass().getResourceAsStream("/tiles/Bush.png"));



            // Add tiles to the map with corresponding IDs and collision settings
            tile.put(0, new Tile(grass, false)); // Grass has no collision
            tile.put(1, new Tile(brick, true));   // Brick has collision
            tile.put(2, new Tile(cobblestone, false)); // Cobblestone has no collision 
            tile.put(3, new Tile(tree, true));    // Tree has collision
            tile.put(4, new Tile(bush, false));  // Bush has no collision

           
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/map.txt");
           if (is == null) {
               throw new FileNotFoundException("map.txt not found");
              }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            String line;


			// Read lines until end of file (line == null)
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");

                // Ensure the line has enough data for the screen width
                if (numbers.length < gp.maxWorldCol) {
                    // Handle short lines (optional: fill with default tile or log error)
                    System.err.println("Warning: Line " + (row + 1) + " in map.txt is shorter than screen width.");
                }

                // Process each number (tile ID) in the line
                for (col = 0; col < numbers.length && col < gp.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
                row++; // Increment row after processing each line
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            Tile t = tile.get(tileNum); // Get tile object from map
            //camera determines screen position and where tiles are drawn to keep player in the center
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (t != null) {
               // System.out.println("Drawing tile: " + tileNum); // Print tile ID for debugging
                g2.drawImage(t.getImage(), screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
            	worldCol = 0;
            	worldRow++;
            }
        }
    }

}
