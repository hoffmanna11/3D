package game_objects;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import game.Camera;
import game.Env;
import game_cat.GameObject;
import overlays.Grid;
import polygons.Square;
import sub.Vector3D;

public class Cube extends GameObject {
	public int length;
	public Square[] squares;
	double rotateSpeed = Env.rand(0, 10);

	public Cube(Vector3D loc, Vector3D dir, int length){
		super(loc, dir.normalize());
		this.length = length;
		initSquares();
	}
	
	public void tick() {
		this.orient.rotate("XY", rotateSpeed);
		recalcSquares();
	}
	
	public void specialRender(Graphics g, Camera camera, Grid grid){
		double[] distances = new double[6];
		for(int i=0; i<6; i++){
			distances[i] = camera.loc.distanceBetween(squares[i].loc());
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
	
	/*
	 * Adjusts their location and orientation
	 */
	public void recalcSquares(){
		for(int i=0; i<6; i++){
			squares[i].setOrient(this.orient);
			squares[i].setLoc(this.loc, this.orient);
			squares[i].setPoints();
		}
	}

	public void initSquares(){
		squares = new Square[6];
		
		for(int i=0; i<6; i++){
			squares[i] = new Square(this.loc, this.orient, length, i);
		}
		
		Random rand = new Random();
		
		for(int i=0; i<6; i++){
			squares[i].color = new Color(rand.nextInt(0xFFFFFF));
		}
		
		/*
		squares[0].color = Color.red;
		squares[1].color = Color.blue;
		squares[2].color = Color.pink;
		squares[3].color = Color.orange;
		squares[4].color = Color.green;
		squares[5].color = Color.yellow;
		*/
	}

}
