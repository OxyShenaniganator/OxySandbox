package main;

import input.KeyboardInputs;
import main.entity.SpawnedEntity;
import utils.Vector2D;

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

    private int vecXP = 0;
    private int vecXN = 0;
    private int vecYP = 0;
    private int vecYN = 0;

    private int playerUpdateTick = 0;
    private int maxPlayerUpdateTick = 10;

    KeyboardInputs inputs = new KeyboardInputs();


    public GamePanel() {

        addKeyListener(inputs);

        Dimension screenSize = new Dimension(screenWidth, screenHeight);

        this.setPreferredSize(screenSize);
        this.setDoubleBuffered(true);

    }

    public void checkPlayerInput() {

        playerUpdateTick++;

        int inputCount = 0;

        if (!inputs.isWPressed && !inputs.isSPressed) {
            vecYP = 0;
            vecYN = 0;
        }

        if (!inputs.isAPressed && !inputs.isDPressed) {
            vecXP = 0;
            vecXN = 0;
        }

        if (inputs.isWPressed && vecYN <= 2) {
            vecYN += 1;
            inputCount += 1;
        }
        if (inputs.isSPressed && vecYP <= 2) {
            vecYP += 1;
            inputCount += 1;
        }
        if (inputs.isAPressed && vecXN <= 2) {
            vecXN += 1;
            inputCount += 1;
        }
        if (inputs.isDPressed && vecXP <= 2) {
            vecXP += 1;
            inputCount += 1;
        }

        if(playerUpdateTick >= maxPlayerUpdateTick) {

            Vector2D playerVelocity = new Vector2D(vecXP-vecXN, vecYP-vecYN);

            if (inputCount >= 2) {
                playerVelocity.normalize();
            }
            Game.entitiesHandler.updatePlayerVelocity(playerVelocity);

            playerUpdateTick = 0;
        }
    }

    public static void gameUpdate() {
        if (Game.entitiesHandler.spawnedEntitiesList.isEmpty()) {
            Game.entitiesHandler.spawnEntity(Game.entitiesHandler.getEntityType("arcanine"), 10, 40, "player");
            Game.entitiesHandler.spawnEntity(Game.entitiesHandler.getEntityType("houndoom"), 100, 100);
        }

        for (SpawnedEntity entity: Game.entitiesHandler.spawnedEntitiesList) {
            if(entity == null) continue;

            entity.updateAnimation();
            entity.updatePosition();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (SpawnedEntity entity: Game.entitiesHandler.spawnedEntitiesList) {

            g2.drawImage(
                    entity.getEntitySprite(), screenWidth/2 + (entity.getxPos()-(entity.getEntityWidth()/2)),  screenHeight/2 + (entity.getyPos()-(entity.getEntityHeight()/2)),
                    entity.getEntityWidth() * scalingSize,
                    entity.getEntityHeight() * scalingSize,null);

        }
    }
}
