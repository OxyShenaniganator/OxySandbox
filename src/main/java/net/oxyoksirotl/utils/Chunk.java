package net.oxyoksirotl.utils;

import net.oxyoksirotl.Game;
import net.oxyoksirotl.entity.SpawnedEntity;
import net.oxyoksirotl.entity.TileEntity;

import java.util.HashMap;

public class Chunk {

    // Chunk positions
    private int chunkX;
    private int chunkY;

    // Chunk contents
    private HashMap<Pos, TileEntity> chunkTiles;
    private HashMap<Pos, SpawnedEntity> chunkEntities;

    public Chunk(int xPos, int yPos) {
        this.chunkX = xPos;
        this.chunkY = yPos;

        this.chunkTiles = new HashMap<>();
        this.chunkEntities = new HashMap<>();

    }

    public void insertTile(TileEntity tile, int x, int y) {
        chunkTiles.put(new Pos(x,y), tile);
    }

    public TileEntity getTile(int x, int y) {

        int newX = (int)Math.floor(x/24) * 24;
        int newY = (int)Math.floor(y/24) * 24;

        return chunkTiles.get(new Pos(newX,newY));
    }

    public TileEntity getTile(Pos pos) {

        int newX = (int)Math.floor(pos.getXPos()/24) * 24;
        int newY = (int)Math.floor(pos.getYPos()/24) * 24;
        System.out.println("Getting tiles: " + Game.chunkHandler.toWorldXPos(chunkX, newX) + " " + Game.chunkHandler.toWorldXPos(chunkY, newY));
        return chunkTiles.get(new Pos(newX, newY));
    }

    public void insertEntity(SpawnedEntity spawnedEntity, int x, int y) {

        chunkEntities.put(new Pos(x,y), spawnedEntity);

    }

    public void removeEntity(int x, int y) {
        chunkEntities.remove(new Pos(x,y));
    }
    public void moveEntity(SpawnedEntity entity, int x, int y) {
        chunkEntities.remove(new Pos(entity.getChunkXPos(),entity.getChunkYPos()));
        chunkEntities.put(new Pos(x,y), entity);
    }

    public SpawnedEntity getEntity(int x, int y) {
        return chunkEntities.get(new Pos(x,y));
    }

    public HashMap<Pos, TileEntity> getChunkTiles() {
        return chunkTiles;
    }

    public HashMap<Pos, SpawnedEntity> getChunkEntities() {
        return chunkEntities;
    }


    // Getter
    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chunk chunk = (Chunk) o;

        if (chunkX != chunk.chunkX) return false;
        return chunkY == chunk.chunkY;
    }

    @Override
    public int hashCode() {
        int result = chunkX;
        result = 31 * result + chunkY;
        return result;
    }
}
