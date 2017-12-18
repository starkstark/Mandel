public class MandelRequest {
	
	public int width, height;	
	public double xCenter, yCenter;
	public double scale;
	public int maxIterations;
	
	public MandelRequest() {
		this.width   = (int)(400 * 3.5);
		this.height  = 400 * 2;
		this.scale   = 1;
		this.xCenter = 0;
		this.yCenter = 0;
		this.maxIterations = 500;
	}
	
	public void updateCenter(int x, int y) {
		this.xCenter -= ((x / (double) this.width)  * 3.0 - 2.5) * this.scale;
		this.yCenter -= ((y / (double) this.height) * 2.0 - 1)   * this.scale;
	}
	
	public double getX(int x) {
		return ((x / (double) this.width)  * 3.0 - (2.5 + this.xCenter)) * this.scale;
	}
	
	public double getY(int y) {
		return ((y / (double) this.height) * 2.0 - (1   + this.yCenter)) * this.scale;
	}

	// logarithmic scaling
	public void increaseScale() {
		this.scale += this.scale / 2.0;
	}
	
	// logarithmic scaling
	public void decreaseScale() {
		this.scale -= this.scale / 2.0;
	}

}