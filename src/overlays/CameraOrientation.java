package overlays;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import game.Camera;
import game_cat.Overlay;
import sub.Matrix;
import sub.Orient3D;
import sub.Vector3D;

public class CameraOrientation extends Overlay {
	private Camera camera;
	
	public CameraOrientation(Camera camera){
		this.camera = camera;
	}
	
	public void render(Graphics g) {
		drawVectors(g);
		drawStrings(g);
	}
	
	public void drawVectors(Graphics g){
		Vector3D projXY = ((Vector3D)(camera.orient.xy.multiply(50))).projectOntoPlane(Vector3D.origin, new Vector3D(-20,50,-20));
		Vector3D projYZ = ((Vector3D)(camera.orient.yz.multiply(50))).projectOntoPlane(Vector3D.origin, new Vector3D(-20,50,-20));
		Vector3D projXZ = ((Vector3D)(camera.orient.xz.multiply(50))).projectOntoPlane(Vector3D.origin, new Vector3D(-20,50,-20));
		
		int[] coordsXY = projXY.getBaseCoordsDeprecated();
		int[] coordsYZ = projYZ.getBaseCoordsDeprecated();
		int[] coordsXZ = projXZ.getBaseCoordsDeprecated();
		
		drawVector(g, coordsXY, Color.BLUE);
		drawVector(g, coordsYZ, Color.gray);
		drawVector(g, coordsXZ, Color.RED);
	}
	
	/*
	 * Draws:
	 * 	text for current orient/loc
	 */
	public void drawStrings(Graphics g){
		DecimalFormat df = new DecimalFormat("#.##");
		g.setColor(Color.white);
		g.drawString("camera loc: " + "(" + camera.loc.dx + "," + camera.loc.dy + "," + camera.loc.dz + ")", 10, 40);
		g.drawString("camera xy: " + "(" + df.format(camera.orient.xy.dx) + "," + df.format(camera.orient.xy.dy) + "," + df.format(camera.orient.xy.dz) + ")", 10, 60);
		g.drawString("camera yz: " + "(" + df.format(camera.orient.yz.dx) + "," + df.format(camera.orient.yz.dy) + "," + df.format(camera.orient.yz.dz) + ")", 10, 80);
		g.drawString("camera xz: " + "(" + df.format(camera.orient.xz.dx) + "," + df.format(camera.orient.xz.dy) + "," + df.format(camera.orient.xz.dz) + ")", 10, 100);
	}

	public void drawVector(Graphics g, int[] coords, Color c){
		g.setColor(c);
		g.drawLine(200, 50, 200 + coords[0], 50 - coords[1]);
	}
	
}
