package game_objects;
import java.awt.Graphics;

import game_cat.GameObject;
import polygons.Square;
import sub.ID;
import sub.Point3D;
import sub.Vector3D;

public class SquareInstance extends GameObject {
	Square square;
	
	public SquareInstance(Point3D loc, Vector3D orient, int length) {
		super(loc, orient);
		square = new Square(loc, orient, length);
	}

	public void tick() {
		
	}

	public void render(Graphics g, Point3D viewerLoc, Vector3D viewerOrient) {
		square.render(g, viewerLoc, viewerOrient);
	}
	
}
