package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

class DrawThread implements Runnable {
	
	public int[] screenRoster, numIterationsPerPixel;
	public int screenWidth, screenHeight;
	private int max;
	private double total;
	private int x0, y0, x1, y1;
	
	private double[] patches;
	private boolean[] threadPatches;
	private Graphics graphic;
	
	public DrawThread(int screenWidth, int screenHeight, int max, double[] patches, boolean[] threadPatches, Graphics g, int x0, int y0, int x1, int y1) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.max = max;
		
		this.patches = patches;
		this.threadPatches = threadPatches;
		this.graphic = g;
		
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	@Override
	public void run() {
		int patchID;
		for (int i = 0; i < threadPatches.length; i++) {
			if (!threadPatches[i]) {
				threadPatches[i] = true;
				patchID = i;
			}
		}
		int patchDiv = 10;
		int patchWidth = screenWidth / patchDiv;
		int patchHeight = screenHeight / patchDiv;
		
		int patchLoc = patchID * 2;
		int px0 = patches[patchLoc];
		int py0 = patches[patchLoc + 1];
		int px1 = px0 + patchWidth;
		int py1 = py0 + patchHeight;
		
		// Get percent values of position on screen
		double xdif = x1 - x0;
		double ydif = y1 - y0;
		double pXStart = findPercentDiff(px0, screenWidth, xdif);
		double pXEnd = findPercentDiff(px1, screenWidth, xdif);
		double pYStart = findPercentDiff(py0, screenHeight, ydif);
		double pYEnd = findPercentDiff(py1, screenHeight, ydif);
		
		// Set coordinates
		x1 = pXEnd + x0;
		x0 = pXStart + x0;
		y1 = pYEnd + y0;
		y0 = pYStart + y0;
		
		Mandelbrot mandel = new Mandelbrot(patchWidth, patchHeight, x0, x1, y0, y1, max);
		draw(mandel, graphic, px0, py0, px1, py1);
	}
	
	private void draw(Mandelbrot mandel, Graphics g, int px0, int py0, int px1, int py1) {
		mandel.calculate();
		mandel.screenRoster;
		mandel.numIterationsPerPixel;
		
		for (int y = py0; y < py1; y++) {
			for (int x = px0; x < px1; x++) {
				int i = convert2Dto1D(x, y);
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
	}
	
	private static double findPercentDiff(int part, int max, double mod) {
		return ((double)part / (double)max) * mod;
	}
	
	private int convert2Dto1D(int x, int y) {
		return (y * screenWidth) + x;
	}
}
