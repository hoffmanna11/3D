package game;

import java.util.HashSet;
import java.util.Set;

import sub.Matrix;
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
	int speed = 2;
	Vector3D XY = new Vector3D(1,0,0);
	Vector3D YZ = new Vector3D(0,1,0);
	Vector3D XZ = new Vector3D(0,0,1);

	public Camera(Point3D loc){
		this.camLoc = loc;
		rotate("XY", 10);
		//this.camOrient = orient;
	}

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
	public void tick(){
		if(shift){
			if(forward){
				if(backward){
					// Do nothing
				}else{
					// Shift + forward
					// = Pitch down
					// = Rotate on YZ CW : Negative
					System.out.println("Pitch down");
					rotate("YZ", false);
				}
			}else if(backward){
				// Shift + backward
				// = Pitch up
				// = Rotate on YZ CCW : Positive
				rotate("YZ", true);
				System.out.println("Pitch up");
			}
			if(left){
				if(right){
					// Do nothing
				}else{
					// Shift + left
					// = Roll left
					// = Rotate on XZ CCW : Positive
					System.out.println("Roll left");
					rotate("XZ", true);
				}
			}else if(right){
				// Shift + right
				// = Roll right
				// = Rotate on XZ CW : Negative
				System.out.println("Roll right");
				rotate("XZ", false);
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
					System.out.println("Move forward");
					camLoc.x += (int)(YZ.dx * speed);
					camLoc.y += (int)(YZ.dy * speed);
					camLoc.z += (int)(YZ.dz * speed);
					// WORLDLENGTH/2, 200, WORLDHEIGHT/2
					System.out.println("(" + (camLoc.x - Env.WORLDLENGTH/2) + "," + (camLoc.y - 200) + "," + (camLoc.z - Env.WORLDHEIGHT/2) + ")");
					//camLoc.print();
					//YZ.print();
				}
			}else if(backward){
				// Backward
				// = Move backward
				// Add to x, y, z -1 * speed * (normalized)[x, y, z]
				System.out.println("Move backward");
				camLoc.x = camLoc.x - (int)(YZ.dx * speed);
				camLoc.y = camLoc.y - (int)(YZ.dy * speed);
				camLoc.z = camLoc.z - (int)(YZ.dz * speed);
				System.out.println("(" + (camLoc.x - Env.WORLDLENGTH/2) + "," + (camLoc.y - 200) + "," + (camLoc.z - Env.WORLDHEIGHT/2) + ")");
				//camLoc.print();
				//YZ.print();
			}

			if(left){
				if(right){
					// Do nothing
				}else{
					// Left
					// = Left
					// = Rotate on XY CCW : Positive
					System.out.println("Rotate left (yaw)");
					rotate("XY", true);
				}
			}else if(right){
				// Right
				// = Right
				// = Rotate on XY CW : Negative
				System.out.println("Rotate right (yaw)");
				rotate("XY", false);
			}
			if(up){
				if(down){
					// Do nothing
				}else{
					// Up
					// = Up
					// = Move upward
					System.out.println("Move upward");
					camLoc.x += XZ.dx * speed;
					camLoc.y += XZ.dy * speed;
					camLoc.z += XZ.dz * speed;
					System.out.println("(" + (camLoc.x - Env.WORLDLENGTH/2) + "," + (camLoc.y - 200) + "," + (camLoc.z - Env.WORLDHEIGHT/2) + ")");
					//camLoc.print();
				}
			}else if(down){
				// Down
				// = Down
				// = Move downward
				System.out.println("Move downward");
				camLoc.x -= XZ.dx * speed;
				camLoc.y -= XZ.dy * speed;
				camLoc.z -= XZ.dz * speed;
				System.out.println("(" + (camLoc.x - Env.WORLDLENGTH/2) + "," + (camLoc.y - 200) + "," + (camLoc.z - Env.WORLDHEIGHT/2) + ")");
				//camLoc.print();
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

	public void rotate(String plane, double theta){
		double radians = Math.toRadians(theta);
		double s1 = 1/Math.sqrt(1 + Math.pow(Math.tan(radians), 2));
		double s2 = s1 * Math.tan(radians);
		switch(plane){
		case "XY":
			Vector3D newXY_1 = (Vector3D)(XY.multiply(s1).add(YZ.multiply(s2)));
			Vector3D newYZ_1 = (Vector3D)(YZ.multiply(s1).add(new Vector3D(-XY.dx, XY.dy, XY.dz).multiply(s2)));
			XY = newXY_1.normalize();
			YZ = newYZ_1.normalize();
			break;
		case "XZ":
			Vector3D newXZ_2 = (Vector3D)XZ.multiply(s1).add(new Vector3D(-XY.dx, XY.dy, XY.dz).multiply(s2));
			Vector3D newXY_2 = (Vector3D)XY.multiply(s1).add(XZ.multiply(s2));
			XZ = newXZ_2.normalize();
			XY = newXY_2.normalize();
			break;
		case "YZ":
			Vector3D newYZ_3 = (Vector3D)YZ.multiply(s1).add(XZ.multiply(s2));
			Vector3D newXZ_3 = (Vector3D)XZ.multiply(s1).add(new Vector3D(YZ.dx, -YZ.dy, YZ.dz).multiply(s2));
			YZ = newYZ_3.normalize();
			XZ = newXZ_3.normalize();
			break;
		default:
			System.out.println("Error, Camera.rotate, exiting");
			System.exit(-1);
		}
	}

	public void rotate(String plane, boolean positive){
		double constant = 5;
		if(positive){
			rotate(plane, constant);
		}else{
			rotate(plane, -constant);
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

	public void main3(String args[]){
		Camera cam = new Camera(new Point3D(0,0,0));

		System.out.println("Before rotation: ");
		XY.print();
		YZ.print();
		XZ.print();

		cam.rotate("XY", 20);

		System.out.println("After rotation: ");
		XY.print();
		YZ.print();
		XZ.print();

		cam.rotate("XY", -19);

		System.out.println("After neg rotation: ");
		XY.print();
		YZ.print();
		XZ.print();
	}
	
	public int[] getBaseCoords(Vector3D pt){
		// Combine all vectors into a matrix
		
		// First, make the point relative to the camera loc
		pt.values[0][0] -= camLoc.x;
		pt.values[1][0] -= camLoc.y;
		pt.values[2][0] -= camLoc.z;
		
		pt.dx -= camLoc.x;
		pt.dy -= camLoc.y;
		pt.dz -= camLoc.z;
		
		//System.out.println("Printing point:");
		//pt.print();
		//System.out.println("Printing camloc:");
		//System.out.println(camLoc.x + ", " + camLoc.y + ", " + camLoc.z);
		
		// Now combine all vectors into a matrix
		// Create vector array
		Vector3D[] vectors = new Vector3D[3];
		vectors[0] = YZ;
		vectors[1] = XZ;
		vectors[2] = pt;
		Matrix system = XY.combineVectors(vectors);
		system.rref();
		//System.out.println("Checking system");
		//system.print();
		
		if(!Matrix.closeToZero(system.values[1][3])){
			//System.out.println("Error, Camera.getBaseCoords(), system YZ value is nonzero: " + system.values[1][3]);
			//system.print();
			//System.exit(-1);
		}
		
		return new int[]{(int)system.values[0][3], (int)system.values[2][3]};
	}

	public Vector3D getXY() {
		return XY;
	}

	public void setXY(Vector3D xY) {
		XY = xY;
	}

	public Vector3D getYZ() {
		return YZ;
	}

	public void setYZ(Vector3D yZ) {
		YZ = yZ;
	}

	public Vector3D getXZ() {
		return XZ;
	}

	public void setXZ(Vector3D xZ) {
		XZ = xZ;
	}
}
