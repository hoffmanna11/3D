package game;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import sub.Matrix;
import sub.Orient3D;
import sub.Vector3D;

public class Camera {
	public Vector3D loc;
	public Orient3D orient;

	public boolean shift = false;
	public boolean forward = false;
	public boolean backward = false;
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	public boolean down = false;

	public int speed = 2;

	public Camera(Vector3D loc, Vector3D yzOrient){
		this.loc = loc;
		this.orient = new Orient3D(yzOrient);
	}

	/*
	 * Draws:
	 * 	text for current orient/loc
	 * 	visual orientation vectors
	 */
	public void render(Graphics g){
		drawStrings(g);
	}

	public void drawStrings(Graphics g){
		DecimalFormat df = new DecimalFormat("#.##");
		g.setColor(Color.green);
		g.drawString("camera loc: " + "(" + loc.dx + "," + loc.dy + "," + loc.dz + ")", 10, 40);
		g.drawString("camera xy: " + "(" + df.format(this.orient.xy.dx) + "," + df.format(this.orient.xy.dy) + "," + df.format(this.orient.xy.dz) + ")", 10, 60);
		g.drawString("camera yz: " + "(" + df.format(this.orient.yz.dx) + "," + df.format(this.orient.yz.dy) + "," + df.format(this.orient.yz.dz) + ")", 10, 80);
		g.drawString("camera xz: " + "(" + df.format(this.orient.xz.dx) + "," + df.format(this.orient.xz.dy) + "," + df.format(this.orient.xz.dz) + ")", 10, 100);
	}

	public void tick(){
		applyKeyInput();
	}

