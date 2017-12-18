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

	public int convertToGreyscale(int iteration) {
		double result = ((double) iteration - (double) this.minIteration) / (double) this.maxIteration;
		int colorValue = Math.round((float)(result * 0xff));	
		return colorValue * 0x00010000 + colorValue * 0x00000100 + colorValue * 0x00000001;
	}

	public int[] convertToGreyscleAll() {
		int[] colors = new int[this.iterations.length];
		for(int i = 0; i < this.iterations.length; i++) {
			colors[i] = convertToGreyscale(this.iterations[i]);
		}
		return colors;
	}
}