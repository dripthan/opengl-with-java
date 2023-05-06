package everything;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class Main {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "OpenGLEngine";
	
	private Window window = new Window(WIDTH, HEIGHT, TITLE);
	
	private float[] quadPositions = {
		 0.5f,  0.5f,
		 0.5f, -0.5f,
		-0.5f, -0.5f,
		-0.5f,  0.5f
	};
	private float[] quadColors = {
		1, 1, 0,
		0, 1, 1,
		1, 0, 1,
		0, 1, 0
	};
	private int[] quadIndices = {
		0, 1, 2,
		0, 2, 3
	};
	
	public Main() {
		
		GL.createCapabilities();
		
		Random random = new Random();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		Model model = loader.loadModel(quadPositions, quadColors, quadIndices);
		Shader shader = new Shader(
			loader.loadTextFromFile("src/shaders/simple.vert"),
			loader.loadTextFromFile("src/shaders/simple.frag"));
		List<Entity> entities = new ArrayList<Entity>();
		
		while (!window.shouldClose()) {
			
			System.out.println(entities.size());
			
			for (int i = 0; i < 10; i++) {
				entities.add(new Entity(
						new Vector3f(0, 0, 0),
						new Vector3f(0, 0, 0),
						new Vector3f(1, 1, 1),
						new Vector3f((random.nextFloat() - 0.5f) * 0.1f, (random.nextFloat() - 0.5f) * 0.1f, 0),
						new Vector3f(random.nextFloat() * 0.1f, random.nextFloat() * 0.1f, random.nextFloat() * 0.1f),
						new Vector3f(-random.nextFloat() * 0.01f, -random.nextFloat() * 0.01f, -random.nextFloat() * 0.01f)));				
			}
			
			Iterator<Entity> it = entities.iterator();
			while (it.hasNext()) {
				Entity e = it.next();
				e.tick();
				if (e.isDead()) it.remove();
			}
			
			renderer.prepareFrame();
			shader.bind();
			shader.setMatrix("projView", renderer.getProjView());
			renderer.renderEntities(entities, model, shader);
			shader.unbind();
			GLFW.glfwSwapBuffers(window.getWindow());
			GLFW.glfwPollEvents();
		}
		
		loader.freeMemory();
		window.freeMemory();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}