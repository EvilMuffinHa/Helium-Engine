package org.hl.engine.io;

import org.hl.engine.math.lalg.Matrix4f;
import org.hl.engine.math.lalg.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;

public class Display {

	private int width, height;
	private String windowName;
	private long window;
	public int frames;
	public int previousFrames = frames;
	public long time;
	public Input input;
	private Vector3f background = new Vector3f(0, 0, 0);
	private GLFWWindowSizeCallback resizeCallback;
	private boolean isResized;
	private boolean isFullscreen;
	private int[] windowXPos = new int[1];
	private int[] windowYPos = new int[1];
	private GLFWVidMode videoMode;
	private int savedPosX;
	private int savedPosY;
	private int savedWidth;
	private int savedHeight;
	private Matrix4f projection;
	private boolean isLocked;
	private double[] cursorX = new double[1];
	private double[] cursorY = new double[1];
	private double[] enabledCursorX = new double[1];
	private double[] enabledCursorY = new double[1];



	// Constructor to create the display
	public Display (int width, int height, String windowName, float fov, float near, float far) {
		this.width = width;
		this.height = height;
		this.windowName = windowName;
		projection = Matrix4f.projection(fov, (float)this.width / (float) this.height, near, far);
	}

	// Change the window name
	public void setWindowName(String windowName) {
		this.windowName = windowName;
		glfwSetWindowTitle(window, windowName);
	}

	// Getters for size, name, window, time, and fullScreen
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public String getWindowName() {
		return windowName;
	}
	public long getWindow() {
		return window;
	}
	public long getTime() {
		return time;
	}

	public boolean isFullscreen() {
		return isFullscreen;
	}

	public Matrix4f getProjectionMatrix() {
		return projection;
	}

	// Makes the screen fullscreen or not based on the argument
	public void setFullscreen(boolean fullscreen) {
		isFullscreen = fullscreen;
		isResized = true;
		GL11.glViewport(0, 0, width, height);
		if (isFullscreen) {

			int[] xpos = {0};
			int[] ypos = {0};
			glfwGetWindowPos(this.window, xpos, ypos);
			savedPosX = xpos[0];
			savedPosY = ypos[0];

			savedWidth = width;
			savedHeight = height;
			glfwGetWindowPos(window, windowXPos, windowYPos);
			glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, videoMode.width(), videoMode.height(), 0);
		} else {
			glfwSetWindowMonitor(window, 0, savedPosX, savedPosY, savedWidth, savedHeight, 0);
		}
	}

	public void mouseState(boolean lock) {
		if (lock != isLocked) {
			if (lock) {
				glfwGetCursorPos(window, enabledCursorX, enabledCursorY);
				glfwSetCursorPos(window, cursorX[0], cursorY[0]);
				isLocked = lock;
				glfwSetInputMode(window, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
			} else {
				glfwGetCursorPos(window, cursorX, cursorY);
				isLocked = lock;
				glfwSetInputMode(window, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
				glfwSetCursorPos(window, enabledCursorX[0], enabledCursorY[0]);
			}
		}
	}

	public boolean isLocked() {
		return isLocked;
	}

	// resized getter

	public boolean isResized() {
		return isResized;
	}


	// Creates the window (should go in the init() function of your Main program)
	public void create() throws Exception {

		GLFWErrorCallback.createPrint(System.err).set();

		// initializing glfw
		if (!glfwInit()) {
			//System.err.println("Failed to initialize GLFW! ");
			//System.exit(1);
			throw new Exception("Failed to initialize GLFW! ");
		}
		if(System.getProperty("os.name").contains("Mac")) {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		} else {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		}
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		//Creating window
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = glfwCreateWindow(this.width, this.height, this.windowName, isFullscreen ? glfwGetPrimaryMonitor():0, 0);
		if (window == 0) {
			//System.err.println("Failed to create window! ");
			//System.exit(1);
			throw new Exception("Failed to create window! ");
		}

		// Setting size of window

		videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		windowXPos[0] = (videoMode.width() - this.width) / 2;
		windowYPos[0] = (videoMode.height() - this.height ) / 2;
		glfwSetWindowPos(window, windowXPos[0], windowYPos[0]);

		glfwSetCursorPos(window, 0, 0);


		// Graphics
		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		GL11.glEnable(GL11.GL_DEPTH_TEST);

		callBacks();
		glfwShowWindow(window);
		glfwSwapInterval(1);

		// setting time

		time = System.currentTimeMillis();

	}


	// Creating the resize callback (all other callbacks were removed and are now in Input class)
	private void callBacks() {

		resizeCallback = new GLFWWindowSizeCallback() {

			@Override
			public void invoke(long window, int w, int h) {
				width = w;
				height = h;
				isResized = true;
			}
		};


		glfwSetWindowSizeCallback(window, resizeCallback);
	}

	// Refreshes the screen, resets frame count
	public int update() {
		if (isResized) {
			GL11.glViewport(0, 0, width, height);
			isResized = false;
		}
		GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT |  GL11.GL_DEPTH_BUFFER_BIT);
		glfwPollEvents();
		frames++;
		if (System.currentTimeMillis() > time + 1000) {
			previousFrames = frames;
			time = System.currentTimeMillis();
			frames = 0;
			return frames;
		} else {
			return previousFrames;

		}
	}

	// Terminates the program (making WindowShouldClose)

	public void terminate() {
		glfwSetWindowShouldClose(window, true);
	}


	// Completely DESTROYS the window
	public void destroy() {
		resizeCallback.free();
		glfwSetErrorCallback(null).free();
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	// switches the buffers (for rendering)

	public void swapBuffers() {
		glfwSwapBuffers(window);
	}


	// get whether the window should close
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}

	// changes the background color

	public void setBackgroundColor(float r, float g, float b) {
		background.setVector(r, g, b);
	}

	public void reset() {
		swapBuffers();
	}
}