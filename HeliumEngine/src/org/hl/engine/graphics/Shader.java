package org.hl.engine.graphics;

import org.hl.engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
    private String vertexFile;
    private String fragmentFile;

    private int vertexID, fragmentID, programID;

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

        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);

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
        GL20.glDeleteProgram(programID);
    }
}
