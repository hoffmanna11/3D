package polygons;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import game.Env;
import game_cat.Polygon;
import sub.Point3D;
import sub.Vector3D;

public class Square extends Polygon {
	int length;
	Point3D p1, p2, p3, p4;
	
	public Square(Point3D loc, Vector3D orient, int length) {
		super(loc, orient);
		this.length = length;
	}
	
	// Background squares
	public Square(Point3D p1, Point3D p2, Point3D p3, Point3D p4){
		super(null, null);
		this.p1 = new Point3D(p1.x, p1.y, p1.z);
		this.p2 = new Point3D(p2.x, p2.y, p2.z);
		this.p3 = new Point3D(p3.x, p3.y, p3.z);
		this.p4 = new Point3D(p4.x, p4.y, p4.z);
	}

	/*
	public void render(Graphics g, Point3D camLoc, Vector3D camOrient, int kill) {
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
	*/
	
	public void oldRender(Graphics g, Point3D camLoc, Vector3D camOrient) {
		/*
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
		*/
	}

	public void tick() {
		
	}

	public void backgroundRender(Graphics g, Point3D camLoc, Vector3D camOrient){
		/*
		// Project to plane
		
		Point3D pointTL = new Point3D(pointTopLeft[0], pointTopLeft[1], pointTopLeft[2]);
		Point3D pointTR = new Point3D(pointTopRight[0], pointTopRight[1], pointTopRight[2]);
		Point3D pointBR = new Point3D(pointBotRight[0], pointBotRight[1], pointBotRight[2]);
		Point3D pointBL = new Point3D(pointBotLeft[0], pointBotLeft[1], pointBotLeft[2]);
		System.out.println("Before projection: " + pointTL.x + "," + pointTL.y + "," + pointTL.z);
		pointTL = pointTL.projectOntoPlane(camLoc, camOrient);
		pointTR = pointTR.projectOntoPlane(camLoc, camOrient);
		pointBR = pointBR.projectOntoPlane(camLoc, camOrient);
		pointBL = pointBL.projectOntoPlane(camLoc, camOrient);
		System.out.println("After projection: " + pointTL.x + "," + pointTL.y + "," + pointTL.z);
		
		// Calc relative "x,y" difference using basis vectors
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		xPoints[0] = Env.RESWIDTH/2 + (pointTL.x - camLoc.x);
		xPoints[1] = Env.RESWIDTH/2 + (pointTR.x - camLoc.x);
		xPoints[2] = Env.RESWIDTH/2 + (pointBR.x - camLoc.x);
		xPoints[3] = Env.RESWIDTH/2 + (pointBL.x - camLoc.x);
		
		System.out.println("Resheight/2 = " + Env.RESHEIGHT/2 + ", pointTL.z: " + pointTL.z + ", camLoc.z: " + camLoc.z);
		yPoints[0] = Env.RESHEIGHT/2 - (pointTL.z - camLoc.z);
		yPoints[1] = Env.RESHEIGHT/2 - (pointTR.z - camLoc.z);
		yPoints[2] = Env.RESHEIGHT/2 - (pointBR.z - camLoc.z);
		yPoints[3] = Env.RESHEIGHT/2 - (pointBL.z - camLoc.z);
		
		// Draw points
		g.setColor(Color.red);
		System.out.print("xPoints: " + Arrays.toString(xPoints));
		System.out.println(", yPoints: " + Arrays.toString(yPoints));
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.gray);
		g.drawPolygon(xPoints, yPoints, 4);
		*/
	}
	
