import org.hl.engine.graphics.*;
import org.hl.engine.io.Display;
import org.hl.engine.io.Input;
import org.hl.engine.math.lalg.Vector3f;
import org.hl.engine.math.lalg.Vector2f;
import org.hl.engine.objects.FirstPersonCamera;
import org.hl.engine.objects.GameObject;
import org.hl.engine.objects.ThirdPersonCamera;
import org.hl.engine.utils.FileUtils;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;


public class Test implements Game {
	// Defining original parts of the game
	public final static int WIDTH = 1280, HEIGHT = 760;
	public final String windowName = "Game!";
	public Display display;
	public Input i;
	public Renderer renderer;
	public Shader shader;

	public boolean lockToggle = false;

	public GameObject cubeObject = new GameObject(new Mesh(new Vertex[] {
			//Back face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

			//Front face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Right face
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Left face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Top face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

			//Bottom face
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
	}, new int[] {
			//Back face
			0, 1, 3,
			3, 1, 2,

			//Front face
			4, 5, 7,
			7, 5, 6,

			//Right face
			8, 9, 11,
			11, 9, 10,

			//Left face
			12, 13, 15,
			15, 13, 14,

			//Top face
			16, 17, 19,
			19, 17, 18,

			//Bottom face
			20, 21, 23,
			23, 21, 22
	}, new Material(new Texture("resources/textures/b.png")), "texture"), new Vector3f(0, 0, 0 ), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public GameObject planeObject = new GameObject("resources/objects/plane.mesh", new Vector3f(0, 0, 0 ), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public GameObject dragonObject = new GameObject("resources/models/dragon.obj", "resources/textures/b.png", new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public ThirdPersonCamera camera = new ThirdPersonCamera(new Vector3f(0, 0, 5), new Vector3f(0, 0, 0), cubeObject, 0.5f, 5, 0.1f, 12f, true, true, true);

	// public FirstPersonCamera camera = new FirstPersonCamera(new Vector3f(0, 0, 5), new Vector3f(0, 0, 0), 0.1f, 0.15f);

	public Test() throws Exception {
	}


	public void run() throws Exception {
		setup();
		while (!(display.shouldClose())) {
			loop();
		}

		close();

	}

	public void loop() throws Exception {


		//First updating
		int frames = display.update();
		display.setWindowName(display.getWindowName().substring(0, 4) + " (Frames : " + frames + ")");
		if (display.isLocked()) {
			camera.standardKeybindUpdate();
		}
		if (i.buttonPress(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			if (!lockToggle) {
				lockToggle = true;
				display.mouseState(true);
			}
		} else if (i.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			lockToggle = false;
			display.mouseState(lockToggle);
		}


		i.reset();


		// Now Render!


		// rendering the cube and plane
		renderer.renderMesh(cubeObject, camera);
		renderer.renderMesh(planeObject, camera);
		renderer.renderMesh(dragonObject, camera);
		//swap buffers so the new one will appear

		display.reset();
	}


	public void setup() throws Exception {

		//First, set up the display
		display = new Display(WIDTH, HEIGHT, windowName, 70, 0.1f, 1000f);
		display.create();



		// Open the shaders
		shader = new Shader(Shader.VERTEXSHADER, Shader.FRAGSHADER);

		// Set up the renderer
		renderer = new Renderer(display, shader);

		// Changing the background color
		display.setBackgroundColor(0.53f, .81f, 0.92f);

		// Creating / displaying the cube and plane
		planeObject.create();
		cubeObject.create();
		dragonObject.create();

		// Creating the shader
		shader.create();

		// Creating the input

		i = new Input(display);

		// Creating the camera
		camera.create(i);

	}

	public void close() {
		// Removing everything
		display.destroy();
		shader.destroy();
		cubeObject.destroy();
		planeObject.destroy();
		dragonObject.destroy();
	}

	public static void main(String[] args) throws Exception {
		//Running
		new Test().run();
	}
}
