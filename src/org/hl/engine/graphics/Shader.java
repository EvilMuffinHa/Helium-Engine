package org.hl.engine.graphics;

import org.hl.engine.math.lalg.Matrix4f;
import org.hl.engine.math.lalg.Vector2f;
import org.hl.engine.math.lalg.Vector3f;
import org.hl.engine.math.lalg.Vector4f;
import org.hl.engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {
	private String vertexFile;
	private String fragmentFile;

	private int vertexID, fragmentID, programID;

	public static final String VERTEXSHADER = "/resources/shaders/mainVertex.glsl";
	public static final String FRAGSHADER = "/resources/shaders/mainFragment.glsl";

	public Shader(String vertexPath, String fragmentPath) {
		vertexFile = FileUtils.loadAsString(vertexPath);
		fragmentFile = FileUtils.loadAsString(fragmentPath);

	}

	public void create() {

		// Creates the program
		programID = GL20.glCreateProgram();


		// loads the vertex shader
		vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

		GL20.glShaderSource(vertexID, vertexFile);
		GL20.glCompileShader(vertexID);

		if (GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Vertex Shader: " + GL20.glGetShaderInfoLog(vertexID));
			System.exit(1);

		}

		// loads the fragment shader
		fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		GL20.glShaderSource(fragmentID, fragmentFile);
		GL20.glCompileShader(fragmentID);

		if (GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Fragment Shader: " + GL20.glGetShaderInfoLog(fragmentID));
			System.exit(1);

		}

		// Attach shaders to program
		GL20.glAttachShader(programID, vertexID);
		GL20.glAttachShader(programID, fragmentID);

		// Link the program
		GL20.glLinkProgram(programID);
		if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(programID));
			System.exit(1);
			return;
		}

		// Validate the program
		GL20.glValidateProgram(programID);
		if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programID));
			System.exit(1);
			return;
		}

	}

	public int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(programID, name);
	}

	public void setUniform(String name, float value) {
		GL20.glUniform1f(getUniformLocation(name), value);
	}

	public void setUniform(String name, int value) {
		GL20.glUniform1i(getUniformLocation(name), value);
	}

	public void setUniform(String name, boolean value) {
		GL20.glUniform1i(getUniformLocation(name), value ? 1 : 0);
	}
	public void setUniform(String name, Vector2f value) {
		GL20.glUniform2f(getUniformLocation(name), value.getX(), value.getY());

	}

	public void setUniform(String name, Vector3f value) {
		GL20.glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
	}

	public void setUniform(String name, Vector4f value) {
		GL20.glUniform4f(getUniformLocation(name), value.getX(), value.getY(), value.getZ(), value.getA());
	}

	public void setUniform(String name, Matrix4f value) {
		FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
		matrix.put(value.convertTo1D()).flip();
		GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
	}

	// Bind so we can use the shader
	public void bind() {
		GL20.glUseProgram(programID);
	}

	// Unbind the shader after use
	public void unbind() {
		GL20.glUseProgram(0);
	}

	// Destroy the program
	public void destroy() {
		GL20.glDetachShader(programID, vertexID);
		GL20.glDetachShader(programID, fragmentID);

		GL20.glDeleteShader(vertexID);
		GL20.glDeleteShader(fragmentID);

		GL20.glDeleteProgram(programID);
	}
}
