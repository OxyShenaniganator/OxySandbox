package main.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {

    String entityID;
    String entityName;
    JSONObject entityData;
    BufferedImage entitySprite;

    int entityWidth;
    int entityHeight;

    int entityCol;
    int entityRow;

    ArrayList<String> availableEntityTypes;
    String entityType;

    public Entity(String entityID, JSONObject entityData, BufferedImage entitySprite) {
        this.entityID = entityID;
        this.entityData = entityData;
        this.entitySprite = entitySprite;

        this.entityName = (String)entityData.get("entityName");
        this.entityWidth = (int)((long)((JSONArray)entityData.get("spriteSize")).get(0));
        this.entityHeight = (int)(long)((JSONArray)entityData.get("spriteSize")).get(1);
        this.entityCol = (int)(long)((JSONArray)entityData.get("spriteSize")).get(2);
        this.entityRow = (int)(long)((JSONArray)entityData.get("spriteSize")).get(3);

        availableEntityTypes = new ArrayList<>();
        JSONArray typeArray = (JSONArray)entityData.get("entityType");

        if (typeArray != null) {
            for (int i=0; i<typeArray.size(); i++) {
                System.out.println("[Entity/INFO] Added entityType " + (String)typeArray.get(i) + " to entity" + this.entityName);
                availableEntityTypes.add((String)typeArray.get(i));
            }
            entityType = availableEntityTypes.get(0);
        }

        if (this.getClass().getName().equals("main.entity.Entity")) {
            System.out.println("[Entity/INFO] Initialized entity: " + this.entityID + " with height " + this.entityHeight + " and width " + this.entityWidth);
            System.out.println("[Entity/INFO] Row:" + this.entityRow + ", Col: " + this.entityCol);
        }
    }

    public int getEntityWidth() {
        return entityWidth;
    }

    public int getEntityHeight() {
        return entityHeight;
    }

    public int getEntityCol() {
        return entityCol;
    }

    public int getEntityRow() {
        return entityRow;
    }

    public String getEntityName() {
        return entityName;
    }

    public BufferedImage getEntitySpriteSheet(){
        return entitySprite;
    }

    public BufferedImage getEntitySprite(int row, int col){
        return entitySprite.getSubimage(this.entityWidth * col, this.entityHeight * row, this.entityWidth, this.entityHeight);
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityID() {
        return entityID;
    }

    public JSONObject getEntityData() {
        return entityData;
    }

    public BufferedImage getEntitySprite() {
        return entitySprite;
    }

    public ArrayList<String> getAvailableEntityTypes() {
        return availableEntityTypes;
    }
}
