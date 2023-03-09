package net.oxyoksirotl;

import net.oxyoksirotl.entity.TileEntity;
import net.oxyoksirotl.input.KeyboardInputs;
import net.oxyoksirotl.entity.SpawnedEntity;
import net.oxyoksirotl.utils.Chunk;
import net.oxyoksirotl.utils.Pos;
import net.oxyoksirotl.utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {

    public final int tileSize = 24;
    public int scalingSize = 2;
    public int resizedTileSize = tileSize * scalingSize;

    final int maxCol = 16;
    final int maxRow = 12;

    private int screenWidth = resizedTileSize * maxCol;
    private int screenHeight = resizedTileSize * maxRow;

    public final int screenX;
    public final int screenY;

    private int vecXP = 0;
    private int vecXN = 0;
    private int vecYP = 0;
    private int vecYN = 0;

    private Vector2D playerVelocity = new Vector2D(vecXP-vecXN, vecYP-vecYN);

    private int playerUpdateTick = 0;
    private int maxPlayerUpdateTick = 10;

    KeyboardInputs inputs = new KeyboardInputs();


    public GamePanel() {

        addKeyListener(inputs);

        Dimension screenSize = new Dimension(screenWidth, screenHeight);

        this.setPreferredSize(screenSize);
        this.setDoubleBuffered(true);

        this.screenX = screenWidth/2 - (tileSize/2);
        this.screenY = screenHeight/2 - (tileSize/2);

    }


    public void renderChunks(int chunkX, int chunkY, Graphics2D graphics2D, HashMap<Pos, TileEntity> chunkTiles, HashMap<Pos, SpawnedEntity> chunkEntities) {

        int worldXPos;
        int worldYPos;

        int playerXPos = Game.entitiesHandler.playerEntity().getWorldXPos();
        int playerYPos = Game.entitiesHandler.playerEntity().getWorldYPos();
        int scalingTileSize = tileSize * scalingSize;

        for (Map.Entry<Pos, TileEntity> entry: chunkTiles.entrySet()) {
            worldXPos = Game.chunkHandler.toWorldXPos(chunkX, entry.getKey().getXPos());
            worldYPos = Game.chunkHandler.toWorldYPos(chunkY, entry.getKey().getYPos());
            TileEntity tile = entry.getValue();

            graphics2D.drawImage( tile.getTileImage(),
                    worldXPos - playerXPos + screenX, worldYPos - playerYPos + screenY,
                    scalingTileSize, scalingTileSize, null
            );
        }

        // Still need to use global rendering for entities. Chunk rendering might cause stuttering.

//        for (Map.Entry<Pos, SpawnedEntity> entry: chunkEntities.entrySet()) {
//            worldXPos = Game.chunkHandler.toWorldXPos(chunkX, entry.getKey().getXPos());
//            worldYPos = Game.chunkHandler.toWorldYPos(chunkY, entry.getKey().getYPos());
//            SpawnedEntity entity = entry.getValue();
//
//            if(entity.getEntityType() != "player") {
//
//                graphics2D.drawImage(entity.getEntitySprite(),
//                        worldXPos - playerXPos + screenX, worldYPos - playerYPos + screenY,
//                        tileSize * scalingSize, tileSize * scalingSize, null
//                );
//            } else {
//                graphics2D.drawImage(
//                entity.getEntitySprite(), screenX - entity.getEntityWidth(), screenY - entity.getEntityHeight(),
//                        entity.getEntityWidth() * scalingSize,
//                        entity.getEntityHeight() * scalingSize, null);
//
//            }
//        }

    }

    public static void gameUpdate() {
        if (Game.entitiesHandler.spawnedEntitiesList.isEmpty()) {
            Game.entitiesHandler.spawnEntity(Game.entitiesHandler.getEntityType("houndoom"), 100, 100);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        int scaledTileSize = tileSize*scalingSize;
        int tileX;
        int tileY;
//
//        int halfTileX;
//        int halfTileY;
//
//        for (int i = 0; i <= Game.chunkHandler.getMaxChunkSize() -1; i++) {
//            for (int j = 0; j <= Game.chunkHandler.getMaxChunkSize() -1; j++) {
//
//                tileX = i * scaledTileSize - Game.entitiesHandler.playerEntity().getWorldXPos();
//                tileY = j * scaledTileSize - Game.entitiesHandler.playerEntity().getWorldYPos();
//
//                halfTileX = i * scaledTileSize - scaledTileSize  / 2 - Game.entitiesHandler.playerEntity().getWorldXPos();
//                halfTileY = j * scaledTileSize - scaledTileSize / 2 - Game.entitiesHandler.playerEntity().getWorldYPos();
//
//                    g2.drawImage(Game.chunkHandler.getChunk(5,5).getTile(i,j).tileImage,
//                            halfTileX, halfTileY,
//                            scaledTileSize, scaledTileSize, null);
//
//            }
//        }

        int playerChunkX = Game.entitiesHandler.playerEntity().getLocatedChunkX();
        int playerChunkY = Game.entitiesHandler.playerEntity().getLocatedChunkY();

        for (int i = playerChunkX -1; i <= playerChunkX + 1; i++) {
            for (int j = playerChunkY -1; j <= playerChunkY +1; j++) {

                if (i<0 || i >= Game.chunkHandler.getMaxChunkCol()
                || j<0 || j >= Game.chunkHandler.getMaxChunkRow()) continue;

                HashMap<Pos, TileEntity>  processChunkTiles = Game.chunkHandler.getChunk(i,j).getChunkTiles();
                HashMap<Pos, SpawnedEntity> processChunkEntities = Game.chunkHandler.getChunk(i,j).getChunkEntities();

                renderChunks(i, j, g2, processChunkTiles, processChunkEntities);

            }
        }

        for (SpawnedEntity entity: Game.entitiesHandler.spawnedEntitiesList) {

            if (entity.getEntityType() != "player") {
                g2.drawImage(
                        entity.getEntitySprite(),
                        entity.getWorldXPos() - entity.getEntityWidth() - Game.entitiesHandler.playerEntity().getWorldXPos() + screenX,
                        entity.getWorldYPos() - entity.getEntityHeight() - Game.entitiesHandler.playerEntity().getWorldYPos() + screenY,
                        entity.getEntityWidth() * scalingSize,
                        entity.getEntityHeight() * scalingSize, null);

            } else {

                g2.drawImage(
                        entity.getEntitySprite(), screenX - entity.getEntityWidth(), screenY - entity.getEntityHeight(),
                        entity.getEntityWidth() * scalingSize,
                        entity.getEntityHeight() * scalingSize, null);

            }
        }
    }
}
