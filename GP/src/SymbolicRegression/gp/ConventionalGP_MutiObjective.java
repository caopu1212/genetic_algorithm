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

public class ConventionalGP_MutiObjective extends BaseGenetic_MutiDimensionData {


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
//            OperatorNode clone = population[i].cloneTree();
            OperatorNode clone = population[i];
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

            //保存计算所用时间
            clone.setTimeUsage(runingTime);
            timeAll = timeAll + runingTime;

//            最小二乘
            double answer = Math.pow(fileData.getIndependentVariable() - reuslt, 2);

            evaluation = evaluation + answer;
        }

        //保存节点数
        int amountOfSubnodes = clone.countSubnodes(clone);
        clone.setAmountOfSubnodes(amountOfSubnodes);

        //三个目标分别是性能, 节点数 , 公式运行时间
        double[] values = {evaluation, amountOfSubnodes, timeAll};

        //归一化
//        values = normalize(values);





        //赋予权重
        double[] weights = {0.5, 2, 0.0005};
//        double[] weights = {0.5, 0.3, 0.02};
//        double[] weights = {0.9, 0.05, 0.05};


        double[] values1 = {10000, 1000, 10};

//        double[] normalizedValues = normalize1(values);

        double weightedValue = linearWeighting(values, weights);
//        System.out.println("Weighted value = " + weightedValue);
//
        totalFitnessCalculatedTimes += 1;

        return weightedValue;
    }


        public static double[] normalize3 ( double[] data){
            // 计算最小值和最大值
            double minVal = Math.min(data[0], Math.min(data[1], data[2]));
            double maxVal = Math.max(data[0], Math.max(data[1], data[2]));

            // 计算范围
            double rangeVal = maxVal - minVal;

            double[] result = new double[3];

            for (int i = 0; i < data.length; i++) {

                if (data[i] == minVal) {
                    result[i] = (data[i] - minVal + Double.MIN_VALUE) / rangeVal;
                } else {
                    result[i] = (data[i] - minVal) / rangeVal;
                }
            }
            return result;

        }

        public static double[] normalize2 ( double[] data){

            double max = Math.max(data[0], Math.max(data[1], data[2]));

            return Arrays.stream(data).map(x -> 10000 * (x / max)).toArray();


        }


        public static double[] normalize1 ( double[] data){
            for (int i = 0; i < data.length; i++) {
                data[i] = 100000000 * (Math.atan(data[i]) * 2 / Math.PI);
            }
            return data;
        }


        public static double[] normalize ( double[] data){
            double[] normalizedData = new double[data.length];

            // 计算均值
            double mean = Arrays.stream(data).average().orElse(0.0);

            // 计算标准差
            double standardDeviation = Math.sqrt(Arrays.stream(data)
                    .map(x -> Math.pow(x - mean, 2))
                    .average().orElse(0.0));

            // 标准化数据
            for (int i = 0; i < data.length; i++) {
                normalizedData[i] = (data[i] - mean) / standardDeviation;
            }

            return normalizedData;
        }


        public static double linearWeighting ( double[] values, double[] weights){
            if (values.length != weights.length || values.length == 0) {
                throw new IllegalArgumentException("Invalid arguments");
            }

            double weightedSum = 0;
            double weightSum = 0;

            for (int i = 0; i < values.length; i++) {

//            System.out.println( values[i] * weights[i]);

                weightedSum += values[i] * weights[i];
                weightSum += weights[i];
            }

            return weightedSum / weightSum;
        }


        public double getRSquare (OperatorNode node){
            return calculateRSquare(evaluateForRSquare(node));
        }


        public Double evaluateForRSquare (OperatorNode clone){
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
        public void geneticAlgorithm ( int size, int depth, OperatorNode[] initialPopulation,int mutationRate,
        int iteration) throws IOException, ClassNotFoundException {

//        dataRecord.add(calculateRSquare(min));

            if (totalGenerations % 50 == 0) {
                System.out.println("At " + totalGenerations + " gens");
                System.out.println("最优fitness = " + min);
                System.out.println("r^2 = " + getRSquare(bestIndividual));
                System.out.println("AmountOfSubnodes = " + bestIndividual.getAmountOfSubnodes());
                System.out.println("TimeUsage = " + bestIndividual.getTimeUsage());

                System.out.println(initialPopulation[0].printContent());
            }

            if (totalGenerations == iteration) {
                System.out.println("Solution not found :" + totalGenerations + " 次以内没找到啊啊啊啊啊");
                System.out.println("best case: " + bestIndividual.printContent());
//            PrintTree.print(bestIndividual);

                System.out.println("fitness = " + bestIndividual.getFitness());
                System.out.println("计算fitness的次数 = " + totalFitnessCalculatedTimes);
//            System.out.println(initialPopulation[0].printContent());

                System.out.println("AmountOfSubnodes = " + bestIndividual.getAmountOfSubnodes());
                System.out.println("TimeUsage = " + bestIndividual.getTimeUsage());
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
        public static void main (String[]args) throws ScriptException, IOException {

            ConventionalGP_MutiObjective gp = new ConventionalGP_MutiObjective();

            // 控制 目标公式，入参数量，参数区间
            gp.initializeSolution();

            long totalStartTime = System.currentTimeMillis();

            ArrayList<Long> timeList = new ArrayList();
            ArrayList<Integer> generationList = new ArrayList();
            ArrayList<Double> RSquareList = new ArrayList();

            ArrayList<Double> subNodesList = new ArrayList<>();

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

                System.out.println("AmountOfSubnodes = " + gp.bestIndividual.getAmountOfSubnodes());
                System.out.println("TimeUsage = " + gp.bestIndividual.getTimeUsage());

                timeList.add(stopTime - startTime);
                generationList.add(gp.getNumberOfGenerations());
                RSquareList.add(gp.getRSquare(gp.bestIndividual));

                subNodesList.add(gp.bestIndividual.getAmountOfSubnodes());


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
            System.out.println("节点数 =  " + subNodesList);


            long totalStopTime = System.currentTimeMillis();

            System.out.println("total time= " + (totalStopTime - totalStartTime));
            Long total = Long.valueOf(0);
            for (Long time : timeList) {
                total += time;
            }
            System.out.println("时间均值：" + total / timeList.size());

            System.out.println("节点数均值：" + subNodesList.stream().mapToDouble(x -> x).sum() / timeList.size());

            Long total1 = Long.valueOf(0);
            for (Integer generation : generationList) {
                total1 += generation;
            }
            System.out.println("迭代数均值：" + total1 / generationList.size());
//        System.out.println("chaos factors is ：" + betterChaoticMapping);

            System.out.println("r^2 均值 =  " + RSquareList.stream().mapToDouble(a -> a).sum() / RSquareList.size());
        }
    }
