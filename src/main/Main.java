package main;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import everything.Entity;
import everything.Loader;
import everything.Model;
import everything.Renderer;
import everything.Shader;
import everything.Window;

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
		1.f, 1.f, 0.f,
		0.f, 1.f, 1.f,
		1.f, 0.f, 1.f,
		0.f, 1.f, 0.f
	};
	private int[] quadIndices = {
		0, 1, 2,
		0, 2, 3
	};
	
	public Main() {
		
		GL.createCapabilities();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		Model model = loader.loadModel(quadPositions, quadColors, quadIndices);
		Shader shader = new Shader(
			loader.loadTextFromFile("src/shaders/simple.vert"),
			loader.loadTextFromFile("src/shaders/simple.frag"));
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(new Entity(
				new Vector3f(0.f, 0.f, 0.f),
				new Vector3f(0.f, 0.f, 0.f),
				new Vector3f(1.f, 1.f, 1.f)));
		entities.add(new Entity(
				new Vector3f(2.f, 0.f, 0.f),
				new Vector3f(0.f, 0.f, 0.f),
				new Vector3f(1.f, 1.f, 1.f)));
		
		while (!window.shouldClose()) {
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