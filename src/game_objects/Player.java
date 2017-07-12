package game_objects;

import java.awt.Graphics;

import game.Camera;
import game_cat.GameObject;
import sub.Vector3D;

public class Player extends GameObject {
	private Camera camera;
	
	public Player(Vector3D loc, Vector3D yzOrient){
		super(loc, yzOrient);
		this.camera = new Camera(loc, yzOrient);
	}

	public void tick() {
		
	}

	public void render(Graphics g, Camera camera) {
		
	}
}
