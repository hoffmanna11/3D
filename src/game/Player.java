package game;

import java.awt.Graphics;

import game_cat.GameObject;
import sub.Orient3D;
import sub.Vector3D;
import underlays.Grid;

public class Player extends GameObject {
	private Camera camera;
	
	public Player(Vector3D loc, Vector3D yzOrient){
		super(loc, yzOrient);
		this.camera = new Camera(loc, yzOrient);
	}

	public void tick() {
		
	}

	public void render(Graphics g, Camera camera, Grid grid) {
		
	}
}
