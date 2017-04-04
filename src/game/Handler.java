package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import game_cat.GameObject;
import game_cat.Overlay;
import polygons.Square;
import sub.Vector3D;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	Camera camera;
	Square[] background = new Square[6];
	ArrayList<Overlay> overlays = new ArrayList<Overlay>();

	public void tick(){
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
		camera.tick();
	}

	public void render(Graphics g) {
		for(int i=0; i<overlays.size(); i++){
			Overlay tempOverlay = overlays.get(i);
			tempOverlay.render(g);
		}
		
		camera.render(g);
		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.render(g, camera);
		}
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	public void addOverlay(Overlay overlay) {
		this.overlays.add(overlay);
	}

	public void removeOverlay(Overlay overlay) {
		this.overlays.remove(overlay);
	}

	public void setCamera(Vector3D loc, Vector3D yzOrient){
		this.camera = new Camera(loc, yzOrient);
	}
	
	

	public void setBackground(){
		// Background squares
		/*
		int WORLDLENGTH = Env.WORLDLENGTH;
		int WORLDWIDTH = Env.WORLDWIDTH;
		int WORLDHEIGHT = Env.WORLDHEIGHT;
		background[0] = new Square( 
				new Vector3D(0, 0, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, 0, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, 0, 0),
				new Vector3D(0, 0, 0));
		background[1] = new Square( 
				new Vector3D(0, 0, WORLDHEIGHT),
				new Vector3D(0, WORLDWIDTH, WORLDHEIGHT),
				new Vector3D(0, WORLDWIDTH, 0),
				new Vector3D(0, 0, 0));
		background[2] = new Square( 
				new Vector3D(0, WORLDWIDTH, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, WORLDWIDTH, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, WORLDWIDTH, 0),
				new Vector3D(0, WORLDWIDTH, 0));
		background[3] = new Square( 
				new Vector3D(WORLDLENGTH, WORLDWIDTH, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, 0, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, 0, 0),
				new Vector3D(WORLDLENGTH, WORLDWIDTH, 0));
		background[4] = new Square( 
				new Vector3D(0, WORLDWIDTH, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, WORLDWIDTH, WORLDHEIGHT),
				new Vector3D(WORLDLENGTH, 0, WORLDHEIGHT),
				new Vector3D(0, 0, 0));
		background[5] = new Square( 
				new Vector3D(0, WORLDWIDTH, 0),
				new Vector3D(WORLDLENGTH, WORLDWIDTH, 0),
				new Vector3D(WORLDLENGTH, 0, 0),
				new Vector3D(0, 0, 0));
		*/
	}

}
