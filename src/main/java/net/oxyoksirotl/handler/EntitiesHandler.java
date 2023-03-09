package net.oxyoksirotl.handler;

import net.oxyoksirotl.Game;
import net.oxyoksirotl.GamePanel;
import net.oxyoksirotl.entity.Entity;
import net.oxyoksirotl.entity.SpawnedEntity;
import net.oxyoksirotl.utils.Pos;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import net.oxyoksirotl.utils.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class EntitiesHandler {

    GamePanel gamePanel;

    // Player controls
    private boolean isWPressed = false;
    private boolean isAPressed = false;
    private boolean isSPressed = false;
    private boolean isDPressed = false;

    // Player Velocity Vectors
    private int playerVecYNegative = 0;
    private int playerVecYPositive = 0;
    private int playerVecXNegative = 0;
    private int playerVecXPositive = 0;
    private Vector2D playerVelocity = new Vector2D(0,0);

    // Player Animation
    private int playerUpdateTick = 0;
    private int maxPlayerUpdateTick = 12;

    // Player Random Spawn;
    private Random random;


    // Entities management
    public HashMap<String, Entity> entitiesHashMap;
    public ArrayList<SpawnedEntity> spawnedEntitiesList;

    Comparator<SpawnedEntity> spawnedEntityComparator = new Comparator<SpawnedEntity>() {
        @Override
        public int compare(SpawnedEntity entity1, SpawnedEntity entity2) {
            return entity1.compareTo(entity2);
        }
    };

    public EntitiesHandler(){

        this.entitiesHashMap = new HashMap<>();
        this.spawnedEntitiesList = new ArrayList<>();
        this.random = new Random();
        this.initEntity();

    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Dimension getGameScreenSize() {
        return new Dimension(gamePanel.screenX, gamePanel.screenY);
    }

    public void initEntity() {

        JSONArray entityList;

        entityList = (JSONArray) ResourcesHandler.loadJson("/assets/entity/entities.json").get("availableEntities");

        for (int i=0; i<entityList.size(); i++)
        {
            this.entitiesHashMap.put(entityList.get(i).toString(), createEntity(entityList.get(i).toString()));
        }
    }

    public static Entity createEntity(String entityID) {

        JSONObject entityData;

        entityData = ResourcesHandler.loadJson("/assets/entity/" + entityID + "/entityData.json");

        if (entityData != null){
            BufferedImage entitySprite = ResourcesHandler.loadImg("/assets/entity/" + entityID + "/sprites.png");
            return new Entity(entityID, entityData, entitySprite);
        } else
        {
            return null;
        }
    }

    public void spawnEntity(Entity baseEntity, int xPos, int yPos) {
        spawnEntity(baseEntity, xPos, yPos, "default");
    }

    public void spawnEntity(Entity baseEntity, int xPos, int yPos, String entityType) {
        this.spawnedEntitiesList.add(new SpawnedEntity(baseEntity, xPos, yPos, entityType));
        System.out.println("[EntityHandler/INFO] Spawned Entity " + baseEntity.getEntityName() + " of entityType " + entityType + " at: " + xPos + " , " + yPos);

    }

    public void sortSpawnedEntity() {
        this.spawnedEntitiesList.sort(spawnedEntityComparator);
    }

    public void update() {

        this.checkPlayerInput();

        for (SpawnedEntity entity: this.spawnedEntitiesList) {
            if(entity == null) continue;

            entity.updatePosition();
            entity.updateAnimation();
        }
    }

    public Entity getEntityType(String entityName) {
        if (this.entitiesHashMap.get(entityName) == null) {
            System.out.println("[EntityHandler/ERROR] Failed to get net.oxyoksirotl.entity with name:" + entityName);
            System.exit(1);
        }

        return this.entitiesHashMap.get(entityName);

    }

    // Player Entity Control


    public boolean isWPressed() {
        return isWPressed;
    }

    public void setWPressed(boolean WPressed) {
        isWPressed = WPressed;
    }

    public boolean isAPressed() {
        return isAPressed;
    }

    public void setAPressed(boolean APressed) {
        isAPressed = APressed;
    }

    public boolean isSPressed() {
        return isSPressed;
    }

    public void setSPressed(boolean SPressed) {
        isSPressed = SPressed;
    }

    public boolean isDPressed() {
        return isDPressed;
    }

    public void setDPressed(boolean DPressed) {
        isDPressed = DPressed;
    }

    public void changePlayerXDelta(int xDelta, boolean isMovement) {

        for(SpawnedEntity entity: this.spawnedEntitiesList) {
            if (Objects.equals(entity.getEntityType(), "player")) {
                entity.setxDelta(xDelta);
                entity.setMoving(isMovement);
                break;
            }
        }
    }

    public void changePlayerYDelta(int yDelta, boolean isMovement) {

        for(SpawnedEntity entity: this.spawnedEntitiesList) {
            if (Objects.equals(entity.getEntityType(), "player")) {
                entity.setyDelta(yDelta);
                entity.setMoving(isMovement);
                break;
            }
        }
    }

    public void updatePlayerVelocity(Vector2D velocity) {
        for(SpawnedEntity entity: this.spawnedEntitiesList) {
            if (Objects.equals(entity.getEntityType(), "player")) {

                entity.setxDelta(velocity.getX());
                entity.setyDelta(velocity.getY());

                if(velocity.getX() == 0 && velocity.getY() == 0) {
                    entity.setMoving(false);
                    break;
                }

                entity.setMoving(true);
                break;
            }
        }
    }

    public SpawnedEntity playerEntity() {
        for(SpawnedEntity entity: this.spawnedEntitiesList) {
            if (Objects.equals(entity.getEntityType(), "player")) {
                return entity;
            }
        }
        return null;
    }

    public void spawnPlayer(Entity entity) {
        if (playerEntity() == null) {

            int playerChunkX = random.nextInt(5) + Math.floorDiv(Game.chunkHandler.getMaxChunkCol(),2) - 2;
            int playerChunkY = random.nextInt(5) + Math.floorDiv(Game.chunkHandler.getMaxChunkRow(),2) - 2;
            int playerX = random.nextInt(5) + Math.floorDiv(Game.chunkHandler.getMaxChunkSize(),2) - 2;
            int playerY = random.nextInt(5) + Math.floorDiv(Game.chunkHandler.getMaxChunkSize(),2) - 2;

            SpawnedEntity playerEntity = new SpawnedEntity(entity,
                    Game.chunkHandler.toWorldXPos(playerChunkX, playerX),
                    Game.chunkHandler.toWorldYPos(playerChunkY, playerY), "player");

            Game.chunkHandler.getChunk(playerChunkX,playerChunkY).insertEntity(playerEntity, playerX, playerY);

            spawnedEntitiesList.add(playerEntity);

        }
    }

    public void checkPlayerInput() {

        if (this.playerEntity() != null) {

            int inputCount = 0;

            if (!this.isWPressed && !this.isSPressed) {
                playerVecYPositive = 0;
                playerVecYNegative = 0;
            }

            if (!this.isAPressed && !this.isDPressed) {
                playerVecXPositive = 0;
                playerVecXNegative = 0;
            }

            if (this.isWPressed && playerVecYNegative <= this.playerEntity().getMaxSpeed()) {
                playerVecYNegative += 1;
                inputCount += 1;
            }
            if (this.isSPressed && playerVecYPositive <= this.playerEntity().getMaxSpeed()) {
                playerVecYPositive += 1;
                inputCount += 1;
            }
            if (this.isAPressed && playerVecXNegative <= this.playerEntity().getMaxSpeed()) {
                playerVecXNegative += 1;
                inputCount += 1;
            }
            if (this.isDPressed && playerVecXPositive <= this.playerEntity().getMaxSpeed()) {
                playerVecXPositive += 1;
                inputCount += 1;
            }

            changePlayerVelocity(inputCount);
        }
    }

    private void changePlayerVelocity(int inputCount) {
        playerVelocity.updateVector(playerVecXPositive - playerVecXNegative, playerVecYPositive - playerVecYNegative);

        playerUpdateTick++;

        if (playerUpdateTick >= maxPlayerUpdateTick) {

            if (inputCount >= 2) {
                playerVelocity.normalize();
            }

            this.updatePlayerVelocity(playerVelocity);

            playerUpdateTick = 0;
        }
    }
}
