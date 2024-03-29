package src.SymbolicRegression.gp;

import src.SymbolicRegression.tools.MyRandom;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FakeRandomMapping extends BaseGenetic_MutiDimensionData {

    MyRandom myRandom = new MyRandom();

    @Override
    public ArrayList chaosMapping(int amountOfChaosMapping) {
        ArrayList mappingList = new ArrayList();
        for (int j = 0; j < amountOfChaosMapping; j++) {
            ArrayList subList = new ArrayList();
            Random random = new Random();
            for (int i = 0; i < 500; i++) {
                double x = random.nextDouble();
                if (x > 0.1 && x < 0.9) {
                    int temp = random.nextInt(4);
                    if (temp == 0) {
                        temp = random.nextInt(2);
                        if (temp == 0) {
                            x = myRandom.makeRandom(0.1f, 0, 2);
                        } else if (temp == 1) {
                            x = myRandom.makeRandom(1, 0.9f, 2);
                        }
                    }
                }
                subList.add(x);
            }
            mappingList.add(subList);
        }
        return mappingList;
    }
//     for (int i = 0; i < 10000; i++) {
//
//        double x = random.nextDouble();
//        double y = random.nextDouble();
//        if (y > 0.1 && y < 0.9) {
//            count++;
//            int temp = random.nextInt(2);
//            if (temp == 0) {
//                count1++;
//                temp = random.nextInt(4);
//                if (temp == 0) {
//                    y = myRandom.makeRandom(0.1f, 0, 2);
//                } else if(temp == 1){
//                    y = myRandom.makeRandom(1, 0.9f, 2);
//                }
//            }
//        }
//
//        series.add(x, y);
//    }


    public static void main(String[] args) throws ScriptException, IOException, ClassNotFoundException {
        FakeRandomMapping gp = new FakeRandomMapping();


//        String formula = "x*x*x*x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^yacht-train-0+x^7+x^6+x^5+x^4 + x^3 + x^2 + x
//        String formula = "x*x*x*x*x*x*x*x*x*x - x*x*x*x*x*x*x*x*x + x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^10 - x^9 +x^7+x^6+x^5+x^4 + x^3 + x^2 + x


//        String formula = "x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x - x*x*x*x*x+x*x*x*x+x*x*x+x*x + x";//x^8+x^7+x^6-x^5+x^4 + x^3 + x^2 + x
//
//        String formula = "x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^7+x^6+x^5+x^4 + x^3 + x^2 + x
//        String formula = "2*x*x*x*x*x*x*x+x*x*x*x*x*x-5*x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//2x^7+x^6-5x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^6+x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^5+x^4 + x^3 + x^2 + x
//        String formula = "(5*x*x*x*x*x)-(x*x*x*x)+(6*x*x*x)+(x*x)-(x)";//5x^5*-x^4 + 6x^3 + x^2 - x

//        String formula = "x*x*x*x+x*x*x+x*x+x";//x^4 + x^3 + x^2 + x
//        String formula = "x*x*x+x*x+x";//x^3 + x^2 + x
//        String formula = "(x*x*x)+(x*x*2)+5*x";//x^3 + 2x^2 + 5x
        String formula = "(x*x*2)+3*x";// 2x^2 + 3x

        // 控制 目标公式，入参数量，参数区间
//        gp.initializeSolution(formula,20,-10.0,10.0);

        long totalStartTime = System.currentTimeMillis();

        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();
        ArrayList<Double> RSquareList = new ArrayList();

        for (int i = 0; i < 50; i++) {
            gp.inputValue.clear();
            // 控制 目标公式，入参数量，参数区间
            gp.initializeSolution();

            gp.evaluateChaoticFactors(5000, 1, 3, 6);

            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(200, 3, null, 60,100);;
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());
            System.out.println("r^2 =  " + gp.getRSquare());
            System.out.println("当前轮数: " + i);

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());
            RSquareList.add(gp.getRSquare());

            //初始化
            gp.totalGenerations = 1;
            ArrayList<ArrayList<Double>> betterChaoticMapping = new ArrayList<>();
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

        System.out.println("r^2 均值 =  " + RSquareList.stream().mapToDouble(a -> a).sum() / RSquareList.size());
    }
}
