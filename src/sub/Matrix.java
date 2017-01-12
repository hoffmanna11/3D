package sub;

import java.util.Arrays;

public class Matrix {
	double[][] values;

	public Matrix(double[][] values){
		this.values = values;
	}

	public void print(){
		System.out.println("---Print Matrix:---");
		for(int i=0; i<this.values.length; i++){
			System.out.println(Arrays.toString(this.values[i]));
		}
		System.out.println("-------------------");
	}

	public Matrix multiply(Matrix m){
		if(this.invalid()){
			System.out.println("Error in matrix mult\n"
					+ "Left matrix is invalid\n"
					+ "Exiting");
			System.exit(-1);
		}else if(!this.canMultiplyWith(m)){
			System.out.println("Error in matrix mult\n"
					+ "Matrices cannot be multiplied: invalid dimensions\n"
					+ "Exiting");
			System.exit(-1);
		}

		double[][] result = new double[this.rows()][m.columns()];

		// recurse through the rows and add
		for(int i=0; i<this.rows(); i++){
			for(int k=0; k<m.columns(); k++){
				double colSum = 0;
				for(int j=0; j<m.rows(); j++){
					colSum += this.values[i][j] * m.values[j][k];
				}
				result[i][k] = colSum;
			}
		}
		return new Matrix(result);
	}

	public boolean invalid(){
		if((this.values.length == 0) || (this.values[0].length == 0)){
			return true;
		}
		return false;
	}

	public boolean canMultiplyWith(Matrix m){
		if( this.columns() == m.rows() ){
			return true;
		}
		return false;
	}

	public int rows(){
		return this.values.length;
	}

	public int columns(){
		return this.values[0].length;
	}

	public boolean invalidMatch(Matrix other){
		return false;
	}

	public static void main1(String args[]){
		/*
		double[][] m1 = {
				{1,6,5},
				{9,4,7},
				{6,7,34},
				{9,6,7},
		};
		double[][] m2 = {
				{1,8,1},
				{8,6,1},
				{1,2,3}
		};
		*/
		double theta = 10;
		double[][] m1 = {
				{Math.cos(theta), 0, -Math.sin(theta)},
				{0,1,0},
				{Math.sin(theta), 0, Math.cos(theta)}
		};
		double[][] m2 = {{1},{1},{0}};
		Matrix M1 = new Matrix(m1);
		M1.print();
		Matrix M2 = new Matrix(m2);
		M2.print();
		Matrix MResult = M1.multiply(M2);
		MResult.print();
		if(null == MResult){
			System.out.println("Null result!");
		}
	}
}
