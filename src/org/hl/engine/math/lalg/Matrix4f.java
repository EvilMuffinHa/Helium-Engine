package org.hl.engine.math.lalg;



public class Matrix4f {
	public static final int SIZE = 4;
	private float[][] elements = new float[SIZE][SIZE];


	public Matrix4f() {

	}

	public Matrix4f(float[][] values) {
		this.elements = values;
	}


	public static Matrix4f identity() {
		float[][] identityArray = {
				{1 , 0 , 0 , 0},
				{0 , 1 , 0 , 0},
				{0 , 0 , 1 , 0},
				{0 , 0 , 0 , 1}
		};
		return new Matrix4f(identityArray);
	}

	public static Matrix4f zeroes() {
		float[][] zeroMatrix = {
				{0 , 0 , 0 , 0},
				{0 , 0 , 0 , 0},
				{0 , 0 , 0 , 0},
				{0 , 0 , 0 , 0}
		};
		return new Matrix4f(zeroMatrix);

	}

	public static Matrix4f translate(Vector3f translate) {
		Matrix4f result = Matrix4f.identity();

		result.set(3, 0, translate.getX());
		result.set(3, 1, translate.getY());
		result.set(3, 2, translate.getZ());

		return result;
	}


	public static Matrix4f flip(Matrix4f matrix) {
		Matrix4f flippedMatrix = matrix;
		for (int i = 0; i < SIZE; i ++) {
			for (int j = 0; j < SIZE; j ++ ) {
				flippedMatrix.set(i, j, matrix.get(j, i));
			}
		}
		return flippedMatrix;
	}

	// This one doesn't work

	/* public static Matrix4f rotate(float angle, Vector3f axis) {

		float cos = (float)Math.cos(Math.toDegrees(angle));
		float sin = (float)Math.sin(Math.toDegrees(angle));
		float C = 1 - cos;
		float x = axis.getX();
		float y = axis.getY();
		float z = axis.getZ();
		float[][] rotArray = {
				{cos + x*x*C , x*y*C - z*sin , x*z*C + y*sin , 0},
				{y*z*C + z*sin , cos + y*y*C , y*z*C - x*sin , 0},
				{z*x*C-y*sin , z*y*C + x*sin  ,  cos + z*z*C , 0},
				{0 , 0 , 0 , 1}
		};

		return new Matrix4f(rotArray);
	} */
	public static Matrix4f rotate(float angle, Vector3f axis) {
		Matrix4f result = Matrix4f.identity();

		float cos = (float) Math.cos(Math.toRadians(angle));
		float sin = (float) Math.sin(Math.toRadians(angle));
		float C = 1 - cos;

		result.set(0, 0, cos + axis.getX() * axis.getX() * C);
		result.set(0, 1, axis.getX() * axis.getY() * C - axis.getZ() * sin);
		result.set(0, 2, axis.getX() * axis.getZ() * C + axis.getY() * sin);
		result.set(1, 0, axis.getY() * axis.getX() * C + axis.getZ() * sin);
		result.set(1, 1, cos + axis.getY() * axis.getY() * C);
		result.set(1, 2, axis.getY() * axis.getZ() * C - axis.getX() * sin);
		result.set(2, 0, axis.getZ() * axis.getX() * C - axis.getY() * sin);
		result.set(2, 1, axis.getZ() * axis.getY() * C + axis.getX() * sin);
		result.set(2, 2, cos + axis.getZ() * axis.getZ() * C);

		return result;
	}

	public static Matrix4f scale(Vector3f scaleVec) {

		Matrix4f result = Matrix4f.identity();

		result.set(0, 0, scaleVec.getX());
		result.set(1, 1, scaleVec.getY());
		result.set(2, 2, scaleVec.getZ());

		return result;
	}

	public static Matrix4f projection( float fov, float aspectRatio, float near, float far) {
		Matrix4f result = Matrix4f.identity();

		float tan = (float)Math.tan(Math.toRadians(fov / 2));
		float range = far - near;

		result.set(0, 0, 1.0f / (aspectRatio * tan));
		result.set(1, 1, 1.0f / tan);
		result.set(2, 2, -((far + near) / range));
		result.set(3, 2, -1.0f);
		result.set(2, 3, -(2.0f*far*near/range));
		result.set(3, 3, 0f);

		return result;
	}

	public static Matrix4f view(Vector3f position, Vector3f rotation) {

		Vector3f negative = new Vector3f(-position.getX(), -position.getY(), -position.getZ());
		Matrix4f translationMatrix = Matrix4f.translate(negative);
		Matrix4f rotationXMatrix = Matrix4f.rotate(-rotation.getX(), new Vector3f(1, 0, 0));
		Matrix4f rotationYMatrix = Matrix4f.rotate(-rotation.getY(), new Vector3f(0, 1, 0));
		Matrix4f rotationZMatrix = Matrix4f.rotate(-rotation.getZ(), new Vector3f(0, 0, 1));

		Matrix4f rotMat = Matrix4f.multiply(rotationZMatrix, Matrix4f.multiply(rotationXMatrix, rotationYMatrix));

		return Matrix4f.multiply(rotMat, translationMatrix);

	}

	public static Matrix4f multiply(Matrix4f first, Matrix4f second) {

		Matrix4f result = Matrix4f.zeroes();

		for (int i = 0; i < SIZE; i ++ ) {
			for (int j = 0; j < SIZE; j ++) {
				result.set(i, j,
									first.get(0, i) * second.get(j, 0) +
										first.get(1, i) * second.get(j, 1) +
										first.get(2, i) * second.get(j, 2) +
										first.get(3, i) * second.get(j, 3)
				);
			}
		}

		return result;

	}

	public float get(int x, int y) {
		return elements[x][y];
	}

	public void set(int x, int y, float value) {
		elements[x][y] = value;
	}

	public float[][] getAll() {
		return elements;
	}

	public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale) {
		Matrix4f translationMatrix = Matrix4f.translate(position);
		Matrix4f rotationXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1, 0, 0));
		Matrix4f rotationYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0, 1, 0));
		Matrix4f rotationZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0, 0, 1));
		Matrix4f scaleMatrix = Matrix4f.scale(scale);

		Matrix4f rotMat = Matrix4f.multiply(rotationXMatrix, Matrix4f.multiply(rotationYMatrix, rotationZMatrix));

		return Matrix4f.multiply(translationMatrix, Matrix4f.multiply(rotMat, scaleMatrix));

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
