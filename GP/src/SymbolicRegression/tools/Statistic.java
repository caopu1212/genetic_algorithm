package src.SymbolicRegression.tools;

import src.SymbolicRegression.gp.BaseGenetic_多维数据集;

import java.io.IOException;
import java.util.ArrayList;

public class Statistic {

    public void demo() {

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BaseGenetic_多维数据集 gp = new BaseGenetic_多维数据集();

        gp.initializeSolution();

        long totalStartTime = System.currentTimeMillis();

        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();
        ArrayList<Double> RSquareList = new ArrayList();

        for (int i = 0; i < 10; i++) {
            gp.inputValue.clear();
            // 控制 目标公式，入参数量，参数区间
            gp.initializeSolution();

            gp.evaluateChaoticFactors(5000, 1, 5, 6);

            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(500, 6, null, 50,100);
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());
            System.out.println("r^2 =  " + gp.getRSquare());

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());
            RSquareList.add(gp.getRSquare());

            //初始化
            gp.totalGenerations = 1;

            gp.min = 1.7976931348623157E308;
            gp.bestIndividual = null;
        }
        System.out.println("Time: " + timeList);
        System.out.println("Number of generations : " + generationList);
        System.out.println("r^2 =  " + RSquareList);


        long totalStopTime = System.currentTimeMillis();

        System.out.println("total time= " + (totalStopTime - totalStartTime));
        Long total = Long.valueOf(0);
        for (Long time : timeList) {
            total += time;
        }
        System.out.println("时间均值：" + total / timeList.size());

        Long total1 = Long.valueOf(0);
        for (Integer generation : generationList) {
            total1 += generation;
        }
        System.out.println("迭代数均值：" + total1 / generationList.size());
//        System.out.println("chaos factors is ：" + betterChaoticMapping);

        System.out.println("r^2 均值=  " + RSquareList.stream().mapToDouble(a -> a).sum() / RSquareList.size());
    }


}
