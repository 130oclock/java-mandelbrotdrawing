package mandelbrot;

public class Mandelbrot {
	
	public int[] screenRoster, numIterationsPerPixel;
	public int screenWidth, screenHeight;
	private int screenHalfWidth, screenHalfHeight, screenLength, max;
	private double total;
	
	public int x0, x1, y0, y1;
	
	public Mandelbrot(int screenWidth, int screenHeight, int x0, int x1, int y0, int y1, int max) {
		screenWidth = screenWidth;
		screenHeight = screenHeight;
		screenHalfWidth = screenWidth/2;
		screenHalfHeight = screenHeight/2;
		screenLength = screenWidth * screenHeight;
		
		max = max;
		
		screenRoster = new int[screenLength];
		numIterationsPerPixel = new int[max+1];
		
		total = calculate(screenRoster, numIterationsPerPixel, max);
	}
	
	public void drawMandelbrot(Graphics g) {
		// Draw roster to canvas
		for(int i = 0; i < screenRoster.length; i++) {
			int x = i % screenWidth;
			int y = i / screenWidth | 0;
    		
			int iterations = screenRoster[i];
    		
			if (iterations <= max) {
				double hue = 0;
				for (int j = 0; j <= iterations; j++) {
					hue += (numIterationsPerPixel[j]);
				}
				// draw pixel
				int c = Math.max(0, 255 - (int)(255 * (hue / total)));
				if (iterations == 0) c = 0;
				Color color = new Color(c,c,c);
				g.setColor(color);
				g.fillRect(x,y,1,1);
			}
		}
	}
	
	private int calculate(int[] screenRoster, int[] numIterationsPerPixel, int max) {
		// Reset array
		for (int i = 0; i < numIterationsPerPixel.length; i++) {
			numIterationsPerPixel[i] = 0;
        }
		
		// Calculate Mandelbrot
		double xdif = (x1-x0)/screenWidth, ydif = (y1-y0)/screenHeight;
		for (int j = 0; j < screenHeight; j++) {
			for (int i = 0; i < screenWidth; i++) {
				double x = x0 + (i * xdif), y = y0 + (j * ydif);
				Complex z0 = new Complex(x, y);
				int iterations = max - mand(z0, max);
				screenRoster[convert2Dto1D(i, j)] = iterations;
			}
		}
    	
		int total = 0;
		for (int i = 0; i < screenLength; i++) {
			numIterationsPerPixel[screenRoster[i]]++;
		}
		for (int i = 0; i < max; i++) {
			total += numIterationsPerPixel[i];
		}
		return total;
	}
	
	private int mand(Complex z0, int max) {
		Complex z = z0;
		for (int t = 0; t < max; t++) {
			if (z.mod() > 2.0) return t;
			z = z.sqr().add(this.c); 
		}
		return max;
	}
	
	private int convert2Dto1D(int x, int y) {
		return (y * screenWidth) + x;
	}
}