	public void render(Graphics g, Point3D camLoc, Vector3D camOrient) {
		if(orientationChanged()){
			getPoints();
		}
		// Get distance between points
		//int distance = this.loc.distanceBetween(camLoc);
		// Get yz difference
		int distance = (int)Math.sqrt(Math.pow(loc.y-camLoc.y,2) + Math.pow(loc.z-camLoc.z, 2));
		// Get scaling factor
		double scaling = (500 / (double)distance);
		
		int halfLen = length/2;
		int scaledLen = (int)(((double)halfLen) * scaling);
		
		System.out.print("Camera: " + camLoc.x + "," + camLoc.y + "," + camLoc.z);
		System.out.print(", Rect: " + loc.x + "," + loc.y + "," + loc.z);
		System.out.println(", Distance: " + distance + ", scaling: " + scaling + ", halfLen: "  + halfLen + ", scaledLen: " + scaledLen);
		
		// Calc four points
		int[] pointTopLeft = {loc.x - scaledLen, 500, loc.z + scaledLen};
		int[] pointTopRight = {loc.x + scaledLen, 500, loc.z + scaledLen};
		int[] pointBotRight = {loc.x + scaledLen, 500, loc.z + -scaledLen};
		int[] pointBotLeft = {loc.x - scaledLen, 500, loc.z + -scaledLen};
		
		/*
		int[] pointTopLeft = {loc.x - halfLen, 500, halfLen};
		int[] pointTopRight = {loc.x + halfLen, 500, halfLen};
		int[] pointBotRight = {loc.x + halfLen, 500, -halfLen};
		int[] pointBotLeft = {loc.x - halfLen, 500, -halfLen};
		*/
		
		// Project to plane
		
		Point3D pointTL = new Point3D(pointTopLeft[0], pointTopLeft[1], pointTopLeft[2]);
		Point3D pointTR = new Point3D(pointTopRight[0], pointTopRight[1], pointTopRight[2]);
		Point3D pointBR = new Point3D(pointBotRight[0], pointBotRight[1], pointBotRight[2]);
		Point3D pointBL = new Point3D(pointBotLeft[0], pointBotLeft[1], pointBotLeft[2]);
		System.out.println("Before projection: " + pointTL.x + "," + pointTL.y + "," + pointTL.z);
		pointTL = pointTL.projectOntoPlane(camLoc, camOrient);
		pointTR = pointTR.projectOntoPlane(camLoc, camOrient);
		pointBR = pointBR.projectOntoPlane(camLoc, camOrient);
		pointBL = pointBL.projectOntoPlane(camLoc, camOrient);
		System.out.println("After projection: " + pointTL.x + "," + pointTL.y + "," + pointTL.z);
		
		// Calc relative "x,y" difference using basis vectors
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		xPoints[0] = Env.RESWIDTH/2 + (pointTL.x - camLoc.x);
		xPoints[1] = Env.RESWIDTH/2 + (pointTR.x - camLoc.x);
		xPoints[2] = Env.RESWIDTH/2 + (pointBR.x - camLoc.x);
		xPoints[3] = Env.RESWIDTH/2 + (pointBL.x - camLoc.x);
		
		System.out.println("Resheight/2 = " + Env.RESHEIGHT/2 + ", pointTL.z: " + pointTL.z + ", camLoc.z: " + camLoc.z);
		yPoints[0] = Env.RESHEIGHT/2 - (pointTL.z - camLoc.z);
		yPoints[1] = Env.RESHEIGHT/2 - (pointTR.z - camLoc.z);
		yPoints[2] = Env.RESHEIGHT/2 - (pointBR.z - camLoc.z);
		yPoints[3] = Env.RESHEIGHT/2 - (pointBL.z - camLoc.z);
		/*
		xPoints[0] = Env.RESWIDTH + pointTopLeft[0] - camLoc.x;
		xPoints[1] = Env.RESWIDTH + pointTopRight[0] - camLoc.x;
		xPoints[2] = Env.RESWIDTH + pointBotRight[0] - camLoc.x;
		xPoints[3] = Env.RESWIDTH + pointBotLeft[0] - camLoc.x;
		
		yPoints[0] = Env.RESHEIGHT + pointTopLeft[2] - camLoc.z;
		yPoints[1] = Env.RESHEIGHT + pointTopRight[2] - camLoc.z;
		yPoints[2] = Env.RESHEIGHT + pointBotRight[2] - camLoc.z;
		yPoints[3] = Env.RESHEIGHT + pointBotLeft[2] - camLoc.z;
		*/
		
		// Draw points
		g.setColor(Color.red);
		System.out.print("xPoints: " + Arrays.toString(xPoints));
		System.out.println(", yPoints: " + Arrays.toString(yPoints));
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.gray);
		g.drawPolygon(xPoints, yPoints, 4);
	}

	public int[][] getPoints() {
		return null;
	}

	public boolean orientationChanged() {
		return orientationChanged;
	}

	public void initPoints() {
		// Set the size of the four points of the square
		// 4 points, 3 attributes (x, y, z)
		points = new int[3][4];
	}
}
