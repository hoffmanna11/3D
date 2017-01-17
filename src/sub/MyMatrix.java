package sub;

import java.util.Arrays;

import testing.MatrixMult;

public class MyMatrix {
	double[][] values;

	public MyMatrix(double[][] values){
		this.values = values;
	}

	public void print(){
		System.out.println("---Print Matrix:---");
		for(int i=0; i<this.values.length; i++){
			System.out.println(Arrays.toString(this.values[i]));
		}
		System.out.println("-------------------");
	}

	public MyMatrix multiply(MyMatrix m){
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
		return new MyMatrix(result);
	}

	public boolean invalid(){
		if((this.values.length == 0) || (this.values[0].length == 0)){
			return true;
		}
		return false;
	}

	public boolean canMultiplyWith(MyMatrix m){
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

	public boolean invalidMatch(MyMatrix other){
		return false;
	}
	
	public static double[][] rref(double[][] mat)
	{
	    double[][] rref = new double[mat.length][mat[0].length];

	    /* Copy matrix */
	    for (int r = 0; r < rref.length; ++r)
	    {
	        for (int c = 0; c < rref[r].length; ++c)
	        {
	            rref[r][c] = mat[r][c];
	        }
	    }

	    for (int p = 0; p < rref.length; ++p)
	    {
	        /* Make this pivot 1 */
	        double pv = rref[p][p];
	        if (pv != 0)
	        {
	            double pvInv = 1.0 / pv;
	            for (int i = 0; i < rref[p].length; ++i)
	            {
	                rref[p][i] *= pvInv;
	            }
	        }

	        /* Make other rows zero */
	        for (int r = 0; r < rref.length; ++r)
	        {
	            if (r != p)
	            {
	                double f = rref[r][p];
	                for (int i = 0; i < rref[r].length; ++i)
	                {
	                    rref[r][i] -= f * rref[p][i];
	                }
	            }
	        }
	    }

	    return rref;
	}

	public static void main(String args[]){
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
		
		double[][] matrix = {
				{3,4,5},
				{5,2,2},
				{5,2,2}
		};
		
		System.out.println("Before:");
		MatrixMult.print(matrix);
		double[][] rref = rref(matrix);
		System.out.println("Check original hasn't changed:");
		MatrixMult.print(matrix);
		System.out.println("RREF: ");
		MatrixMult.print(rref);
		/*
		MyMatrix M1 = new MyMatrix(m1);
		M1.print();
		MyMatrix M2 = new MyMatrix(m2);
		M2.print();
		MyMatrix MResult = M1.multiply(M2);
		MResult.print();
		if(null == MResult){
			System.out.println("Null result!");
		}
		*/
	}
}
