package org.hl.engine.objects;

import org.hl.engine.graphics.Mesh;
import org.hl.engine.math.lalg.Vector3f;

public class GameObject {
	private Vector3f position, rotation, scale;
	private Mesh mesh;

	public GameObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
	}

	public void update() {
		position.add(0, 0, -0.01F);
		rotation.add(0, 0.001F, 0);
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Mesh getMesh() {
		return mesh;
	}
}
