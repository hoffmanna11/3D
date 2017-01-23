package polygons;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import game.Camera;
import game.Env;
import game_cat.Polygon;
import sub.Orient3D;
import sub.Point3D;
import sub.Vector3D;

public class Square extends Polygon {
	int length;
	public Point3D[] points;
	boolean bg = false;
	public Color color = null;
	public Orient3D orient3D;

	public Square(Point3D loc, int length) {
		super(loc, null);
		this.length = length;
		setPoints();
	}

	public Square(Point3D loc, Orient3D orient, int length){
		super(loc, null);
		this.orient3D = orient;
		this.length = length;
		points = new Point3D[4];
		//setPointsCorrectly();
		for(int i=0; i<4; i++){
			points[i] = new Point3D(0,0,0);
		}
	}

	public void setPointsCorrectly(Point3D cubeLoc, int len){
		//int len = length / 2;
		
		points[0] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		points[1] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		points[2] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(-len)))).toPoint3D()));
		points[3] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(-len)))).toPoint3D()));

		/*
		System.out.println("Square loc: " + loc.toString());
		System.out.println("Square points:");
		for(int i=0; i<4; i++){
			System.out.println(points[i].toString());
		}
		System.out.println("");
		*/
	}

	public void setLocProper(Point3D cubeLoc, int len){
		
	}
	
	public Point3D[] getPointsForRender(Camera camera, double len){
		Point3D scaledPoints[] = new Point3D[4];
		
		scaledPoints[0] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		scaledPoints[1] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		scaledPoints[2] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(-len)))).toPoint3D()));
		scaledPoints[3] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(-len)))).toPoint3D()));
		
		/*
		System.out.println("Square points:");
		for(int i=0; i<4; i++){
			System.out.println(scaledPoints[i].toString());
		}
		*/
		/*
		if(color == Color.red){
			System.out.println("point: ");
			for(int i=0; i<4; i++){
				scaledPoints[i].print();
			}System.out.println("");
		}
		*/
		
		return scaledPoints;
	}

	// Background squares
	public Square(Point3D p1, Point3D p2, Point3D p3, Point3D p4){
		super(null, null);
		bg = true;
		points = new Point3D[4];
		points[0] = new Point3D(p1.x, p1.y, p1.z);
		points[1] = new Point3D(p2.x, p2.y, p2.z);
		points[2] = new Point3D(p3.x, p3.y, p3.z);
		points[3] = new Point3D(p4.x, p4.y, p4.z);
	}

	public void setPoints(){
		points = new Point3D[4];
		int halfLen = this.length / 2;
		points[0] = new Point3D(loc.x - halfLen, loc.y, loc.z + halfLen);
		points[1] = new Point3D(loc.x + halfLen, loc.y, loc.z + halfLen);
		points[2] = new Point3D(loc.x + halfLen, loc.y, loc.z - halfLen);
		points[3] = new Point3D(loc.x - halfLen, loc.y, loc.z - halfLen);
	}

	public void render(Graphics g, Camera camera, int len){
		Point3D points[] = getPointsForRender(camera, len);

		Vector3D[] projectedPoints = new Vector3D[4];
		
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		for(int i=0; i<4; i++){
			projectedPoints[i] = points[i].projectOntoPlane(camera.getCamLoc(), camera.getYZ());
			// If the projection involved a positive amount of the yz vector, then don't render
			double d1 = points[i].distanceBetween(projectedPoints[i].toPoint3D());
			double d2 = points[i].add(camera.getYZ()).distanceBetween(projectedPoints[i].toPoint3D());
			
			if(d2 < d1){
				return;
			}
			
			// draw the projected point in terms of the xy and xz vector
			int[] drawLoc = camera.getBaseCoords(projectedPoints[i]);
			xPoints[i] = Env.RESWIDTH/2 + drawLoc[0];
			yPoints[i] = Env.RESHEIGHT/2 - drawLoc[1];
		}

		// Draw points
		if(null != color){
			g.setColor(color);
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.gray);
			g.drawPolygon(xPoints, yPoints, 4);
		}
		else if(bg){
			g.setColor(Color.blue);
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.black);
			g.drawPolygon(xPoints, yPoints, 4);
		}else{
			g.setColor(Color.red);
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.gray);
			g.drawPolygon(xPoints, yPoints, 4);
		}
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

	public void lessOldRender(Graphics g, Point3D camLoc, Vector3D camOrient) {
		if(orientationChanged()){
			getPoints();
		}
		// Get distance between points
		int distance = (int)Math.sqrt(Math.pow(loc.x-camLoc.x,2) + Math.pow(loc.y-camLoc.y,2) + Math.pow(loc.z-camLoc.z, 2));
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

		//pointTL = pointTL.projectOntoPlane(camLoc, camOrient);
		//pointTR = pointTR.projectOntoPlane(camLoc, camOrient);
		//pointBR = pointBR.projectOntoPlane(camLoc, camOrient);
		//pointBL = pointBL.projectOntoPlane(camLoc, camOrient);
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
		// points = new int[3][4];
	}

	@Override
	public void render(Graphics g, Camera camera) {
		// TODO Auto-generated method stub
		
	}
}
