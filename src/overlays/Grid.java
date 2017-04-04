package overlays;

import java.awt.Color;
import java.awt.Graphics;

import game.Camera;
import game_cat.Overlay;

public class Grid extends Overlay {
	Camera camera;
	int resWidth;
	int resHeight;
	
	Graphics g = null;
	
	double initXSpacingPercent = .15;
	double initYSpacingPercent = .1;
	
	double fociLengthPercent = 1.5;
	
	public Grid(Camera camera, int resWidth, int resHeight){
		this.camera = camera;
		this.resWidth = resWidth;
		this.resHeight = resHeight;
	}
	
	public void render(Graphics g) {
		this.g = g;
		drawGrid();
	}
	
	private void drawGrid(){
		drawXLines();
		drawYLines();
	}
	
	private void drawXLines(){
		double curYSpacing = resHeight * initYSpacingPercent;
		double curYSpace = curYSpacing;
		
		g.setColor(Color.green);
		for(int i=0; i<100; i++){
			g.drawLine(0, (int)(resHeight - curYSpace), resWidth, (int)(resHeight - curYSpace));
			curYSpace += curYSpacing;
			curYSpacing *= .90;
		}
	}
	
	private void drawYLines(){
		double currentX1 = resWidth / 2;
		double currentX2 = resWidth / 2;
		
		int fociX = resWidth / 2;
		int fociY = -100;
		
		double curXSpacing = resWidth * initXSpacingPercent;
		
		g.setColor(Color.green);
		for(int i=0; i<25; i++){
			// draw left
			g.drawLine((int)currentX1, resHeight, fociX, fociY);
			currentX1 -= curXSpacing;
			
			// draw right
			g.drawLine((int)currentX2, resHeight, fociX, fociY);
			currentX2 += curXSpacing;
			
			curXSpacing *= .90;
		}
	}
	
}
