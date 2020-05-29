package org.hl.engine.objects;

import org.hl.engine.graphics.Material;
import org.hl.engine.graphics.Mesh;
import org.hl.engine.graphics.Texture;
import org.hl.engine.graphics.Vertex;
import org.hl.engine.math.lalg.Vector2f;
import org.hl.engine.math.lalg.Vector3f;
import org.hl.engine.objects.yloaders.YMesh;
import org.hl.engine.objects.yloaders.YPoint;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;


public class GameObject {
	private Vector3f position, rotation, scale;
	private Mesh mesh;

	public GameObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
	}

	public GameObject(String meshFileName, Vector3f position, Vector3f rotation, Vector3f scale) throws Exception {
		if (!meshFileName.endsWith(".mesh")) { throw new Exception("Wrong file type! "); }
		Yaml yaml = new Yaml();
		FileInputStream inputStream = new FileInputStream(meshFileName);
		YMesh yMesh = yaml.loadAs(inputStream, YMesh.class);
		Integer[] cull = yMesh.getCull().toArray(new Integer[yMesh.getCull().size()]);
		String type = yMesh.getType();
		String texture = yMesh.getTexture();
		YPoint[] vertices = yMesh.getVertices().toArray(new YPoint[yMesh.getVertices().size()]);
		Vertex[] meshFormat = new Vertex[vertices.length];

		for (YPoint vertex : vertices) {
			if (vertex.getVertex().size() != 3) {
				throw new Exception("Incorrect number of coordinates. ");
			}
			if (vertex.getColor().size() != 3) {
				throw new Exception("Incorrect number of color values. ");
			}
			if (vertex.getTexture().size() != 2) {
				throw new Exception("Incorrect number of texture coordinates. ");
			}
		}
		if (!type.equals("texture") && !type.equals("color")) {
			throw new Exception("Incorrect type. Type can only be texture or color. ");
		}
		for (int i = 0; i < vertices.length; i ++) {
			Vertex value = new Vertex(
					new Vector3f(vertices[i].getVertex().get(0), vertices[i].getVertex().get(1), vertices[i].getVertex().get(2)),
					new Vector3f(vertices[i].getColor().get(0), vertices[i].getColor().get(1), vertices[i].getColor().get(2)),
					new Vector2f(vertices[i].getTexture().get(0), vertices[i].getTexture().get(1)));
			meshFormat[i] = value;
		}
		int[] indices = new int[cull.length];
		for (int j = 0; j < cull.length; j ++) {
			indices[j] = cull[j];
		}
		this.mesh = new Mesh(meshFormat, indices, new Material(new Texture(texture)), type);
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;

	}


	public void create() {
		mesh.create();
	}

	public void destroy() {
		mesh.destroy();
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GameObject that = (GameObject) o;
		return Objects.equals(getPosition(), that.getPosition()) &&
				Objects.equals(getRotation(), that.getRotation()) &&
				Objects.equals(getScale(), that.getScale()) &&
				Objects.equals(getMesh(), that.getMesh());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPosition(), getRotation(), getScale(), getMesh());
	}
}
