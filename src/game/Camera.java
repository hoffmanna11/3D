package game;

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
	static Vector3D XY = new Vector3D(1,0,0);
	static Vector3D XZ = new Vector3D(0,0,1);
	static Vector3D YZ = new Vector3D(0,1,0);

	public Camera(Point3D loc, Vector3D orient){
		this.camLoc = loc;
		this.camOrient = orient;
	}
	
	public static void main(String args[]){
		Camera.rotate("", 45);
		
		//double theta = 45;
		//double radians = Math.toRadians(theta);
		//System.out.println(Math.toDegrees(Math.atan(1)));
		
		/*
		Camera cam = new Camera(null, null);
		System.out.println("Testing XY rotation. Initial vectors:");
		XY.print();
		XZ.print();
		YZ.print();
		System.out.println("After XY rotation:");
		cam.rotate("XY", 10);
		XY.print();
		XZ.print();
		YZ.print();
		*/
	}
	
	public static void rotate(String plane, double theta){
		double radians = Math.toRadians(theta);
		double s1 = 1/Math.sqrt(1 + Math.pow(Math.tan(radians), 2));
		double s2 = s1 * Math.tan(radians);
		System.out.println("s1: " + s1 + ", s2: " + s2);
		switch(plane){
		case "XY":
			Vector3D newXY = (Vector3D)XY.multiply(s1).add(YZ.multiply(s2));
			Vector3D newYZ = (Vector3D)YZ.multiply(s1).add(new Vector3D(-XY.dx, XY.dy, XY.dz).multiply(s2));
			XY = newXY;
			YZ = newYZ;
			break;
		case "XZ":
			Vector3D newXZ_ = (Vector3D)XZ.multiply(s1).add(new Vector3D(-XY.dx, XY.dy, XY.dz).multiply(s2));
			Vector3D newXY_ = (Vector3D)XY.multiply(s1).add(XZ.multiply(s2));
			XZ = newXZ_;
			XY = newXY_;
			
			//XZ.rotateXZ(theta);
			//XY.rotateXZ(theta);
			break;
		case "YZ":
			Vector3D _newYZ = (Vector3D)YZ.multiply(s1).add(XZ.multiply(s2));
			Vector3D _newXZ = (Vector3D)YZ.multiply(s1).add(new Vector3D(-XY.dx, XY.dy, XY.dz).multiply(s2));
			YZ = _newYZ;
			XZ = _newXZ;
			
			//YZ.rotateYZ(theta);
			//XZ.rotateYZ(theta);
			break;
		default:
			System.out.println("Error, Camera.rotate, exiting");
			System.exit(-1);
		}
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
		
		/*
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
		*/
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
