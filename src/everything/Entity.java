package everything;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {

	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;
	private Vector3f velocity;
	private Vector3f spin;
	private Vector3f grow;
	private Matrix4f model = new Matrix4f();
	private boolean dead = false;
	
	public Entity(Vector3f position, Vector3f rotation, Vector3f scale,
			Vector3f velocity, Vector3f spin, Vector3f grow) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.velocity = velocity;
		this.spin = spin;
		this.grow = grow;
	}
	
	public void die() {
		dead = true;
	}

	public void tick() {
		position.add(velocity);
		rotation.add(spin);
		scale.add(grow);
		if (scale.x() < 0 || scale.y() < 0 || scale.z() < 0) die();
	}
	
	public Matrix4f getModel() {
		model.identity();
		model.translate(position);
		model.rotate(rotation.get(0), new Vector3f(1, 0, 0));
		model.rotate(rotation.get(1), new Vector3f(0, 1, 0));
		model.rotate(rotation.get(2), new Vector3f(0, 0, 1));
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

	public Vector3f getVelocity() {
		return velocity;
	}

	public Vector3f getSpin() {
		return spin;
	}

	public Vector3f getGrow() {
		return grow;
	}

	public boolean isDead() {
		return dead;
	}
}
