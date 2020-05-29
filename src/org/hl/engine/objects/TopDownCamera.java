package org.hl.engine.objects;

import org.hl.engine.math.lalg.Vector3f;

public class TopDownCamera extends FixedCamera {
	public TopDownCamera(Vector3f position) {
		super(position, new Vector3f(90, 0, 0));
	}
}
