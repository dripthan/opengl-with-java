package everything;

public class Model {

	private int vao;
	private int ebo;
	private int numIndices;
	private int modelMatrixVBO;
	
	public Model(int vao, int ebo, int numIndices, int modelMatrixVBO) {
		this.vao = vao;
		this.ebo = ebo;
		this.numIndices = numIndices;
		this.modelMatrixVBO = modelMatrixVBO;
	}
	
	public int getVAO() {
		return vao;
	}
	
	public int getEBO() {
		return ebo;
	}
	
	public int getNumIndices() {
		return numIndices;
	}
	
	public int getModelMatrixVBO() {
		return modelMatrixVBO;
	}
}
