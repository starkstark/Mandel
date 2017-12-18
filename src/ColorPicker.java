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
			int iter  = this.iterations[i];
			if(iter == 0) {
				colors[i] = 0x00ffffff;
				continue;
			} if (iter <= 0x000000ff) {
				colors[i] = iter % 0xff * 0x00000010;
			} if (iter <= 0x0000ff00) {
				colors[i] += iter % 0xff * 0x00000100;
			} if (iter <= 0x000000ff) {
				colors[i] += iter % 0xff * 0x00010000;
			} else {
				colors[i] = 0x00000000;
				continue;
			}
		}
		return colors;
	}
}