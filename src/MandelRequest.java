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
		this.xCenter = 2.5;
		this.yCenter = 1;
		this.maxIterations = 300;
	}
	
	public void updateCenter(int x, int y) {
		/*
		this.xCenter -= ((x / (double) this.width)  * 3.5 - 1.75) * this.scale;
		this.yCenter += ((y / (double) this.height) * 2.0 - 1.0)  * this.scale;
		*/
		this.xCenter -= ((x / (double) this.width)  * 3.5 - 1.75) * this.scale;
		this.yCenter += ((y / (double) this.height) * 2.0 - 1.0)  * this.scale;		
		
	}
	
	public double getX(int x) {
		return ((x / (double) this.width) * 3.5 * this.scale - (this.xCenter)) * this.scale;	
	}
	
	public double getY(int y) {
		return ((y / (double) this.height) * 2.0 * this.scale - (this.yCenter)) * this.scale;		
	}

	// logarithmic scaling
	public void increaseScale() {
		this.scale   *= this.scaleFactor;
		//this.xCenter /= this.scaleFactor;
		//this.yCenter /= this.scaleFactor;
	}
	
	// logarithmic scaling
	public void decreaseScale() {
		this.scale   /= this.scaleFactor;
		//this.xCenter *= this.scaleFactor;
		//this.yCenter *= this.scaleFactor;
	}

}