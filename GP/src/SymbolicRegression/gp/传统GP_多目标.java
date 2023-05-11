package src.SymbolicRegression.gp;

import lombok.SneakyThrows;
import src.SymbolicRegression.Elements.FileData;
import src.SymbolicRegression.tree.Node;
import src.SymbolicRegression.tree.OperatorNode;

import javax.script.ScriptException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;

public class 传统GP_多目标 extends BaseGenetic_多维数据集 {


    @Override
    public OperatorNode generateTree(int depth, ArrayList probabilityList) {


        int value = random.nextInt(nodeClassList.size());
        ArrayList<Double> empty = new ArrayList<>();
        Constructor con = nodeClassList.get(value).getDeclaredConstructors()[0];
        try {
            OperatorNode leftNode = generateRecursiveTree(depth - 1, empty);
            return (Node) con.newInstance(con.getParameterCount() > 1 ? Arrays.asList(leftNode, generateRecursiveTree(depth - 1, empty)).toArray() : Arrays.asList(leftNode).toArray());
        } catch (Exception e) {
            return null;
        }
    }

    //Generate rest of the tree
    @Override
    public Node generateNewNode(Double value, ArrayList<Double> cumulativeProbability, int depth) {
        int i = random.nextInt(nodeClassList.size());

        Constructor con = nodeClassList.get(i).getDeclaredConstructors()[0];
        try {
            OperatorNode leftNode = generateRecursiveTree(depth - 1, cumulativeProbability);
            return (Node) con.newInstance(con.getParameterCount() > 1 ? Arrays.asList(leftNode, generateRecursiveTree(depth - 1, cumulativeProbability)).toArray() : Arrays.asList(leftNode).toArray());
        } catch (Exception e) {
            return null;
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
                dadStopPoint.setRight(momStopPoint);
            }
        } else {
            if (decision == 0) {
                dadStopPoint.setLeft(generateTree(1, null));
            } else {
                dadStopPoint.setRight(generateTree(1, null));
            }
        }
        return cloneDad;
    }

    //
    @Override
    public boolean evaluateFitness(OperatorNode[] population) {
        boolean sequenceFound = false;
        for (int i = 0; i < population.length; i++) {
            OperatorNode clone = population[i].cloneTree();
            Double result = evaluate(clone);
            if (result == 0) {
                sequenceFound = true;
                System.out.println("Content of tree (in reverse order): " + population[i].printContent());
            }

            population[i].setFitness(Math.abs(result));
            if (result < min) {
                min = result;
                System.out.println("更新最优: " + min);
                bestIndividual = population[i];
            }

        }
        return sequenceFound;
    }


    @Override
    public Double evaluate(OperatorNode clone) {
        Double evaluation = 0.0;
        Double timeAll = 0.0;
        Double totalInput = Double.valueOf(inputValue.size());
        for (FileData fileData : inputValue) {
//            int randomIndex = this.random.nextInt(inputValue.size());

            //将树的变量替换为目标输入
            parse(clone, fileData.getFeatures());


            // 记录时间作为一个目标
            long startTime = System.nanoTime();

            Double reuslt = clone.operate();
            long endTime = System.nanoTime();

            long runingTime = endTime - startTime;

            timeAll = timeAll + runingTime;

//            最小二乘
            double answer = Math.pow(fileData.getIndependentVariable() - reuslt, 2);

            evaluation = evaluation + answer;
        }



        //三个目标分别是性能, 节点数 , 运行时间
        double[] values = {evaluation, (double) clone.countSubnodes(clone), timeAll};

        //赋予权重
        double[] weights = {0.8, 0.1, 0.1};

        double weightedValue = linearWeighting(values, weights);
//        System.out.println("Weighted value = " + weightedValue);
//
        totalFitnessCalculatedTimes += 1;

        return weightedValue;
    }
    public static double linearWeighting(double[] values, double[] weights) {
        if (values.length != weights.length || values.length == 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        double weightedSum = 0;
        double weightSum = 0;

        for (int i = 0; i < values.length; i++) {
            weightedSum += values[i] * weights[i];
            weightSum += weights[i];
        }

        return weightedSum / weightSum;
    }


    public double getRSquare(OperatorNode node) {
        return calculateRSquare(evaluateForRSquare(node));
    }


    public Double evaluateForRSquare(OperatorNode clone) {
        Double evaluation = 0.0;
        Double totalInput = Double.valueOf(inputValue.size());
        for (FileData fileData : inputValue) {
            //将树的变量替换为目标输入
            parse(clone, fileData.getFeatures());
            double answer = Math.pow(fileData.getIndependentVariable() - clone.operate(), 2);
            evaluation = evaluation + answer;
        }
        return evaluation;
    }


    @Override
    public void geneticAlgorithm(int size, int depth, OperatorNode[] initialPopulation, int mutationRate, int iteration) throws IOException, ClassNotFoundException {

//        dataRecord.add(calculateRSquare(min));

        if (totalGenerations % 50 == 0) {
            System.out.println("At " + totalGenerations + " gens");
            System.out.println("最优fitness = " + min);
            System.out.println("r^2 = " + getRSquare(bestIndividual));
            System.out.println(initialPopulation[0].printContent());
        }

        if (totalGenerations == iteration) {
            System.out.println("Solution not found :" + totalGenerations + " 次以内没找到啊啊啊啊啊");
            System.out.println("best case: " + bestIndividual.printContent());
//            PrintTree.print(bestIndividual);

            System.out.println("fitness = " + bestIndividual.getFitness());
            System.out.println("计算fitness的次数 = " + totalFitnessCalculatedTimes);
//            System.out.println(initialPopulation[0].printContent());


//            inputValue = new FileOperator().readTestFile();
//            for (FileData fileData : inputValue) {
//                //将树的变量替换为目标输入
//                parse(bestIndividual, fileData.getFeatures());
//                System.out.println("预测值: " + bestIndividual.operate() + " label: " + fileData.getIndependentVariable());
//            }
            return;
        }
        OperatorNode[] population;
        if (initialPopulation == null) population = initiatePopulation(size, depth);
        else population = initialPopulation;

        if (evaluateFitness(population)) {

            return;
        } else {
            OperatorNode[] breed = reproduction(population, size, mutationRate);
            ++this.totalGenerations;
            geneticAlgorithm(size, depth, breed, mutationRate, iteration);
        }

    }

    @SneakyThrows
    public static void main(String[] args) throws ScriptException, IOException {

        传统GP_多目标 gp = new 传统GP_多目标();

        // 控制 目标公式，入参数量，参数区间
        gp.initializeSolution();

        long totalStartTime = System.currentTimeMillis();

        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();
        ArrayList<Double> RSquareList = new ArrayList();

        for (int i = 0; i < 1; i++) {
            gp.inputValue.clear();
            gp.initializeSolution();

            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(100, 3, null, 60, 100);

            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());
            System.out.println("r^2 =  " + gp.getRSquare(gp.bestIndividual));
//            System.out.println("adjusted r^2 =  " + gp.getAdjustedRSquare(gp.getRSquare()));

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());
            RSquareList.add(gp.getRSquare(gp.bestIndividual));

            ArrayList<Double> temp = gp.getDataRecord();


            //初始化
            gp.dataRecord = null;
            gp.totalGenerations = 1;
            gp.min = 1.7976931348623157E308;
            gp.bestIndividual = null;
            gp.totalFitnessCalculatedTimes = 0;
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
