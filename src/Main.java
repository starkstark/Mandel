import java.awt.image.*;

class Main {
 
	static final String FILE_PATH   = "./res/test.png";	
    static final int WIDTH          = 4 * 300;
    static final int HEIGHT         = 4 * 200;
    static final int MAX_ITERATIONS = 1000;
 
    public static void main(String s[]) {
    	BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    	bufferedImage.setRGB(0, 0, WIDTH, HEIGHT, computeMandelbrot(), 0, WIDTH);
    	
    	/*
    	File file = new File(FILE_PATH);
    	try {
    		ImageIO.write(bufferedImage, "png", file);
    	} catch (IOException e) {
    		System.err.println(e.getMessage());
    	}
    	*/
    	
    	new UserInterface(bufferedImage);
    }
    
    public static int[] computeMandelbrot() {
    	int iterations[] = new Mandel(WIDTH, HEIGHT, MAX_ITERATIONS).iterations;    	
    	ColorPicker colorPicker = new ColorPicker(iterations, MAX_ITERATIONS);
    	//pick colors
    	int colors[] = new int[WIDTH * HEIGHT];
    	for(int y = 0; y < HEIGHT; y++) {
    		int height = y * WIDTH;
    		for(int x = 0; x < WIDTH; x++) {
    			colors[height + x] = colorPicker.convertToGreyscale(iterations[height + x]);
    		}
    	}
    	return colors;	   	
    }
}
