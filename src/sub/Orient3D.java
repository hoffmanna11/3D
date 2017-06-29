package sub;

public class Orient3D {
	public Vector3D xy;
	public Vector3D yz;
	public Vector3D xz;

	public Orient3D(Vector3D xy, Vector3D yz, Vector3D xz){
		this.xy = xy;
		this.yz = yz;
		this.xz = xz;
	}

	// Takes a 3D directional vector
	// Outputs 3 basis vectors assuming an upward orientation
	public Orient3D(Vector3D yz){
		this.xz = Vector3D.XZ.projectOntoPlane(Vector3D.origin, yz).normalize();
		this.yz = yz;
		this.xy = Vector3D.crossProduct(yz, xz).normalize();
	}

	// Takes a 3D directional vector
	// Outputs 3 basis vectors assuming an upward orientation
	public Orient3D getUpwardOrient(Vector3D dir){
		Vector3D myXZ = Vector3D.XZ.projectOntoPlane(Vector3D.origin, dir).normalize();
		Vector3D myXY = Vector3D.crossProduct(dir, myXZ).normalize();
		return new Orient3D(myXY, dir.normalize(), myXZ);
	}
	
	public void print(){
		System.out.println("Orientation:");
		System.out.println("XY: (" + xy.dx() + ", " + xy.dy() + ", " + xy.dz() + ")");
		System.out.println("YZ: (" + yz.dx() + ", " + yz.dy() + ", " + yz.dz() + ")");
		System.out.println("XZ: (" + xz.dx() + ", " + xz.dy() + ", " + xz.dz() + ")");
	}
	
	public void rotate(String plane, double theta){
		// Why not just keep these in radians at all times?
		double radians = Math.toRadians(theta);
		double s1 = 1/Math.sqrt(1 + Math.pow(Math.tan(radians), 2));
		double s2 = s1 * Math.tan(radians);
		
		switch(plane){
		case "XY":
			Vector3D newXY_1 = (Vector3D)(xy.multiply(s1).add(yz.multiply(s2)));
			Vector3D newYZ_1 = (Vector3D)(yz.multiply(s1).add(new Vector3D(-xy.dx(), -xy.dy(), -xy.dz()).multiply(s2)));
			xy = newXY_1.normalize();
			yz = newYZ_1.normalize();
			break;
		case "YZ":
			Vector3D newYZ_3 = (Vector3D)yz.multiply(s1).add(xz.multiply(s2));
			Vector3D newXZ_3 = (Vector3D)xz.multiply(s1).add(new Vector3D(-yz.dx(), -yz.dy(), -yz.dz()).multiply(s2));
			yz = newYZ_3.normalize();
			xz = newXZ_3.normalize();
			break;
		case "XZ":
			Vector3D newXZ_2 = (Vector3D)xz.multiply(s1).add(new Vector3D(-xy.dx(), -xy.dy(), -xy.dz()).multiply(s2));
			Vector3D newXY_2 = (Vector3D)xy.multiply(s1).add(xz.multiply(s2));
			xz = newXZ_2.normalize();
			xy = newXY_2.normalize();
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
}
