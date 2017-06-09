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
}
