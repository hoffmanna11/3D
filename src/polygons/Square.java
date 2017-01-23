package polygons;

import java.awt.Color;
import java.awt.Graphics;
import game.Camera;
import game.Env;
import sub.Orient3D;
import sub.Vector3D;

public class Square {
	int length;
	public Vector3D[] points;
	boolean bg = false;
	public Color color = null;
	public Orient3D orient;
	public Vector3D loc;

	public Square(Vector3D loc, Orient3D orient, int length){
		this.orient = orient;
		this.length = length;
		// Initialize points
		points = new Vector3D[4];
		for(int i=0; i<4; i++){
			points[i] = new Vector3D(0,0,0);
		}
	}

	public void render(Graphics g, Camera camera, int len){
		setPointsForRender(camera, len);

		Vector3D[] projectedPoints = new Vector3D[4];
		
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		for(int i=0; i<4; i++){
			projectedPoints[i] = points[i].projectOntoPlane(camera.loc, camera.orient.yz);
			// If the projection involved a positive amount of the yz vector, then don't render
			double d1 = ((Vector3D)points[i]).distanceBetween(projectedPoints[i]);
			double d2 = ((Vector3D)points[i].add(camera.orient.yz)).distanceBetween(projectedPoints[i]);
			
			if(d2 < d1){
				return;
			}
			
			// draw the projected point in terms of the xy and xz vector
			int[] drawLoc = camera.getBaseCoords(projectedPoints[i]);
			xPoints[i] = Env.RESWIDTH/2 + drawLoc[0];
			yPoints[i] = Env.RESHEIGHT/2 - drawLoc[1];
		}
		
		System.out.println("Orient:");
		this.orient.xy.print();
		this.orient.yz.print();
		this.orient.xz.print();
		
		System.out.println("Points:");
		for(int i=0; i<4; i++){
			System.out.print("(" + xPoints[i] + "," + yPoints[i] + "), ");
		}System.out.println("");

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
	
	public void setPointsForRender(Camera camera, double len){
		points[0] = (Vector3D) loc.add(orient.xy.multiply(-len).add(orient.xz.multiply(len)));
		points[1] = (Vector3D) loc.add(orient.xy.multiply(len).add(orient.xz.multiply(len)));
		points[2] = (Vector3D) loc.add(orient.xy.multiply(len).add(orient.xz.multiply(-len)));
		points[3] = (Vector3D) loc.add(orient.xy.multiply(-len).add(orient.xz.multiply(-len)));
	}
}
