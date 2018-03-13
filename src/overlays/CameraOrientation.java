package overlays;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;

import game.Camera;
import object_categories.Overlay;
import support_lib.Vector3D;

public class CameraOrientation extends Overlay {
	private Camera camera;
	int numValues = 4;
	ArrayList<int[]> offsets;
	
	public CameraOrientation(Camera camera){
		this.camera = camera;
		offsets = new ArrayList<int[]>(numValues);
		for(int i=0; i<numValues; i++){
			offsets.add(new int[2]);
			offsets.set(i, Overlay.newOffset());
		}
	}
	
	public void render(Graphics g) {
		drawVectors(g);
		drawStrings(g);
	}
	
	public void drawVectors(Graphics g){
		Vector3D projXY = ((Vector3D)(camera.orient.xy.multiply(50))).projectOntoPlane(Vector3D.origin, new Vector3D(-20,50,-20));
		Vector3D projYZ = ((Vector3D)(camera.orient.yz.multiply(50))).projectOntoPlane(Vector3D.origin, new Vector3D(-20,50,-20));
		Vector3D projXZ = ((Vector3D)(camera.orient.xz.multiply(50))).projectOntoPlane(Vector3D.origin, new Vector3D(-20,50,-20));
		
		drawVector(g, new int[]{(int)projXY.dx(), (int)projXY.dz()}, Color.BLUE);
		drawVector(g, new int[]{(int)projYZ.dx(), (int)projYZ.dz()}, Color.gray);
		drawVector(g, new int[]{(int)projXZ.dx(), (int)projXZ.dz()}, Color.RED);
	}
	
	/* 
	 * Draws text for current orient/loc 
	 */
	public void drawStrings(Graphics g){
		DecimalFormat df = new DecimalFormat("#.##");
		g.setColor(Color.white);
		g.drawString("camera loc: " + "(" + (int)(camera.loc.dx()) + "," + (int)(camera.loc.dy()) + "," + (int)(camera.loc.dz()) + ")", offsets.get(0)[0], offsets.get(0)[1]);
		g.drawString("camera xy: " + "(" + df.format(camera.orient.xy.dx()) + "," + df.format(camera.orient.xy.dy()) + "," + df.format(camera.orient.xy.dz()) + ")", offsets.get(1)[0], offsets.get(1)[1]);
		g.drawString("camera yz: " + "(" + df.format(camera.orient.yz.dx()) + "," + df.format(camera.orient.yz.dy()) + "," + df.format(camera.orient.yz.dz()) + ")", offsets.get(2)[0], offsets.get(2)[1]);
		g.drawString("camera xz: " + "(" + df.format(camera.orient.xz.dx()) + "," + df.format(camera.orient.xz.dy()) + "," + df.format(camera.orient.xz.dz()) + ")", offsets.get(3)[0], offsets.get(3)[1]);
	}

	public void drawVector(Graphics g, int[] coords, Color c){
		g.setColor(c);
		g.drawLine(200, 50, 200 + coords[0], 50 - coords[1]);
	}
	
}
