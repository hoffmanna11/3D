package game;

import sub.Point3D;
import sub.Vector3D;

public class Camera {
	Point3D camLoc;
	Vector3D camOrient;
	boolean up = false;
	boolean down = false;
	boolean forward = false;
	boolean backward = false;
	boolean left = false;
	boolean right = false;
	boolean test;
	int speed = 1;

	public Camera(Point3D loc, Vector3D orient){
		this.camLoc = loc;
		this.camOrient = orient;
	}
	
	public void tick(){
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

	/** @category getter */
	public boolean isTest() {
		return test;
	}

	/** @category setter */
	public void setTest(boolean test) {
		this.test = test;
	}
}
