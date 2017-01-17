package sub;

public class Vector3D extends MyMatrix {
	public double dx, dy, dz;

	public Vector3D(double dx, double dy, double dz) {
		super(new double[][]{{dx},{dy},{dz}});
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	public static void main(String args[]){
		Vector3D v1 = new Vector3D(1,0,0);
		v1.rotateYZ(10);
		v1.print();
		
		Vector3D v2 = new Vector3D(1,0,0);
		v2.rotateXZ(10);
		v2.print();
	}
	
	public Vector3D rotateXY(double theta){
		double[][] rotationXYValues = {
				{Math.cos(theta), -Math.sin(theta), 0},
				{Math.sin(theta), Math.cos(theta), 0},
				{0,0,0}
		};
		MyMatrix XYRotationMatrix = new MyMatrix(rotationXYValues);
		//XZRotationMatrix.print();
		//Vector3D result = (Vector3D)XYRotationMatrix.multiply(this);
		MyMatrix result = XYRotationMatrix.multiply(this);
		//result.print();
		return new Vector3D(result.values[0][0], result.values[1][0], result.values[2][0]);
	}
	
	public Vector3D rotateXZ(double theta){
		double[][] rotationXZValues = {
				{Math.cos(theta), 0, -Math.sin(theta)},
				{0,0,0},
				{Math.sin(theta), 0, Math.cos(theta)}
		};
		MyMatrix XZRotationMatrix = new MyMatrix(rotationXZValues);
		//XZRotationMatrix.print();
		//Vector3D result = (Vector3D)XYRotationMatrix.multiply(this);
		MyMatrix result = XZRotationMatrix.multiply(this);
		//result.print();
		return new Vector3D(result.values[0][0], result.values[1][0], result.values[2][0]);
	}
	
	public Vector3D rotateYZ(double theta){
		double[][] rotationYZValues = {
				{0,0,0},
				{0, Math.cos(theta), -Math.sin(theta)},
				{0, Math.sin(theta), Math.cos(theta)}
		};
		MyMatrix YZRotationMatrix = new MyMatrix(rotationYZValues);
		//XZRotationMatrix.print();
		//Vector3D result = (Vector3D)XYRotationMatrix.multiply(this);
		MyMatrix result = YZRotationMatrix.multiply(this);
		//result.print();
		return new Vector3D(result.values[0][0], result.values[1][0], result.values[2][0]);
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
