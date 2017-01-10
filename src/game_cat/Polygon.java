package game_cat;
import java.awt.Graphics;

import sub.Point3D;
import sub.Vector3D;

public abstract class Polygon extends GameObject {
	protected boolean orientationChanged = true;
	protected int[][] points;
	
	public Polygon(Point3D loc, Vector3D orient){
		super(loc, orient);
	}
	
	public abstract void initPoints();
	public abstract boolean orientationChanged();
	public abstract void render(Graphics g, Point3D viewerLoc, Vector3D viewerOrient);
	public abstract int[][] getPoints();
}
