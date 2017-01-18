package game_cat;
import java.awt.Graphics;

import game.Camera;
import sub.Point3D;
import sub.Vector3D;

public abstract class GameObject {
	public  Point3D loc;
	public Vector3D orient;
	
	public GameObject(Point3D loc, Vector3D orient) {
		this.loc = loc;
		this.orient = orient;
	}

	public abstract void tick();
	public abstract void render(Graphics g, Camera camera);
}