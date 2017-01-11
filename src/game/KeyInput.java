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

		switch(key){
		case KeyEvent.VK_W:
			// If also shift, then roll forward = true
			break;
		case KeyEvent.VK_A:
			break;
		case KeyEvent.VK_S:
			break;
		case KeyEvent.VK_D:
			break;
		case KeyEvent.VK_CONTROL:
			break;
		case KeyEvent.VK_SPACE:
			break;
		case KeyEvent.VK_SHIFT:
			break;
		default:
			break;
		}

		/*
		if(key == KeyEvent.VK_W){
			handler.camera.setForward(true);
		}
		if(key == KeyEvent.VK_S){ handler.camera.setBackward(true);

		}
		if(key == KeyEvent.VK_D){
			handler.camera.setRight(true);
		}
		if(key == KeyEvent.VK_A){
			handler.camera.setLeft(true);
		}
		if(key == KeyEvent.VK_SPACE){
			handler.camera.setUp(true);
		}
		if(key == KeyEvent.VK_CONTROL){
			handler.camera.setDown(true);
		}
		if(key == KeyEvent.VK_SHIFT){
			handler.camera.setShift(true);
		}
		*/
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		//System.out.println("Gone\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nGone");
		if(key == KeyEvent.VK_W) handler.camera.setForward(false);
		if(key == KeyEvent.VK_S) handler.camera.setBackward(false);
		if(key == KeyEvent.VK_D) handler.camera.setRight(false);
		if(key == KeyEvent.VK_A) handler.camera.setLeft(false);
		if(key == KeyEvent.VK_SPACE) handler.camera.setUp(false);
		if(key == KeyEvent.VK_CONTROL) handler.camera.setDown(false);
		if(key == KeyEvent.VK_SHIFT) handler.camera.setShift(false);
	}

}
