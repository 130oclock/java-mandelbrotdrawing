package mandelbrot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Julia extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Complex c;
	
	static final int screenWidth = 600;
	static final int screenHeight = 600;
	static final int screenHalfWidth = screenWidth/2;
	static final int screenHalfHeight = screenHeight/2;
	static final int screenLength = screenWidth*screenHeight;
	
	static int max = 250;
	static int[] screenRoster;
	static int[] numIterationsPerPixel;
	static double total;
	
	static double x0 = -1.5, x1 = 1.5, y0 = -1.5, y1 = 1.5;
	
	public Julia(Complex _c) {
		this.c = _c;
		
		// Initialize Arrays
		screenRoster = new int[screenLength];
		numIterationsPerPixel = new int[max+1];
	}
	
	public void createWindow() {
		JFrame frame = new JFrame("Julia Set: c = " + c);
		Julia drawing = new Julia(c);
		drawing.setPreferredSize(new Dimension(screenWidth, screenHeight));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(drawing);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void drawMandelbrot(Graphics g) {
		total = calculate(screenRoster, numIterationsPerPixel, max);
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
        
		//System.out.println("Finished Drawing " + total);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMandelbrot(g);
    	}
	
	public int calculate(int[] screenRoster, int[] numIterationsPerPixel, int max) {
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
	
	public int mand(Complex z0, int max) {
		Complex z = z0;
		for (int t = 0; t < max; t++) {
			if (z.mod() > 2.0) return t;
			z = z.sqr().add(this.c); 
		}
		return max;
	}
	
	public int convert2Dto1D(int x, int y) {
		return (y * screenWidth) + x;
	}
}
