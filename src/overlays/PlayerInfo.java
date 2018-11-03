package overlays;

import java.awt.Graphics;

import game.Env;
import object_categories.Overlay;

public class PlayerInfo extends Overlay {

	@Override
	public void render(Graphics g) {
		g.drawString("FPS: " + Env.currentFPS, baseXOffset, baseYOffset);
	}

}
