package overlays;

import java.awt.Graphics;

import object_categories.Overlay;

public class Controls extends Overlay {
	public Controls() {
		addOffset(); // Movement
		addOffset(); // Speed
		addOffset(); // Rotation
	}
	
	@Override
	public void render(Graphics g) {
		setMessage(0, "E: Forward, D: Backward, S: Left, F: Right");
		setMessage(1, "Hold left click to speed up, middle click to slow down");
		setMessage(2, "A, G to rotate");
		renderStrings(g);
	}
	
}
