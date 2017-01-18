package sub;

public class Vector3D extends Matrix {
	public double dx, dy, dz;

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
		
		//System.out.println("Combined matrix:");
		//new Matrix(values).print();
		return new Matrix(values);
	}
	
	public Vector3D normalize(){
		double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
		//System.out.println("dist: " + dist);
		return new Vector3D(dx/dist, dy/dist, dz/dist);
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
