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

	public Square(Vector3D loc, Orient3D orient, int length, int id){
		this.orient = orient;
		this.length = length;
		this.id = id;

		this.loc = (Vector3D)loc.add(orient.yz.multiply(-length/2));

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

	public void render(Graphics g, Camera camera, Grid grid){
		if(! ( (id == 0) || (id == 2) ) ){
			return;
		}
		
		System.out.print(id + ": ");
		loc.print();
		
		if(id == 5){
			System.out.println("");
		}
		
		if(id != 0){
			//return;
		}
		
		Vector3D[] diffs = new Vector3D[4];

		for(int i=0; i<4; i++){
			// Get the difference from the camera to the point
			diffs[i] = new Vector3D(
					this.points[i].dx - camera.loc.dx,
					this.points[i].dy - camera.loc.dy,
					this.points[i].dz - camera.loc.dz
					);
		}

		Vector3D mults[] = new Vector3D[4];
		for(int i=0; i<4; i++){
			mults[i] = Vector3D.getBasisMultiples(camera.orient.xy, camera.orient.yz, camera.orient.xz, diffs[i]);
			System.out.println("mult[" + i + "]: { " + mults[i].dx + ", " + mults[i].dy + ", " + mults[i].dz + " }");
		}
		
		

		int xPoints[] = new int[4];
		int yPoints[] = new int[4];
		for(int i=0; i<4; i++){
			int[] screenLoc = grid.getScreenLoc(mults[i], camera);
			xPoints[i] = screenLoc[0];
			yPoints[i] = screenLoc[1];
			System.out.println("Point " + i + ": (" + (xPoints[i]-450) + ", " + (yPoints[i]-450) + ")");
		}
		
		if(id == 5){
			System.out.println("");
		}

		g.setColor(this.color);
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.WHITE);
		g.drawPolygon(xPoints, yPoints, 4);
	}

}
