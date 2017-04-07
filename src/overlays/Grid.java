package overlays;

import java.awt.Color;
import java.awt.Graphics;

import game.Camera;
import game.Env;
import game_cat.Overlay;

public class Grid extends Overlay {
	// Had to make these static for the static methods
	// Should probably find another way to do that
	static Graphics g = null;
	static double initXSpacingPercent = .15;
	static double initYSpacingPercent = .1;
	static double xSpacingDec = .9;
	static double ySpacingDec = .9;
	static double gridScale = 10;

	public int fociX;
	public int fociY;
	
	Camera camera;
	public int resWidth;
	public int resHeight;
	double fociLengthPercent = 1.5;

	public Grid(Camera camera, int resWidth, int resHeight){
		this.camera = camera;
		this.resWidth = resWidth;
		this.resHeight = resHeight;
		fociX = resWidth / 2;
		fociY = -100;
	}

	public void render(Graphics g) {
		Grid.g = g;
		drawGrid();
	}

	private void drawGrid(){
		drawXLines();
		drawYLines();
	}

	public static int convertYToGrid(int y){
		double curYSpacing = Env.RESHEIGHT * initYSpacingPercent;
		double curYSpace = curYSpacing;

		for(int i=0; i<Math.abs(y); i++){
			curYSpace += curYSpacing;
			curYSpacing *= ySpacingDec;
		}

		if(y < 0){
			return -(int)curYSpace;
		}
		return (int)curYSpace;
	}

	public static int convertXToGrid(int x){
		double xVal = 0;		

		double curXSpacing = Env.RESWIDTH * initXSpacingPercent;

		g.setColor(Color.green);
		for(int i=0; i<x; i++){
			// draw left
			xVal += curXSpacing;

			curXSpacing *= xSpacingDec;
		}

		if(x < 0){
			return -(int)xVal;
		}
		return (int)xVal;
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
