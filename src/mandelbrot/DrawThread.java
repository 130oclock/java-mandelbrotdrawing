package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

class DrawThread implements Runnable {
	
	public int[] screenRoster, numIterationsPerPixel;
	public int screenWidth, screenHeight;
	private int screenHalfWidth, screenHalfHeight, screenLength, max;
	private double total;
	
	private int x0, x1, y0, y1;
	
	public DrawThread(int x0, int x1, int y0, int y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	@Override
	public void run() {
		
	}
}
