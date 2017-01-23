package game_cat;
import java.awt.Graphics;

import game.Camera;
import sub.Orient3D;
import sub.Vector3D;

public abstract class GameObject {
	public  Vector3D loc;
	public Orient3D orient;
	
	public GameObject(Vector3D loc, Vector3D yzOrient) {
		this.loc = loc;
		// Set Orient3D based on yzOrient
		//asdf
	}

	public abstract void tick();
	public abstract void render(Graphics g, Camera camera);
}