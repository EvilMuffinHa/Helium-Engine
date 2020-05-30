package org.hl.engine.objects.yloaders ;

import java.util.ArrayList;

public class YMesh implements java.io.Serializable {
	private String type;
	private ArrayList<YPoint> vertices;
	private ArrayList<Integer> cull;
	private String texture;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<YPoint> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<YPoint> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<Integer> getCull() {
		return cull;
	}

	public void setCull(ArrayList<Integer> cull) {
		this.cull = cull;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}
}
