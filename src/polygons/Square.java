package polygons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import game.Camera;
import game.Env;
import sub.Orient3D;
import sub.Vector3D;
import underlays.Grid;

public class Square implements Runnable {
	private Vector3D loc;
	public Orient3D orient;
	public Vector3D[] points;
	public Color color = null;
	public int length;
	int id;

	public static boolean debug1 = false;

	public static int time = 0;
	public static int indexThing = 0;

	public Square(Vector3D cubeLoc, Orient3D cubeOrient, int length, int id){
		this.length = length;
		this.id = id;

		// Initialize points
		points = new Vector3D[4];
		for(int i=0; i<4; i++){
			points[i] = new Vector3D(0,0,0);
		}

		// Calculate cubes orient
		setOrient(cubeOrient);

		// Get location according to cubeLoc
		setLoc(cubeLoc, cubeOrient);

		setPoints();
	}

	public void setPoints(){
		points[0] = (Vector3D) loc.add(orient.xy.multiply(-length/2).add(orient.xz.multiply(length/2)));
		points[1] = (Vector3D) loc.add(orient.xy.multiply(length/2).add(orient.xz.multiply(length/2)));
		points[2] = (Vector3D) loc.add(orient.xy.multiply(length/2).add(orient.xz.multiply(-length/2)));
		points[3] = (Vector3D) loc.add(orient.xy.multiply(-length/2).add(orient.xz.multiply(-length/2)));
	}

	public Vector3D setLoc(Vector3D cubeLoc, Orient3D cubeOrient){
		this.loc = (Vector3D)cubeLoc.add(orient.yz.multiply(( -1.0 * (double)length)/2.0));
		return this.loc;
	}

	public Orient3D setOrient(Orient3D cubeOrient){
		Orient3D myOrient = null;
		
		switch(id){
		case 0:
			myOrient = cubeOrient.clone();
			break;
		case 1:
			Vector3D orientXY1 = (Vector3D)cubeOrient.yz.multiply(-1);
			Vector3D orientYZ1 = (Vector3D)cubeOrient.xy.multiply(1);
			Vector3D orientXZ1 = (Vector3D)cubeOrient.xz.multiply(1);
			myOrient = new Orient3D(orientXY1, orientYZ1, orientXZ1);
			break;
		case 2:
			Vector3D orientXY2 = (Vector3D)cubeOrient.xy.multiply(-1);
			Vector3D orientYZ2 = (Vector3D)cubeOrient.yz.multiply(-1);
			Vector3D orientXZ2 = (Vector3D)cubeOrient.xz.multiply(1);
			myOrient = new Orient3D(orientXY2, orientYZ2, orientXZ2);
			break;
		case 3:
			// Square 3
			Vector3D orientXY3 = (Vector3D)cubeOrient.yz.multiply(1);
			Vector3D orientYZ3 = (Vector3D)cubeOrient.xy.multiply(-1);
			Vector3D orientXZ3 = (Vector3D)cubeOrient.xz.multiply(1);
			myOrient = new Orient3D(orientXY3, orientYZ3, orientXZ3);
			break;
		case 4:
			Vector3D orientXY4 = (Vector3D)cubeOrient.xy.multiply(1);
			Vector3D orientYZ4 = (Vector3D)cubeOrient.xz.multiply(-1);
			Vector3D orientXZ4 = (Vector3D)cubeOrient.yz.multiply(1);
			myOrient = new Orient3D(orientXY4, orientYZ4, orientXZ4);
			break;
		case 5:
			Vector3D orientXY5 = (Vector3D)cubeOrient.xy.multiply(1);
			Vector3D orientYZ5 = (Vector3D)cubeOrient.xz.multiply(1);
			Vector3D orientXZ5 = (Vector3D)cubeOrient.yz.multiply(-1);
			myOrient = new Orient3D(orientXY5, orientYZ5, orientXZ5);
			break;
		default:
			return null;
		}
		
		this.orient = myOrient;
		return myOrient;
	}
	
	public int[] getScreenLoc(Vector3D mults, double xyAngle, double xzAngle){
		double dx = mults.dx();
		double dy = mults.dy();
		double dz = mults.dz();
		xyAngle = Math.toRadians(xyAngle);
		xzAngle = Math.toRadians(xzAngle);
		
		double resXHalf = Env.resWidth / 2;
		double resYHalf = Env.resHeight / 2;
		
		Dimension dim = Env.window.frame.getSize();
		
		if(dim.getWidth() != Env.resWidth){
			Env.resWidth = (int)dim.getWidth();
		}
		if(dim.getHeight() != Env.resHeight){
			Env.resHeight = (int)dim.getHeight();
		}

		if(dy <= 0){
			return null;
		}

		int x = (int) ( resXHalf + ( dx * Math.tan(xyAngle) / dy * resXHalf ) );
		int y = (int) ( resYHalf - ( dz * Math.tan(xzAngle) / dy * resYHalf ) );

		return new int[]{x,y};
	}

	public int[] getRender(Graphics g, Camera camera, Grid grid){
		Vector3D[] diffs = new Vector3D[4];

		for(int i=0; i<4; i++){
			// Get the difference from the camera to the point
			diffs[i] = new Vector3D(
					this.points[i].dx() - camera.loc.dx(),
					this.points[i].dy() - camera.loc.dy(),
					this.points[i].dz() - camera.loc.dz()
					);
		}

		Vector3D mults[] = new Vector3D[4];

		//Vector3D mults[] = new Vector3D[4];
		boolean atLeastOneIsVisible = false;
		//int visibleCount = 0;
		for(int i=0; i<4; i++){
			mults[i] = Vector3D.getBasisMultiples(camera.orient, diffs[i]);
			if(mults[i].dy() > 0){
				atLeastOneIsVisible = true;
				//visibleCount++;
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
			int[] screenLoc = getScreenLoc(mults[i], xyAngle, xzAngle);

			if(null == screenLoc){
				return null;
			}

			xPoints[i] = screenLoc[0];
			yPoints[i] = screenLoc[1]; 
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

	public Vector3D loc(){
		return this.loc;
	}

	public void run() {
		
	}

}
