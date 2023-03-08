package net.oxyoksirotl;

import net.oxyoksirotl.input.KeyboardInputs;
import net.oxyoksirotl.entity.SpawnedEntity;
import net.oxyoksirotl.utils.Vector2D;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final int tileSize = 24;
    private int scalingSize = 2;
    private int resizedTileSize = tileSize * scalingSize;

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

    public static void gameUpdate() {
        if (Game.entitiesHandler.spawnedEntitiesList.isEmpty()) {
            Game.entitiesHandler.spawnEntity(Game.entitiesHandler.getEntityType("arcanine"), 0, 0, "player");
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

        int halfTileX;
        int halfTileY;

        for (int i = -Game.tilesHandler.maxMapSize/2; i <= Game.tilesHandler.maxMapSize/2; i++) {
            for (int j = -Game.tilesHandler.maxMapSize/2; j <= Game.tilesHandler.maxMapSize/2; j++) {

                tileX = i * scaledTileSize - Game.entitiesHandler.playerEntity().getxPos();
                tileY = j * scaledTileSize - Game.entitiesHandler.playerEntity().getyPos();

                halfTileX = i * scaledTileSize - scaledTileSize  / 2 - Game.entitiesHandler.playerEntity().getxPos();
                halfTileY = j * scaledTileSize - scaledTileSize / 2 - Game.entitiesHandler.playerEntity().getyPos();


                if (halfTileX + tileSize > Game.entitiesHandler.playerEntity().getxPos() - screenWidth &&
                        halfTileX - tileSize < Game.entitiesHandler.playerEntity().getxPos() + screenWidth &&
                        halfTileY + tileSize > Game.entitiesHandler.playerEntity().getyPos() - screenHeight &&
                        halfTileY - tileSize < Game.entitiesHandler.playerEntity().getyPos() + screenHeight ) {

                    g2.drawImage(Game.tilesHandler.getTile(i, j).tileImage,
                            halfTileX, halfTileY,
                            scaledTileSize, scaledTileSize, null);


                }
            }
        }

        for (SpawnedEntity entity: Game.entitiesHandler.spawnedEntitiesList) {

            if (entity.getEntityType() != "player") {
                g2.drawImage(
                        entity.getEntitySprite(),
                        entity.getxPos() - Game.entitiesHandler.playerEntity().getxPos() + screenX,
                        entity.getyPos() - Game.entitiesHandler.playerEntity().getyPos() + screenY,
                        entity.getEntityWidth() * scalingSize,
                        entity.getEntityHeight() * scalingSize, null);

            } else {

                g2.drawImage(
                        entity.getEntitySprite(), screenX, screenY,
                        entity.getEntityWidth() * scalingSize,
                        entity.getEntityHeight() * scalingSize, null);

            }
        }
    }
}