	public void applyKeyInput(){
		/* Check DIRECTION and opposite axial DIRECTION
		 *   If both, do nothing
		 *   Else If one, check for shift
		 *     If shift, check:
		 *       If 
		 *     If shift, rotate in that dual axis plane
		 *     Else move in that direction
		 *   Else check next direction

		 * Vector3D.XY: Yaw   ( left / right )
		 * Vector3D.XZ: Roll  ( shift + left / right )
		 * Vector3D.YZ: Pitch ( shift + forward / backward )
		 */

		if(shift){
			if(forward){
				if(backward){
					// Do nothing
				}else{
					// Shift + forward
					// = Pitch down
					// = Rotate on this.orient.yz CW : Negative
					//System.out.println("Pitch down");
					rotate("YZ", false);
				}
			}else if(backward){
				// Shift + backward
				// = Pitch up
				// = Rotate on this.orient.yz CCW : Positive
				rotate("YZ", true);
				//System.out.println("Pitch up");
			}
			if(left){
				if(right){
					// Do nothing
				}else{
					// Shift + left
					// = Roll left
					// = Rotate on this.orient.xz CCW : Positive
					//System.out.println("Roll left");
					rotate("XZ", true);
				}
			}else if(right){
				// Shift + right
				// = Roll right
				// = Rotate on this.orient.xz CW : Negative
				//System.out.println("Roll right");
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
					//System.out.println("Move forward");
					loc.dx += (int)(this.orient.yz.dx * speed);
					loc.dy += (int)(this.orient.yz.dy * speed);
					loc.dz += (int)(this.orient.yz.dz * speed);
					// WORLDLENGTH/2, 200, WORLDHEIGHT/2
					//System.out.println("(" + (loc.x - Env.WORLDLENGTH/2) + "," + (loc.y - 200) + "," + (loc.z - Env.WORLDHEIGHT/2) + ")");
					//loc.print();
					//this.orient.yz.print();
				}
			}else if(backward){
				// Backward
				// = Move backward
				// Add to x, y, z -1 * speed * (normalized)[x, y, z]
				//System.out.println("Move backward");
				loc.dx = loc.dx - (int)(this.orient.yz.dx * speed);
				loc.dy = loc.dy - (int)(this.orient.yz.dy * speed);
				loc.dz = loc.dz - (int)(this.orient.yz.dz * speed);
				//System.out.println("(" + (loc.x - Env.WORLDLENGTH/2) + "," + (loc.y - 200) + "," + (loc.z - Env.WORLDHEIGHT/2) + ")");
				//loc.print();
				//this.orient.yz.print();
			}

			if(left){
				if(right){
					// Do nothing
				}else{
					// Left
					// = Left
					// = Rotate on this.orient.xy CCW : Positive
					//System.out.println("Rotate left (yaw)");
					rotate("XY", true);
				}
			}else if(right){
				// Right
				// = Right
				// = Rotate on this.orient.xy CW : Negative
				//System.out.println("Rotate right (yaw)");
				rotate("XY", false);
			}
			if(up){
				if(down){
					// Do nothing
				}else{
					// Up
					// = Up
					// = Move upward
					//System.out.println("Move upward");
					loc.dx += this.orient.xz.dx * speed;
					loc.dy += this.orient.xz.dy * speed;
					loc.dz += this.orient.xz.dz * speed;
					//System.out.println("(" + (loc.x - Env.WORLDLENGTH/2) + "," + (loc.y - 200) + "," + (loc.z - Env.WORLDHEIGHT/2) + ")");
					//loc.print();
				}
			}else if(down){
				// Down
				// = Down
				// = Move downward
				//System.out.println("Move downward");
				loc.dx -= this.orient.xz.dx * speed;
				loc.dy -= this.orient.xz.dy * speed;
				loc.dz -= this.orient.xz.dz * speed;
			}
		}
	}

	public void rotate(String plane, double theta){
		double radians = Math.toRadians(theta);
		double s1 = 1/Math.sqrt(1 + Math.pow(Math.tan(radians), 2));
		double s2 = s1 * Math.tan(radians);
		switch(plane){
		case "XY":
			Vector3D newXY_1 = (Vector3D)(this.orient.xy.multiply(s1).add(Vector3D.YZ.multiply(s2)));
			Vector3D newYZ_1 = (Vector3D)(this.orient.yz.multiply(s1).add(new Vector3D(-this.orient.xy.dx, -this.orient.xy.dy, -this.orient.xy.dz).multiply(s2)));
			this.orient.xy = newXY_1.normalize();
			this.orient.yz = newYZ_1.normalize();
			break;
		case "XZ":
			Vector3D newXZ_2 = (Vector3D)this.orient.xz.multiply(s1).add(new Vector3D(-this.orient.xy.dx, -this.orient.xy.dy, -this.orient.xy.dz).multiply(s2));
			Vector3D newXY_2 = (Vector3D)this.orient.xy.multiply(s1).add(this.orient.xz.multiply(s2));
			this.orient.xz = newXZ_2.normalize();
			this.orient.xy = newXY_2.normalize();
			break;
		case "YZ":
			Vector3D newYZ_3 = (Vector3D)this.orient.yz.multiply(s1).add(this.orient.xz.multiply(s2));
			Vector3D newXZ_3 = (Vector3D)this.orient.xz.multiply(s1).add(new Vector3D(-this.orient.yz.dx, -this.orient.yz.dy, -this.orient.yz.dz).multiply(s2));
			this.orient.yz = newYZ_3.normalize();
			this.orient.xz = newXZ_3.normalize();
			break;
		default:
			System.out.println("Error, Camera.rotate, exiting");
			System.exit(-1);
		}
	}

	public void rotate(String plane, boolean positive){
		double constant = 1;
		if(positive){
			rotate(plane, constant);
		}else{
			rotate(plane, -constant);
		}
	}

	public int[] getBaseCoords(Vector3D pt){
		// Combine all vectors into a matrix

		// First, make the point relative to the camera loc
		pt.values[0][0] -= loc.dx;
		pt.values[1][0] -= loc.dy;
		pt.values[2][0] -= loc.dz;

		pt.dx -= loc.dx;
		pt.dy -= loc.dy;
		pt.dz -= loc.dz;

		// Now combine all vectors into a matrix
		// Create vector array
		Vector3D[] vectors = new Vector3D[3];
		vectors[0] = this.orient.yz;
		vectors[1] = this.orient.xz;
		vectors[2] = pt;
		Matrix system = this.orient.xy.combineVectors(vectors);
		system.rref();

		return new int[]{(int)system.values[0][3], (int)system.values[2][3]};
	}
}
