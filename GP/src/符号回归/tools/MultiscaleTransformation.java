package src.符号回归.tools;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class MultiscaleTransformation {

    public static void main(String[] args) throws Exception {
        double[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(data);
        SingularValueDecomposition svd = matrix.svd();
        Matrix S = svd.getS();
        double[][] newData = svd.getU().getArray();
        for (double[] newDatum : newData) {
            for (double v : newDatum) {
                System.out.println(v);
            }
            System.out.println("asdasd");
        }
//        System.out.println(newData);
    }

}
