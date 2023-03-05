package main.handler;

import com.sun.source.tree.BreakTree;
import main.entity.Entity;
import main.entity.SpawnedEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Vector2D;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EntitiesHandler {

    // public ArrayList<Entity> entitiesList;
    public HashMap<String, Entity> entitiesHashMap;
    public ArrayList<SpawnedEntity> spawnedEntitiesList;

    public EntitiesHandler(){

        this.entitiesHashMap = new HashMap<>();
        this.spawnedEntitiesList = new ArrayList<>();
        this.initEntity();

    }

    public void initEntity() {

        JSONArray entityList;

        entityList = (JSONArray) ResourcesHandler.loadJson("/entity/entities.json").get("availableEntities");

        for (int i=0; i<entityList.size(); i++)
        {
            this.entitiesHashMap.put(entityList.get(i).toString(), createEntity(entityList.get(i).toString()));
        }
    }

    public static Entity createEntity(String entityID) {

        JSONObject entityData;

        entityData = ResourcesHandler.loadJson("/entity/" + entityID + "/entityData.json");

        if (entityData != null){
            BufferedImage entitySprite = ResourcesHandler.loadImg("/entity/" + entityID + "/sprites.png");
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

        this.spawnedEntitiesList.add(new SpawnedEntity(baseEntity, xPos, -yPos, entityType));
        System.out.println("[EntityHandler/INFO] Spawned Entity " + baseEntity.getEntityName() + " of entityType " + entityType + " at: " + xPos + " , " + yPos);

    }

    public Entity getEntityType(String entityName) {
        if (this.entitiesHashMap.get(entityName) == null) {
            System.out.println("[EntityHandler/ERROR] Failed to get entity with name:" + entityName);
            System.exit(1);
        }

        return this.entitiesHashMap.get(entityName);

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
}
