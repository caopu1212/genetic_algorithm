package src.求数字.gp;

import src.求数字.tree.*;

import java.util.ArrayList;
import java.util.Random;

public class NumberGuessing {
    public static int count = 0;

    private int totalGenerations = 1;
    private Random random = new Random();



    /**
     * //先决定根节点，然后生成左右子树
     * @param cap   目标结果
     * @param depth 树的级树
     * @return
     */
    private OperatorNode generateTree(int cap, int depth) {
        int value = random.nextInt(3);

        if (value == 0) {
            return new SumNode(generateRecursiveTree(cap, depth), generateRecursiveTree(cap, depth));
        } else if (value == 1) {
            return new SubNode(generateRecursiveTree(cap, depth), generateRecursiveTree(cap, depth));
        } else {
            return new MultNode(generateRecursiveTree(cap, depth), generateRecursiveTree(cap, depth));
        }
    }

    /**
     * 生成树的剩余部分
     * @param cap
     * @param depth
     * @return
     */
    private OperatorNode generateRecursiveTree(int cap, int depth) {
        if (depth == 0) {
            return new TerminalNode(random.nextInt(cap), null, null);
        }

        int value = random.nextInt(5);

        if (value == 1) {
            return new SumNode(generateRecursiveTree(cap, depth - 1), generateRecursiveTree(cap, depth - 1));
        } else if (value == 2) {
            return new SubNode(generateRecursiveTree(cap, depth - 1), generateRecursiveTree(cap, depth - 1));
        } else if (value == 3) {
            return new MultNode(generateRecursiveTree(cap, depth - 1), generateRecursiveTree(cap, depth - 1));
        } else {
            return new TerminalNode(random.nextInt(cap), null, null);
        }
    }

    /**
     * 随机选节点
     *
     * @param root
     * @return
     */
    private OperatorNode pickStop(OperatorNode root) {
        if (root.isTerminal()) {
            return root;
        }

        int decision = random.nextInt(3);
        if (decision == 0) {
            return pickStop(root.getLeft());
        } else if (decision == 1) {
            return root;
        } else {
            return pickStop(root.getRight());
        }
    }

    /**
     * 交叉 突变
     *
     * @param dad
     * @param mom
     * @param mutationRate
     * @param solution
     * @return
     */
    private OperatorNode crossOver(OperatorNode dad, OperatorNode mom, int mutationRate, int solution) {
        int mutation = random.nextInt(100), decision = random.nextInt(2);
        OperatorNode cloneDad = dad.cloneTree(), cloneMom = mom.cloneTree();
        OperatorNode dadStopPoint = pickStop(cloneDad), momStopPoint = pickStop(cloneMom);

        if (mutation > mutationRate) {
            if (decision == 0) {
                dadStopPoint.setLeft(momStopPoint);
            } else {
                dadStopPoint.setRight(momStopPoint);
            }
        } else {
            if (decision == 0) {
                dadStopPoint.setLeft(generateTree(solution, 1));
            } else {
                dadStopPoint.setRight(generateTree(solution, 1));
            }
        }

        return cloneDad;
    }

    public OperatorNode[] initiatePopulation(int size, int solution) {
        OperatorNode[] population = new Node[size];

        for (int i = 0; i < size; i++) {
            population[i] = generateTree(solution, 3);
            System.out.println(population[i].printContent());

        }

        return population;
    }

    /**
     * 计算fitness
     *
     * @param population
     * @param solution
     * @return
     */
    public boolean evaluateFitness(OperatorNode[] population, int solution) {
        boolean sequenceFound = false;

        for (int i = 0; i < population.length; i++) {
            int result = population[i].operate();
            if (result == solution) {
                sequenceFound = true;
                System.out.println("Content of tree (in reverse order notation): " + population[i].printContent());
                System.out.println("result =  " + population[i].operate());


                //计算树的所有操作符节点
//                int size = population[i].countSize(population[i]);
//                System.out.println("size =  " + size);
                //找到指定序列的树
//                System.out.println("目标node： " + population[i].getNode(population[i], size, random.nextInt(size)).printContent());
                count = 0;
            }
            population[i].setFitness(Math.abs(result - solution));
//            population[i].setFitness((int) (Math.pow(result, 2) * -1 + solution));
        }

        return sequenceFound;
    }

    /**
     * 选择(没有实现轮盘赌（当前fitness无法准确反映优劣），四个一组对比选的)
     *
     * @param firstGen
     * @return
     */
    public OperatorNode tournamentSelection(OperatorNode[] firstGen) {
        OperatorNode[] tournament = new OperatorNode[4];
        OperatorNode finalistOne, finalistTwo;

        for (int i = 0; i < 4; i++) {
            tournament[i] = firstGen[random.nextInt(firstGen.length)];
        }

        if (tournament[0].getFitness() < tournament[1].getFitness()) {
            finalistOne = tournament[0];
        } else {
            finalistOne = tournament[1];
        }

        if (tournament[2].getFitness() < tournament[3].getFitness()) {
            finalistTwo = tournament[2];
        } else {
            finalistTwo = tournament[3];
        }

        if (finalistOne.getFitness() < finalistTwo.getFitness()) {
            return finalistOne;
        } else {
            return finalistTwo;
        }
    }


    /**
     * 繁殖
     *
     * @param fittest
     * @param size
     * @param solution
     * @return
     */
    public OperatorNode[] reproduction(OperatorNode[] fittest, int size, int solution) {
        OperatorNode[] breed = new OperatorNode[size];
        OperatorNode dad, mom;
        int filled = 0;

        if (fittest[0].getFitness() < fittest[1].getFitness()) {
            dad = fittest[0];
            mom = fittest[1];
        } else {
            mom = fittest[0];
            dad = fittest[1];
        }

        while (filled < size) {
            breed[filled++] = crossOver(dad, mom, 5, solution);
        }

        return breed;
    }

    /**
     *
     * @param size 初始族群大小
     * @param solution 目标
     * @param initialPopulation
     */
    private void geneticAlgorithm(int size, int solution, OperatorNode[] initialPopulation) {
        OperatorNode[] population;
        if (initialPopulation == null)
            population = initiatePopulation(size, solution);
        else population = initialPopulation;

        if (evaluateFitness(population, solution)) {
            return;
        } else {
            OperatorNode[] fittest = new OperatorNode[2];
            fittest[0] = tournamentSelection(population);
            fittest[1] = tournamentSelection(population);
            OperatorNode[] breed = reproduction(fittest, size, solution);
            ++this.totalGenerations;
            geneticAlgorithm(size, solution, breed);
        }
    }

    private int getNumberOfGenerations() {
        return this.totalGenerations;
    }

    public static void main(String[] args) {
        long totalStartTime = System.currentTimeMillis();

        NumberGuessing gp = new NumberGuessing();

        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();
        int example = 567;

        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(200, example, null);
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());
            gp.totalGenerations = 1;
        }
        System.out.println("Time: " + timeList);
        System.out.println("Number of generations : " + generationList);

        long totalStopTime = System.currentTimeMillis();

        System.out.println("total time= " + (totalStopTime - totalStartTime));



        Long total = Long.valueOf(0);
        for (Long time :timeList) {
            total +=time;
        }
        System.out.println("时间均值："+ total/ timeList.size());

        Long total1 = Long.valueOf(0);
        for (Integer generation :generationList) {
            total1 +=generation;
        }
        System.out.println("迭代数均值："+ total1/ generationList.size());


    }

}





