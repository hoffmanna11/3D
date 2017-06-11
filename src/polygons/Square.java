package polygons;

import java.awt.Color;
import java.awt.Graphics;
import game.Camera;
import game.Env;
import overlays.Grid;
import sub.Orient3D;
import sub.Vector3D;

public class Square {
	public Vector3D loc;
	public Orient3D orient;
	public Vector3D[] points;
	public Color color = null;
	public int length;
	int id;
	
	public static int time = 0;
	public static int indexThing = 0;

	public Square(Vector3D cubeLoc, Orient3D orient, int length, int id){
		this.orient = orient;
		this.length = length;
		this.id = id;

		this.loc = (Vector3D)cubeLoc.add(orient.yz.multiply(-length/2));

		// Initialize points
		points = new Vector3D[4];
		for(int i=0; i<4; i++){
			points[i] = new Vector3D(0,0,0);
		}

		points[0] = (Vector3D) loc.add(orient.xy.multiply(-length/2).add(orient.xz.multiply(length/2)));
		points[1] = (Vector3D) loc.add(orient.xy.multiply(length/2).add(orient.xz.multiply(length/2)));
		points[2] = (Vector3D) loc.add(orient.xy.multiply(length/2).add(orient.xz.multiply(-length/2)));
		points[3] = (Vector3D) loc.add(orient.xy.multiply(-length/2).add(orient.xz.multiply(-length/2)));
	}
	
	public int[] getRender(Graphics g, Camera camera, Grid grid){
		if(time == 0){
			for(int i=0; i<10; i++){
				System.out.println("");
			}
		}
		
		if(time == 0){
			System.out.print(id + ": ");
			loc.print();
			orient.print();
		}
		
		Vector3D[] diffs = new Vector3D[4];

		for(int i=0; i<4; i++){
			if(time == 0){
				System.out.print("Point " + i + " loc: ");
				points[i].print();
			}
			
			// Get the difference from the camera to the point
			diffs[i] = new Vector3D(
					this.points[i].dx() - camera.loc.dx(),
					this.points[i].dy() - camera.loc.dy(),
					this.points[i].dz() - camera.loc.dz()
					);
		}

		Vector3D mults[] = new Vector3D[4];
		boolean atLeastOneIsVisible = false;
		for(int i=0; i<4; i++){
			mults[i] = Vector3D.getBasisMultiples(camera.orient.xy, camera.orient.yz, camera.orient.xz, diffs[i]);
			if(time == 0){
				System.out.println("mult[" + i + "]: { " + mults[i].dx() + ", " + mults[i].dy() + ", " + mults[i].dz() + " }");
			}
			if(mults[i].dy() > 0){
				atLeastOneIsVisible = true;
			}
		}
		
		if(atLeastOneIsVisible){
			// Then draw it
		}else{
			// If no visible, no draw
			return null;
		}

		int xPoints[] = new int[4];
		int yPoints[] = new int[4];
		double xyAngle = 30;
		double xzAngle = 45;
		for(int i=0; i<4; i++){
			int[] screenLoc = grid.getScreenLoc2(mults[i], xyAngle, xzAngle);
			
			//int[] screenLoc = grid.getScreenLoc(mults[i], camera);
			xPoints[i] = screenLoc[0];
			yPoints[i] = screenLoc[1];
			if(time == 0){
				System.out.println("Point " + i + ": (" + (xPoints[i]-450) + ", " + (yPoints[i]-450) + ")");
			}
		}
		
		int[] points = new int[8];
		for(int i=0; i<4; i++){
			points[i] = xPoints[i];
			points[i+4] = yPoints[i];
		}
		return points;
	}

	public void render(Graphics g, Camera camera, Grid grid){
		int[] points = new int[8];
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		points = getRender(g, camera, grid);
		if(null == points){
			return;
		}
		
		for(int i=0; i<4; i++){
			xPoints[i] = points[i];
			yPoints[i] = points[i+4];
		}
		
		g.setColor(this.color);
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.WHITE);
		g.drawPolygon(xPoints, yPoints, 4);
		
		time++;
	}

}
