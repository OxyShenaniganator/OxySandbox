package net.oxyoksirotl.utils;

public class Pos {

    private int XPos;
    private int YPos;

    public Pos(int worldXPos, int worldYPos) {
        this.XPos = worldXPos;
        this.YPos = worldYPos;
    }

    // Getter & Setter
    public int getXPos() {
        return XPos;
    }

    public int getYPos() {
        return YPos;
    }

    public void setXPos(int XPos) {
        this.XPos = XPos;
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pos pos = (Pos) o;

        if (XPos != pos.XPos) return false;
        return YPos == pos.YPos;
    }

    @Override
    public int hashCode() {
        int result = XPos;
        result = 31 * result + YPos;
        return result;
    }
}
