import java.util.Arrays;

public class MatrixOperation {
    public Matrix multiple(Matrix A, Matrix B){
        if (A.column != B.row){
            throw new RuntimeException("Not multiplicative");
        }
        Matrix C = new Matrix(new double[A.row][B.column]);
        for (int i=0; i<C.matrix.length; i++){
            Arrays.fill(C.matrix[i], 0);
        }
        for (int i=0; i<A.row; i++){
            for (int j=0; j<B.column; j++){
                for (int k=0; k<A.column; k++) {
                    C.matrix[i][j] += A.matrix[i][k] * B.matrix[k][j];
                }
            }
        }
        return C;
    }

    public  Matrix addition(Matrix A, Matrix B){
        if (A.row != B.row || A.column != B.column){
            throw new RuntimeException("Not additive");
        }
        Matrix C = new Matrix(new double[A.row][B.column]);
        for (int i=0; i<A.row; i++){
            for (int j=0; j<A.column; j++){
                C.matrix[i][j] = A.matrix[i][j] + B.matrix[i][j];
            }
        }
        return C;
    }
}
