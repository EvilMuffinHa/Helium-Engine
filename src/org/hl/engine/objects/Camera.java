package org.hl.engine.objects;
import org.hl.engine.math.lalg.Vector3f;

public class Camera {
	private Vector3f position;
	private Vector3f rotation;

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}



	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void movePosition(float offsetX, float offsetY, float offsetZ) {
		if(offsetZ != 0) {
			position.setZ(position.getZ() + (float) Math.cos(Math.toRadians(rotation.getY())) * offsetZ);
			position.setX(position.getX() + (float) Math.sin(Math.toRadians(rotation.getY())) * -offsetZ);
		}
		if(offsetX != 0) {
			position.setX(position.getX() + (float) Math.cos(Math.toRadians(rotation.getY())) * offsetX) ;
			position.setZ(position.getZ() + (float) Math.sin(Math.toRadians(rotation.getY())) * offsetX);
		}

		position.setY(position.getY() + offsetY);
	}
}
