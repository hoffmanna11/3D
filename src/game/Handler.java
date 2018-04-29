package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import object_categories.GameObject;
import object_categories.Overlay;
import object_categories.Polygon;
import object_categories.Underlay;
import polygons.Square;
import support_lib.ParallelMergeSort;

public class Handler implements Runnable {
	Camera camera;
	Square[] background = new Square[6];

	LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
	ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	ArrayList<Overlay> overlays = new ArrayList<Overlay>();
	ArrayList<Underlay> underlays = new ArrayList<Underlay>();

	public void run(){
		
	}

	public void tick(){
		for(int i = 0; i < gameObjects.size(); i++) {
			GameObject tempObject = gameObjects.get(i);
			tempObject.tick();
		}
		camera.tick();
		/* TODO
		 * instead of camera.tick(), have
		 * 'player.tick()'
		 * player will have a camera
		 * apply controller input
		 *  (should we use listeners instead?)
		 */
	}

	public void render(Graphics g) {
		for(int i=0; i<underlays.size(); i++){
			Underlay temp = underlays.get(i);
			temp.render(g);
		}

		renderGameObjects(g);

		for(int i=0; i<overlays.size(); i++){
			Overlay temp = overlays.get(i);
			temp.render(g);
		}
	}

	public void renderGameObjects(Graphics g){
		int numPolygons = polygons.size();
		double[] distances = new double[numPolygons];
		int[] indexArr = new int[polygons.size()];

		for(int i=0; i<numPolygons; i++){
			distances[i] = polygons.get(i).loc().distanceBetween(camera.loc);
		}

		for(int i=0; i<numPolygons; i++){
			indexArr[i] = i;
		}

		//bubbleSort(distances, indexArr);
		ParallelMergeSort.sort(distances, indexArr);

		for(int i=polygons.size() - 1; i >= 0; i--) {
			polygons.get(indexArr[i]).render(g,camera);
		}
	}

	public void bubbleSort(double[] arr, int[] indexArr) {
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

	public void initPolygons() {
		// Collect all the polygons
		GameObject tempObject = null;
		Polygon[] tempPolygons = null;
		for(int i=0; i<gameObjects.size(); i++){
			tempObject = gameObjects.get(i);
			tempPolygons = tempObject.getPolygons();
			for(int j=0; j<tempPolygons.length; j++) {
				polygons.add(tempPolygons[j]);
			}
		}
	}

	public void addUnderlay(Underlay underlay){
		this.underlays.add(underlay);
	}

	public void removeUnderlay(Underlay underlay){
		this.underlays.remove(underlay);
	}

	public void addOverlay(Overlay overlay) {
		this.overlays.add(overlay);
	}

	public void removeOverlay(Overlay overlay) {
		this.overlays.remove(overlay);
	}

	public void addObject(GameObject object) {
		this.gameObjects.add(object);
		addPolygons(object.getPolygons());
	}
	
	public void addPolygons(Polygon[] objectPolygons) {
		for(int i=0; i<objectPolygons.length; i++) {
			this.polygons.add(objectPolygons[i]);
		}
	}

	public void removeObject(GameObject object) {
		this.gameObjects.remove(object);
	}

	public void setCamera(Camera camera){
		this.camera = camera;
	}

}
