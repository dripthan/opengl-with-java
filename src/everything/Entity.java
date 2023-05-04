package everything;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {

	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	private Matrix4f model = new Matrix4f();
	
	public Entity(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4f getModel() {
		model.identity();
		model.translate(position);
		model.rotate(rotation.get(0), new Vector3f(1.f, 0.f, 0.f));
		model.rotate(rotation.get(1), new Vector3f(0.f, 1.f, 0.f));
		model.rotate(rotation.get(2), new Vector3f(0.f, 0.f, 1.f));
		model.scale(scale);
		return model;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}
}
