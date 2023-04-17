package src.SymbolicRegression.gp;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TentMapiing extends BaseGenetic_多维数据集 {
    @Override
    public ArrayList chaosMapping(int amountOfChaosMapping) {
        ArrayList mappingList = new ArrayList();
        for (int j = 0; j < amountOfChaosMapping; j++) {
            ArrayList subList = new ArrayList();
            Random random = new Random();
            double x = random.nextDouble();
            for (int i = 0; i < 1000; i++) {
                if (x <= 0.5) {
                    x = 1.5 * x;
                } else {
                    x = 1.5 * (1 - x);
                }
                subList.add(x);
            }
            mappingList.add(subList);
        }
        return mappingList;
    }

    public static void main(String[] args) throws ScriptException, IOException, ClassNotFoundException {
        TentMapiing gp = new TentMapiing();

        int[] example = {0, 0, 4};

//            String formula = "x*x*x*x*x*x*x*x*x*x*x*x*x*x*x";//x^15


//
//        String formula = "x*x*x*x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^11+x^7+x^6+x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x*x*x*x*x*x - x*x*x*x*x*x*x*x*x + x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^10 - x^9 +x^7+x^6+x^5+x^4 + x^3 + x^2 + x


//        String formula = "x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x - x*x*x*x*x+x*x*x*x+x*x*x+x*x + x";//x^8+x^7+x^6-x^5+x^4 + x^3 + x^2 + x
//
//        String formula = "x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^7+x^6+x^5+x^4 + x^3 + x^2 + x
//        String formula = "2*x*x*x*x*x*x*x+x*x*x*x*x*x-5*x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//2x^7+x^6-5x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^6+x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^5+x^4 + x^3 + x^2 + x
//        String formula = "(5*x*x*x*x*x)-(x*x*x*x)+(6*x*x*x)+(x*x)-(x)";//5x^5*-x^4 + 6x^3 + x^2 - x

//        String formula = "x*x*x*x+x*x*x+x*x+x";//x^4 + x^3 + x^2 + x
//        String formula = "15*x*x*x*x+20*x*x*x-120*x*x+200*x";//15x^4 + 20x^3 -120x^2 + 200x

//        String formula = "x*x*x+x*x+x";//x^3 + x^2 + x
//        String formula = "(x*x*x)+(x*x*2)+5*x";//x^3 + 2x^2 + 5x
//        String formula = "(x*x*2)+3*x";// 2x^2 + 3x
        String formula = "x+x+x";

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

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());
            RSquareList.add(gp.getRSquare());

            //初始化
            gp.totalGenerations = 1;
            betterChaoticMapping = new ArrayList<>();
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