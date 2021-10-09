package graphics2D;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Drawing extends Canvas {

	static int screenWidth = 1000;
	static int screenHeight = 1000;
	static int screenLength = screenWidth*screenHeight;
	
	static int max = 300;
	static int[] screenRoster;
	static int[] numIterationsPerPixel;
	static double total;
	
	static double x0 = -2, x1 = 2, y0 = -2, y1 = 2; //pattern 0
	//static double x0 = -0.7092, x1 = -0.712, y0 = 0.24445, y1 = 0.2487; //pattern 1
	//static double x0 = -0.777120613150274923773, x1 = -0.777120273471042550002, y0 = 0.126857111509958518545, y1 = 0.126857366062765260933; //pattern 2
	//static double x0 = -0.741536187499999998982, x1 = -0.733203562499999999338, y0 = 0.188327906250000000015, y1 = 0.194577375000000000015; //pattern 3
	
	public static void main(String[] args) {
		// Generate Window
		JFrame frame = new JFrame("Mandelbrot Set");
        Canvas canvas = new Drawing();
        canvas.setSize(screenWidth, screenHeight);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        
        // Initialize Arrays
    	screenRoster = new int[screenLength];
    	numIterationsPerPixel = new int[max+1];
    	
    	//System.out.println();
    	
    	drawMandelbrot(canvas);
	}
	
	public static void drawMandelbrot(Canvas canvas) {
		total = calculate(screenRoster, numIterationsPerPixel, max);
		draw(canvas.getGraphics());
	}
	
	public static void draw(Graphics g) {
	
    	// Draw Roster to Canvas
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
	    		//int c = (int)(255 * ((double)iterations/(double)max));
	    		if (iterations == 0) c = 0;
	    		Color color = new Color(c,c,c);
	    		g.setColor(color);
	    		g.fillRect(x,y,1,1);
    		}
    	}
        
    	System.out.println("Finished Drawing");
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
