package org.hl.engine.math;

import java.util.Objects;

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

	public static Vector3f add(Vector3f first, Vector3f second) {
		return new Vector3f(first.getX() + second.getX(), first.getY() + second.getY(), first.getZ() + second.getZ());
	}

	public static Vector3f sub(Vector3f first, Vector3f second) {
		return new Vector3f(first.getX() - second.getX(), first.getY() - second.getY(), first.getZ() - second.getZ());
	}

	public static Vector3f mul(Vector3f first, Vector3f second) {
		return new Vector3f(first.getX() * second.getX(), first.getY() * second.getY(), first.getZ() * second.getZ());
	}

	public static Vector3f div(Vector3f first, Vector3f second) {
		return new Vector3f(first.getX() / second.getX(), first.getY() / second.getY(), first.getZ() / second.getZ());
	}

	public static float dot(Vector3f first, Vector3f second) {
		return first.getX()*second.getX() + first.getY()*second.getY() + first.getZ()*second.getZ();
	}

	public static float magnitude(Vector3f vector) {
		return (float)Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY() + vector.getZ()*vector.getZ());
	}

	public static Vector3f normalize(Vector3f vector) {
		float len = Vector3f.magnitude(vector);
		return Vector3f.div(vector, new Vector3f(len, len, len));
	}

	public static Vector3f scale(Vector3f vector, float scalar) {
		return new Vector3f(vector.getX()*scalar, vector.getY()*scalar, vector.getZ()*scalar);
	}

	public static Vector3f projection(Vector3f projecting, Vector3f projectedOnto) {
		return Vector3f.scale(projectedOnto, ( Vector3f.dot(projecting, projectedOnto) / Vector3f.dot(projectedOnto, projectedOnto) ));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector3f vector3f = (Vector3f) o;
		return Float.compare(vector3f.getX(), getX()) == 0 &&
				Float.compare(vector3f.getY(), getY()) == 0 &&
				Float.compare(vector3f.getZ(), getZ()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), getZ());
	}

	@Override
	public String toString() {
		return "Vector3f{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
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
