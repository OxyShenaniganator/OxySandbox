package net.oxyoksirotl.handler;

import net.oxyoksirotl.Game;
import net.oxyoksirotl.GamePanel;
import net.oxyoksirotl.utils.Chunk;
import net.oxyoksirotl.utils.Pos;

import java.util.HashMap;

public class ChunkHandler {

    private GamePanel gamePanel;

    private Chunk[][] worldChunks;
    private HashMap<Pos, Chunk> worldChunksReordered;
    private int tileSize;
    private int maxChunkCol;
    private int maxChunkRow;
    private int maxChunkSize;
    private int seed;

    public ChunkHandler(int maxChunkCol, int maxChunkRow) {
        this.maxChunkCol = maxChunkCol;
        this.maxChunkRow = maxChunkRow;
        this.maxChunkSize = 9;
        this.worldChunks = new Chunk[maxChunkCol][maxChunkRow];
        this.worldChunksReordered = new HashMap<>();
        this.seed = (int)Math.floor(10000 * Math.random());

        this.initChunks();

    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileSize = this.gamePanel.tileSize;
    }

    public int toWorldXPos(int chunkX, int x) {
        try {
            if (x < maxChunkSize * tileSize) {
                return chunkX * maxChunkSize * tileSize + x;
            } else throw new IllegalArgumentException("Position x: " + x + " out of bound!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        // return (chunkNum % 4) * maxChunkSize;
    }

    public int toWorldYPos(int chunkY, int y) {
        try {
            if (y < maxChunkSize * tileSize) {
                return chunkY * maxChunkSize * tileSize + y;
            } else throw new IllegalArgumentException("Position y: " + y + " out of bound!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        // return (int)Math.floor(chunkNum/4) * maxChunkSize + y;
    }

    public Pos[] toChunkPos(int worldX, int worldY) {

        int x = worldX % (maxChunkSize * tileSize);
        int chunkX = (worldX - x) / (maxChunkSize * tileSize);

        int y = worldY % (maxChunkSize * tileSize);
        int chunkY = (worldY - y) / (maxChunkSize * tileSize);

        Pos[] chunkPos = new Pos[2];
        chunkPos[0] = new Pos(chunkX, chunkY);
        chunkPos[1] = new Pos(x,y);

        return chunkPos;

    }

    public void initChunks() {
        for (int i=0; i < maxChunkCol; i++) {
            for (int j=0; j < maxChunkRow; j++) {
                worldChunks[i][j] = new Chunk(i,j);
            }
        }
    }

    public void populateChunks() {

        int chunkPointerX;
        int chunkPointerY;

        int chunkPosX;
        int chunkPosY;

        for(int i=0; i < maxChunkCol * maxChunkSize; i++) {
            for(int j=0; j< maxChunkRow * maxChunkSize; j++) {

                chunkPointerX = (int)Math.floor(i/maxChunkSize);
                chunkPointerY = (int)Math.floor(j/maxChunkSize);

                chunkPosX = i % maxChunkSize;
                chunkPosY = j % maxChunkSize;

                worldChunks[chunkPointerX][chunkPointerY].insertTile(Game.tilesHandler.tileDeterminer(seed, i, j), chunkPosX * tileSize,chunkPosY * tileSize);
//                System.out.println("Generated chunk tile: [" + chunkPosX + ", " +chunkPosY + "] at ["
//                + toWorldXPos(chunkPointerX, chunkPosX) + " (" + chunkPointerX +"), "
//                        + toWorldYPos(chunkPointerY, chunkPosY) + " (" + chunkPointerY +")]");
            }
        }
    }

    public void reorderChunks() {
        for (int i = 0; i < maxChunkCol; i++) {
            for (int j = 0; j < maxChunkRow; j++) {
                worldChunksReordered.put(new Pos(i - (int)Math.floor(maxChunkCol/2),j - (int)Math.floor(maxChunkRow/2)),worldChunks[i][j]);
            }
        }
    }

    public Chunk getChunk(int x, int y) {
        return worldChunks[x][y];
    }

    public Chunk getChunk(Pos pos) {
        return worldChunks[pos.getXPos()][pos.getXPos()];
    }

    public int getMaxChunkSize() {
        return maxChunkSize;
    }

    public int getMaxChunkCol() {
        return maxChunkCol;
    }

    public int getMaxChunkRow() {
        return maxChunkRow;
    }
}
