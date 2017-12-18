
public class Mandel {
	
	public int iterations[];
	
	private final int width;
	private final int height;
	private final int maxIterations;
	
	public Mandel(int width, int height, int maxIterations) {
		this.width = width;
		this.height = height;
		this.maxIterations = maxIterations;
		
    	mandelSingleThreaded();
	}
	
    private int mandel(int x, int y) {
    	return mandel(
    			x / (double) this.width  * 3.5 - 2.5, 
    			y / (double) this.height * 2.0 - 1);
    }
    
    private int mandel(double x0, double y0) {
    	double x = 0.0;
    	double y = 0.0;
    	int iteration = 0;
    	while (x*x + y*y < 2*2  &&  iteration < this.maxIterations) {
    	    double xtemp = x*x - y*y + x0;
    	    y = 2*x*y + y0;
    	    x = xtemp;
    	    iteration = iteration + 1;
    	}
    	return iteration;
    }
	
	//computes without multi-threading
	private void mandelSingleThreaded() {
    	iterations = new int[this.width * this.height];
    	for(int y = 0; y < this.height; y++) {
    		int tempHeight = y * this.width;
    		for(int x = 0; x < this.width; x++) {
    			iterations[tempHeight + x] = mandel(x, y);
    		}
    	}
	}

	//computes with multi-threading
	private void mandelMultiThreaded() {
    	iterations = new int[this.width * this.height];
    	for(int y = 0; y < this.height; y++) {
    		int tempHeight = y * this.width;
    		for(int x = 0; x < this.width; x++) {
    			iterations[tempHeight + x] = mandel(x, y);
    		}
    	}
	}
	
	private class MandelThread implements Runnable {
		
		private MandelThread(int x, int y) {
			this.x = x; this.y = y;
		}
		
		public void run() {
			System.out.println("runner");
			this.result = mandel(x, y);
		}
		
		private int x;
		private int y;
		public  int result = -1;
	}
	
}
