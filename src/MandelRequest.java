public class MandelRequest {
	
	public int width, height;	
	public double xCenter, yCenter;
	public double scale;
	public int maxIterations;
	
	private double scaleFactor = 1.09;
	
	public MandelRequest() {
		this.width   = (int)(400 * 3.5);
		this.height  = 400 * 2;
		this.scale   = 1;
		this.xCenter = 0;
		this.yCenter = 0;
		this.maxIterations = 100;
	}
	
	public void updateCenter(int x, int y) {
		this.xCenter -= x / (double) this.width  * 3.5 * this.scale - 1.75 * this.scale;
		this.yCenter += y / (double) this.height * 2.0 * this.scale - 1.0  * this.scale;		
	}

	public double getX(int x) {
		return x / (double) this.width * 3.5 * this.scale - 1.75 * this.scale - this.xCenter;
	}
	
	public double getY(int y) {
		return y / (double) this.height * 2.0 * this.scale - 1.0 * this.scale - this.yCenter;
	}
	
	// logarithmic scaling
	public void increaseScale() {
		this.scale *= this.scaleFactor;
	}
	
	// logarithmic scaling
	public void decreaseScale() {
		this.scale /= this.scaleFactor;
	}

}