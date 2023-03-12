package net.oxyoksirotl.entity;

import net.oxyoksirotl.Game;
import net.oxyoksirotl.handler.AnimationsHandler;
import net.oxyoksirotl.utils.Pos;
import net.oxyoksirotl.utils.enums.Movement;
import org.json.simple.JSONArray;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class SpawnedEntity extends Entity implements Comparable<SpawnedEntity>{


    // Position
    private int locatedChunkX;
    private int locatedChunkY;
    
    private int chunkXPos;
    private int chunkYPos;
    
    private int worldXPos;
    private int worldYPos;

    // Movement & Velocity
    private int xDelta;
    private int yDelta;
    private int maxSpeed;
    private boolean isMoving;

    // Animations
    private int animTick;
    private int animSpeed;
    private int animIndex;
    private int animChange;

    private String lastAnimation;
    private String currentAnimation;


    private final HashMap<String, ArrayList<BufferedImage>> animationStorage;
    private final Rectangle collisionBox;
    private Movement movement;

    public SpawnedEntity(Entity entityType, int xPos, int yPos) {
        this(entityType, xPos, yPos, "default");
    }

    public SpawnedEntity(Entity entityType, int xPos, int yPos, String type) {

        super(entityType.entityID, entityType.entityData, entityType.entitySprite);

        this.worldXPos = xPos;
        this.worldYPos = yPos;

        this.xDelta = 0;
        this.yDelta = 0;

        this.maxSpeed = 1;

        this.animSpeed = 30;
        this.animTick = 0;
        this.animIndex = 2;
        this.animChange = 1;
        this.lastAnimation = "S";
        this.currentAnimation = "S";

        this.isMoving = false;
        this.movement = Movement.IDLE;

        int collisionX = ((Long)((JSONArray)this.entityData.get("collisionSize")).get(0)).intValue();
        int collisionY = ((Long)((JSONArray)this.entityData.get("collisionSize")).get(1)).intValue();
        int collisionWidth = ((Long)((JSONArray)this.entityData.get("collisionSize")).get(2)).intValue();
        int collisionHeight = ((Long)((JSONArray)this.entityData.get("collisionSize")).get(3)).intValue();

        this.collisionBox = new Rectangle(collisionX, collisionY, collisionWidth, collisionHeight);
        this.animationStorage = AnimationsHandler.initEntityAnimation(this);
        System.out.println(this.animationStorage);

        if (!Objects.equals(type, "default") && availableTypes.contains(type)) {
            System.out.println("[SpawnedEntity/INFO] Use entityType " + type + " to SpawnedEntity" + this.entityName);
            this.entityType = type;
        }
    }

    public void updatePosition() {

        this.worldXPos += this.xDelta;
        this.worldYPos += this.yDelta;
        this.updateDelta();
        
        // updateChunkPos();
    }
    
    public void updateChunkPos() {

        Pos chunkPos = Game.chunkHandler.toChunkPos(worldXPos, worldYPos)[0];
        Pos inChunkPos = Game.chunkHandler.toChunkPos(worldXPos, worldYPos)[1];

        Game.chunkHandler.getChunk(chunkPos.getXPos(), chunkPos.getYPos()).insertEntity(
                this, inChunkPos.getXPos(), inChunkPos.getXPos());

        if (Game.chunkHandler.getChunk(locatedChunkX, locatedChunkY).getEntity(chunkXPos, chunkYPos) == this) {
            Game.chunkHandler.getChunk(locatedChunkX, locatedChunkY).removeEntity(
                    chunkXPos, chunkYPos);
        }

        locatedChunkX = chunkPos.getXPos();
        locatedChunkY = chunkPos.getYPos();

        chunkXPos = inChunkPos.getXPos();
        chunkYPos = inChunkPos.getYPos();

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

    public void updateMovement() {

        this.lastAnimation = this.currentAnimation;

        if (this.xDelta < 0) {
            if (this.yDelta < 0) {
                this.movement = Movement.UP_LEFT;
//                this.currentAnimation = "WA";
            }
            else if (this.yDelta == 0) {
                this.movement = Movement.LEFT;
//                this.currentAnimation = "A";
            }
            else {
                this.movement = Movement.DOWN_LEFT;
//                this.currentAnimation = "SA";
            }
        } else if (this.xDelta == 0) {
            if (this.yDelta < 0) {
                this.movement = Movement.UP;
//                this.currentAnimation = "W";
            }
            else if (this.yDelta == 0) {
                this.movement = Movement.IDLE;
                this.currentAnimation = this.lastAnimation;
            }
            else {
                this.movement = Movement.DOWN;
//                this.currentAnimation = "S";
            }
        } else {
            if (this.yDelta < 0) {
                this.movement = Movement.UP_RIGHT;
//                this.currentAnimation = "WD";
            }
            else if (this.yDelta == 0){
                this.movement = Movement.RIGHT;
//                this.currentAnimation = "D";
            }
            else {
                this.movement = Movement.DOWN_RIGHT;
//                this.currentAnimation = "SD";
            }
        }
        if(this.movement != Movement.IDLE) {
            this.currentAnimation = this.movement.animationID;
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


    public int getLocatedChunkX() {
        return locatedChunkX;
    }

    public void setLocatedChunkX(int locatedChunkX) {
        this.locatedChunkX = locatedChunkX;
    }

    public int getLocatedChunkY() {
        return locatedChunkY;
    }

    public void setLocatedChunkY(int locatedChunkY) {
        this.locatedChunkY = locatedChunkY;
    }

    public int getChunkXPos() {
        return chunkXPos;
    }

    public void setChunkXPos(int chunkXPos) {
        this.chunkXPos = chunkXPos;
    }

    public int getChunkYPos() {
        return chunkYPos;
    }

    public void setChunkYPos(int chunkYPos) {
        this.chunkYPos = chunkYPos;
    }

    public int getWorldXPos() {
        return worldXPos;
    }

    public void setWorldXPos(int worldXPos) {
        this.worldXPos = worldXPos;
    }

    public int getWorldYPos() {
        return worldYPos;
    }

    public void setWorldYPos(int worldYPos) {
        this.worldYPos = worldYPos;
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

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public Movement getMovement() {
        return movement;
    }

    // Comparator
    @Override
    public String toString() {
        return "{" + "entityID=" + entityID + '\'' +
                ", entityType='" + entityType + "}";
    }

    @Override
    public int compareTo(SpawnedEntity spawnedEntity) {

        int thisCollisionMiddlePointX = this.worldXPos + this.collisionBox.x + this.collisionBox.width/2;
        int thisCollisionMiddlePointY = this.worldYPos + this.collisionBox.y + this.collisionBox.height/2;
        int collisionMiddlePointX = spawnedEntity.getWorldXPos() + spawnedEntity.getCollisionBox().x + spawnedEntity.getCollisionBox().width/2;
        int collisionMiddlePointY = spawnedEntity.getWorldYPos() + spawnedEntity.getCollisionBox().y + spawnedEntity.getCollisionBox().height/2;

        return Integer.compare(thisCollisionMiddlePointY, collisionMiddlePointY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SpawnedEntity entity = (SpawnedEntity) o;

        if (locatedChunkX != entity.locatedChunkX) return false;
        if (locatedChunkY != entity.locatedChunkY) return false;
        if (chunkXPos != entity.chunkXPos) return false;
        if (chunkYPos != entity.chunkYPos) return false;
        if (worldXPos != entity.worldXPos) return false;
        if (worldYPos != entity.worldYPos) return false;
        if (!animationStorage.equals(entity.animationStorage)) return false;
        return collisionBox.equals(entity.collisionBox);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + worldXPos;
        result = 31 * result + worldYPos;
        result = 31 * result + animationStorage.hashCode();
        return result;
    }
}
