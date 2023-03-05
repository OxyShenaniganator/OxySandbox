package main;

import input.KeyboardInputs;
import main.entity.SpawnedEntity;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final int tileSize = 24;
    private int scalingSize = 2;
    private int resizedTileSize = tileSize * scalingSize;
    final int maxCol = 16;
    final int maxRow = 12;

    private int frames = 0;


    public GamePanel() {

        addKeyListener(new KeyboardInputs());

        int screenWidth = resizedTileSize * maxCol;
        int screenHeight = resizedTileSize * maxRow;
        Dimension screenSize = new Dimension(screenWidth, screenHeight);

        this.setPreferredSize(screenSize);
        this.setDoubleBuffered(true);

    }

    public static void gameUpdate() {
        if (Game.entitiesHandler.spawnedEntitiesList.isEmpty()) {
            Game.entitiesHandler.spawnEntity(Game.entitiesHandler.getEntityType("arcanine"), 10, 40);
            Game.entitiesHandler.spawnEntity(Game.entitiesHandler.getEntityType("houndoom"), 100, 400, "player");
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
                    entity.getEntitySprite(), entity.getxPos(), entity.getyPos(),
                    entity.getEntityWidth() * scalingSize,
                    entity.getEntityHeight() * scalingSize,null);

        }
    }
}
