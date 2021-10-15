package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

class DrawThread implements Runnable {
	
	public int[] screenRoster, numIterationsPerPixel;
	public int screenWidth, screenHeight;
	private int screenHalfWidth, screenHalfHeight, screenLength, max;
	private double total;
	
	private double[] patches;
	private boolean[] threadPatches;
	private Graphics graphic;
	
	public DrawThread(double[] patches, boolean[] threadPatches. Graphics g) {
		this.patches = patches;
		this.threadPatches = threadPatches;
		this.graphic = g;
	}
	
	@Override
	public void run() {
		
	}
	
	private void draw(Graphics g) {
		
	}
}
