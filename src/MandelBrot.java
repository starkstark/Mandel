public class MandelBrot {
		
	public int iterations[];

	private final MandelRequest mandelRequest;

	public MandelBrot(MandelRequest mandelRequest) {
		this.mandelRequest = mandelRequest;
		
		
		
		
    	mandelSingleThreaded();
	}
	
    private int mandel(int xi, int yi) {
    	double x0 = mandelRequest.getX(xi);
    	double y0 = mandelRequest.getY(yi);
    	double x = 0.0;
    	double y = 0.0;
    	int iteration = 0;
    	while (x*x + y*y < 2*2 && iteration < mandelRequest.maxIterations) {
    	    double xtemp = x*x - y*y + x0;
    	    y = 2*x*y + y0;
    	    x = xtemp;
    	    iteration = iteration + 1;
    	}
    	return iteration;
    }
	
	//computes without multi-threading
	private void mandelSingleThreaded() {
    	this.iterations = new int[mandelRequest.width * mandelRequest.height];
    	for(int y = 0; y < mandelRequest.height; y++) {
    		int tempHeight = y * mandelRequest.width;
    		for(int x = 0; x < mandelRequest.width; x++) {
    			this.iterations[tempHeight + x] = mandel(x, y);
    		}
    	}
	}

	//computes with multi-threading
	private void mandelMultiThreaded() {
		//TODO
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