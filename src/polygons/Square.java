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

	public Square(Point3D loc, Orient3D orient, int length){
		super(loc, null);
		this.orient3D = orient;
		this.length = length;
		// Initialize points
		points = new Point3D[4];
		for(int i=0; i<4; i++){
			points[i] = new Point3D(0,0,0);
		}
	}

	public void setPointsCorrectly(Point3D cubeLoc, int len){
		points[0] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		points[1] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		points[2] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(-len)))).toPoint3D()));
		points[3] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(-len)))).toPoint3D()));
	}
	
	public Point3D[] getPointsForRender(Camera camera, double len){
		Point3D scaledPoints[] = new Point3D[4];
		
		scaledPoints[0] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		scaledPoints[1] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(len)))).toPoint3D()));
		scaledPoints[2] = loc.add((((Vector3D) (orient3D.xy.multiply(len).add(orient3D.xz.multiply(-len)))).toPoint3D()));
		scaledPoints[3] = loc.add((((Vector3D) (orient3D.xy.multiply(-len).add(orient3D.xz.multiply(-len)))).toPoint3D()));
		
		return scaledPoints;
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

	public boolean orientationChanged() {
		return orientationChanged;
	}
}
