package org.hl.engine.graphics;

import org.hl.engine.math.lalg.*;

public class Vertex {

    // Just a vertex

    private Vector3f position;
    private Vector3f color;
    private Vector2f textureCoords;

    public Vertex (Vector3f position, Vector3f color, Vector2f texture) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector2f getTextureCoords() {
        return textureCoords;
    }
}
