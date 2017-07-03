package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import game_cat.GameObject;
import game_cat.Overlay;
import game_cat.Underlay;
import game_objects.Cube;
import polygons.Square;
import sub.ParallelMergeSort;
import underlays.Grid;

public class Handler implements Runnable {
	Grid grid;
	Camera camera;
	Square[] background = new Square[6];

	LinkedList<GameObject> object = new LinkedList<GameObject>();
	ArrayList<Overlay> overlays = new ArrayList<Overlay>();
	ArrayList<Underlay> underlays = new ArrayList<Underlay>();

	public static int index = 0;

	public void run(){

	}

	public void tick(){
		for(int i = 0; i < object.size(); i++) {
			/*
			GameObject tempObject = object.get(i);
			tempObject.tick();
			 */
			Env.threads[index].tick(object, i);
			index = (index + 1) % Env.numCores;
		}
		camera.tick();
	}

	public void render(Graphics g) {
		for(int i=0; i<underlays.size(); i++){
			Env.threads[index].renderUnderlays(g, underlays, i);
			index = (index + 1) % Env.numCores;
			/*
			Underlay tempUnderlay = underlays.get(i);
			tempUnderlay.render(g);
			 */
		}

		renderGameObjects(g);

		for(int i=0; i<overlays.size(); i++){
			Env.threads[index].renderOverlays(g, overlays, i);
			index = (index + 1) % Env.numCores;
			/*
			Overlay tempOverlay = overlays.get(i);
			tempOverlay.render(g);
			 */
		}
	}

	public void renderGameObjects(Graphics g){
		// Collect all the polygons
		ArrayList<Cube> cubes = new ArrayList<Cube>();
		ArrayList<Square> squares = new ArrayList<Square>();

		for(int i=0; i<object.size(); i++){
			Env.threads[index].renderGameObjects1(object, i, cubes);
			index = (index + 1) % Env.numCores;
			/*
			GameObject tempObject = object.get(i);
			if(tempObject.getClass().equals(Cube.class)){
				cubes.add((Cube)tempObject);
			}
			 */
		}

		for(int i=0; i<cubes.size(); i++){
			cubes.get(i).trailblaze();
			for(int j=0; j<6; j++){
				Env.threads[index].renderGameObjects2(squares, cubes, i, j);
				index = (index + 1) % Env.numCores;
				// squares.add(cubes.get(i).squares[j]);
			}
		}

		render(g, camera, grid, squares);
	}

	public void render(Graphics g, Camera camera, Grid grid, ArrayList<Square> squares){
		int numSquares = squares.size();
		double[] distances = new double[numSquares];
		int[] indexArr = new int[squares.size()];

		for(int i=0; i<numSquares; i++){
			Env.threads[index].renderGameObjects3(distances, squares, camera, i);
			index = (index + 1) % Env.numCores;
			//distances[i] = squares.get(i).loc().distanceBetween(camera.loc);
		}

		for(int i=0; i<squares.size(); i++){
			Env.threads[index].renderGameObjects4(indexArr, i);
			index = (index + 1) % Env.numCores;
			// indexArr[i] = i;
		}

		//oldSort(distances, indexArr);
		sort(distances, indexArr);

		int rounds = 10;
		for(int i=squares.size() - 1; i>=0; ){
			Env.threads[index].renderGameObjects5(squares, indexArr, g, camera, grid, i, rounds);
			index = (index + 1) % Env.numCores;
			i -= rounds;
			// squares.get(indexArr[i]).render(g,camera,grid);
		}

		if(Square.time >= 90){
			// Once square has been rendered X times, switch to next index
			Square.time = 0;
			Square.indexThing = (Square.indexThing + 1) % 6;
		}else{
			// If not rendered X times yet, keep going
		}
	}

	public void oldSort(double[] arr, int[] indexArr){
		for(int i=0; i<arr.length-1; i++){
			for(int j=0; j<arr.length-1; j++){
				//Env.threads[index].renderGameObjectsSort(arr, indexArr, j);
				//index = (index + 1) % Env.numCores;

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
	
	public void sort(double[] arr, int[] indexArr){
		/*
		double[] arrCopy;
		int[] indexArrCopy;

		long startTime1 = System.nanoTime();
		
		*/
		
		/*
		for(int i=0; i<arr.length-1; i++){
			for(int j=0; j<arr.length-1; j++){
				//Env.threads[index].renderGameObjectsSort(arr, indexArr, j);
				//index = (index + 1) % Env.numCores;

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
		*/
		
		//long time1 = System.nanoTime() - startTime1;
		
		//arrCopy = Arrays.copyOf(arr, arr.length);
		//indexArrCopy = Arrays.copyOf(indexArr, indexArr.length);

		//long startTime2 = System.nanoTime();
		ParallelMergeSort.sort(arr, indexArr);
		//long time2 = System.nanoTime() - startTime2;

		/*
		for(int i=0; i<arrCopy.length; i++){
			System.out.printf("%3f -> %3f, %3d -> %3d\n", arr[i], arrCopy[i], indexArr[i], indexArrCopy[i]);
		}
		*/
		
		//double improvement = ( (double)time2 / (double)time1 );
		//System.out.println("time1: " + time1 + ", time2: " + time2 + ", improvement ratio: " + improvement + ", " + (1 / improvement));

		//for(int i=0; i<arr.length-1; i++){
		//	for(int j=0; j<arr.length-1; j++){
		//		Env.threads[index].renderGameObjectsSort(arr, indexArr, j);
		//		index = (index + 1) % Env.numCores;
		/*
				if(arr[j] > arr[j+1]){
					double temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;

					int tempInt = indexArr[j];
					indexArr[j] = indexArr[j+1];
					indexArr[j+1] = tempInt;
				}
		 */
		//	}
		//}
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
