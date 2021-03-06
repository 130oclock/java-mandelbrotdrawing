package mandelbrot;

import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Drawing extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int screenWidth = 1000;
	static final int screenHeight = 1000;
	static final int screenHalfWidth = screenWidth/2;
	static final int screenHalfHeight = screenHeight/2;
	static final int screenLength = screenWidth*screenHeight;
	
	static int max = 200;
	
	static Mandelbrot mandel;
	/*static boolean[] threadPatches;
	static int numOfCores = 1;
	static int patchDiv = 10;
	static int totalPatches = (patchDiv) * (patchDiv);*/
	
	static double x0 = -2, x1 = 2, y0 = -2, y1 = 2; //pattern 0
	//static double x0 = -0.7092, x1 = -0.712, y0 = 0.24445, y1 = 0.2487; //pattern 1
	//static double x0 = -0.777120613150274923773, x1 = -0.777120273471042550002, y0 = 0.126857111509958518545, y1 = 0.126857366062765260933; //pattern 2
	//static double x0 = -0.741536187499999998982, x1 = -0.733203562499999999338, y0 = 0.188327906250000000015, y1 = 0.194577375000000000015; //pattern 3
	
	static int mouseLastX = 0;
	static int mouseLastY = 0;
	
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
		
		// Create Mandelbrot
		mandel = new Mandelbrot(screenWidth, screenHeight, x0, x1, y0, y1, max);
		mandel.drawMandelbrot(drawing.getGraphics());
		
		//threadPatches = new boolean[totalPatches];
		//assignThreads(drawing.getGraphics());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mandel != null) mandel.drawMandelbrot(g);
		
		/*if (mandel != null) {
			for (int i = 0; i < threadPatches.length; i++) {
				if (threadPatches[i] == false) return;
			}
			assignThreads(g);
		}*/
    }
	
	// Mouse Listener
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mouseLastX = e.getX();
			mouseLastY = e.getY();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			updateArea(x, mouseLastX, y, mouseLastY);
			repaint();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3) {
			double xdif = x1-x0, ydif = y1-y0;
			new Julia(new Complex(findPercentDiff(e.getX(), screenWidth, xdif) + x0, findPercentDiff(e.getY(), screenHeight, ydif)  + y0)).createWindow();
		}
		if(e.getButton() == MouseEvent.BUTTON2) {
			x0 = -2; x1 = 2; y0 = -2; y1 = 2;
			mandel.setArea(x0, x1, y0, y1);

			mandel.calculate();
			repaint();
		}
	}
	
	// Mandelbrot Calculations
	
	public static void updateArea(int xstart, int xend, int ystart, int yend) {
		// Correcting the order of the coordinates
		int nXStart = Math.min(xstart, xend);
		int nYStart = Math.min(ystart, yend);
		
		int width = Math.abs(xstart - xend);
		//int height = Math.abs(ystart - yend);
		
		int nXEnd = nXStart + width;
		int nYEnd = nYStart + width;
		
		// Get percent values of position on screen
		double xdif = x1 - x0;
		double ydif = y1 - y0;
		double pXStart = findPercentDiff(nXStart, screenWidth, xdif);
		double pXEnd = findPercentDiff(nXEnd, screenWidth, xdif);
		double pYStart = findPercentDiff(nYStart, screenHeight, ydif);
		double pYEnd = findPercentDiff(nYEnd, screenHeight, ydif);
		
		// Set coordinates
		x1 = pXEnd + x0;
		x0 = pXStart + x0;
		y1 = pYEnd + y0;
		y0 = pYStart + y0;

		mandel.setArea(x0, x1, y0, y1);
		mandel.calculate();
	}
	
	/*// Thread Calculations
	public static int[] calculateThreadPatch() {
		int patchWidth = screenWidth / patchDiv;
		int patchHeight = screenHeight / patchDiv;
		
		threadPatches = new boolean[totalPatches];
		int[] patchXY = new int[totalPatches * 2];
		
		for (int j = 0; j < patchDiv; j++) {
			for (int i = 0; i < patchDiv; i++) {
				int k = (j * patchDiv + i) * 2;
				patchXY[k] = i * patchWidth;
				patchXY[k+1] = j * patchHeight;
			}
		}
		return patchXY;
	}
	
	public static void assignThreads(Graphics g) {
		int[] patches = calculateThreadPatch();
		mandel.total = 0;
		for (int i = 0; i < mandel.numIterationsPerPixel.length; i++) {
			mandel.numIterationsPerPixel[i] = 0;
        }
		//System.out.println(x0 + " " + y0 + " " + x1 + " " + y1);
		for (int i = 0; i < numOfCores; i++) {
			new DrawThread(screenWidth, screenHeight, max, patches, threadPatches, g, mandel, x0, y0, x1, y1).start();
		}
	}*/
	
	public static double findPercentDiff(int part, int max, double mod) {
		return ((double)part / (double)max) * mod;
	}
}
