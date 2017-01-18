package game_objects;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import game.Camera;
import game.Env;
import game_cat.GameObject;
import polygons.Square;
//import sub.ID;
import sub.Point3D;
import sub.Vector3D;

public class Cube extends GameObject {
	int length;
	Square[] squares;

	public Cube(Point3D loc, int length) {
		super(loc, null);
		this.loc = loc;
		this.length = length;
		squares = new Square[6];
		setPolygons();
	}

	public void tick() {

	}

	public void render(Graphics g, Camera camera) {
		double dist[] = new double[6];
		for(int i=0; i<6; i++){
			for(int j=0; j<4; j++){
				dist[i] += squares[i].points[j].distanceBetween(camera.getCamLoc());
			}
		}
		
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				if(dist[j] < dist[j+1]){
					double temp = dist[j];
					dist[j] = dist[j+1];
					dist[j+1] = temp;
					
					Square tempSq = squares[j];
					squares[j] = squares[j+1];
					squares[j+1] = tempSq;
				}
			}
		}
		
		for(int i=5; i>=0; i--){
			squares[i].render(g, camera);
		}
	}

	public void setPolygons(){
		// squares squares
		int halfLen = length/2;
		squares[0] = new Square(
				new Point3D(loc.x - halfLen, loc.y - halfLen, (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), loc.y - halfLen, (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), loc.y - halfLen, loc.z - halfLen),
				new Point3D(loc.x - halfLen, loc.y - halfLen, loc.z - halfLen));
		squares[1] = new Square(
				new Point3D(loc.x - halfLen, loc.y - halfLen, (loc.z + halfLen)),
				new Point3D(loc.x - halfLen, (loc.y + halfLen), (loc.z + halfLen)),
				new Point3D(loc.x - halfLen, (loc.y + halfLen), (loc.z - halfLen)),
				new Point3D(loc.x - halfLen, (loc.y - halfLen), (loc.z - halfLen)));
		squares[2] = new Square( 
				new Point3D(loc.x - halfLen, (loc.y + halfLen), (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), (loc.y + halfLen), (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), (loc.y + halfLen), (loc.z - halfLen)),
				new Point3D(loc.x - halfLen, (loc.y + halfLen), loc.z - halfLen));
		squares[3] = new Square( 
				new Point3D((loc.x + halfLen), (loc.y + halfLen), (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), loc.y - halfLen, (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), loc.y - halfLen, loc.z - halfLen),
				new Point3D((loc.x + halfLen), (loc.y + halfLen), loc.z - halfLen));
		squares[4] = new Square( 
				new Point3D(loc.x - halfLen, (loc.y + halfLen), (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), (loc.y + halfLen), (loc.z + halfLen)),
				new Point3D((loc.x + halfLen), loc.y - halfLen, (loc.z + halfLen)),
				new Point3D(loc.x - halfLen, loc.y - halfLen, loc.z + halfLen));
		squares[5] = new Square( 
				new Point3D(loc.x - halfLen, (loc.y + halfLen), loc.z - halfLen),
				new Point3D((loc.x + halfLen), (loc.y + halfLen), loc.z - halfLen),
				new Point3D((loc.x + halfLen), loc.y - halfLen, loc.z - halfLen),
				new Point3D(loc.x - halfLen, loc.y - halfLen, loc.z - halfLen));

		squares[0].color = Color.red;
		squares[1].color = Color.blue;
		squares[2].color = Color.pink;
		squares[3].color = Color.orange;
		squares[4].color = Color.green;
		squares[5].color = Color.yellow;
		
		
	}

	public void setPolygons(Square[] polygons, Camera camera){
		// Shrink the cube (polygons), then project onto the camera plane
		// double newLength = getNewLength(viewerLoc);
	}

	public int getNewLength(Point3D viewerLoc){
		double distance = Math.sqrt( 
				Math.pow((loc.x-viewerLoc.x), 2) + 
				Math.pow((loc.y-viewerLoc.y), 2) + 
				Math.pow((loc.z-viewerLoc.z), 2) );
		double slope = ((double)length / (double)Env.maxDistance);
		int newLength = (2 * length) - (int)(slope * distance);
		return newLength;
	}

	/** @category getter */
	public int getLength() {
		return length;
	}

	/** @category setter */
	public void setLength(int length) {
		this.length = length;
	}

	public Square[] getSquares() {
		return squares;
	}

	public void setSquares(Square[] squares) {
		this.squares = squares;
	}

}
