package testing;

public class MatrixMult {
	public static void main1(String args[]){
		double[][] m1 = {
				{1,2,3},
				{2,0,1},
				{1,2,1}
		};
		double[][] m2 = {
				{1,2,4,4},
				{2,2,2,8},
				{3,2,9,0}
		};
		double[][] m3 = {
				{0,2,4,4},
				{0,0,2,8},
				{0,2,9,0}
		};
		
		rref(m3);
	}
	
	public static void rref(double[][] matrix){
		System.out.println("Converting matrix to RREF:");
		print(matrix);
		int pivotRow = 0;
		int rows = matrix.length;
		int cols = matrix[0].length;
		int colsToIterate;
		if(rows >= cols){
			colsToIterate = cols;
		}else{
			colsToIterate = rows;
		}
		
		System.out.println("cols: " + cols + ", rows: " + rows);
		
		for(int col=0; col<colsToIterate; col++){
			double pivot = matrix[pivotRow][col];
			if(pivot == 0){
				// Search for a pivot in the column
				// If found, swap with that row and continue
				// Else continue to the next column, but print an error warning
				boolean foundPivot = false;
				for(int i=pivotRow+1; i<rows; i++){
					if(matrix[i][col] != 0){
						// We've found our pivot
						// Swap rows and break
						rowSwap(matrix, pivotRow, i);
						foundPivot = true;
						break;
					}
				}
				if(foundPivot){
					// No need to do anything else
				}else{
					// Continue to the next column
					pivotRow++;
					continue;
				}
			}
			
			// Make the pivot 1
			rowMult(matrix, pivotRow, 1/matrix[pivotRow][col]);
			
			// Zero out the values below pivot
			for(int row=pivotRow+1; row<rows; row++){
				double rowMult = -1 * matrix[row][col] / matrix[pivotRow][col];
				rowAdd(matrix, pivotRow, row, rowMult);
			}
			
			pivotRow++;
		}
		
		// Now to zero out the upper half
		System.out.println("Zero upper half:");
		pivotRow = rows-1;
		
		System.out.println("colsToIterate: " + colsToIterate + ", pivotRow: " + pivotRow + ", rows: " + rows);
		
		for(int col = colsToIterate-1; col >= 1; col--){
			double pivot = matrix[pivotRow][col];
			if(pivot == 0){
				System.out.println("Pivot is zero: @ (" + pivotRow + ", " + col + ")");
				pivotRow--;
				col++;
				continue;
			}
			for(int row = pivotRow-1; row >= 0; row--){
				rowAdd(matrix, pivotRow, row, -matrix[row][col]/pivot);
			}
			pivotRow--;
		}
	}
	
	public static void rowSwap(double[][] matrix, int r1, int r2){
		for(int i=0; i<matrix[0].length; i++){
			double temp = matrix[r1][i];
			matrix[r1][i] = matrix[r2][i];
			matrix[r2][i] = temp;
		}
		System.out.println("c" + r1 + " <-> c" + r2);
		print(matrix);
	}
	
	public static void rowAdd(double[][] matrix, int c_adder, int c_addedTo, double mult){
		for(int col=0; col<matrix[0].length; col++){
			matrix[c_addedTo][col] += mult * matrix[c_adder][col];
		}
		String sign;
		if(mult >= 0){
			sign = "+";
		}else{
			sign = "-";
		}
		System.out.println("c" + c_addedTo + " " + sign + " " + Math.abs(mult) + "c" + c_adder);
		print(matrix);
	}
	
	public static void rowMult(double[][] matrix, int row, double mult){
		for(int col=0; col<matrix[0].length; col++){
			matrix[row][col] *= mult;
		}
		System.out.println("c" + row + " * " + mult);
		print(matrix);
	}
	
	public static void print(double[][] matrix){
		System.out.println(matrixToStr(matrix));
	}
	
	public static String matrixToStr(double[][] matrix){
		int spaces = getSpacing(matrix);
		String str = "";
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				double abs = Math.abs(matrix[i][j]);
				int addSpaces;
				if(closeToZero(abs)){
					addSpaces = spaces - 1;
				}else{
					addSpaces = spaces - (int)Math.log10(abs);
				}
				
				if(closeToZero(matrix[i][j])){
					addSpaces++;
				}
				
				if(matrix[i][j] >= 0){
					// Sometimes a negative 0 happens, so this is to avoid that
					matrix[i][j] = abs;
					str += String.format(" %.0f", matrix[i][j]);
					for(int k=0; k<addSpaces; k++){
						str += " ";
					}
					//str += "+";
				}else{
					str += String.format("%.0f", matrix[i][j]);
					for(int k=0; k<addSpaces; k++){
						str += " ";
					}
					//str += "-";
				}
			}str += "\n";
		}
		return str;
	}
	
	public static int getSpacing(double[][] matrix){
		double max = Math.abs(matrix[0][0]);
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				double abs = Math.abs(matrix[i][j]);
				if(abs > max){
					max = abs;
				}
			}
		}
		return (int)Math.log10(max);
	}
	
	public static boolean closeToZero(double val){
		if(Math.abs(val) < .00001){
			return true;
		}
		return false;
	}
}
