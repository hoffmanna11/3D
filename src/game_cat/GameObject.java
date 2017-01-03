package game_cat;
import java.awt.Graphics;

import sub.ID;
import sub.Point3D;
import sub.Vector3D;

public abstract class GameObject {
	protected ID id;
	protected Point3D loc;
	protected Vector3D orient;
	
	public GameObject(ID id, Point3D loc, Vector3D orient) {
		this.loc = loc;
		this.id = id;
		this.orient = orient;
	}

	public abstract void tick();
	public abstract void render(Graphics g, Point3D viewerLoc, Vector3D viewerOrient);
	
	public void setId(ID id) {
		this.id = id;
	}
	public ID getId() {
		return id;
	}
}