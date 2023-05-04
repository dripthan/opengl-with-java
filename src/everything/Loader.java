package everything;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	
	public Model loadModel(float[] positions, float[] colors, int[] indices) {
		int vao = GL46.glGenVertexArrays();
		vaos.add(vao);
		GL46.glBindVertexArray(vao);
		loadAttrib(0, 2, false, positions);
		loadAttrib(1, 3, false, colors);
		GL46.glBindVertexArray(0);
		int ebo = loadIndices(indices);
		return new Model(vao, ebo, indices.length);
	}
	
	private void loadAttrib(int location, int size, boolean normalized, float[] data) {
		FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.length).put(data).flip();
		int vbo = GL46.glGenBuffers();
		vbos.add(vbo);
		GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo);
		GL46.glBufferData(GL46.GL_ARRAY_BUFFER, dataBuffer, GL46.GL_STATIC_DRAW);
		GL46.glVertexAttribPointer(location, size, GL46.GL_FLOAT, normalized, 0, 0);
		GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
	}
	
	private int loadIndices(int[] data) {
		IntBuffer dataBuffer = BufferUtils.createIntBuffer(data.length).put(data).flip();
		int ebo = GL46.glGenBuffers();
		vbos.add(ebo);
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, dataBuffer, GL46.GL_STATIC_DRAW);
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0);
		return ebo;
	}
	
	public String loadTextFromFile(String path) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while((line = reader.readLine()) !=null) shaderSource.append(line).append("\n");
			reader.close();
			return shaderSource.toString();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
	}
	
	public void freeMemory() {
		for (int vao : vaos) GL46.glDeleteVertexArrays(vao);
		for (int vbo : vbos) GL46.glDeleteBuffers(vbo);
	}
}
