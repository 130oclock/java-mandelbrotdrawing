package graphics2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Drawing extends JPanel implements MouseListener {

	static final int screenWidth = 1000;
	static final int screenHeight = 1000;
	static final int screenHalfWidth = screenWidth/2;
	static final int screenHalfHeight = screenHeight/2;
	static final int screenLength = screenWidth*screenHeight;
	
	static int max = 250;
	static int[] screenRoster;
	static int[] numIterationsPerPixel;
	static double total;
	
	static int mouseLastX = 0;
	static int mouseLastY = 0;
	
	static double x0 = -2, x1 = 2, y0 = -2, y1 = 2; //pattern 0
	//static double x0 = -0.7092, x1 = -0.712, y0 = 0.24445, y1 = 0.2487; //pattern 1
	//static double x0 = -0.777120613150274923773, x1 = -0.777120273471042550002, y0 = 0.126857111509958518545, y1 = 0.126857366062765260933; //pattern 2
	//static double x0 = -0.741536187499999998982, x1 = -0.733203562499999999338, y0 = 0.188327906250000000015, y1 = 0.194577375000000000015; //pattern 3
	
	public static void main(String[] args) {
		// Generate Window
		JFrame frame = new JFrame("Mandelbrot Set");
		Drawing drawing = new Drawing();
		drawing.setPreferredSize(new Dimension(screenWidth, screenHeight));
		drawing.addMouseListener(drawing);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(drawing);
		frame.pack();
		frame.setVisible(true);

		// Initialize Arrays
		screenRoster = new int[screenLength];
		numIterationsPerPixel = new int[max+1];
    	
		// Only calculate the image once to make it faster
		total = calculate(screenRoster, numIterationsPerPixel, max);
	}
	
	public static void drawMandelbrot(Graphics g) {
		// Draw roster to canvas
    	for(int i = 0; i < screenRoster.length; i++) {
    		int x = i % screenWidth;
    		int y = i / screenWidth | 0;
    		
    		int iterations = screenRoster[i];
    		//System.out.println(iterations);
    		
    		if (iterations <= max) {
    			double hue = 0;
	    		for (int j = 0; j <= iterations; j++) {
	    			hue += (numIterationsPerPixel[j]);
	        		//System.out.println(total);
	    		}
	    		// draw pixel
	    		int c = Math.max(0, 255 - (int)(255 * (hue / total)));
	    		//int c = 255 - (int)(255 * ((double)iterations/(double)max));
	    		if (iterations == 0) c = 0;
	    		Color color = new Color(c,c,c);
	    		g.setColor(color);
	    		g.fillRect(x,y,1,1);
    		}
    	}
        
    	//System.out.println("Finished Drawing");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMandelbrot(g);
    }
	
	// Mouse Listener
	@Override
	public void mousePressed(MouseEvent e) {
		mouseLastX = e.getX();
		mouseLastY = e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		updateArea(x, mouseLastX, y, mouseLastY);
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	// Mandelbrot Calculations
	
	public static void updateArea(int xstart, int xend, int ystart, int yend) {
		// Correcting the order of the coordinates
		int nXStart = Math.min(xstart, xend);
		int nYStart = Math.min(ystart, yend);
		
		int width = Math.abs(xstart - xend);
		int height = Math.abs(ystart - yend);
		
		int nXEnd = nXStart + width;
		int nYEnd = nYStart + width;
		
		// Get percent values of position on screen
		double xdiff = x1 - x0;
		double ydiff = y1 - y0;
		double pXStart = ((double)nXStart / (double)screenWidth) * xdiff;
		double pXEnd = ((double)nXEnd / (double)screenWidth) * xdiff;
		double pYStart = ((double)nYStart / (double)screenHeight) * ydiff;
		double pYEnd = ((double)nYEnd / (double)screenHeight) * ydiff;
		
		// Set coordinates
		x1 = pXEnd + x0;
		x0 = pXStart + x0;
		y1 = pYEnd + y0;
		y0 = pYStart + y0;

		total = calculate(screenRoster, numIterationsPerPixel, max);
	}
	
	public static int calculate(int[] screenRoster, int[] numIterationsPerPixel, int max) {
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
	
	public static int mand(Complex z0, int max) {
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
	
	public static int convert2Dto1D(int x, int y) {
		return (y * screenWidth) + x;
	}

}
