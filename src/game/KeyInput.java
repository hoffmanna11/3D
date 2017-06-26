package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class KeyInput extends KeyAdapter implements MouseListener {
	public double mouseX = -1;
	public double mouseY = -1;
	
	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_W){
			handler.camera.forward = true;
		}
		if(key == KeyEvent.VK_S){
			handler.camera.backward = true;
		}
		if(key == KeyEvent.VK_D){
			handler.camera.right = true;
		}
		if(key == KeyEvent.VK_A){
			handler.camera.left = true;
		}
		if(key == KeyEvent.VK_SPACE){
			handler.camera.up = true;
		}
		if(key == KeyEvent.VK_CONTROL){
			handler.camera.down = true;
		}
		if(key == KeyEvent.VK_SHIFT){
			handler.camera.shift = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_W) handler.camera.forward = false;
		if(key == KeyEvent.VK_S) handler.camera.backward = false;
		if(key == KeyEvent.VK_D) handler.camera.right = false;
		if(key == KeyEvent.VK_A) handler.camera.left = false;
		if(key == KeyEvent.VK_SPACE) handler.camera.up = false;
		if(key == KeyEvent.VK_CONTROL) handler.camera.down = false;
		if(key == KeyEvent.VK_SHIFT) handler.camera.shift = false;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(SwingUtilities.isRightMouseButton(arg0)){
			handler.camera.rightClick = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(SwingUtilities.isRightMouseButton(arg0)){
			handler.camera.rightClick = false;
			mouseX = -1;
			mouseY = -1;
		}
	}

}
