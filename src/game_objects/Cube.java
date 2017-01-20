package game_objects;
import java.awt.Color;
import java.awt.Graphics;

import game.Camera;
import game.Env;
import game_cat.GameObject;
import polygons.Square;
import sub.Orient3D;
//import sub.ID;
import sub.Point3D;
import sub.Vector3D;

public class Cube extends GameObject {
	int length;
	Square[] squares;
	Orient3D orient;

	public Cube(Point3D loc, int length) {
		super(loc, null);
		this.loc = loc;
		this.length = length;
		squares = new Square[6];
		setPolygons();
	}

	public Cube(Point3D loc, Vector3D dir, int length){
		super(loc, dir.normalize());
		dir = dir.normalize();
		this.length = length;
		squares = new Square[6];
		setOrient(dir);
		setSquares();
	}

	public void setSquares(){
		int len = length / 2;

		// Square 0
		Point3D loc0 = loc.add((Vector3D)orient.yz.multiply(-1 * len));
		squares[0] = new Square(loc0, orient, length);

		// Square 1
		Vector3D orientXY1 = (Vector3D)orient.yz.multiply(-1);
		Vector3D orientYZ1 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientXZ1 = (Vector3D)orient.xz.multiply(1);
		Point3D loc1 = loc.add((Vector3D)orient.xy.multiply(-len));
		squares[1] = new Square(loc1, new Orient3D(orientXY1, orientYZ1, orientXZ1), length);

		// Square 2
		Vector3D orientXY2 = (Vector3D)orient.xy.multiply(-1);
		Vector3D orientYZ2 = (Vector3D)orient.yz.multiply(-1);
		Vector3D orientXZ2 = (Vector3D)orient.xz.multiply(1);
		Point3D loc2 = loc.add((Vector3D)orient.yz.multiply(len));
		squares[2] = new Square(loc2, new Orient3D(orientXY2, orientYZ2, orientXZ2), length);

		// Square 3
		Vector3D orientXY3 = (Vector3D)orient.yz.multiply(1);
		Vector3D orientYZ3 = (Vector3D)orient.xy.multiply(-1);
		Vector3D orientXZ3 = (Vector3D)orient.xz.multiply(1);
		Point3D loc3 = loc.add((Vector3D)orient.xy.multiply(len));
		squares[3] = new Square(loc3, new Orient3D(orientXY3, orientYZ3, orientXZ3), length);

		// Square 4: Top
		Vector3D orientXY4 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientYZ4 = (Vector3D)orient.xz.multiply(-1);
		Vector3D orientXZ4 = (Vector3D)orient.yz.multiply(1);
		Point3D loc4 = loc.add((Vector3D)orient.yz.multiply(len));
		squares[4] = new Square(loc4, new Orient3D(orientXY4, orientYZ4, orientXZ4), length);

		// Square 5: Bottom
		Vector3D orientXY5 = (Vector3D)orient.xy.multiply(1);
		Vector3D orientYZ5 = (Vector3D)orient.xz.multiply(1);
		Vector3D orientXZ5 = (Vector3D)orient.yz.multiply(-1);
		Point3D loc5 = loc.add((Vector3D)orient.yz.multiply(-len));
		squares[5] = new Square(loc5, new Orient3D(orientXY5, orientYZ5, orientXZ5), length);
		
		System.out.println("Cube base location: " + loc.toString() + "\n");
		
		squares[0].color = Color.red;
		squares[1].color = Color.blue;
		squares[2].color = Color.pink;
		squares[3].color = Color.orange;
		squares[4].color = Color.green;
		squares[5].color = Color.yellow;
	}

	// Takes a 3D directional vector
	// Outputs 3 basis vectors for cube
	public void setOrient(Vector3D dir){
		Vector3D myXZ = Vector3D.XZ.projectOntoPlane(Point3D.origin, dir).normalize();
		Vector3D myXY = Vector3D.crossProduct(dir, myXZ).normalize();
		this.orient = new Orient3D(myXY, myXZ, dir.normalize());
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

		// Print location
		g.setColor(Color.green);
		g.drawString("Cube: " + "(" + loc.x + "," + loc.y + "," + loc.z + ")", 10, 20);
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
