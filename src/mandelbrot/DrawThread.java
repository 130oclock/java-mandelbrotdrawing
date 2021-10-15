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
		int patchID;
		for (int i = 0; i < threadPatches.length; i++) {
			if (!threadPatches[i]) {
				threadPatches[i] = true;
				patchID = i;
			}
		}
		patchID = patchID * 2;
		int x0 = patches[patchID];
		int y0 = patches[patchID + 1];
		int x1 = patches[patchID + 2];
		int y1 = patches[patchID + 3];
	}
	
	private void draw(Graphics g) {
		
	}
}
