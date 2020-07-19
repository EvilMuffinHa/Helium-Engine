package org.hl.engine.graphics;

import org.hl.engine.io.Display;
import org.hl.engine.math.lalg.Matrix4f;
import org.hl.engine.objects.Camera;
import org.hl.engine.objects.GameObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

	private Shader shader;
	private Display display;
	private Matrix4f projectionMatrix;
	private static final float fov = 70;
	private static final float near = 0.1f;
	private static final float far = 1000f;
	private Matrix4f orthoProjection;


	public Renderer(Display display, Shader shader) {
		this.shader = shader;
		this.display = display;
		this.projectionMatrix = Matrix4f.perspective(fov, this.display.getAspectRatio(), near, far);
	}

	public void renderObject3D(GameObject object, Camera camera) {

		// Renders the mesh by drawing it using triangles (least complicated)
		GL30.glBindVertexArray(object.getMesh().getVertexArrayObject());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIndicesBufferObject());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTexture().getId());

		shader.bind();

		shader.setUniform("type", object.getMesh().isType());
		shader.setUniform("projection", this.projectionMatrix);
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));

		GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

		shader.unbind();
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);

	}

	public void renderScene3D(Scene scene, Camera camera) {
		for (GameObject object: scene.getObjects()) {
			GL30.glBindVertexArray(object.getMesh().getVertexArrayObject());
			GL30.glEnableVertexAttribArray(0);
			GL30.glEnableVertexAttribArray(1);
			GL30.glEnableVertexAttribArray(2);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIndicesBufferObject());

			GL13.glActiveTexture(GL13.GL_TEXTURE0);

			GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTexture().getId());

			shader.bind();

			shader.setUniform("type", object.getMesh().isType());
			shader.setUniform("projection", this.projectionMatrix);
			shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
			shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));

			GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

			shader.unbind();
			GL30.glDisableVertexAttribArray(0);
			GL30.glDisableVertexAttribArray(1);
			GL30.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
		}
	}

	public void renderObject2D(GameObject object, Camera camera, float top, float bottom, float left, float right) {

		orthoProjection = Matrix4f.orthoProjection(right, left, bottom, top, near, far);

		// Renders the mesh by drawing it using triangles (least complicated)
		GL30.glBindVertexArray(object.getMesh().getVertexArrayObject());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIndicesBufferObject());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTexture().getId());

		shader.bind();

		shader.setUniform("type", object.getMesh().isType());
		shader.setUniform("projection", this.orthoProjection);
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));

		GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);

		shader.unbind();
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);

	}

}
