package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

class DrawThread extends Thread {
	
	public int[] screenRoster, numIterationsPerPixel;
	public int screenWidth, screenHeight;
	private int max;
	private double total;
	private double x0o, y0o, x1o, y1o;
	private double x0, y0, x1, y1;
	
	private int[] patches;
	private boolean[] threadPatches;
	private Graphics graphic;
	private Mandelbrot omandel;
	
	public DrawThread(int screenWidth, int screenHeight, int max, int[] patches, boolean[] threadPatches, Graphics g, Mandelbrot omandel, double x0, double y0, double x1, double y1) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.max = max;
		
		this.patches = patches;
		this.threadPatches = threadPatches;
		this.graphic = g;
		this.omandel = omandel;
		
		this.x0o = x0;
		this.y0o = y0;
		this.x1o = x1;
		this.y1o = y1;
		
	}
	
	@Override
	public void run() {
		int patchID = 100;
		for (int i = 0; i < threadPatches.length; i++) {
			if (threadPatches[i] == false) {
				threadPatches[i] = true;
				patchID = i;
				//System.out.println(patchID);
				break;
			}
		}
		// Stop thread once all patches are assigned
		if (patchID > 99) return;
		
		int patchDiv = 10;
		int patchWidth = screenWidth / patchDiv;
		int patchHeight = screenHeight / patchDiv;
		
		int patchLoc = patchID * 2;
		int px0 = patches[patchLoc];
		int py0 = patches[patchLoc + 1];
		int px1 = px0 + patchWidth;
		int py1 = py0 + patchHeight;
		
		// Get percent values of position on screen
		double xdif = x1o - x0o;
		double ydif = y1o - y0o;
		double pXStart = findPercentDiff(px0, screenWidth, xdif);
		double pXEnd = findPercentDiff(px1, screenWidth, xdif);
		double pYStart = findPercentDiff(py0, screenHeight, ydif);
		double pYEnd = findPercentDiff(py1, screenHeight, ydif);
		
		// Set coordinates
		x1 = pXEnd + x0o;
		x0 = pXStart + x0o;
		y1 = pYEnd + y0o;
		y0 = pYStart + y0o;
		
		//System.out.println("(" + x0 + ", " + y0 + ") ( " + x1 + ", " + y1 + ")");
		
		Mandelbrot mandel = new Mandelbrot(patchWidth, patchHeight, x0, x1, y0, y1, max);
		updateMain(mandel, px0, py0, px1, py1, patchID);
		//draw(mandel, graphic, px0, py0, px1, py1);
		run();
	}
	
	private void draw(Mandelbrot mandel, Graphics g, int px0, int py0, int px1, int py1) {
		mandel.calculate();
		int[] screenRoster = mandel.screenRoster;
		int[] numIterationsPerPixel = mandel.numIterationsPerPixel;
		total = mandel.total;
		
		for (int y = py0; y < py1; y++) {
			for (int x = px0; x < px1; x++) {
				int i = convert2Dto1D(x - px0, y - py0, py1 - py0);
				int iterations = screenRoster[i];
				//if (x == px0) System.out.println(i);
	    		
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
	
	private void updateMain(Mandelbrot mandel, int px0, int py0, int px1, int py1, int patchID) {
		mandel.calculate();
		
		for (int i = 0; i < mandel.screenRoster.length; i++) {
			omandel.numIterationsPerPixel[mandel.screenRoster[i]]++;
		}
		
		omandel.total += mandel.total;
		mergeArray(mandel.screenRoster, omandel.screenRoster, px0, py0, px1, py1);
		
		boolean remainder = false;
		for (int i = 0; i < threadPatches.length; i++) {
			if (threadPatches[i] == false) {
				remainder = true;
				break;
			}
		}
		if (!remainder) {
			// Do something once all patches are completed
			omandel.drawMandelbrot(graphic);
		}
	}
	
	private void mergeArray(int[] arraypull, int[] arrayput, int px0, int py0, int px1, int py1) {
		int xdiff = px1 - px0;
		int ydiff = py1 - py0;
		for (int j = 0; j < ydiff; j++) {
			for (int i = 0; i < xdiff; i++) {
				int k = convert2Dto1D(i, j, xdiff);
				int l = convert2Dto1D(px0 + i, py0 + j, screenWidth);
				
				arrayput[l] = arraypull[k];
			}
		}
	}
	
	private static double findPercentDiff(int part, int max, double mod) {
		return ((double)part / (double)max) * mod;
	}
	
	private int convert2Dto1D(int x, int y, int diff) {
		return (y * diff) + x;
	}
}
