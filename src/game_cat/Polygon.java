package game_cat;
import java.awt.Graphics;

import sub.Point3D;
import sub.Vector3D;

public abstract class Polygon {
	public Point3D loc;
	public Vector3D orient;
	public Point3D projectionLoc;

	public Polygon(Point3D loc, Vector3D orient){
		this.loc = loc;
		this.orient = orient;
		this.projectionLoc = new Point3D(0,0,0);
	}
	
	public abstract void render(Graphics g, Point3D viewerLoc, Vector3D viewerOrient);
	
	/** @category getter */
	public Point3D getLoc() {
		return loc;
	}

	/** @category setter */
	public void setLoc(Point3D loc) {
		this.loc = loc;
	}

	/** @category getter */
	public Vector3D getOrient() {
		return orient;
	}

	/** @category setter */
	public void setOrient(Vector3D orient) {
		this.orient = orient;
	}

	/** @category getter */
	public Point3D getProjectionLoc() {
		return projectionLoc;
	}

	/** @category setter */
	public void setProjectionLoc(Point3D projectionLoc) {
		this.projectionLoc = projectionLoc;
	}
}
