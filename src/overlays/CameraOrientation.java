package overlays;

import java.awt.Color;
import java.awt.Graphics;

import game_cat.Overlay;
import sub.Orient3D;
import sub.Vector3D;

public class CameraOrientation extends Overlay {
	private Orient3D cameraOrient;
	
	public CameraOrientation(Orient3D cameraOrient){
		this.cameraOrient = cameraOrient;
	}
	
	public void render(Graphics g) {
		drawVectors(g);
	}
	
	public void drawVectors(Graphics g){
		Vector3D XYMult = new Vector3D(this.cameraOrient.xy.dx * 50, this.cameraOrient.xy.dy * 50, this.cameraOrient.xy.dz * 50);
		Vector3D YZMult = new Vector3D(this.cameraOrient.yz.dx * 50, this.cameraOrient.yz.dy * 50, this.cameraOrient.yz.dz * 50);
		Vector3D XZMult = new Vector3D(this.cameraOrient.xz.dx * 50, this.cameraOrient.xz.dy * 50, this.cameraOrient.xz.dz * 50);

		Vector3D proj_XY = XYMult.projectOntoPlane(new Vector3D(0,0,0), new Vector3D(-20,50,-20));
		Vector3D proj_YZ = YZMult.projectOntoPlane(new Vector3D(0,0,0), new Vector3D(-20,50,-20));
		Vector3D proj_XZ = XZMult.projectOntoPlane(new Vector3D(0,0,0), new Vector3D(-20,50,-20));

		//int[] projXY = proj_XY.getBaseCoords(proj_XY);
		//int[] projYZ = proj_YZ.getBaseCoords(proj_YZ);
		//int[] projXZ = proj_XZ.getBaseCoords(proj_XZ);

		//drawVector(g, projXY, Color.RED);
		//drawVector(g, projYZ, Color.BLUE);
		//drawVector(g, projXZ, Color.GREEN);
	}

	public void drawVector(Graphics g, int[] coords, Color c){
		g.setColor(c);
		g.drawLine(200, 200, 200 + coords[0], 200 - coords[1]);
	}
	
}
