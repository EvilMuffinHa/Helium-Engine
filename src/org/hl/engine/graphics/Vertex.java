package org.hl.engine.graphics;

import org.hl.engine.math.lalg.Vector2f;
import org.hl.engine.math.lalg.Vector3f;

public class Vertex {

    // Just a vertex

    private Vector3f position;
    private Vector3f color;
    private Vector2f textureCoordinates;

    public Vertex (Vector3f position, Vector3f color, Vector2f textureCoordinates) {
        this.position = position;
        this.color = color;
        this.textureCoordinates = textureCoordinates;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getTextureCoordinates() {
        return textureCoordinates;
    }

    public Vector3f getColor() {
        return color;
    }
}
