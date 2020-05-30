package org.hl.engine.math.lalg;

import java.util.Objects;

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
	public static Vector4f add(Vector4f first, Vector4f second) {
		return new Vector4f(first.getX() + second.getX(), first.getY() + second.getY(), first.getZ() + second.getZ(), first.getA() + second.getA());
	}

	public static Vector4f sub(Vector4f first, Vector4f second) {
		return new Vector4f(first.getX() - second.getX(), first.getY() - second.getY(), first.getZ() - second.getZ(), first.getA() - second.getA());
	}

	public static Vector4f mul(Vector4f first, Vector4f second) {
		return new Vector4f(first.getX() * second.getX(), first.getY() * second.getY(), first.getZ() * second.getZ(), first.getA() * second.getA());
	}

	public static Vector4f div(Vector4f first, Vector4f second) {
		return new Vector4f(first.getX() / second.getX(), first.getY() / second.getY(), first.getZ() / second.getZ(), first.getA() / second.getA());
	}

	public static float dot(Vector4f first, Vector4f second) {
		return first.getX()*second.getX() + first.getY()*second.getY() + first.getZ()*second.getZ() + first.getA()*second.getA();
	}

	public static float magnitude(Vector4f vector) {
		return (float)Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY() + vector.getZ()*vector.getZ() + vector.getA()*vector.getA());
	}

	public static Vector4f normalize(Vector4f vector) {
		float len = Vector4f.magnitude(vector);
		return Vector4f.div(vector, new Vector4f(len, len, len, len));
	}

	public static Vector4f scale(Vector4f vector, float scalar) {
		return new Vector4f(vector.getX()*scalar, vector.getY()*scalar, vector.getZ()*scalar, vector.getA()*scalar);
	}

	public static Vector4f projection(Vector4f projecting, Vector4f projectedOnto) {
		return Vector4f.scale(projectedOnto, ( Vector4f.dot(projecting, projectedOnto) / Vector4f.dot(projectedOnto, projectedOnto) ));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector4f vector4f = (Vector4f) o;
		return Float.compare(vector4f.getX(), getX()) == 0 &&
				Float.compare(vector4f.getY(), getY()) == 0 &&
				Float.compare(vector4f.getZ(), getZ()) == 0 &&
				Float.compare(vector4f.getA(), getA()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), getZ(), getA());
	}

	@Override
	public String toString() {
		return "Vector4f{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", a=" + a +
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

	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}
}
