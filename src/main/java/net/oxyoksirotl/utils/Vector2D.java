package net.oxyoksirotl.utils;

public class Vector2D {

    private int x;
    private int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateVector(int x, int y) {

        this.x = x;
        this.y = y;

    }

    public void normalize() {
        double normalizedLength = (float)(this.x + this.y)/2;

        this.x = (int)Math.round(normalizedLength);
        this.y = (int)Math.round(normalizedLength);

    }

    public static Vector2D getNormalize(int legnth, double deg) {

        double x = legnth * (Math.cos(Math.toRadians(deg)));
        double y = legnth * (Math.sin(Math.toRadians(deg)));

        return new Vector2D((int)Math.round(x), (int)Math.round(y));
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
