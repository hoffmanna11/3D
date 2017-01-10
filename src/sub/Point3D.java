package sub;

public class Point3D {
	public int x, y, z;

	public Point3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Point3D projectOntoPlane(Point3D planePoint, Vector3D planeNormal){
		Point3D projection;
		//double t = (a*d - a*x + b*e - b*y + c*f - c*z) / 
		//		( Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2) );
		
		double t = (planeNormal.dx*planePoint.x - planeNormal.dx*x + planeNormal.dy*planePoint.y - planeNormal.dy*y + planeNormal.dz*planePoint.z - planeNormal.dz*z) / 
						( Math.pow(planeNormal.dx, 2) + Math.pow(planeNormal.dy, 2) + Math.pow(planeNormal.dz, 2) );
		
		projection = new Point3D((int)(x + t*planeNormal.dx), (int)(y + t*planeNormal.dy), (int)(z + t*planeNormal.dz));
		
		return projection;
	}
	
	public int distanceBetween(Point3D point){
		return (int)Math.sqrt(Math.pow((point.x - x), 2) + Math.pow((point.y - y), 2) + Math.pow((point.z - z), 2));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	
}
