package src.通过输入输出求公式.gp;

import src.通过输入输出求公式.tree.*;

import javax.script.ScriptException;
import java.util.ArrayList;

public class 传统GP_demo extends BaseGenetic {

    @Override
    public OperatorNode generateTree(int depth, ArrayList probabilityList) {
        int value = random.nextInt(3);

        if (value == 0) {
            return new AddNode(generateRecursiveTree(depth, null), generateRecursiveTree(depth, null));
        } else if (value == 1) {
            return new MinNode(generateRecursiveTree(depth, null), generateRecursiveTree(depth, null));
        } else if (value == 2) {
            return new MultNode(generateRecursiveTree(depth, null), generateRecursiveTree(depth, null));
        } else {
            return new SinNode(generateRecursiveTree(depth, null));
        }
    }

    //Generate rest of the tree
    @Override
    public OperatorNode generateRecursiveTree(int depth, ArrayList<Double> cumulativeProbability) {

        int value = random.nextInt(8);

        if (value == 0) {
            return new AddNode(generateRecursiveTree(depth - 1, null), generateRecursiveTree(depth - 1, null));
        } else if (value == 1) {
            return new MinNode(generateRecursiveTree(depth - 1, null), generateRecursiveTree(depth - 1, null));
        } else if (value == 2) {
            return new MultNode(generateRecursiveTree(depth - 1, null), generateRecursiveTree(depth - 1, null));
        } else if (value == 3) {
            return new SinNode(generateRecursiveTree(depth - 1, null));
        } else {
            return new TerminalX1();
        }
    }

    @Override
    public OperatorNode[] initiatePopulation(int size, int depth) {
        OperatorNode[] population = new Node[size];

        for (int i = 0; i < size; i++) {
            population[i] = generateTree(depth, null);
            System.out.println("new population: " + population[i].printContent());
        }
        return population;
    }

    @Override
    public OperatorNode crossOver(OperatorNode dad, OperatorNode mom, int mutationRate) {
        int mutation = random.nextInt(100), decision = random.nextInt(2);
        OperatorNode cloneDad = dad.cloneTree(), cloneMom = mom.cloneTree();
        OperatorNode dadStopPoint = pickStop(cloneDad), momStopPoint = pickStop(cloneMom);

        if (mutation > mutationRate) {
            if (decision == 0) {
                dadStopPoint.setLeft(momStopPoint);
            } else {
                dadStopPoint.setRight(momStopPoint); }
        } else {
            if (decision == 0) {
                dadStopPoint.setLeft(generateTree(1, null));
            } else {
                dadStopPoint.setRight(generateTree(1, null));
            }
        }
        return cloneDad;
    }

    public static void main(String[] args) throws ScriptException {
        传统GP_demo gp = new 传统GP_demo();

//        String formula = "x*x*x*x*x*x*x*x*x*x*x*x*x*x*x";//x^15
//        String formula = "x*x*x*x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^11+x^7+x^6+x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x*x*x*x*x*x - x*x*x*x*x*x*x*x*x + x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^10 - x^9 +x^7+x^6+x^5+x^4 + x^3 + x^2 + x


//        String formula = "x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x - x*x*x*x*x+x*x*x*x+x*x*x+x*x + x";//x^8+x^7+x^6-x^5+x^4 + x^3 + x^2 + x
//
//        String formula = "x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^7+x^6+x^5+x^4 + x^3 + x^2 + x
//        String formula = "2*x*x*x*x*x*x*x+x*x*x*x*x*x-5*x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//2x^7+x^6-5x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^6+x^5+x^4 + x^3 + x^2 + x

        String formula = "x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^5+x^4 + x^3 + x^2 + x
//        String formula = "(5*x*x*x*x*x)-(x*x*x*x)+(6*x*x*x)+(x*x)-(x)";//5x^5*-x^4 + 6x^3 + x^2 - x

//        String formula = "x*x*x*x+x*x*x+x*x+x";//x^4 + x^3 + x^2 + x
//        String formula = "15*x*x*x*x+20*x*x*x-120*x*x+200*x";//15x^4 + 20x^3 -120x^2 + 200x

//        String formula = "x*x*x+x*x+x";//x^3 + x^2 + x
//        String formula = "(x*x*x)+(x*x*2)+5*x";//x^3 + 2x^2 + 5x
//        String formula = "(x*x*2)+3*x";// 2x^2 + 3x

        // 控制 目标公式，入参数量，参数区间
        gp.initializeSolution(formula, 10, -50.0, 50.0);

        long totalStartTime = System.currentTimeMillis();


//        for (int i = 0; i < 10; i++) {
//        }
//              gp.evaluateChaoticFactors(10000, 3, 10,4, example);

        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();

        for (int i = 0; i < 5; i++) {
            inputValue.clear();
            // 控制 目标公式，入参数量，参数区间
            gp.initializeSolution(formula, 10, -1.0, 1.0);

            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(200, 3, null,50);
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());

            //初始化
            gp.totalGenerations = 1;

            min = 100000000.0;
            gp.bestIndividual = null;
        }
        System.out.println("Time: " + timeList);
        System.out.println("Number of generations : " + generationList);

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
    }


}
