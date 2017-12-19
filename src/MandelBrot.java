import java.lang.Thread;

public class MandelBrot {
		
	private final MandelRequest mandelRequest;
	private Thread threads[];
	public boolean shouldCancel = false;
	
	public double iterations[];
	
	private final static int MAX_THREADS = 4;
	
	public MandelBrot(MandelRequest mandelRequest) {
		this.mandelRequest = mandelRequest;
		this.iterations = new double[this.mandelRequest.width * this.mandelRequest.height];

		mandelMultiThreaded();
		//mandelSingleThreaded();
	}
	
	/*
	private double escapeTimeDiscrete(int xi, int yi) {
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
    	return (double) iteration;		
	}
	*/
	
    private double escapeTimeSmooth(int xi, int yi) {
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
    	double iterationSmooth = (double) iteration;
    	if (iteration < mandelRequest.maxIterations) {
		    double log_zn = Math.log( x*x + y*y ) / 2.0;
		    double nu = Math.log(log_zn / Math.log(2.0) ) / Math.log(2.0);
		    iterationSmooth = iterationSmooth + 1 - nu;
		}
		return iterationSmooth;
    }
	
    /*
	private void mandelSingleThreaded() {
    	this.iterations = new double[mandelRequest.width * mandelRequest.height];
    	for(int y = 0; y < mandelRequest.height; y++) {
    		int tempHeight = y * mandelRequest.width;
    		for(int x = 0; x < mandelRequest.width; x++) {
    			this.iterations[tempHeight + x] = escapeTimeSmooth(x, y);
    		}
    	}
	}
	*/

	private void mandelMultiThreaded() {
		this.threads = new Thread[MandelBrot.MAX_THREADS];
    	for(int t = 0; t < this.threads.length; t++) {
    		this.threads[t] = new Thread(new MandelThread(t));
    		this.threads[t].run();
    	}
    	
    	try {
    		for(int i = 0; i < threads.length; i++) {
				while(threads[i].isAlive()) {
					if(this.shouldCancel) {
						this.cancel();
						return;
					}
					threads[i].join(100);
				}
			}
		} catch (InterruptedException e) {
			this.cancel();
		}
	}
	
	private class MandelThread implements Runnable {

		private MandelThread(int id) {
			this.id = id;
		}
		
		public void run() {
	    	for(int y = 0; y < mandelRequest.height; y++) {
	    		int tempHeight = y * mandelRequest.width;
	    		for(int x = this.id; x < mandelRequest.width; x += MandelBrot.MAX_THREADS) {
	    			int index = tempHeight + x;
	    			iterations[index] = escapeTimeSmooth(x, y);
	    		}
	    	}
		}

		private final int id;
	}

	
	public void cancel() {
		if(this.threads != null) {
			for(int i = 0; i < this.threads.length; i++) {
				this.threads[i].interrupt();
			}
		}
	}
}