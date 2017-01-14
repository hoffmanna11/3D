package testing;

public class MatrixMult {
	public static void main(String args[]){
		double[][] m1 = {
				{1,2,3},
				{2,0,1},
				{1,2,1}
		};
		int[][] m2 = {
				{1,2,4,4},
				{2,2,2,8},
				{3,2,9,0}
		};
		
		print(m1);
		rowEchelon(m1);
	}
	
	public static void rowEchelon(double[][] matrix){
		int startingRow = 1;
		int rows = matrix.length;
		int cols = matrix[0].length;
		for(int col=0; col<cols-1; col++){
			rowMult(matrix, startingRow-1, 1/matrix[startingRow-1][col]);
			for(int row=startingRow; row<rows; row++){
				double rowMult = -1 * matrix[row][col] / matrix[startingRow-1][col];
				rowAdd(matrix, startingRow-1, row, rowMult);
				String sign;
				if(rowMult >= 0){
					sign = "+";
				}else{
					sign = "-";
				}
				System.out.println("c" + row + " = c" + row + " " + sign + " " + Math.abs(rowMult) + "c" + (startingRow-1));
				//print(matrix);
				System.out.println(matrixToStr(matrix));
			}
			startingRow++;
		}
	}
	
	public static void rowAdd(double[][] matrix, int c_adder, int c_addedTo, double mult){
		for(int col=0; col<matrix[0].length; col++){
			matrix[c_addedTo][col] += mult * matrix[c_adder][col];
		}
	}
	
	public static void rowMult(double[][] matrix, int row, double mult){
		for(int col=0; col<matrix[0].length; col++){
			matrix[row][col] *= mult;
		}
	}
	
	public static void print(double[][] matrix){
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				System.out.print(matrix[i][j] + " ");
			}System.out.print("\n");
		}System.out.print("\n");
	}
	
	public static String matrixToStr(double[][] matrix){
		String str = "";
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				str += (matrix[i][j] + " ");
			}str += "\n";
		}
		return str;
	}
}
