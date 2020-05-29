package org.hl.engine.objects.yloaders;

import java.util.ArrayList;

public class YPoint implements java.io.Serializable  {
	private ArrayList<Float> vertex;
	private ArrayList<Float> texture;
	private ArrayList<Float> color;

	public ArrayList<Float> getVertex() {
		return vertex;
	}

	public void setVertex(ArrayList<Float> vertex) {
		this.vertex = vertex;
	}

	public ArrayList<Float> getTexture() {
		return texture;
	}

	public void setTexture(ArrayList<Float> texture) {
		this.texture = texture;
	}

	public ArrayList<Float> getColor() {
		return color;
	}

	public void setColor(ArrayList<Float> color) {
		this.color = color;
	}
}
