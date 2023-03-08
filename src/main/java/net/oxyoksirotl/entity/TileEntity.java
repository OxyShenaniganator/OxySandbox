package net.oxyoksirotl.entity;

import java.awt.image.BufferedImage;

public class TileEntity {

    public BufferedImage tileImage;
    private boolean collision = false;

    public TileEntity(BufferedImage tileImage, boolean collision ) {

        this.tileImage = tileImage;
        this.collision = collision;

    }

    // Getter & Setter

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public void setTileImage(BufferedImage tileImage) {
        this.tileImage = tileImage;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
