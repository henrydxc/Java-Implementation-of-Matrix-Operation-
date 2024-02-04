/**
 * @author Xiangcheng Ding
 */

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

    public void GaussElimination() {
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

    public void GaussJordanElimination() {
        GaussElimination();
        int cnt = 1;
        while (cnt < row) {
            int le = checkLeadingEntry(cnt);
            for (int i=0; i<cnt; i++) {
                rowReduction(i, matrix[i][le] / matrix[cnt][le], cnt);
            }
            cnt++;
        }
    }

    public Matrix transpose(){
        double [][] t = new double[column][row];
        for (int r = 0; r < column; r++) {
            for (int c = 0; c < row; c++) {
                t[r][c] = matrix[c][r];
            }
        }
        return new Matrix(t);
    }

    public Matrix inverse() {
        if (!invertible()){
            throw new RuntimeException("This matrix is not invertible");
        }
        double [][] inverse = new double[row][column];
        double [][] equation = new double[row][2*column];
        for (int i=0; i<row; i++) {
            equation[i] = Arrays.copyOf(matrix[i], 2*column);
            equation[i][column+i] = 1;
        }
        Matrix system = new Matrix(equation);
        system.GaussJordanElimination();
        for (int i=0; i<row; i++) {
            inverse[i] = Arrays.copyOfRange(system.matrix[i],column,2*column);
        }
        return new Matrix(inverse);
    }

    public boolean invertible() {
        if (column != row) {
            return false;
        }
        if (getDeterminant() == 0) {
            return false;
        }
        return true;
    }
    public double getDeterminant() {
        if (row == 1)
            return matrix[0][0];
        double det = 0;
        for (int i=0; i<column;i++){
            double entry = matrix[0][i];
            Matrix sub = subMatrix(i);
            int sign = (i % 2 == 0) ? 1 : -1;
            det += sign * entry * sub.getDeterminant();
        }
        return det;
    }

    public Matrix subMatrix(int c) {
        Matrix sub = new Matrix(row-1,column-1);
        int j=0,k=0;
        for (int i=0; i<column; i++){
            if (i == c){
                continue;
            }
            j = 0;
            while (j < sub.row) {
                sub.matrix[j][k] = matrix[j+1][i];
                j++;
            }
            k++;
        }
        return sub;
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
