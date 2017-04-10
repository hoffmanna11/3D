package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import game_cat.GameObject;
import game_cat.Overlay;
import game_cat.Underlay;
import overlays.Grid;
import polygons.Square;
import sub.Vector3D;

public class Handler {
	Grid grid;
	Camera camera;
	Square[] background = new Square[6];
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	ArrayList<Overlay> overlays = new ArrayList<Overlay>();
	ArrayList<Underlay> underlays = new ArrayList<Underlay>();

	public void tick(){
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
		camera.tick();
	}

	public void render(Graphics g) {
		for(int i=0; i<underlays.size(); i++){
			Underlay tempUnderlay = underlays.get(i);
			tempUnderlay.render(g);
		}
		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.render(g, camera, grid);
		}
		
		for(int i=0; i<overlays.size(); i++){
			Overlay tempOverlay = overlays.get(i);
			tempOverlay.render(g);
		}
	}
	
	public void setGrid(Grid grid){
		this.grid = grid;
		addUnderlay(grid);
	}
	
	public void addUnderlay(Underlay underlay){
		this.underlays.add(underlay);
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

	public void setCamera(Camera camera){
		this.camera = camera;
	}

}
