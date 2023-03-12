package net.oxyoksirotl.entity;

import java.awt.image.BufferedImage;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileEntity that = (TileEntity) o;

        if (collision != that.collision) return false;
        return Objects.equals(tileImage, that.tileImage);
    }

    @Override
    public int hashCode() {
        int result = tileImage != null ? tileImage.hashCode() : 0;
        result = 31 * result + (collision ? 1 : 0);
        return result;
    }
}
