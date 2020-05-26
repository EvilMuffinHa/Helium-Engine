package org.hl.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

	private Shader shader;

	public Renderer(Shader shader) {
		this.shader = shader;
	}


	public void renderMesh(Mesh mesh) {

		// Renders the mesh by drawing it using triangles (least complicated)
		GL30.glBindVertexArray(mesh.getVertexArrayObject());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesBufferObject());

		shader.bind();

		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);

		shader.unbind();
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);

	}
}
