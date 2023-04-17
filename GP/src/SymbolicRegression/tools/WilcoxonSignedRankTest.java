package src.SymbolicRegression.tools;

import java.util.Arrays;

public class WilcoxonSignedRankTest {


    public static void main(String[] args) {
        double[] x = {2.3, 3.4, 5.6, 1.2, 4.5};
        double[] y = {1123, 422, 21123, 2232312, 12312312};

        double p = wilcoxonSignedRankTest(x, y);
        System.out.println("p-value: " + p);
    }

    public static double wilcoxonSignedRankTest(double[] x, double[] y) {
        double[] z = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            z[i] = x[i] - y[i];
        }
        Arrays.sort(z);

        double sum = 0;
        for (int i = 0; i < z.length; i++) {
            if (z[i] != 0) {
                sum += Math.signum(z[i]) * (i + 1);
            }
        }
        double w = Math.abs(sum);

        double n = z.length;
        double mu = n * (n + 1) / 4.0;
        double sigma = Math.sqrt(n * (n + 1) * (2 * n + 1) / 24.0);

        double zScore = (w - mu) / sigma;
        double p = 2 * (1 - normalDistribution(Math.abs(zScore)));
        return p;
    }

    public static double normalDistribution(double x) {
        return (1.0 / Math.sqrt(2 * Math.PI)) * Math.exp(-x * x / 2.0);
    }




}
