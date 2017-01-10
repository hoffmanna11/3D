package game_objects;
import java.awt.Graphics;

import game.Env;
import game_cat.GameObject;
import polygons.Square;
import sub.ID;
import sub.Point3D;
import sub.Vector3D;

public class Cube extends GameObject {
	int length;
	Square[] polygons;

	public Cube(Point3D loc, Vector3D orient, int length) {
		super(loc, orient);
		this.loc = loc;
		this.orient = orient;
		this.length = length;
		polygons = new Square[6];
	}

	public void tick() {
		
	}

	public void render(Graphics g, Point3D viewerLoc, Vector3D viewerOrient) {
		Square[] polygons = new Square[6];
		setPolygons(polygons, viewerLoc, viewerOrient);
		for(int i=0; i<6; i++){
			polygons[i].render(g, viewerLoc, viewerOrient);
		}
	}

	public void setPolygons(Square[] polygons, Point3D viewerLoc, Vector3D viewerOrient){
		// Shrink the cube (polygons), then project onto the camera plane
		double newLength = getNewLength(viewerLoc);
		
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

	/** @category getter */
	public Square[] getPolygons() {
		return polygons;
	}

	/** @category setter */
	public void setPolygons(Square[] polygons) {
		this.polygons = polygons;
	}
	
}
