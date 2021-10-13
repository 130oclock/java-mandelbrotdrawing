package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

public class Mandelbrot {
	
	public int[] screenRoster;
	public int[] numIterationsPerPixel;
	public int screenWidth, screenHeight;
	private int screenLength, max;
	private double total;
	
	public double x0, x1, y0, y1;
	
	public Mandelbrot(int screenWidth, int screenHeight, double x0, double x1, double y0, double y1, int max) {
		// Set and initialize variables
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		screenLength = screenWidth * screenHeight;
		
		this.max = max;
		
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		
		screenRoster = new int[screenLength];
		numIterationsPerPixel = new int[max+1];
		
		// Calculate and store the image once at the beginning
		calculate();
	}
	
	public void drawMandelbrot(Graphics g) {
		if (screenRoster == null) return;
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
					// Calculate color of pixel
					int c = Math.max(0, 255 - (int)(255 * (hue / total)));
					if (iterations == 0) c = 0;
					Color color = new Color(c,c,c);
					// Draw pixel
					g.setColor(color);
					g.fillRect(x,y,1,1);
				}
			}
	}
	
	public void calculate() {
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
    	
		total = 0;
		for (int i = 0; i < screenLength; i++) {
			numIterationsPerPixel[screenRoster[i]]++;
		}
		for (int i = 0; i < max; i++) {
			total += numIterationsPerPixel[i];
		}
	}
	
	private int mand(Complex z0, int max) {
		Complex z = z0;
		for (int t = 0; t < max; t++) {
			if (z.mod() > 2.0) return t;
			z = z.sqr().add(z0); // Mandelbrot; Escape Radius: 2
			//z = new Complex(Math.abs(z.real()), Math.abs(z.imaginary())).sqr().add(z0); // Burning Ship; Escape Radius: 2
			//z = z.cos().add(z0); // Escape Radius 10*PI
			//z = z.mult(z.sin());
		}
		return max;
	}
	
	private int convert2Dto1D(int x, int y) {
		return (y * screenWidth) + x;
	}
}
