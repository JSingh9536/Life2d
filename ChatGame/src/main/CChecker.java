package main;

import entity.Entity;
import tile.Tile;

public class CChecker {
    GamePanel gp;

    public CChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getX() + entity.colider.x;
        int entityRightWorldX = entity.getX() + entity.colider.x + entity.colider.width;
        int entityTopWorldY = entity.getY() + entity.colider.y;
        int entityBottomWorldY = entity.getY() + entity.colider.y + entity.colider.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Adjust position based on direction
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                break;
        }

        // Calculate keys for both sides based on adjusted row/col for each direction
        int leftKey = calculateKey(entityLeftCol, entityTopRow);
        int rightKey = calculateKey(entityRightCol, entityTopRow);
        int bottomLeftKey = calculateKey(entityLeftCol, entityBottomRow);
        int bottomRightKey = calculateKey(entityRightCol, entityBottomRow);

        // Check for collisions on all four sides based on direction
        boolean leftCollision = leftTileCollision(leftKey) || leftTileCollision(bottomLeftKey);
        boolean rightCollision = rightTileCollision(rightKey) || rightTileCollision(bottomRightKey);
        boolean topCollision = topTileCollision(leftKey) || topTileCollision(rightKey);
        boolean bottomCollision = bottomTileCollision(bottomLeftKey) || bottomTileCollision(bottomRightKey);

        // Set collision flag based on collision with solid tiles
        if (entity.direction.equals("up")) {
            entity.collisionOn = topCollision;
        } else if (entity.direction.equals("down")) {
            entity.collisionOn = bottomCollision;
        } else if (entity.direction.equals("left")) {
            entity.collisionOn = leftCollision;
        } else if (entity.direction.equals("right")) {
            entity.collisionOn = rightCollision;
        }
    }


    private int calculateKey(int col, int row) {
        return col + (row * gp.maxWorldCol);
    }

    private boolean leftTileCollision(int key) {
        Tile leftTile = gp.tileM.tile.get(key);
        return leftTile != null && leftTile.collision;
    }

    private boolean rightTileCollision(int key) {
        Tile rightTile = gp.tileM.tile.get(key);
        return rightTile != null && rightTile.collision;
    }

    private boolean topTileCollision(int key) {
        Tile topTile = gp.tileM.tile.get(key);
        return topTile != null && topTile.collision;
    }

    private boolean bottomTileCollision(int key) {
        Tile bottomTile = gp.tileM.tile.get(key);
        return bottomTile != null && bottomTile.collision;
    }
}
