package org.hl.engine.graphics;


import org.hl.engine.math.lalg.*;


public class Vertex {

	// Just a vertex

	private Vector3f position;
	private Vector3f color;
	private Vector2f textureCoords;
	private boolean type;

	public Vertex(Vector3f position, Vector2f textureCoords) {
		this.position = position;
		this.textureCoords = textureCoords;
		this.color = new Vector3f(1, 1, 1);
	}

	public Vertex(Vector3f position, Vector3f color) {
		this.position = position;
		this.color = color;
		this.textureCoords = new Vector2f(0, 0);
	}

	public Vertex(Vector3f position, Vector3f color, Vector2f textureCoords) {
		this.position = position;
		this.color = color;
		this.textureCoords = textureCoords;
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

	public boolean isType() {
		return type;
	}
}
