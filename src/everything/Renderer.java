package everything;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

public class Renderer {
	
	private float aspect = Main.WIDTH / Main.HEIGHT;
	private float fov = 120;
	private float zNear = 0.01f;
	private float zFar = 1000;
	
	private Vector3f position = new Vector3f(0, 0, 25);
	private Vector3f direction = new Vector3f(0, 0, 0);
	private Vector3f up = new Vector3f(0, 1, 0);
	
	private Matrix4f proj = new Matrix4f();
	private Matrix4f view = new Matrix4f();
	
	private FloatBuffer memBuffer = MemoryUtil.memAllocFloat(16 * 1000000);
	
	public Renderer() {
		GL46.glEnable(GL46.GL_VERTEX_PROGRAM_POINT_SIZE);
		GL46.glClearColor(0, 0, 0, 1);
	}
	
	public void prepareFrame() {
		GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
	}

	public void renderEntities(List<Entity> entities, Model model, Shader shader) {
		memBuffer.clear();
		int i = 0;
		for (Entity e : entities) {
			e.getModel().get(16 * i, memBuffer);
			i++;
		}
		
		GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, model.getModelMatrixVBO());
		GL46.glBufferData(GL46.GL_ARRAY_BUFFER, memBuffer, GL46.GL_DYNAMIC_DRAW);
		GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
		
		GL46.glBindVertexArray(model.getVAO());
		GL46.glEnableVertexAttribArray(0);
		GL46.glEnableVertexAttribArray(1);
		GL46.glEnableVertexAttribArray(2);
		GL46.glEnableVertexAttribArray(3);
		GL46.glEnableVertexAttribArray(4);
		GL46.glEnableVertexAttribArray(5);
		
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, model.getEBO());
		GL46.glDrawElementsInstanced(GL46.GL_LINE_LOOP, model.getNumIndices(), GL46.GL_UNSIGNED_INT, 0, entities.size());
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GL46.glDisableVertexAttribArray(5);
		GL46.glDisableVertexAttribArray(4);
		GL46.glDisableVertexAttribArray(3);
		GL46.glDisableVertexAttribArray(2);
		GL46.glDisableVertexAttribArray(1);
		GL46.glDisableVertexAttribArray(0);
		GL46.glBindVertexArray(0);
	}
	
	public Matrix4f getProjView() {
		proj.identity();
		proj.perspective(fov, aspect, zNear, zFar);
		view.identity();
		view.lookAt(position, direction, up);
		return proj.mul(view);
	}
}
