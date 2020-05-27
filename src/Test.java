import org.hl.engine.graphics.*;
import org.hl.engine.io.Display;
import org.hl.engine.io.Input;
import org.hl.engine.math.lalg.Vector3f;
import org.hl.engine.math.lalg.Vector2f;
import org.hl.engine.objects.Camera;
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

	public Mesh mesh = new Mesh(new Vertex[] {
			new Vertex(new Vector3f(-0.5F, 0.5F, 0.0F), new Vector3f(0, 0, 1.0F), new Vector2f(0, 0)),
			new Vertex(new Vector3f(-0.5F, -0.5F, 0.0F), new Vector3f(0, 0, 1.0F), new Vector2f(0, 1)),
			new Vertex(new Vector3f(0.5F, -0.5F, 0.0F), new Vector3f(1.0F, 0, 1.0F), new Vector2f(1, 1)),
			new Vertex(new Vector3f(0.5F, 0.5F, 0.0F),  new Vector3f(1.0F, 0, 1.0F), new Vector2f(1, 0)),

	}, new int[] {
			0, 1, 2,
			0, 2, 3

	}, new Material(new Texture("resources/textures/b.png")));

	public GameObject testObject = new GameObject(mesh, new Vector3f(0, 0, 0 ), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public Camera camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));

	public void run() throws Exception {
		setup();
		i = new Input(display);
		while (!(display.shouldClose()) && !i.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			loop();
		}

		close();

	}

	public void loop(){


		//First updating
		int frames = display.update();
		display.setWindowName(display.getWindowName().substring(0, 4) + " (Frames : " + frames + ")");

		float ms = 0.05f;
		Vector3f cameraPos = camera.getPosition();
		Vector3f cameraRot = camera.getRotation();
		if (i.isKeyDown(GLFW.GLFW_KEY_A)) cameraPos = Vector3f.add(cameraPos, new Vector3f(-ms, 0, 0));
		if (i.isKeyDown(GLFW.GLFW_KEY_D)) cameraPos = Vector3f.add(cameraPos, new Vector3f(ms, 0, 0));
		if (i.isKeyDown(GLFW.GLFW_KEY_W)) cameraPos = Vector3f.add(cameraPos, new Vector3f(0, 0, -ms));
		if (i.isKeyDown(GLFW.GLFW_KEY_S)) cameraPos = Vector3f.add(cameraPos, new Vector3f(0, 0, ms));
		if (i.isKeyDown(GLFW.GLFW_KEY_SPACE)) cameraPos = Vector3f.add(cameraPos, new Vector3f(0, ms, 0));
		if (i.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) cameraPos = Vector3f.add(cameraPos, new Vector3f(0, -ms, 0));
		camera.setPosition(cameraPos);
		camera.setRotation(cameraRot);


		i.reset();


		// Now Render!


		// rendering the mesh
		renderer.renderMesh(testObject, camera);
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
		display.setBackgroundColor(0F, 0F, 0F);

		// Creating / displaying the mesh
		mesh.create();

		// Creating the shader
		shader.create();

	}

	public void close() {
		// Removing everything
		display.destroy();
		mesh.destroy();
		shader.destroy();
	}

	public static void main(String[] args) throws Exception {
		//Running
		new Test().run();
	}
}
