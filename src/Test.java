import org.hl.engine.graphics.*;
import org.hl.engine.io.Display;
import org.hl.engine.io.Input;
import org.hl.engine.math.lalg.Vector3f;
import org.hl.engine.math.lalg.Vector2f;
import org.hl.engine.objects.FirstPersonCamera;
import org.hl.engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;



public class Test extends Game {
	// Defining original parts of the game
	public final static int WIDTH = 1280, HEIGHT = 760;
	public final String windowName = "Game!";
	public Display display;
	public Input i;
	public Renderer renderer;
	public Shader shader;

	public Mesh plane = new Mesh(new Vertex[] {
			new Vertex(new Vector3f(-20, -0.5000001f, 20), new Vector2f(0, 0)),
			new Vertex(new Vector3f(-20, -0.5000001f, -20), new Vector2f(0, 1)),
			new Vertex(new Vector3f(20, -0.5000001f, -20), new Vector2f(1, 1)),
			new Vertex(new Vector3f(20, -0.5000001f, 20), new Vector2f(1, 0)),
	}, new int[] {
			0, 1, 3,
			3, 1, 2

	}, new Material(new Texture("resources/textures/thonk.png")));

	public Mesh mesh = new Mesh(new Vertex[] {
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
	}, new Material(new Texture("resources/textures/b.png")));

	public boolean lockToggle = false;

	public GameObject testObject = new GameObject(mesh, new Vector3f(0, 0, 0 ), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public GameObject testingPlane = new GameObject(plane, new Vector3f(0, 0, 0 ), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public FirstPersonCamera camera = new FirstPersonCamera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0), 0.05f, 0.15f);

	public void run() throws Exception {
		setup();
		while (!(display.shouldClose())) {
			loop();
		}

		close();

	}

	public void loop(){


		//First updating
		int frames = display.update();
		display.setWindowName(display.getWindowName().substring(0, 4) + " (Frames : " + frames + ")");
		if (display.isLocked()) {
			camera.update();
		}
		if (i.buttonPress(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			lockToggle = !lockToggle;
			display.mouseState(lockToggle);
		} else if (i.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			lockToggle = false;
			display.mouseState(lockToggle);
		}


		i.reset();


		// Now Render!


		// rendering the mesh
		renderer.renderMesh(testObject, camera);
		renderer.renderMesh(testingPlane, camera);
		//swap buffers so the new one will appear
		display.swapBuffers();
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

		// Creating / displaying the mesh
		mesh.create();
		plane.create();

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
		mesh.destroy();
		shader.destroy();
		plane.destroy();
	}

	public static void main(String[] args) throws Exception {
		//Running
		new Test().run();
	}
}
