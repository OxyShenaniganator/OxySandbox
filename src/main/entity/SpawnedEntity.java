package main.entity;

import main.handler.AnimationsHandler;
import java.awt.image.BufferedImage;
import java.util.*;

public class SpawnedEntity extends Entity implements Comparable<SpawnedEntity>{

    private int xPos;
    private int yPos;

    private int xDelta;
    private int yDelta;

    private int maxSpeed;

    private int animTick;
    private int animSpeed;

    private int animIndex;
    private int animChange;

    private String lastAnimation;
    private String currentAnimation;

    private boolean isMoving;
    private final HashMap<String, ArrayList<BufferedImage>> animationStorage;


    public SpawnedEntity(Entity entityType, int xPos, int yPos) {
        this(entityType, xPos, yPos, "default");
    }

    public SpawnedEntity(Entity entityType, int xPos, int yPos, String type) {

        super(entityType.entityID, entityType.entityData, entityType.entitySprite);

        this.xPos = xPos;
        this.yPos = yPos;

        this.xDelta = 0;
        this.yDelta = 0;

        this.maxSpeed = 2;

        this.animSpeed = 20;
        this.animTick = 0;
        this.animIndex = 2;
        this.animChange = 1;
        this.lastAnimation = "S";
        this.currentAnimation = "S";

        this.isMoving = false;

        this.animationStorage = AnimationsHandler.initEntityAnimation(this);
        System.out.println(this.animationStorage);

        if (!Objects.equals(type, "default") && availableEntityTypes.contains(type)) {
            System.out.println("[SpawnedEntity/INFO] Use entityType " + type + " to SpawnedEntity" + this.entityName);
            this.entityType = type;
        }
    }

    public void updatePosition() {

        this.xPos += this.xDelta;
        this.yPos += this.yDelta;
        this.updateDelta();
    }

    private void updateDelta() {

        if (xDelta !=0) {
            if (xDelta <0) xDelta += 1;
            else xDelta -= 1;
        }

        if (yDelta !=0) {
            if (yDelta <0) yDelta += 1;
            else yDelta -= 1;
        }

    }

    public void updateAnimation() {

        this.lastAnimation = this.currentAnimation;
        if (this.xDelta < 0) {
            if (this.yDelta < 0) { this.currentAnimation = "WA";}
            else if (this.yDelta == 0) { this.currentAnimation = "A"; }
            else { this.currentAnimation = "SA"; }
        } else if (this.xDelta == 0) {
            if (this.yDelta < 0) this.currentAnimation = "W";
            else if (this.yDelta == 0) this.currentAnimation = this.lastAnimation;
            else this.currentAnimation = "S";
        } else {
            if (this.yDelta < 0) this.currentAnimation = "WD";
            else if (this.yDelta == 0) this.currentAnimation = "D";
            else this.currentAnimation = "SD";
        }
    }

    public BufferedImage getEntitySprite() {

        this.animTick++;
        if (this.animTick >= this.animSpeed) {
            this.animTick = 0;
            if(this.animIndex >= this.entityCol - 1 || this.animIndex <= 1 ) this.animChange *= -1;
            this.animIndex += this.animChange;
        }

        if (!this.isMoving) {
            return this.animationStorage.get(this.lastAnimation).get(0);
        }
        else {
            return this.animationStorage.get(this.currentAnimation).get(this.animIndex);
        }

    }

    // Getter & Setter

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxDelta() {
        return xDelta;
    }

    public void setxDelta(int xDelta) {
        this.xDelta = xDelta;
    }

    public int getyDelta() {
        return yDelta;
    }

    public void setyDelta(int yDelta) {
        this.yDelta = yDelta;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    // Comparator


    @Override
    public String toString() {
        return "{" + "entityID=" + entityID + '\'' +
                ", entityType='" + entityType + "}";
    }

    @Override
    public int compareTo(SpawnedEntity spawnedEntity) {
        return Integer.compare(this.yPos, spawnedEntity.getyPos());
    }
}
