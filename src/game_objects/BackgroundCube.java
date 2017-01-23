package game_objects;

import java.awt.Color;
import java.awt.Graphics;

import game.Camera;
import sub.Vector3D;

public class BackgroundCube extends Cube {

	public BackgroundCube(Vector3D loc, Vector3D dir, int length) {
		super(loc, dir, length);
		setColors();
	}
	
	public void setColors(){
		for(int i=0; i<6; i++){
			squares[i].color = Color.blue;
		}
	}
	
	public void render(Graphics g, Camera camera) {
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
			squares[indices[i]].renderOutlineOnly(g, camera, len);
		}

		// Print location
		//g.setColor(Color.green);
		//g.drawString("Cube: " + "(" + loc.dx + "," + loc.dy + "," + loc.dz + ")", 10, 20);
	}

}
