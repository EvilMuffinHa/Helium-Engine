package org.hl.engine.math.lalg;


public class Vector3f {
	private float x;
	private float y;
	private float z;

	// Just a vector if you know what I mean
	public Vector3f (float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setVector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
}

