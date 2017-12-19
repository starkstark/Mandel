import java.awt.Color;

public class ColorPickerSmooth {
	
	private final double[] iterations;
	private final double maxIteration;
	private final double minIteration;
	
	ColorPickerSmooth(double[] iterations, double maxIterations) {
		this.iterations = iterations;	
		double minIteration = maxIterations;
		double maxIteration = 0;
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

	public int[] gradientLinear(int color1, int color2) {
		int[] colors = new int[this.iterations.length];
		for(int i = 0; i < this.iterations.length; i++) {
			double gradient = (this.iterations[i] - this.minIteration) / this.maxIteration;
			Color c1 = new Color(color1);
			Color c2 = new Color(color2);
			int red   = (int)(gradient * c1.getRed()   + (1.0 - gradient) * c2.getRed()   / 2.0);
			int green = (int)(gradient * c1.getGreen() + (1.0 - gradient) * c2.getGreen() / 2.0);
			int blue  = (int)(gradient * c1.getBlue()  + (1.0 - gradient) * c2.getBlue()  / 2.0);
		
			colors[i] = new Color(red, green, blue).getRGB();
		}
		return colors;
	}

}