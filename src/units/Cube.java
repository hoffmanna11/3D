package units;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import _game.Camera;
import _game.Env;
import object_categories.GameObject;
import polygons.Square;
import rendering.Render;
import support_lib.Vector3D;
import underlays.Grid;

public class Cube extends GameObject {
	public static Camera camera;
	public Square[] squares;
	
	public int length;
	double rotateSpeed = 50; // Env.rand(0, 10);
	boolean rotate = false;
	double speed = 0;
	// TODO: Probably should be using a global Random object
	Random rand = new Random();
	boolean ready = false;

	public Cube(Vector3D loc, Vector3D dir, int length, Camera cam){
		super(loc, dir.normalize());
		camera = cam;
		this.length = length;
		initSquares();
		double rand = Math.random();
		if(rand > .75){
			rotate = true;
		}

		if(length != 1000000){
			speed = Math.random() * 50;
		}else{
			rotate = false;
			speed = 0;
		}
	}

	public void tick() {
		if(rotate){
			this.orient.rotate("XY", rotateSpeed);
			this.loc.add$(this.orient.yz.multiply(speed));
			recalcSquares();
		}else{
			this.loc.add$(this.orient.yz.multiply(speed));
			recalcSquares();
		}
	}

	public void specialRender(Graphics g, Camera camera){
		double[] distances = new double[6];
		for(int i=0; i<6; i++){
			distances[i] = camera.loc.distanceBetween(squares[i].loc());
		}

		int[] indexArr = {0,1,2,3,4,5};
		sort(distances, indexArr);
		for(int i=5; i>=0; i--){
			squares[i].render(g,camera);
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

	public void render(Graphics g, Camera camera){
		specialRender(g, camera);

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
