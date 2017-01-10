package game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game_cat.GameObject;
import game_cat.Polygon;
import polygons.Square;
import sub.Point3D;
import sub.Vector3D;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	List<Polygon> polygons = new ArrayList<Polygon>();
	Camera camera;
	Square[] background = new Square[6];

	public void tick(){
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.tick();
		}
		camera.tick();
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

	public void setBackground(){
		// Background squares
		int WORLDLENGTH = Env.WORLDLENGTH;
		int WORLDWIDTH = Env.WORLDWIDTH;
		int WORLDHEIGHT = Env.WORLDHEIGHT;
		background[0] = new Square( 
				new Point3D(0, 0, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, 0, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, 0, 0),
				new Point3D(0, 0, 0));
		background[1] = new Square( 
				new Point3D(0, 0, WORLDHEIGHT),
				new Point3D(0, WORLDWIDTH, WORLDHEIGHT),
				new Point3D(0, WORLDWIDTH, 0),
				new Point3D(0, 0, 0));
		background[2] = new Square( 
				new Point3D(0, WORLDWIDTH, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, WORLDWIDTH, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, WORLDWIDTH, 0),
				new Point3D(0, WORLDWIDTH, 0));
		background[3] = new Square( 
				new Point3D(WORLDLENGTH, WORLDWIDTH, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, 0, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, 0, 0),
				new Point3D(WORLDLENGTH, WORLDWIDTH, 0));
		background[4] = new Square( 
				new Point3D(0, WORLDWIDTH, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, WORLDWIDTH, WORLDHEIGHT),
				new Point3D(WORLDLENGTH, 0, WORLDHEIGHT),
				new Point3D(0, 0, 0));
		background[5] = new Square( 
				new Point3D(0, WORLDWIDTH, 0),
				new Point3D(WORLDLENGTH, WORLDWIDTH, 0),
				new Point3D(WORLDLENGTH, 0, 0),
				new Point3D(0, 0, 0));
	}

}
