package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) handler.camera.setForward(true);
		if(key == KeyEvent.VK_S) handler.camera.setBackward(true);
		if(key == KeyEvent.VK_D) handler.camera.setRight(true);
		if(key == KeyEvent.VK_A) handler.camera.setLeft(true);
		if(key == KeyEvent.VK_SPACE) handler.camera.setUp(true);
		if(key == KeyEvent.VK_CONTROL) handler.camera.setDown(true);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_W) handler.camera.setForward(false);
		if(key == KeyEvent.VK_S) handler.camera.setBackward(false);
		if(key == KeyEvent.VK_D) handler.camera.setRight(false);
		if(key == KeyEvent.VK_A) handler.camera.setLeft(false);
		if(key == KeyEvent.VK_SPACE) handler.camera.setUp(false);
		if(key == KeyEvent.VK_CONTROL) handler.camera.setDown(false);
	}

}
