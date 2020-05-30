package org.hl.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
	private Vertex[] vertices;
	private int[] indices;
	private int vertexArrayObject, positionBufferObject, indicesBufferObject, colorBufferObject, textureBufferObject;
	private Material material;
	private boolean type;

	// A group of vertices combined based on the indexes
	public Mesh(Vertex[] vertices, int[] indices, Material material, String type) throws Exception {
		this.vertices = vertices;
		this.indices = indices;
		this.material = material;
		if (!type.equals("texture") && !type.equals("color")) {
			throw new Exception("Type must be either texture or color. ");
		}
		this.type = type.equals("texture");


	}

	// Destroy the mesh
	public void destroy () {
		GL15.glDeleteBuffers(positionBufferObject);
		GL15.glDeleteBuffers(indicesBufferObject);
		GL15.glDeleteBuffers(colorBufferObject);

		GL30.glDeleteVertexArrays(vertexArrayObject);
	}

	// getters for the mesh

	public Vertex[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public int getVertexArrayObject() {
		return vertexArrayObject;
	}

	public int getPositionBufferObject() {
		return positionBufferObject;
	}

	public int getIndicesBufferObject() {
		return indicesBufferObject;
	}

	public int getColorBufferObject() {
		return colorBufferObject;
	}

	public void create() {

		// Creates the mesh by formatting the vertices and indices and inputting them to OpenGL
		vertexArrayObject = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vertexArrayObject);


		// Putting the position of the vertex into the buffer so the renderer can read it

		FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
		float[] positionData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i ++ ) {
			positionData[i * 3] = vertices[i].getPosition().getX();
			positionData[i * 3 + 1] = vertices[i].getPosition().getY();
			positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
		}
		positionBuffer.put(positionData).flip();

		positionBufferObject = storeData(positionBuffer, 0, 3);

		// Putting the color into the buffer so renderer and shader can read it

		FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
		float[] colorData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i ++ ) {
			colorData[i * 3] = vertices[i].getColor().getX();
			colorData[i * 3 + 1] = vertices[i].getColor().getY();
			colorData[i * 3 + 2] = vertices[i].getColor().getZ();
		}
		colorBuffer.put(colorData).flip();

		colorBufferObject = storeData(colorBuffer, 1, 3);

		IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();

		indicesBufferObject = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferObject);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	// Storing data to the buffer at position index (helps with storing color / position)

	private int storeData(FloatBuffer buffer, int index, int size) {
		int bufferID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		return bufferID;
	}

	public boolean isType() {
		return type;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
