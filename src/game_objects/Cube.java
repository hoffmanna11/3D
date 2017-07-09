package game_objects;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import game.Camera;
import game.Env;
import game_cat.GameObject;
import polygons.Square;
import rendering.Render;
import sub.Vector3D;
import underlays.Grid;

public class Cube extends GameObject {
	public int length;
	public Square[] squares;
	double rotateSpeed = 50; // Env.rand(0, 10);
	boolean rotate = false;
	double speed = 0;
	int trailBlazeLength = 100;
	Vector3D[] trailBlaze = new Vector3D[trailBlazeLength];
	int trailBlazeIndexDestroy = 0;
	int trailBlazeIndexDraw = 0;
	public static Camera camera;
	Random rand = new Random();
	Color trailBlazeColor = new Color(rand.nextInt(0xFFFFFF));
	boolean destroyTrailBlaze = false;
	int frameCount = 0;
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
		speed = Math.random() * 50;
		for(int i=0; i<trailBlaze.length; i++){
			trailBlaze[i] = new Vector3D(0,0,0);
		}
	}

	public void tick() {
		frameCount = (frameCount + 1) % 11;
		if(rotate){
			this.orient.rotate("XY", rotateSpeed);
			this.loc.add$(this.orient.yz.multiply(speed));
			recalcSquares();
		}else{
			this.loc.add$(this.orient.yz.multiply(speed));
			recalcSquares();
		}
	}

	public void trailblaze(){
		if(true){
			//return;
		}
		trailBlaze[trailBlazeIndexDraw] = (Vector3D) this.loc.add(this.orient.yz.multiply(-1 * (double)this.length / 1.7));
		trailBlazeIndexDraw = (trailBlazeIndexDraw + 1) % trailBlaze.length;

		if(ready == false){
			if(trailBlazeIndexDraw > trailBlaze.length / 2){
				ready = true;
			}

		}

		if(ready == false){
			for(int i=0; i<trailBlazeIndexDraw; i++){
				int[] loc = Render.getRender(Env.g, camera, trailBlaze[i]);
				if(null != loc){
					Env.g.setColor(trailBlazeColor);
					Env.g.drawOval(loc[0], loc[1], 5, 5);
				}
			}
			//trailBlazeIndexDraw = (trailBlazeIndexDraw + 1) % trailBlaze.length;
			return;
		}else{
			if(trailBlazeIndexDraw >= (trailBlaze.length / 2)){
				for(int i=trailBlazeIndexDraw; i<trailBlaze.length - 1; i++){
					int[] loc = Render.getRender(Env.g, camera, trailBlaze[i]);
					if(null != loc){
						Env.g.setColor(trailBlazeColor);
						Env.g.drawOval(loc[0], loc[1], 2, 2);
					}
				}
				for(int i=0; i<(trailBlaze.length / 2) - (trailBlaze.length - trailBlazeIndexDraw); i++){
					int[] loc = Render.getRender(Env.g, camera, trailBlaze[i]);
					if(null != loc){
						Env.g.setColor(trailBlazeColor);
						Env.g.drawOval(loc[0], loc[1], 2, 2);
					}
				}
			}else{
				for(int i=trailBlazeIndexDraw; i<trailBlazeIndexDraw + trailBlaze.length / 2; i++){
					int[] loc = Render.getRender(Env.g, camera, trailBlaze[i]);

					if(null != loc){
						Env.g.setColor(trailBlazeColor);
						Env.g.drawOval(loc[0], loc[1], 2, 2);
					}
				}
			}
		}
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
