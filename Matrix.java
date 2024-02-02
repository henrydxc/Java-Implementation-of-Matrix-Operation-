import java.util.Arrays;

public class Matrix {
    public int column;
    public int row;
    public double [][] matrix;
    public Matrix(){}
    public Matrix(int row, int column) {
        this.column = column;
        this.row = row;
        this.matrix = new double[row][column];
    }
    public Matrix(double[][] matrix) {
        this.row = matrix.length;
        this.column = matrix[0].length;
        this.matrix = matrix;
    }
    public void rowSwap(int r1, int r2) {
        double [] temp1 = Arrays.copyOf(matrix[r1],matrix[r1].length);
        matrix[r1] = matrix[r2];
        matrix[r2] = temp1;
    }
    public void rowReduction(int r2, double multiple, int r1) {
        double[] temp1 = Arrays.copyOf(matrix[r1], matrix[r1].length);
        for (int i=0; i<temp1.length; i++) {
            matrix[r2][i] -=  temp1[i] * multiple;
        }
    }
    public void rowMultiply(int row, double multiple) {
        for (int i=0; i<column; i++) {
            matrix[row][i] *= multiple;
        }
    }

    public void REF() {
        //int [][] result = new int[row][column];
        int rowCnt = 0, temp = 0;
        while (rowCnt < row - 1) {
            temp = rowCnt + 1;
            zeroRowGoesDown();
            if (matrix[rowCnt][rowCnt] != 0) {
                while (temp < row) {
                    rowReduction(temp, matrix[temp][rowCnt]/matrix[rowCnt][rowCnt], rowCnt);
                    temp++;
                }
            }
            rowCnt++;
        }
        for (int r=0; r<row; r++){
            if (checkLeadingEntry(r) != -1) {
                int leIndex = checkLeadingEntry(r);
                rowMultiply(r, 1 / matrix[r][leIndex]);
            }
        }
        // uncompleted
    }
    public void zeroRowGoesDown() {
        for (int i=0; i<row-1; i++) {
            for (int j=0; j<row-1-i; j++) {
                if (checkLeadingEntry(j) > checkLeadingEntry(j+1)) {
                    rowSwap(j, j+1);
                }
            }
        }
    }

    public int checkLeadingEntry(int row) {
        for (int i=0; i<column; i++) {
            if (matrix[row][i] != 0){
                return i;
            }
        }
        return column;
    }

    public void display(){
        for (double[] i : matrix) {
            for (double e : i){
                System.out.print(e + "  ");
            }
            System.out.println();
        }
    }
}
