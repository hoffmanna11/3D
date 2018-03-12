package _game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import object_categories.GameObject;
import object_categories.Overlay;
import object_categories.Underlay;
import polygons.Square;
import underlays.Grid;
import units.Cube;

public class MyThread extends Thread {
	Handler handler;
	int id;
	boolean debug = false;
	boolean running = false;
	
	public MyThread(Handler handler, int id){
		super();
		this.handler = handler;
		this.id = id;
	}
	
	public void tick(LinkedList<GameObject> object, int i){
		GameObject tempObject = object.get(i);
		tempObject.tick();
	}
	
	public void renderUnderlays(Graphics g, ArrayList<Underlay> underlays, int i){
		Underlay tempUnderlay = underlays.get(i);
		tempUnderlay.render(g);
	}
	
	public void renderOverlays(Graphics g, ArrayList<Overlay> overlays, int i){
		Overlay tempOverlay = overlays.get(i);
		tempOverlay.render(g);
	}
	
	public void renderGameObjects1(LinkedList<GameObject> object, int i, ArrayList<Cube> cubes){
		GameObject tempObject = object.get(i);
		if(tempObject.getClass().equals(Cube.class)){
			cubes.add((Cube)tempObject);
		}
	}
	
	public void renderGameObjects2(ArrayList<Square> squares, ArrayList<Cube> cubes, int i, int j){
		squares.add(cubes.get(i).squares[j]);
	}
	
	public void renderGameObjects3(double[] distances, ArrayList<Square> squares, Camera camera, int i){
		distances[i] = squares.get(i).loc().distanceBetween(camera.loc);
	}
	
	public void renderGameObjects4(int indexArr[], int i){
		indexArr[i] = i;
	}
	
	public void renderGameObjects5(ArrayList<Square> squares, int[] indexArr, Graphics g, Camera camera, Grid grid, int startIndex, int rounds){
		int endIndex = startIndex - rounds;
		
		for(int i=startIndex; i>=endIndex; i--){
			if(i < 0){
				return;
			}
			squares.get(indexArr[i]).render(g,camera);
		}
	}
	
	public void renderGameObjectsSort0(double arr[], int[] indexArr, int j){
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
