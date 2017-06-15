package game_objects;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import game.Camera;
import game.Handler;
import game_cat.GameObject;
import overlays.Grid;
import polygons.Square;
import sub.Orient3D;
import sub.Vector3D;

public class Cube extends GameObject {
	public int length;
	public Square[] squares;

	public Cube(Vector3D loc, Vector3D dir, int length){
		super(loc, dir.normalize());
		this.length = length;
		initSquares();
	}
	
	public void tick() {
		
	}
	
	public void specialRender(Graphics g, Camera camera, Grid grid){
		double[] distances = new double[6];
		for(int i=0; i<6; i++){
			distances[i] = camera.loc.distanceBetween(squares[i].loc);
		}
		
		int[] indexArr = {0,1,2,3,4,5};
		sort(distances, indexArr);
		for(int i=5; i>=0; i--){
			squares[i].render(g,camera,grid);
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
	
	public void render(Graphics g, Camera camera, Grid grid){
		specialRender(g, camera, grid);
		
		if(Square.time >= 90){
			// Once square has been rendered X times, switch to next index
			Square.time = 0;
			Square.indexThing = (Square.indexThing + 1) % 6;
		}else{
			// If not rendered X times yet, keep going
		}
	}

	public void initSquares(){
		Vector3D cubeLoc = loc;
		
		squares = new Square[6];

		// Square 0
		squares[0] = new Square(cubeLoc, orient, length, 0);

		// Square 1
		Vector3D orientXY1 = (Vector3D)orient.yz.multiply(-1);
		Vector3D orientYZ1 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientXZ1 = (Vector3D)orient.xz.multiply(1);
		squares[1] = new Square(cubeLoc, new Orient3D(orientXY1, orientYZ1, orientXZ1), length, 1);

		// Square 2
		Vector3D orientXY2 = (Vector3D)orient.xy.multiply(-1);
		Vector3D orientYZ2 = (Vector3D)orient.yz.multiply(-1);
		Vector3D orientXZ2 = (Vector3D)orient.xz.multiply(1);
		squares[2] = new Square(cubeLoc, new Orient3D(orientXY2, orientYZ2, orientXZ2), length, 2);

		// Square 3
		Vector3D orientXY3 = (Vector3D)orient.yz.multiply(1);
		Vector3D orientYZ3 = (Vector3D)orient.xy.multiply(-1);
		Vector3D orientXZ3 = (Vector3D)orient.xz.multiply(1);
		squares[3] = new Square(cubeLoc, new Orient3D(orientXY3, orientYZ3, orientXZ3), length, 3);

		// Square 4: Top
		Vector3D orientXY4 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientYZ4 = (Vector3D)orient.xz.multiply(-1);
		Vector3D orientXZ4 = (Vector3D)orient.yz.multiply(1);
		squares[4] = new Square(cubeLoc, new Orient3D(orientXY4, orientYZ4, orientXZ4), length, 4);

		// Square 5: Bottom
		Vector3D orientXY5 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientYZ5 = (Vector3D)orient.xz.multiply(1);
		Vector3D orientXZ5 = (Vector3D)orient.yz.multiply(-1);
		squares[5] = new Square(cubeLoc, new Orient3D(orientXY5, orientYZ5, orientXZ5), length, 5);

		squares[0].color = Color.red;
		squares[1].color = Color.blue;
		squares[2].color = Color.pink;
		squares[3].color = Color.orange;
		squares[4].color = Color.green;
		squares[5].color = Color.yellow;
	}

}
