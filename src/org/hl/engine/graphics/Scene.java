package org.hl.engine.graphics;

import org.hl.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Scene {

	private ArrayList<GameObject> objects;

	public Scene() {
		objects = new ArrayList();
	}

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}

	public void setObjects(GameObject[] objects) {
		this.objects.addAll(Arrays.asList(objects));
	}

	public void addObject(GameObject object) {
		this.objects.add(object);
	}

	public void removeObject(int index) {
		this.objects.remove(index);
	}

	public GameObject getObject(int index) {
		return this.objects.get(index);
	}

}
