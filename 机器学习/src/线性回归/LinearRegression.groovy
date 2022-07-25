package 线性回归

public class LinearRegression {

    //线性回归
    private double theta0 = 0.0;  //截距
    private double theta1 = 0.0;  //斜率
    private double alpha = 0.01;  //学习速率

    private int max_itea = 20000; //最大迭代步数


    public double getTheta0() {
        return theta0;
    }

    public void setTheta0(double theta0) {
        this.theta0 = theta0;
    }

    public double getTheta1() {
        return theta1;
    }

    public void setTheta1(double theta1) {
        this.theta1 = theta1;
    }

    //计算结果
    public double predict(double x) {
        return theta0 + theta1 * x;
    }



    public double calc_error(double x, double y) {
        return predict(x) - y;
    }

    public void gradientDescient(List<Double> x_arr, List<Double> y_arr) {
        double slope = 0.0; // 截距
        double intercept = 0.0; //斜率

        for (int i = 0; i < x_arr.size(); i++) {
            slope += calc_error(x_arr.get(i), y_arr.get(i));
            intercept += calc_error(x_arr.get(i), y_arr.get(i)) * x_arr.get(i);
        }

        this.theta0 = theta0 - alpha * slope / x_arr.size();// 截距
        this.theta1 = theta1 - alpha * intercept / x_arr.size();//斜率
    }

    public void lineGre(List<Double> x_arr, List<Double> y_arr) {
        int itea = 0;
        while (itea < max_itea) {
            gradientDescient(x_arr, y_arr);
            itea++;
        }
    }

    public double predict_value(List<Double> y_arr) {
        List<Double> x_arr = new ArrayList<Double>();
        for (int i = 1; i <= y_arr.size(); i++) {
            x_arr.add((double) i);
        }
        this.lineGre(x_arr, y_arr);
        double predict_value = predict(x_arr.size() + 1);
        return predict_value;
    }

    public static void main(String[] args) {
        LinearRegression linearRegression = new LinearRegression()

        print(linearRegression.predict_value(Arrays.asList(1.0,2.0,3.0))+ "\n")
        println ("截距: "+linearRegression.getTheta0()+"  " +"斜率: "+linearRegression.getTheta1())
    }
}