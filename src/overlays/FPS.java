package overlays;

import java.awt.Graphics;

import game.Env;
import game_cat.Overlay;

public class FPS extends Overlay {
	int xOffset, yOffset;
	
	public FPS(){
		int[] offsets = Overlay.newOffset();
		this.xOffset = offsets[0];
		this.yOffset = offsets[1];
	}

	public void render(Graphics g) {
		g.drawString("FPS: " + Env.currentFPS, xOffset, yOffset);
	}

}
