package org.hl.engine.math.lalg;

public class Vector4f {
	private float x;
	private float y;
	private float z;
	private float a;

	// Just a vector if you know what I mean
	public Vector4f (float x, float y, float z, float a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}

	public void setVector(float x, float y, float z, float a) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}

	public void add(float x, float y, float z, float a) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.a += a;
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

	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}
}
