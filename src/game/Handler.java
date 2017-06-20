package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import game_cat.GameObject;
import game_cat.Overlay;
import game_cat.Underlay;
import game_objects.Cube;
import overlays.Grid;
import polygons.Square;

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

		renderGameObjects(g);

		for(int i=0; i<overlays.size(); i++){
			Overlay tempOverlay = overlays.get(i);
			tempOverlay.render(g);
		}
	}

	public void renderGameObjects(Graphics g){
		// Collect all the polygons
		ArrayList<Cube> cubes = new ArrayList<Cube>();
		ArrayList<Square> squares = new ArrayList<Square>();
		
		for(int i=0; i<object.size(); i++){
			GameObject tempObject = object.get(i);
			if(tempObject.getClass().equals(Cube.class)){
				cubes.add((Cube)tempObject);
			}
		}
		
		for(int i=0; i<cubes.size(); i++){
			for(int j=0; j<6; j++){
				squares.add(cubes.get(i).squares[j]);
			}
		}
		
		render(g, camera, grid, squares);
	}
	
	public void render(Graphics g, Camera camera, Grid grid, ArrayList<Square> squares){
		double[] distances = new double[6];
		for(int i=0; i<6; i++){
			distances[i] = 0;
			// Add up distances between four points on square to get an average
			for(int j=0; j<4; j++){
				distances[i] += camera.loc.distanceBetween(squares.get(i).points[j]);
			}
		}
		
		int[] indexArr = {0,1,2,3,4,5};
		sort(distances, indexArr);
		for(int i=5; i>=0; i--){
			squares.get(indexArr[i]).render(g,camera,grid);
		}
		
		if(Square.time >= 90){
			// Once square has been rendered X times, switch to next index
			Square.time = 0;
			Square.indexThing = (Square.indexThing + 1) % 6;
		}else{
			// If not rendered X times yet, keep going
		}
	}
	
	public void sort(double[] arr, int[] indexArr){
		for(int i=0; i<arr.length-1; i++){
			for(int j=0; j<arr.length-1; j++){
				if(arr[j] > arr[j+1]){
					double temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
					
					int tempInt = indexArr[j];
					indexArr[j] = indexArr[j+1];
					indexArr[j+1] = tempInt;
				}
			}
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
