package org.hl.engine.objects;

import org.hl.engine.io.Input;
import org.hl.engine.math.lalg.Vector3f;
import org.lwjgl.glfw.GLFW;

public class ThirdPersonCamera extends Camera {

	private GameObject object;
	private Input i;
	private float distance;
	private float angle = 0;
	private float horizAngle = 0;
	private float vertAngle = 0;
	private float moveSpeed;
	private float sensitivity;
	private float near;
	private float far;
	private boolean clickToMove;
	private boolean clickToZoom;
	private boolean zoomEnabled;

	private double oldMouseX, oldMouseY = 0;
	private double newMouseX, newMouseY;

	public ThirdPersonCamera(Vector3f position, Vector3f rotation, GameObject object, float sensitivity, float distance, float near, float far, boolean clickToMove, boolean clickToZoom, boolean zoomEnabled) {
		super(position, rotation);
		this.object = object;
		this.sensitivity = sensitivity;
		this.clickToMove = clickToMove;
		this.clickToZoom = clickToZoom;
		this.zoomEnabled = zoomEnabled;
		this.near = near;
		this.far = far;
		this.distance = distance;
	}

	public void create(Input i) throws Exception {

		this.i = i;
		setRotation(getRotation());
		setPosition(getPosition());
	}


	public void update() throws Exception {

		near = 0.1f;

		newMouseX = i.getMouseX();
		newMouseY = i.getMouseY();


		float dx = (float) ((float)newMouseX - oldMouseX);
		float dy = (float) ((float)newMouseY - oldMouseY);

		if (clickToMove && clickToZoom) {
			if (i.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
				vertAngle -= dy * sensitivity;
				horizAngle += dx * sensitivity;
			}

			if (i.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT) && zoomEnabled) {
				if (distance > 0) {
					distance += dy * sensitivity;
				} else {
					distance = near;
				}
			}
		} else if (clickToMove) {

			if (i.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
				vertAngle -= dy * sensitivity;
				horizAngle += dx * sensitivity;
			}
			if (zoomEnabled) {
				if (distance > 0) {
					distance += dy * sensitivity;
				} else {
					distance = near;
				}
			}

		} else if (clickToZoom) {

			vertAngle -= dy * sensitivity;
			horizAngle += dx * sensitivity;

			if (i.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT) && zoomEnabled) {
				if (distance > 0) {
					distance += dy * sensitivity;
				} else {
					distance = near;
				}
			}

		} else {

			vertAngle -= dy * sensitivity;
			horizAngle += dx * sensitivity;
			if (zoomEnabled) {
				if (distance > 0) {
					distance += dy * sensitivity;
				} else {
					distance = near;
				}
			}

		}


		oldMouseX = newMouseX;
		oldMouseY = newMouseY;

		float horizDistance = (float) (distance * Math.cos(Math.toRadians(vertAngle)));
		float vertDistance = (float) (distance * Math.sin(Math.toRadians(vertAngle)));

		float xOffset = (float) (horizDistance * Math.sin(Math.toRadians(-horizAngle)));
		float zOffset = (float) (horizDistance * Math.cos(Math.toRadians(-horizAngle)));

		setPosition(new Vector3f(object.getPosition().getX() + xOffset, object.getPosition().getY() - vertDistance, object.getPosition().getZ() + zOffset));
		setRotation(new Vector3f(-vertAngle, horizAngle,0));

		i.reset();


	}
}
