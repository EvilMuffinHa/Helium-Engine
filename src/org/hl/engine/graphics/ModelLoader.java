package org.hl.engine.graphics;

import org.hl.engine.math.lalg.Vector2f;
import org.hl.engine.math.lalg.Vector3f;
import org.lwjgl.assimp.*;

public class ModelLoader {
	public static Mesh loadModel(String path, String texture) throws Exception {
		AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate);

		if (scene == null) {
			throw new Exception("Couldn't load model at " + path);
		}

		AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
		int vertexCount = mesh.mNumVertices();

		AIVector3D.Buffer vertices = mesh.mVertices();
		AIVector3D.Buffer normals = mesh.mNormals();

		Vertex[] vertexList = new Vertex[vertexCount];

		for (int i = 0; i < vertexCount; i ++) {
			AIVector3D vertex = vertices.get(i);
			Vector3f engineVertex = new Vector3f(vertex.x(), vertex.y(), vertex.z());

			AIVector3D normal = normals.get(i);
			Vector3f engineNormal = new Vector3f(normal.x(), normal.y(), normal.z());

			Vector2f meshTextureCoord = new Vector2f(0, 0);

			if (mesh.mNumUVComponents().get(0) != 0) {
				AIVector3D tex = mesh.mTextureCoords(0).get(i);
				meshTextureCoord.setX(tex.x());
				meshTextureCoord.setY(tex.y());
			}

			vertexList[i] = new Vertex(engineVertex, meshTextureCoord, engineNormal);

		}

		int faceCount = mesh.mNumFaces();
		AIFace.Buffer indices = mesh.mFaces();
		int[] indicesList = new int[faceCount*3];

		for (int i = 0; i < faceCount; i ++) {
			AIFace face = indices.get(i);
			indicesList[i * 3 + 0] = face.mIndices().get(0);
			indicesList[i * 3 + 1] = face.mIndices().get(1);
			indicesList[i * 3 + 2] = face.mIndices().get(2);

		}

		return new Mesh(vertexList, indicesList, new Material(new Texture(texture)), "texture");


	}
}



