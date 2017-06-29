package game;

import java.awt.Point;

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
					
					//rotate(this.orient, "YZ", -mouseDYPercent * 100 * 2);
					//rotate(this.orient, "XY", -mouseDXPercent * 100 * 4);
					this.orient.rotate("YZ", -mouseDYPercent * 100 * 2);
					this.orient.rotate("XY", -mouseDXPercent * 100 * 4);
				}

				keyInput.mouseX = mouseXNew;
				keyInput.mouseY = mouseYNew;
			}
		}

		if(shift){
			if(forward){
				if(backward){
				}else{
					//rotate(this.orient, "YZ", false);
					this.orient.rotate("YZ", false);
				}
			}else if(backward){
				//rotate(this.orient, "YZ", true);
				this.orient.rotate("YZ", true);
			}
			if(left){
				if(right){
				}else{
					//rotate(this.orient, "XY", true);
					this.orient.rotate("XY", true);
				}
			}else if(right){
				//rotate(this.orient, "XY", false);
				this.orient.rotate("XY", false);
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
}
