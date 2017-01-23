package polygons;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import game.Camera;
import game.Env;
import sub.Orient3D;
import sub.Vector3D;

public class Square {
	int length;
	public Vector3D[] points;
	boolean bg = false;
	public Color color = null;
	public Orient3D orient3D;
	public Vector3D loc;

	public Square(Vector3D loc, Orient3D orient, int length){
		this.orient3D = orient;
		this.length = length;
		// Initialize points
		points = new Vector3D[4];
		for(int i=0; i<4; i++){
			points[i] = new Vector3D(0,0,0);
		}
	}

	public void setPointsCorrectly(Vector3D cubeLoc, int len){
		points[0] = (Vector3D) loc.add(orient3D.xy.multiply(-len).add(orient3D.xz.multiply(len)));
		points[1] = (Vector3D) loc.add(orient3D.xy.multiply(len).add(orient3D.xz.multiply(len)));
		points[2] = (Vector3D) loc.add(orient3D.xy.multiply(len).add(orient3D.xz.multiply(-len)));
		points[3] = (Vector3D) loc.add(orient3D.xy.multiply(-len).add(orient3D.xz.multiply(-len)));
	}
	
	public Vector3D[] getPointsForRender(Camera camera, double len){
		Vector3D scaledPoints[] = new Vector3D[4];
		
		scaledPoints[0] = (Vector3D) loc.add(orient3D.xy.multiply(-len).add(orient3D.xz.multiply(len)));
		scaledPoints[1] = (Vector3D) loc.add(orient3D.xy.multiply(len).add(orient3D.xz.multiply(len)));
		scaledPoints[2] = (Vector3D) loc.add(orient3D.xy.multiply(len).add(orient3D.xz.multiply(-len)));
		scaledPoints[3] = (Vector3D) loc.add(orient3D.xy.multiply(-len).add(orient3D.xz.multiply(-len)));
		
		return scaledPoints;
	}

	public void render(Graphics g, Camera camera, int len){
		Vector3D points[] = getPointsForRender(camera, len);

		Vector3D[] projectedPoints = new Vector3D[4];
		
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		for(int i=0; i<4; i++){
			projectedPoints[i] = points[i].projectOntoPlane(camera.loc, camera.getYZ());
			// If the projection involved a positive amount of the yz vector, then don't render
			double d1 = ((Vector3D)points[i]).distanceBetween(projectedPoints[i]);
			double d2 = ((Vector3D)points[i].add(camera.getYZ())).distanceBetween(projectedPoints[i]);
			
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
}
