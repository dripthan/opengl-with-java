package everything;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL46;

import main.Main;

public class Renderer {
	
	private float aspect = Main.WIDTH / Main.HEIGHT;
	private float fov = 120.f;
	private float zNear = 0.01f;
	private float zFar = 1000.f;
	
	private Vector3f position = new Vector3f(0.f, 0.f, 5.f);
	private Vector3f direction = new Vector3f(0.f, 0.f, 0.f);
	private Vector3f up = new Vector3f(0.f, 1.f, 0.f);
	
	private Matrix4f proj = new Matrix4f();
	private Matrix4f view = new Matrix4f();
	
	public Renderer() {
		GL46.glEnable(GL46.GL_VERTEX_PROGRAM_POINT_SIZE);
		GL46.glClearColor(0.f, 0.f, 0.f, 1.f);
	}
	
	public void prepareFrame() {
		GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
	}

	public void renderEntities(List<Entity> entities, Model model, Shader shader) {
		GL46.glBindVertexArray(model.getVAO());
		GL46.glEnableVertexAttribArray(0);
		GL46.glEnableVertexAttribArray(1);
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, model.getEBO());
		for (Entity e : entities) {
			shader.setMatrix("model", e.getModel());
			GL46.glDrawElements(GL46.GL_TRIANGLES, model.getNumIndices(), GL46.GL_UNSIGNED_INT, 0);
		}
		GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0);
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
