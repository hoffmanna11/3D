package sub;

public class Vector3D extends Matrix {
	public double dx, dy, dz;
	
	public static Vector3D XY = new Vector3D(1,0,0);
	public static Vector3D YZ = new Vector3D(0,1,0);
	public static Vector3D XZ = new Vector3D(0,0,1);

	public Vector3D(double dx, double dy, double dz) {
		super(new double[][]{
			{dx},
			{dy},
			{dz}
			});
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	public Vector3D(double[][] values){
		super(values);
		if(
				(values.length != 3) ||
				(values[0].length != 1)
				){
			System.out.println("Error creating Vector3D, exiting");
			System.exit(-1);
		}
		this.dx = values[0][0];
		this.dy = values[1][0];
		this.dz = values[2][0];
	}
	
	public Point3D toPoint3D(){
		return new Point3D((int)dx, (int)dy, (int)dz);
	}
	
	public static void main3(String args[]){
		Vector3D a = new Vector3D(1,2,3);
		Vector3D b = new Vector3D(4,5,6);
		Vector3D c = new Vector3D(3,2,1);
		Vector3D d = new Vector3D(0,0,0);
		Vector3D[] vs = new Vector3D[3];
		vs[0] = b;
		vs[1] = c;
		vs[2] = d;
		a.combineVectors(vs);
		/*
		Vector3D v1 = new Vector3D(1,0,0);
		v1.rotateYZ(10);
		v1.print();
		
		Vector3D v2 = new Vector3D(1,0,0);
		v2.rotateXZ(10);
		v2.print();
		*/
	}
	
	public static Vector3D crossProduct(Vector3D u, Vector3D v){
		return new Vector3D(
				(u.dy * v.dz - u.dz * v.dy),
				(-(u.dx * v.dz - u.dz * v.dx)),
				(u.dx * v.dy - u.dy * v.dx)
				);
	}
	
	public Matrix combineVectors(Vector3D[] vectors){
		int cols = 1 + vectors.length;
		int rows = 3;
		
		double[][] values = new double[rows][cols];
		
		values[0][0] = dx;
		values[1][0] = dy;
		values[2][0] = dz;
		
		for(int col = 1; col < cols; col++){
			for(int row = 0; row < rows; row++){
				double x = vectors[1].values[1][0];
				values[row][col] = vectors[col-1].values[row][0];
			}
		}
		
		return new Matrix(values);
	}
	
	public int[] getBaseCoords(Vector3D pt){
		// Combine all vectors into a matrix
		
		Vector3D XY = new Vector3D(1,0,0);
		Vector3D YZ = new Vector3D(0,1,0);
		Vector3D XZ = new Vector3D(0,0,1);
		
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
	
	public Vector3D projectOntoPlane(Point3D planePoint, Vector3D planeNormal){
		Vector3D projection;
		//double t = (a*d - a*x + b*e - b*y + c*f - c*z) / 
		//		( Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2) );
		
		double t = (planeNormal.dx*planePoint.x - planeNormal.dx*dx + planeNormal.dy*planePoint.y - planeNormal.dy*dy + planeNormal.dz*planePoint.z - planeNormal.dz*dz) / 
						( Math.pow(planeNormal.dx, 2) + Math.pow(planeNormal.dy, 2) + Math.pow(planeNormal.dz, 2) );
		
		projection = new Vector3D((dx + t*planeNormal.dx), (dy + t*planeNormal.dy), (dz + t*planeNormal.dz));
		
		return projection;
	}
	
	public Vector3D normalize(){
		double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
		//System.out.println("dist: " + dist);
		return new Vector3D(dx/dist, dy/dist, dz/dist);
	}
	
	public double dot(Vector3D v){
		return dx * v.dx + dy * v.dy + dz * v.dz;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getDz() {
		return dz;
	}

	public void setDz(double dz) {
		this.dz = dz;
	}
	
	
}
