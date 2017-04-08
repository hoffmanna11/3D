package overlays;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import game.Camera;
import game_cat.Overlay;
import sub.Orient3D;
import sub.Vector3D;

public class CameraOrientation extends Overlay {
	private Orient3D cameraOrient;
	private Camera camera;
	
	public CameraOrientation(Orient3D cameraOrient, Camera camera){
		this.cameraOrient = cameraOrient;
		this.camera = camera;
	}
	
	public void render(Graphics g) {
		//drawVectors(g);
		drawStrings(g);
	}
	
	public void drawVectors(Graphics g){
		Vector3D XYMult = new Vector3D(this.cameraOrient.xy.dx * 50, this.cameraOrient.xy.dy * 50, this.cameraOrient.xy.dz * 50);
		Vector3D YZMult = new Vector3D(this.cameraOrient.yz.dx * 50, this.cameraOrient.yz.dy * 50, this.cameraOrient.yz.dz * 50);
		Vector3D XZMult = new Vector3D(this.cameraOrient.xz.dx * 50, this.cameraOrient.xz.dy * 50, this.cameraOrient.xz.dz * 50);

		Vector3D proj_XY = XYMult.projectOntoPlane(new Vector3D(0,0,0), new Vector3D(-20,50,-20));
		Vector3D proj_YZ = YZMult.projectOntoPlane(new Vector3D(0,0,0), new Vector3D(-20,50,-20));
		Vector3D proj_XZ = XZMult.projectOntoPlane(new Vector3D(0,0,0), new Vector3D(-20,50,-20));

		int[] projXY = proj_XY.getBaseCoordsDeprecated(proj_XY);
		int[] projYZ = proj_YZ.getBaseCoordsDeprecated(proj_YZ);
		int[] projXZ = proj_XZ.getBaseCoordsDeprecated(proj_XZ);

		//drawVector(g, projXY, Color.RED);
		//drawVector(g, projYZ, Color.BLUE);
		//drawVector(g, projXZ, Color.GREEN);
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
		g.drawLine(200, 200, 200 + coords[0], 200 - coords[1]);
	}
	
}
