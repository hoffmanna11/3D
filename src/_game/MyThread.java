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
	
	public MyThread(Handler handler, int id){
		super();
		if(debug){
			System.out.println("Creating thread: " + id);
		}
		this.handler = handler;
		this.id = id;
	}
	
	public void tick(LinkedList<GameObject> object, int i){
		if(debug){
			System.out.println("tick: " + id);
		}
		GameObject tempObject = object.get(i);
		tempObject.tick();
	}
	
	public void renderUnderlays(Graphics g, ArrayList<Underlay> underlays, int i){
		if(debug){
			System.out.println("renderUnderlays: " + id);
		}
		Underlay tempUnderlay = underlays.get(i);
		tempUnderlay.render(g);
	}
	
	public void renderOverlays(Graphics g, ArrayList<Overlay> overlays, int i){
		if(debug){
			System.out.println("renderOverlays: " + id);
		}
		Overlay tempOverlay = overlays.get(i);
		tempOverlay.render(g);
	}
	
	public void renderGameObjects1(LinkedList<GameObject> object, int i, ArrayList<Cube> cubes){
		if(debug){
			System.out.println("renderGameObjects1: " + id);
		}
		GameObject tempObject = object.get(i);
		if(tempObject.getClass().equals(Cube.class)){
			cubes.add((Cube)tempObject);
		}
	}
	
	public void renderGameObjects2(ArrayList<Square> squares, ArrayList<Cube> cubes, int i, int j){
		if(debug){
			System.out.println("renderGameObjects2: " + id);
		}
		squares.add(cubes.get(i).squares[j]);
	}
	
	public void renderGameObjects3(double[] distances, ArrayList<Square> squares, Camera camera, int i){
		if(debug){
			System.out.println("renderGameObjects3: " + id);
		}
		distances[i] = squares.get(i).loc().distanceBetween(camera.loc);
	}
	
	public void renderGameObjects4(int indexArr[], int i){
		if(debug){
			System.out.println("renderGameObjects4: " + id);
		}
		indexArr[i] = i;
	}
	
	public void renderGameObjects5(ArrayList<Square> squares, int[] indexArr, Graphics g, Camera camera, Grid grid, int startIndex, int rounds){
		if(debug){
			System.out.println("renderGameObjects5: " + id);
		}
		
		int endIndex = startIndex - rounds;
		
		for(int i=startIndex; i>=endIndex; i--){
			if(i < 0){
				return;
			}
			squares.get(indexArr[i]).render(g,camera);
		}
	}
	
	public void renderGameObjectsSort(double arr[], int[] indexArr, int j){
		if(debug){
			//System.out.println("renderGameObjectsSort: " + id);
		}
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
