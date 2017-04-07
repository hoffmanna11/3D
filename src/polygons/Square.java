package polygons;

import java.awt.Color;
import java.awt.Graphics;
import game.Camera;
import game.Env;
import game.Handler;
import overlays.Grid;
import sub.Orient3D;
import sub.Vector3D;

public class Square {
	public Vector3D loc;
	public Orient3D orient;
	public Vector3D[] points;
	public Color color = null;
	public int length;

	public Square(Vector3D loc, Orient3D orient, int length){
		this.loc = loc;
		this.orient = orient;
		this.length = length;
		
		// Initialize points
		points = new Vector3D[4];
		for(int i=0; i<4; i++){
			points[i] = new Vector3D(0,0,0);
		}
	}
	
	public void render(Graphics g, Camera camera, int len, Grid grid){
		setPointsForRender(camera, len);
		
		Vector3D[] diffs = new Vector3D[4];
		
		for(int i=0; i<4; i++){
			// Get the difference from the camera to the point
			diffs[i] = new Vector3D(
					this.points[i].dx - camera.loc.dx,
					this.points[i].dy - camera.loc.dy,
					this.points[i].dz - camera.loc.dz
					);
		}
		
		int[] mults1 = Vector3D.getBasisMultiples(camera.orient.xy, camera.orient.yz, camera.orient.xz, diffs[0]);
		int[] mults2 = Vector3D.getBasisMultiples(camera.orient.xy, camera.orient.yz, camera.orient.xz, diffs[1]);
		int[] mults3 = Vector3D.getBasisMultiples(camera.orient.xy, camera.orient.yz, camera.orient.xz, diffs[2]);
		int[] mults4 = Vector3D.getBasisMultiples(camera.orient.xy, camera.orient.yz, camera.orient.xz, diffs[3]);
		
		int[] screenLoc1 = getScreenLoc(mults1, camera);
		int[] screenLoc2 = getScreenLoc(mults2, camera);
		int[] screenLoc3 = getScreenLoc(mults3, camera);
		int[] screenLoc4 = getScreenLoc(mults4, camera);
	}
	
	public int[] getScreenLoc(int[] mults, Camera camera){
		Grid grid = Handler.grid;
		//grid.fociX;
		//grid.fociY;
		
		return null;
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
			
			
			
			xPoints[i] = Env.RESWIDTH/2 + (Grid.convertXToGrid(drawLoc[0]) / 12);
			yPoints[i] = Env.RESHEIGHT/2 - (Grid.convertYToGrid(drawLoc[1]) / 12);
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
