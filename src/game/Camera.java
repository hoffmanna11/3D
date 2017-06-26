package game;

import java.awt.Point;

import sub.Matrix;
import sub.Orient3D;
import sub.Vector3D;

public class Camera {
	public Vector3D loc;
	public Orient3D orient;
	
	public KeyInput keyInput;
	
	public Env env;

	public boolean shift = false;
	public boolean forward = false;
	public boolean backward = false;
	public boolean left = false;
	public boolean right = false;
	public boolean up = false;
	public boolean down = false;
	public boolean rightClick = false;

	public int speed = 2;
	
	public Camera(Vector3D loc, Vector3D yzOrient, KeyInput keyInput, Env env){
		this.loc = loc;
		this.orient = new Orient3D(yzOrient);
		this.keyInput = keyInput;
		this.env = env;
	}

	public void tick(){
		applyKeyInput();
	}

	public void applyKeyInput(){
		if(rightClick){
			Point p = env.getMousePosition();
			if(null != p){
				double mouseXNew = p.getX();
				double mouseYNew = p.getY();
				
				if(keyInput.mouseX == -1){
					// No change to apply since we don't have any previous input to gauge a (dx, dy) from
				}else{
					double mouseDX = mouseXNew - keyInput.mouseX;
					double mouseDY = mouseYNew - keyInput.mouseY;
					
					// Percentage of screen
					double mouseDXPercent = mouseDX / (double)Env.RESWIDTH;
					double mouseDYPercent = mouseDY / (double)Env.RESHEIGHT;
					rotate("YZ", -mouseDYPercent * 100 * 2);
					rotate("XY", -mouseDXPercent * 100 * 4);
				}
				
				keyInput.mouseX = mouseXNew;
				keyInput.mouseY = mouseYNew;
			}
		}
		
		if(shift){
			if(forward){
				if(backward){
				}else{
					rotate("YZ", false);
				}
			}else if(backward){
				rotate("YZ", true);
			}
			if(left){
				if(right){
				}else{
					rotate("XY", true);
				}
			}else if(right){
				rotate("XY", false);
			}
		// No shift
		}else{			
			if(forward){
				if(backward){
				}else{
					loc.add$(orient.yz.multiply(speed));
					/*
					loc.setDX(loc.dx() + (int)(this.orient.yz.dx() * speed) );
					loc.setDY(loc.dy() + (int)(this.orient.yz.dy() * speed) );
					loc.setDZ(loc.dz() + (int)(this.orient.yz.dz() * speed) );
					*/ 
					
				}
			}else if(backward){
				loc.add$(orient.yz.multiply(-speed));
				/*
				loc.dx -= (int)(this.orient.yz.dx * speed);
				loc.dy -= (int)(this.orient.yz.dy * speed);
				loc.dz -= (int)(this.orient.yz.dz * speed);
				*/
			}

			if(left){
				if(right){
				}else{
					//loc.dx += (int)(this.orient.xy.dx * -speed);
					//loc.dy += (int)(this.orient.xy.dy * -speed);
					//loc.dz += (int)(this.orient.xy.dz * -speed);
					
					/*
					System.out.println("Before: ");
					System.out.println("loc: ");
					loc.print();
					System.out.println("orient.xy: ");
					orient.xy.print();
					System.out.println("orient.xy.multiply(-1 * speed): ");
					Vector3D temp1 = (Vector3D) this.orient.xy.multiply(-1 * speed);
					temp1.print();
					System.out.println("loc.add(...): ");
					*/
					loc.add$(this.orient.xy.multiply(-1 * speed));
					/*
					loc.print();
					System.out.println("Exiting");
					System.exit(0);
					*/
				}
			}else if(right){
				loc.add$(this.orient.xy.multiply(speed));
			}
			if(up){
				if(down){
				}else{
					loc.add$(orient.xz.multiply(speed));
					/*
					loc.dx += this.orient.xz.dx * speed;
					loc.dy += this.orient.xz.dy * speed;
					loc.dz += this.orient.xz.dz * speed;
					*/
				}
			}else if(down){
				loc.add$(orient.xz.multiply(-speed));
				/*
				loc.dx -= this.orient.xz.dx * speed;
				loc.dy -= this.orient.xz.dy * speed;
				loc.dz -= this.orient.xz.dz * speed;
				*/
			}
		}
	}

	public void rotate(String plane, double theta){
		// Why not just keep these in radians at all times?
		double radians = Math.toRadians(theta);
		double s1 = 1/Math.sqrt(1 + Math.pow(Math.tan(radians), 2));
		double s2 = s1 * Math.tan(radians);
		switch(plane){
		case "XY":
			Vector3D newXY_1 = (Vector3D)(this.orient.xy.multiply(s1).add(this.orient.yz.multiply(s2)));
			Vector3D newYZ_1 = (Vector3D)(this.orient.yz.multiply(s1).add(new Vector3D(-this.orient.xy.dx(), -this.orient.xy.dy(), -this.orient.xy.dz()).multiply(s2)));
			this.orient.xy = newXY_1.normalize();
			this.orient.yz = newYZ_1.normalize();
			break;
		case "YZ":
			Vector3D newYZ_3 = (Vector3D)this.orient.yz.multiply(s1).add(this.orient.xz.multiply(s2));
			Vector3D newXZ_3 = (Vector3D)this.orient.xz.multiply(s1).add(new Vector3D(-this.orient.yz.dx(), -this.orient.yz.dy(), -this.orient.yz.dz()).multiply(s2));
			this.orient.yz = newYZ_3.normalize();
			this.orient.xz = newXZ_3.normalize();
			break;
		case "XZ":
			Vector3D newXZ_2 = (Vector3D)this.orient.xz.multiply(s1).add(new Vector3D(-this.orient.xy.dx(), -this.orient.xy.dy(), -this.orient.xy.dz()).multiply(s2));
			Vector3D newXY_2 = (Vector3D)this.orient.xy.multiply(s1).add(this.orient.xz.multiply(s2));
			this.orient.xz = newXZ_2.normalize();
			this.orient.xy = newXY_2.normalize();
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
		pt.values[0][0] -= loc.dx();
		pt.values[1][0] -= loc.dy();
		pt.values[2][0] -= loc.dz();

		pt.setDX(pt.dx() - loc.dx());
		pt.setDY(pt.dy() - loc.dy());
		pt.setDZ(pt.dz() - loc.dz());

		// Now combine all vectors into a matrix
		// Create vector array
		Vector3D[] vectors = new Vector3D[3];
		vectors[0] = this.orient.yz;
		vectors[1] = this.orient.xz;
		vectors[2] = pt;
		@SuppressWarnings("static-access")
		Matrix system = this.orient.xy.combineVectors(vectors);
		system.rref();

		return new int[]{(int)system.values[0][3], (int)system.values[2][3]};
	}
}
