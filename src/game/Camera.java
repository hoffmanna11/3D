package game;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import sub.Point3D;
import sub.Vector3D;

public class Camera {
	Point3D camLoc;
	Vector3D camOrient;
	Set<Boolean> keysPressed = new HashSet<Boolean>();
	boolean shift = false;
	boolean forward = false;
	boolean backward = false;
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;
	int speed = 1;

	public Camera(Point3D loc, Vector3D orient){
		this.camLoc = loc;
		this.camOrient = orient;
	}

	public void tick(){
		// Check DIRECTION and opposite axial DIRECTION
		//   If both, do nothing
		//   Else If one, check for shift
		//     If shift, check:
		//       If 
		//     If shift, rotate in that dual axis plane
		//     Else move in that direction
		//   Else check next direction

		// XY: Yaw   ( left / right )
		// XZ: Roll  ( shift + left / right )
		// YZ: Pitch ( shift + forward / backward )

		if(shift){
			if(forward){
				if(backward){
					// Do nothing
				}else{
					// Shift + forward
					// = Pitch down
					// = Rotate on YZ CW : Negative
				}
			}else if(backward){
				// Shift + backward
				// = Pitch up
				// = Rotate on YZ CCW : Positive
			}
			if(left){
				if(right){
					// Do nothing
				}else{
					// Shift + left
					// = Roll left
					// = Rotate on XZ CCW : Positive
				}
			}else if(right){
				// Shift + right
				// = Roll right
				// = Rotate on XZ CW : Negative
			}
		}else{
			// No shift
			if(forward){
				if(backward){
					// Do nothing
				}else{
					// Forward
					// = Move forward
					// Add to x, y, z speed * (normalized)[x, y, z]
				}
			}else if(backward){
				// Backward
				// = Move backward
				// Add to x, y, z -1 * speed * (normalized)[x, y, z]
			}

			if(left){
				if(right){
					// Do nothing
				}else{
					// Left
					// = Left
					// = Rotate on XY CCW : Positive
				}
			}else if(right){
				// Right
				// = Right
				// = Rotate on XY CW : Negative
			}
			if(up){
				if(down){
					return;
				}else{
					// Up
					// = Up
					// = Move upward
				}
			}else if(down){
				// Down
				// = Pitch down
				// = Rotate on YZ CW : Negative
			}
		}

		if(shift){
			System.out.println("Shift!!\n\n\n\n\nShift!!");
		}
		if(forward){
			//System.out.println("Forward");
			camLoc.y += speed;
			//camLoc.x += speed * camOrient.dx;
			//camLoc.y += speed * camOrient.dy;
			//camLoc.z += speed * camOrient.dz;
		}if(backward){
			//System.out.println("Backward");
			camLoc.y -= speed;
			//camLoc.x += speed * camOrient.dx;
			//camLoc.y += speed * camOrient.dy;
			//camLoc.z += speed * camOrient.dz;
		}if(up){
			//System.out.println("Upward");
			camLoc.z += speed;
		}if(down){
			//System.out.println("Downward");
			camLoc.z -= speed;
		}if(left){
			//System.out.println("Leftward");
			camLoc.x -= speed;
		}if(right){
			//System.out.println("Rightward");
			camLoc.x += speed;
		}
	}

	/** @category getter */
	public Point3D getCamLoc() {
		return camLoc;
	}

	/** @category setter */
	public void setCamLoc(Point3D camLoc) {
		this.camLoc = camLoc;
	}

	/** @category getter */
	public Vector3D getCamOrient() {
		return camOrient;
	}

	/** @category setter */
	public void setCamOrient(Vector3D camOrient) {
		this.camOrient = camOrient;
	}

	/** @category getter */
	public boolean isUp() {
		return up;
	}

	/** @category setter */
	public void setUp(boolean up) {
		this.up = up;
	}

	/** @category getter */
	public boolean isDown() {
		return down;
	}

	/** @category setter */
	public void setDown(boolean down) {
		this.down = down;
	}

	/** @category getter */
	public boolean isForward() {
		return forward;
	}

	/** @category setter */
	public void setForward(boolean forward) {
		this.forward = forward;
	}

	/** @category getter */
	public boolean isBackward() {
		return backward;
	}

	/** @category setter */
	public void setBackward(boolean backward) {
		this.backward = backward;
	}

	/** @category getter */
	public boolean isLeft() {
		return left;
	}

	/** @category setter */
	public void setLeft(boolean left) {
		this.left = left;
	}

	/** @category getter */
	public boolean isRight() {
		return right;
	}

	/** @category setter */
	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isShift() {
		return shift;
	}

	public void setShift(boolean shift) {
		this.shift = shift;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
