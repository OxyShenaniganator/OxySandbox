package main.handler;

import main.entity.Entity;
import main.entity.SpawnedEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

        this.spawnedEntitiesList.add(new SpawnedEntity(baseEntity, xPos, yPos, entityType));
        System.out.println("[EntityHandler/INFO] Spawned Entity " + baseEntity.getEntityName() + " of entityType " + entityType + " at: " + xPos + " , " + yPos);

    }

    public Entity getEntityType(String entityName) {
        if (this.entitiesHashMap.get(entityName) == null) {
            System.out.println("[EntityHandler/ERROR] Failed to get entity with name:" + entityName);
            System.exit(1);
        }

        return this.entitiesHashMap.get(entityName);

    }

    public void changePlayerXDelta(int xDelta) {

        for(SpawnedEntity entity: this.spawnedEntitiesList) {
            if (Objects.equals(entity.getEntityType(), "player")) {
                entity.setxDelta(xDelta);
                break;
            }
        }
    }

    public void changePlayerYDelta(int yDelta) {

        for(SpawnedEntity entity: this.spawnedEntitiesList) {
            if (Objects.equals(entity.getEntityType(), "player")) {
                entity.setyDelta(yDelta);
                break;
            }
        }
    }

}
