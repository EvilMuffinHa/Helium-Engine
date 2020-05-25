import org.hl.engine.graphics.*;
import org.hl.engine.io.Display;
import org.hl.engine.io.Input;
import org.hl.engine.math.lalg.Vector3f;
import org.hl.engine.math.lalg.Vector2f;
import org.lwjgl.glfw.GLFW;



public class Test {
	// Defining original parts of the game
	public final static int WIDTH = 640, HEIGHT = 480;
	public final String windowName = "Game!";
	public Display display;
	public Input i;
	public Renderer renderer;
	public Shader shader;

	public Mesh mesh = new Mesh(new Vertex[] {
			new Vertex(new Vector3f(-0.5F, 0.5F, 0.0F), new Vector3f(0, 0, 1.0F), new Vector2f(0, 0)),
			new Vertex(new Vector3f(-0.5F, -0.5F, 0.0F), new Vector3f(0, 0, 1.0F), new Vector2f(0, 1)),
			new Vertex(new Vector3f(0.5F, -0.5F, 0.0F), new Vector3f(1.0F, 0, 1.0F), new Vector2f(1, 1)),
			new Vertex(new Vector3f(0.5F, 0.5F, 0.0F), new Vector3f(1.0F, 0, 1.0F), new Vector2f(1, 0)),

	}, new int[] {
			0, 1, 2,
			0, 2, 3

	}, new Material(new Texture("resources/textures/thonk.png")));



	public void run() throws Exception {
		setup();
		i = new Input(display);
		while (!(display.shouldClose()) && !i.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			loop();
		}

		close();

	}

	public void loop() {
		update();
		render();
	}


	public void setup() throws Exception {
		//First, set up the display
		display = new Display(WIDTH, HEIGHT, windowName);
		display.create();

		// Open the shaders (not necessary there are textures)
		shader = new Shader("/resources/shaders/mainVertex.glsl", "/resources/shaders/mainFragment.glsl");

		// Set up the renderer
		renderer = new Renderer(shader);

		// Changing the background color
		display.setBackgroundColor(1F, 1F, 1F);

		// Creating / displaying the mesh
		mesh.create();

		// Creating the shader
		shader.create();

	}

	// Updating
	private void update() {
		// Just updating lmfao
		int frames = display.update();
		display.setWindowName(display.getWindowName().substring(0, 4) + " (Frames : " + frames + ")");

		i.reset();

	}

	private void render() {
		// rendering the mesh
		renderer.renderMesh(mesh);
		//swap buffers so the new one will appear
		display.swapBuffers();

	}

	private void close() {
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
