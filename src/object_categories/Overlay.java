package object_categories;

import java.awt.Graphics;

import game.Camera;
import game.Env;

public abstract class Overlay {
	static int baseXOffset = 5;
	static int baseYOffset = 13;
	Camera camera;
	public int[] offsets;
	
	public Overlay(Camera camera){
		this.camera = camera;
		this.offsets = Overlay.newOffset();
	}
	
	public abstract void render(Graphics g);
	
	public static int[] newOffset(){
		if(baseYOffset > (Env.resHeight)){
			baseXOffset += 250;
			baseYOffset = 13;
			int[] ret = new int[]{baseXOffset, baseYOffset};
			baseYOffset += 20;
			return ret;
		}
		
		int[] ret = new int[]{baseXOffset, baseYOffset};
		baseYOffset += 20;
		return ret;
	}
}
