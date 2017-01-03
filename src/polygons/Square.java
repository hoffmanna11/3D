package polygons;
import java.awt.Color;
import java.awt.Graphics;

import game_cat.Polygon;
import sub.Point3D;
import sub.Vector3D;

public class Square extends Polygon {
	int length;
	
	public Square(Point3D loc, Vector3D orient, int length) {
		super(loc, orient);
		this.length = length;
	}

	public void render(Graphics g, Point3D viewerLoc, Vector3D viewerOrient, int kill) {
		int[] xVals = new int[4];
		int[] yVals = new int[4];
		int halfLength = (int)(.5 * length);
		xVals[0] = loc.x - halfLength;
		yVals[0] = loc.y - halfLength;
		xVals[1] = loc.x + halfLength;
		yVals[1] = loc.y - halfLength;
		xVals[2] = loc.x + halfLength;
		yVals[2] = loc.y + halfLength;
		xVals[3] = loc.x - halfLength;
		yVals[3] = loc.y + halfLength;
		g.setColor(Color.RED);
		g.fillPolygon(xVals, yVals, 4);
	}
	
	public void render(Graphics g, Point3D viewerLoc, Vector3D viewerOrient) {
		int[] xVals = new int[4];
		int[] yVals = new int[4];
		int halfLength = (int)(.5 * length);
		xVals[0] = loc.x - halfLength;
		yVals[0] = loc.y - halfLength;
		xVals[1] = loc.x + halfLength;
		yVals[1] = loc.y - halfLength;
		xVals[2] = loc.x + halfLength;
		yVals[2] = loc.y + halfLength;
		xVals[3] = loc.x - halfLength;
		yVals[3] = loc.y + halfLength;
		g.setColor(Color.RED);
		g.fillPolygon(xVals, yVals, 4);
	}
}
