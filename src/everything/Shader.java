package everything;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;

public class Shader {

	private int program;
	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public Shader(String vsSource, String fsSource) {
		this.program = GL46.glCreateProgram();
		int vs = createShader(GL46.GL_VERTEX_SHADER, vsSource);
		int fs = createShader(GL46.GL_FRAGMENT_SHADER, fsSource);
		GL46.glAttachShader(program, vs);
		GL46.glAttachShader(program, fs);
		GL46.glDeleteShader(vs);
		GL46.glDeleteShader(fs);
		GL46.glLinkProgram(program);
		GL46.glValidateProgram(program);
	}
	
	private int createShader(int type, String source) {
		int shader = GL46.glCreateShader(type);
		GL46.glShaderSource(shader, source);
		GL46.glCompileShader(shader);
		if (GL46.glGetShaderi(shader, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE) {
			System.out.println(GL46.glGetShaderInfoLog(shader, 512));
			System.err.println("cant compile shader");
			System.exit(-1);
		}
		return shader;
	}
	
	public void bind() {
		GL46.glUseProgram(program);
	}
	
	public void unbind() {
		GL46.glUseProgram(0);
	}
	
	public void setMatrix(String name, Matrix4f value) {
		matrixBuffer.clear();
		value.get(matrixBuffer);
		GL46.glUniformMatrix4fv(GL46.glGetUniformLocation(program, name), false, matrixBuffer);
	}
	
	public int getProgram() {
		return program;
	}
}
