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
	ArrayList<Integer> randomOrder = new ArrayList<Integer>();
	int count = 0;
	int index = 0;

	public Cube(Vector3D loc, Vector3D dir, int length){
		super(loc, dir.normalize());
		this.length = length;
		initSquares();
		
		for(int i=0; i<6; i++){
			randomOrder.add(i);
		}
	}

	public void mixEmUp(ArrayList<Integer> randomOrder){
		Random r = new Random();
		for(int i=0; i<20; i++){
			int index1 = (int)(r.nextFloat() * 6);
			int index2 = (int)(r.nextFloat() * 6);
			
			int temp = randomOrder.get(index1); 
			randomOrder.set(index1, randomOrder.get(index2));
			randomOrder.set(index2, temp);
		}
	}
	
	public void tick() {
		
	}
	
	/*
	if(count >= (60)){
		mixEmUp(randomOrder);
		count = 0;
	}else{
		count++;
	}
	*/
	
	/*
	if(count > 10){
		index = (index + 1) % 6;
		count = 0;
	}else{
		count++;
	}
	*/
	
	//squares[index].render(g, camera, grid);
	public void render(Graphics g, Camera camera, Grid grid){
		for(int i=0; i<6; i++){
			//squares[randomOrder.get(i)].render(g, camera, grid);
			squares[i].render(g, camera, grid);
		}
	}

	public void renderOld(Graphics g, Camera camera, Grid grid) {
		double dist[] = new double[6];
		for(int i=0; i<6; i++){
			for(int j=0; j<4; j++){
				dist[i] += squares[i].points[j].distanceBetween(camera.loc);
			}
		}

		int[] indices = new int[6];
		for(int i=0; i<6; i++){
			indices[i] = i;
		}

		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				if(dist[j] < dist[j+1]){
					double temp = dist[j];
					dist[j] = dist[j+1];
					dist[j+1] = temp;

					int tempIndex = indices[j];
					indices[j] = indices[j+1];
					indices[j+1] = tempIndex;
				}
			}
		}

		// Set square locations
		setSquareRenderLocs(camera.loc);

		Vector3D camLoc = camera.loc;
		// Get distance between points
		double distance = (int)Math.sqrt(Math.pow(loc.dx-camLoc.dx,2) + Math.pow(loc.dy-camLoc.dy,2) + Math.pow(loc.dz-camLoc.dz, 2));
		// Get scaling factor
		double scaling = (200 / distance);
		int halfLen = length/2;
		int len = (int)(((double)halfLen) * scaling);


		for(int i=0; i<6; i++){
			squares[indices[i]].render(g, camera, grid);
			/* old render method
			 * squares[indices[i]].render(g, camera, len);
			 */
		}

		// Print location
		if(length == 51){
			g.setColor(Color.white);
			g.drawString("cube loc: " + "(" + loc.dx + "," + loc.dy + "," + loc.dz + ")", 10, 20);
		}
	}

	public void initSquares(){
		squares = new Square[6];

		// Square 0
		squares[0] = new Square(loc, orient, 50, 0);

		// Square 1
		Vector3D orientXY1 = (Vector3D)orient.yz.multiply(-1);
		Vector3D orientYZ1 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientXZ1 = (Vector3D)orient.xz.multiply(1);
		squares[1] = new Square(loc, new Orient3D(orientXY1, orientYZ1, orientXZ1), 50, 1);

		// Square 2
		Vector3D orientXY2 = (Vector3D)orient.xy.multiply(-1);
		Vector3D orientYZ2 = (Vector3D)orient.yz.multiply(-1);
		Vector3D orientXZ2 = (Vector3D)orient.xz.multiply(1);
		squares[2] = new Square(loc, new Orient3D(orientXY2, orientYZ2, orientXZ2), 50, 2);

		// Square 3
		Vector3D orientXY3 = (Vector3D)orient.yz.multiply(1);
		Vector3D orientYZ3 = (Vector3D)orient.xy.multiply(-1);
		Vector3D orientXZ3 = (Vector3D)orient.xz.multiply(1);
		squares[3] = new Square(loc, new Orient3D(orientXY3, orientYZ3, orientXZ3), 50, 3);

		// Square 4: Top
		Vector3D orientXY4 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientYZ4 = (Vector3D)orient.xz.multiply(-1);
		Vector3D orientXZ4 = (Vector3D)orient.yz.multiply(1);
		squares[4] = new Square(loc, new Orient3D(orientXY4, orientYZ4, orientXZ4), 50, 4);

		// Square 5: Bottom
		Vector3D orientXY5 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientYZ5 = (Vector3D)orient.xz.multiply(1);
		Vector3D orientXZ5 = (Vector3D)orient.yz.multiply(-1);
		squares[5] = new Square(loc, new Orient3D(orientXY5, orientYZ5, orientXZ5), 50, 5);
		
		/*
		squares[0].loc = (Vector3D)loc.add(squares[0].orient.yz.multiply(-length/2));
		squares[1].loc = (Vector3D)loc.add(squares[1].orient.xy.multiply(-length/2));
		squares[2].loc = (Vector3D)loc.add(squares[2].orient.yz.multiply(length/2));
		squares[3].loc = (Vector3D)loc.add(squares[3].orient.xy.multiply(length/2));
		squares[4].loc = (Vector3D)loc.add(squares[4].orient.xz.multiply(length/2));
		squares[5].loc = (Vector3D)loc.add(squares[5].orient.xz.multiply(-length/2));
		*/
		
		/*
		System.out.print("Cube orient: "); orient.print();
		System.out.print("Cube loc: ");
		loc.print();
		
		for(int i=0; i<6; i++){
			System.out.print("Square[" + i + "] loc: ");
			squares[i].loc.print();
		}
		System.exit(0);
		*/

		squares[0].color = Color.red;
		squares[1].color = Color.blue;
		squares[2].color = Color.pink;
		squares[3].color = Color.orange;
		squares[4].color = Color.green;
		squares[5].color = Color.yellow;
	}

	public void setSquareRenderLocs(Vector3D camLoc){
		// Get distance between points
		double distance = (int)Math.sqrt(Math.pow(loc.dx-camLoc.dx,2) + Math.pow(loc.dy-camLoc.dy,2) + Math.pow(loc.dz-camLoc.dz, 2));
		// Get scaling factor
		double scaling = (200 / distance);

		int halfLen = length/2;
		int len = (int)(((double)halfLen) * scaling);

		squares[0].loc = (Vector3D)loc.add(orient.yz.multiply(-len));
		squares[1].loc = (Vector3D)loc.add(orient.xy.multiply(-len));
		squares[2].loc = (Vector3D)loc.add(orient.yz.multiply(len));
		squares[3].loc = (Vector3D)loc.add(orient.xy.multiply(len));
		squares[4].loc = (Vector3D)loc.add(orient.xz.multiply(len));
		squares[5].loc = (Vector3D)loc.add(orient.xz.multiply(-len));
	}

}
