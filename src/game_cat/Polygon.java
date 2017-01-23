package game_cat;

import sub.Point3D;
import sub.Vector3D;

public abstract class Polygon {
	public boolean orientationChanged = true;
	public int[][] points;
	public Point3D loc;
	
	public Polygon(Point3D loc, Vector3D yz){
		this.loc = loc;
	}
	
	//
	//public abstract void render(Graphics g, Point3D camLoc, Orient3D camOrient, Point3D cubeLoc);
}
