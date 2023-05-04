package everything;

import java.nio.IntBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Window {

	private int width;
	private int height;
	private String title;
	private long window;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		init();
	}
	
	public void init() {
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!GLFW.glfwInit()) throw new IllegalStateException("cant init GLFW");
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		window = GLFW.glfwCreateWindow(
				width, height, title,
				MemoryUtil.NULL,
				MemoryUtil.NULL);
		if (window == MemoryUtil.NULL)  throw new IllegalStateException("cant make window");
		
		GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
				GLFW.glfwSetWindowShouldClose(window, true);
		});
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			GLFW.glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			GLFW.glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(window);
	}

	public void freeMemory() {
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public long getWindow() {
		return window;
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
}
