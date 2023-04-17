package src.SymbolicRegression.tools;

import junit.framework.TestCase;
import lombok.val;
import src.SymbolicRegression.Elements.FileData;

import java.util.ArrayList;

public class PCAtest extends TestCase {
    private PCA pca;

    @Override
    protected void setUp() throws Exception {
        pca = new PCA();
        super.setUp();
    }


    public void testPca() {

        //读取数据集(只有特征)
        FileOperator fileOperator = new FileOperator();
        ArrayList<FileData> data = fileOperator.readFile();
        double[][] t = new double[data.size()][data.get(0).getFeatures().size()];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getFeatures().size(); j++) {
               t[i][j] = data.get(i).getFeatures().get(j);
            }
        }

//        double[][] v = {{1.0, 2.0, 3.0, 5.7,2.0, 3.0, 5.7}, {3.0, 4.0, 5.7, 4.9,2.0, 3.0, 5.7}, {5.7, 4.9, 5.0, 6.0,2.0, 3.0, 5.7}};

        val doubles = pca.pcaNormalized(t);
        System.out.println("213");
    }


    private void show(double[][] v) {
        for (double[] aV : v) {
            for (int column = 0; column < v[0].length; column++) {
                System.out.print(aV[column] + "    ");
            }
            System.out.println();
        }
    }
}