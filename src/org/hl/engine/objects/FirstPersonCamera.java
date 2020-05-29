package org.hl.engine.objects;

import org.hl.engine.io.Display;
import org.hl.engine.io.Input;
import org.hl.engine.math.lalg.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.Vector;

public class FirstPersonCamera extends Camera {
	private Input i;
	private float moveSpeed;
	private float sensitivity;

	private double oldMouseX, oldMouseY = 0;
	private double newMouseX, newMouseY;

	public FirstPersonCamera(Vector3f position, Vector3f rotation, float moveSpeed, float sensitivity) {
		super(position, rotation);
		this.moveSpeed = moveSpeed;
		this.sensitivity = sensitivity;
	}

	public void create(Input i) {
		this.i = i;
	}

	public void update () throws Exception {

		newMouseX = i.getMouseX();
		newMouseY = i.getMouseY();


		Vector3f cameraPos = getPosition();
		Vector3f cameraRot = getRotation();


		float dx = (float) ((float)newMouseX - oldMouseX);
		float dy = (float) ((float)newMouseY - oldMouseY);
		oldMouseX = newMouseX;
		oldMouseY = newMouseY;

		cameraRot = Vector3f.add(cameraRot, new Vector3f(dy*sensitivity, dx*sensitivity, 0));

		if (i.isKeyDown(GLFW.GLFW_KEY_A)) movePosition(-moveSpeed, 0, 0);
		if (i.isKeyDown(GLFW.GLFW_KEY_D)) movePosition(moveSpeed, 0, 0);
		if (i.isKeyDown(GLFW.GLFW_KEY_W)) movePosition(0, 0, -moveSpeed);
		if (i.isKeyDown(GLFW.GLFW_KEY_S)) movePosition(0, 0, moveSpeed);
		if (i.isKeyDown(GLFW.GLFW_KEY_SPACE)) movePosition(0, moveSpeed, 0);
		if (i.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) movePosition(0, -moveSpeed, 0);
		if (i.keyPress(GLFW.GLFW_KEY_R)) moveSpeed = 4*moveSpeed;
		if (i.keyReleased(GLFW.GLFW_KEY_R)) moveSpeed = moveSpeed / 4;

		setRotation(cameraRot);
		setPosition(cameraPos);

		i.reset();

	}
}
