public class ColorPicker {

	private final int[] iterations;
	private final int maxIteration;
	private final int minIteration;
	
	ColorPicker(int[] iterations, int maxIterations) {
		this.iterations = iterations;
		
		int minIteration = maxIterations;
		int maxIteration = 0;
		for(int x = 0; x < this.iterations.length; x++) {
			if(this.iterations[x] < minIteration) {
				minIteration = this.iterations[x];
			}
			if(this.iterations[x] > maxIteration) {
				maxIteration = this.iterations[x];
			}
		}
		this.maxIteration = maxIteration;
		this.minIteration = minIteration;
	}

	public int[] convertToGreyscle() {
		int[] colors = new int[this.iterations.length];
		for(int i = 0; i < this.iterations.length; i++) {
			int iter = this.iterations[i];
			double result = ((double) iter - (double) this.minIteration) / (double) this.maxIteration;
			int colorValue = Math.round((float)(result * 0xff));	
			colors[i] = colorValue * 0x00010000 + colorValue * 0x00000100 + colorValue * 0x00000001;
		}
		return colors;
	}
	
	public int[] convertToRainbow() {
		int[] colors = new int[this.iterations.length];
		for(int i = 0; i < this.iterations.length; i++) {
			int iter = this.iterations[i];
			if(iter <= 0) {
				colors[i] = 0x00ffffff;
				continue;
			}
			if(iter >= this.maxIteration) {
				colors[i] = 0x00000000;
				continue;
			}	
			double scale = (double) (iter - this.minIteration) / (double) this.maxIteration;
			if (scale > (double) 1.0 / 3.0) {
				colors[i] = (int) Math.round(scale * 3.0 * 0x00000001);
				
			} if (iter > (double) 2.0 / 3.0) {
				colors[i] += (int) Math.round(scale * 1.5 * 0x00000100);
			}
			
			colors[i] += (int) Math.round(scale * 1.5 * 0x00010000);
		}
		return colors;
	}
	
	//TODO
	public int[] convertGolden() {
		int[] colors = new int[this.iterations.length];
		for(int i = 0; i < this.iterations.length; i++) {
			double gradient = 1.0 - (double) (this.iterations[i] - this.minIteration) / (double) this.maxIteration;
			colors[i] = (int) Math.floor(gradient * 0x00ffd700);
		}
		return colors;
	}
}