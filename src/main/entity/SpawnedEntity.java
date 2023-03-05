package main.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Vector;

public class SpawnedEntity extends Entity{

    private int xPos;
    private int yPos;

    private int xDelta;
    private int yDelta;

    public SpawnedEntity(Entity entityType, int xPos, int yPos) {
        this(entityType, xPos, yPos, "default");
    }

    public SpawnedEntity(Entity entityType, int xPos, int yPos, String type) {

        super(entityType.entityID, entityType.entityData, entityType.entitySprite);

        this.xPos = xPos;
        this.yPos = yPos;

        this.xDelta = 0;
        this.yDelta = 0;

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
}
