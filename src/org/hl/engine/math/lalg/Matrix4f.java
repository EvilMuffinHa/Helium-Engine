package org.hl.engine.math.lalg;

public class Matrix4f {
	public static final int SIZE = 4;
	private float[][] elements;
	public float get(int x, int y) {
		return elements[x][y];
	}
	public void set(int x, int y, float value) {
		elements[x][y] = value;
	}
	public float[][] getAll() {
		return elements;
	}

	public float[] convertTo1D() {
		float[] returnedArray = new float[SIZE*SIZE];
		int sizeOfRow = elements[0].length;
		for (int i = 0; i < elements.length; i ++) {
			for (int j = 0; j < sizeOfRow; j ++) {
				returnedArray[i*SIZE + j] = elements[i][j];
			}
		}
		return returnedArray;
	}
}
