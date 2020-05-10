package org.hl.engine.math.lalg;

public class Vector2f {
    private float x;
    private float y;

    // Just a vector if you know what I mean
    public Vector2f (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
