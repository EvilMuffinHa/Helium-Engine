package org.hl.engine.graphics;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

public class Texture {
	private int id;

	private int width;

	private int height;

	private int type;

	private String fileName;
	private ByteBuffer imageBuffer;

	private int pixelFormat;


	public Texture(int width, int height, int pixelFormat) {
		this.type = 0;
		this.width = width;
		this.height = height;
		this.pixelFormat = pixelFormat;
	}

	public Texture(String fileName) {
		this.type = 1;
		this.fileName = fileName;
	}

	public Texture(ByteBuffer imageBuffer) {
		type = 2;
		this.imageBuffer = imageBuffer;
	}

	public void create() {
		if (this.type == 0) {
			this.id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, this.id);
			glTexImage2D(GL_TEXTURE_2D, 0, this.pixelFormat, this.width, this.height, 0, this.pixelFormat, GL_FLOAT, (ByteBuffer) null);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glBindTexture(GL_TEXTURE_2D, 0);

		} else if (this.type == 1) {
			ByteBuffer buf;
			try(MemoryStack stack = MemoryStack.stackPush()) {
				IntBuffer w = stack.mallocInt(1);
				IntBuffer h = stack.mallocInt(1);
				IntBuffer channels = stack.mallocInt(1);

				buf = stbi_load(this.fileName, w, h, channels, 4);
				if(buf == null) {
					System.err.println("Image file [" + this.fileName + "] not loaded: " + stbi_failure_reason());
					System.exit(1);
				}

				this.width = w.get();
				this.height = h.get();
			}

			this.id = createTexture(buf);

			stbi_image_free(buf);

		} else {
			ByteBuffer buf;
			try(MemoryStack stack = MemoryStack.stackPush()) {
				IntBuffer w = stack.mallocInt(1);
				IntBuffer h = stack.mallocInt(1);
				IntBuffer channels = stack.mallocInt(1);

				buf = stbi_load_from_memory(this.imageBuffer, w, h, channels, 4);
				if(buf == null) {
					System.err.println("Image file not loaded: " + stbi_failure_reason());
					System.exit(1);
				}

				this.width = w.get();
				this.height = h.get();
			}

			this.id = createTexture(buf);

			stbi_image_free(buf);

		}

	}

	private int createTexture(ByteBuffer buf) {
		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);

		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
		glGenerateMipmap(GL_TEXTURE_2D);

		glBindTexture(GL_TEXTURE_2D, 0);

		return textureID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getId() {
		return id;
	}

	public void destroy() {
		glDeleteTextures(id);
	}
}



