package org.hl.engine.objects;

import org.hl.engine.math.lalg.Vector3f;

public class FixedCamera extends Camera {
	public FixedCamera(Vector3f position, Vector3f rotation) {
		super(position, rotation);
	}

	@Override
	public void setRotation(Vector3f rotation) throws Exception {
		throw new Exception("You cannot rotate a fixed camera! ");
	}

	@Override
	public void setPosition(Vector3f rotation) throws Exception {
		throw new Exception("You cannot move a fixed camera! ");
	}
}
