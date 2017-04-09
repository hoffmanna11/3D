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
		
		System.out.println("loc: "); loc.add(orient.xy.multiply(-length/2).add(orient.xz.multiply(length/2))).print();
		loc.print();
		//System.exit(0);
	}
	
	public void render(Graphics g, Camera camera, Grid grid){
		//int[] myX = {456, 504, 504, 456};
		//int[] myY = {406, 406, 500, 500};
		
		if(id != 7){
		
		//setPointsForRender(camera, len);
		
		Vector3D[] diffs = new Vector3D[4];
		
		for(int i=0; i<4; i++){
			// Get the difference from the camera to the point
			diffs[i] = new Vector3D(
					this.points[i].dx - camera.loc.dx,
					this.points[i].dy - camera.loc.dy,
					this.points[i].dz - camera.loc.dz
					);
		}
		
		//System.out.println("Square " + id + ": ");
		
		Vector3D mults[] = new Vector3D[4];
		for(int i=0; i<4; i++){
			mults[i] = Vector3D.getBasisMultiples(camera.orient.xy, camera.orient.yz, camera.orient.xz, diffs[i]);
			//System.out.println("Basis multiples: (" + mults[i].dx + ", " + mults[i].dy + ", " + mults[i].dz + ")");
		}
		
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];
		for(int i=0; i<4; i++){
			int[] screenLoc = grid.getScreenLoc(mults[i], camera);
			xPoints[i] = screenLoc[0];
			yPoints[i] = screenLoc[1];
		}
		
		for(int i=0; i<4; i++){
			//System.out.println("Screen loc: (" + xPoints[i] + ", " + yPoints[i] + ")");
		}//System.out.println();
		
		System.out.println("Square " + id);
		System.out.print("loc: "); loc.print();
		//orient.print();
		System.out.println("Points:");
		for(int i=0; i<4; i++){
			System.out.println(points[i].dx + ", " + points[i].dy + ", " + points[i].dz);
			//points[i].printMatrix();
		}
		
		//System.out.println();
		
		g.setColor(this.color);
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.WHITE);
		g.drawPolygon(xPoints, yPoints, 4);
		}
	}
	
	public void renderOutlineOnly(Graphics g, Camera camera, int len){
		
	}

	public void renderOld(Graphics g, Camera camera, int len){
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
				//return;
			}
			
			// Draw the projected point in terms of the xy and xz vector
			int[] drawLoc = camera.getBaseCoords(projectedPoints[i]);
			
			xPoints[i] = Env.RESWIDTH/2 + (Grid.convertXToGridOld(drawLoc[0]) / 12);
			yPoints[i] = Env.RESHEIGHT/2 - (Grid.convertYToGridOld(drawLoc[1]) / 12);
			//System.out.println("(x,y): (" + (Env.RESWIDTH/2 + drawLoc[0]) + ", " + (Env.RESHEIGHT/2 - drawLoc[1]) + "), new: (" + xPoints[i] + ", " + yPoints[i] + ")");
		}
		
		/*
		System.out.println("Orient:");
		this.orient.xy.print();
		this.orient.yz.print();
		this.orient.xz.print();
		
		System.out.println("Points:");
		for(int i=0; i<4; i++){
			System.out.print("(" + xPoints[i] + "," + yPoints[i] + "), ");
		}System.out.println("");
		*/

		// Draw points
		if(null != color){
			g.setColor(color);
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.gray);
			g.drawPolygon(xPoints, yPoints, 4);
		}else{
			g.setColor(Color.red);
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.gray);
			g.drawPolygon(xPoints, yPoints, 4);
		}
	}
	
	public void renderOutlineOnlyOld(Graphics g, Camera camera, int len){
		setPointsForRender(camera, len);

		Vector3D[] projectedPoints = new Vector3D[4];
		
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		for(int i=0; i<4; i++){
			projectedPoints[i] = points[i].projectOntoPlane(camera.loc, camera.orient.yz);
			
			/* Always render the outline
			// If the projection involved a positive amount of the yz vector, then don't render
			double d1 = ((Vector3D)points[i]).distanceBetween(projectedPoints[i]);
			double d2 = ((Vector3D)points[i].add(camera.orient.yz)).distanceBetween(projectedPoints[i]);
			
			if(d2 < d1){
				return;
			}
			*/
			
			// Draw the projected point in terms of the xy and xz vector
			int[] drawLoc = camera.getBaseCoords(projectedPoints[i]);
			xPoints[i] = Env.RESWIDTH/2 + drawLoc[0];
			yPoints[i] = Env.RESHEIGHT/2 - drawLoc[1];
		}

		// Draw points
		if(null != color){
			//g.setColor(color);
			//g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(Color.gray);
			g.drawPolygon(xPoints, yPoints, 4);
		}else{
			//g.setColor(Color.red);
			//g.fillPolygon(xPoints, yPoints, 4);
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
