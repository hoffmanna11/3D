package object_categories;

import java.awt.Graphics;

import game.Env;

public abstract class Overlay {
	static int xOffset = 5;
	static int yOffset = 13;
	
	public abstract void render(Graphics g);
	
	public static int[] newOffset(){
		if(yOffset > (Env.resHeight)){
			xOffset += 250;
			yOffset = 13;
			int[] ret = new int[]{xOffset, yOffset};
			yOffset += 20;
			return ret;
		}
		
		int[] ret = new int[]{xOffset, yOffset};
		yOffset += 20;
		return ret;
	}
}
