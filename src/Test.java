import org.hl.engine.graphics.*;
import org.hl.engine.io.Display;
import org.hl.engine.io.Input;
import org.hl.engine.math.lalg.*;
import org.lwjgl.glfw.GLFW;



public class Test {
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

    }, new Material("/textures/beautiful.png"));

    public void run() {
        init();
        i = new Input(display);
        while (!(display.shouldClose()) && !i.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
        }

        close();

    }
    public void init() {
        // System.out.println("Initializing Game ");
        display = new Display(WIDTH, HEIGHT, windowName);
        shader = new Shader("/resources/shaders/mainVertex.glsl", "/resources/shaders/mainFragment.glsl");
        renderer = new Renderer(shader);
        display.setBackgroundColor(1F, 0, 0);
        display.create();
        mesh.create();
        shader.create();

    }
    private void update() {
        // System.out.println("Updating ");
        int frames = display.update();
        display.setWindowName(display.getWindowName().substring(0, 4) + " (Frames : " + frames + ")");

        i.reset();

    }

    private void render() {
        // System.out.println("Rendering ");
        renderer.renderMesh(mesh);
        display.swapBuffers();

    }

    private void close() {
        display.destroy();
        mesh.destroy();
        shader.destroy();
    }

    public static void main(String[] args) {
        new Test().run();
    }
}
