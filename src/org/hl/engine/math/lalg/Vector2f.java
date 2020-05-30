package org.hl.engine.math.lalg;

import java.util.Objects;

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


	public static Vector2f add(Vector2f first, Vector2f second) {
		return new Vector2f(first.getX() + second.getX(), first.getY() + second.getY());
	}

	public static Vector2f sub(Vector2f first, Vector2f second) {
		return new Vector2f(first.getX() - second.getX(), first.getY() - second.getY());
	}

	public static Vector2f mul(Vector2f first, Vector2f second) {
		return new Vector2f(first.getX() * second.getX(), first.getY() * second.getY());
	}

	public static Vector2f div(Vector2f first, Vector2f second) {
		return new Vector2f(first.getX() / second.getX(), first.getY() / second.getY());
	}

	public static float dot(Vector2f first, Vector2f second) {
		return first.getX()*second.getX() + first.getY()*second.getY();
	}

	public static float magnitude(Vector2f vector) {
		return (float)Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY());
	}

	public static Vector2f normalize(Vector2f vector) {
		float len = Vector2f.magnitude(vector);
		return Vector2f.div(vector, new Vector2f(len, len));
	}

	public static Vector2f scale(Vector2f vector, float scalar) {
		return new Vector2f(vector.getX()*scalar, vector.getY()*scalar);
	}

	public static Vector2f projection(Vector2f projecting, Vector2f projectedOnto) {
		return Vector2f.scale(projectedOnto, ( Vector2f.dot(projecting, projectedOnto) / Vector2f.dot(projectedOnto, projectedOnto) ));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vector2f vector2f = (Vector2f) o;
		return Float.compare(vector2f.getX(), getX()) == 0 &&
				Float.compare(vector2f.getY(), getY()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY());
	}

	@Override
	public String toString() {
		return "Vector2f{" +
				"x=" + x +
				", y=" + y +
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
}
