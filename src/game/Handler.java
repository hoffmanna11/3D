package game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game_cat.GameObject;
import game_cat.Polygon;
import sub.Point3D;
import sub.Vector3D;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	List<Polygon> polygons = new ArrayList<Polygon>();
	Camera camera;
	
	public void tick(){
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g, camera.camLoc, camera.camOrient);
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	public void setCamera(Point3D loc, Vector3D orient){
		this.camera = new Camera(loc, orient);
	}
	
}
