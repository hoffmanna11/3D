package SFX;

import java.awt.Color;
import java.util.Random;

import support_lib.Vector3D;
import units.Cube;

public class TrailBlaze {
	Cube cube;
	int trailBlazeLength = 100;
	Vector3D[] trailBlaze = new Vector3D[trailBlazeLength];
	int trailBlazeIndexDestroy = 0;
	int trailBlazeIndexDraw = 0;
	Random rand = new Random();
	Color trailBlazeColor = new Color(rand.nextInt(0xFFFFFF));
	boolean destroyTrailBlaze = false;
	boolean ready = false;
	
	public TrailBlaze(Cube cube){
		this.cube = cube;
	}
}
