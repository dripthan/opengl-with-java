package everything;

public class Model {

	private int vao;
	private int ebo;
	private int numIndices;
	
	public Model(int vao, int ebo, int numIndices) {
		this.vao = vao;
		this.ebo = ebo;
		this.numIndices = numIndices;
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
}
