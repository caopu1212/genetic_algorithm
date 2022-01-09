package src.求数字.gp;

import src.求数字.tree.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * logistic mapping
 * Xnew =Xμ（1-X），μ = 4， 随机选N个X初始值， 得到N个mapping（size为3，因为只有3个操作符）
 * <p>
 * 遍历所有mapping，针对每个mapping，使用轮盘赌选择，赋给不同操作符相应mapping的被选择概率
 * <p>
 * 通过如上方法为每一个mapping生成 一定数量的初始chaotic population，计算每一个mapping所生成的population的平均值
 * <p>
 * 将最好的W个mapping存为全局变量
 * <p>
 * 在正式进行GP时，对每一次生成 都随机选择存好的mapping进行生成
 */


class WithOutChaotic {
    public static int count = 0;
    private Random random = new Random();
    private int totalGenerations = 1;
    public static ArrayList<ArrayList<Double>> betterChaoticMapping = new ArrayList<>();

    private int getNumberOfGenerations() {
        return this.totalGenerations;
    }


    /**
     * 生成树
     *
     * @param cap
     * @param depth
     * @param probabilityList 单个混沌factor
     * @return
     */
    private OperatorNode generateTree(int cap, int depth, ArrayList probabilityList) {
        //将混沌映射三个一组遍历分配给运算符，并进行轮盘赌计算选择概率
//        for (Object probabilityList_ : chaoticList) {
        ArrayList<Double> tempList = new ArrayList();
        for (int i = 0; i < probabilityList.size(); i++) {
//            tempList.add((Double) probabilityList.get(random.nextInt(probabilityList.size())));
            tempList.add((Double) probabilityList.get(i));
            if (tempList.size() == 3) {
                Double total = tempList.get(0) + tempList.get(1) + tempList.get(2);

                //累计概率
                Double cumulativeCount = 0.0;
                ArrayList<Double> individualProbability = new ArrayList();
                // 加减乘
                individualProbability.add(tempList.get(0) / total);
                individualProbability.add(tempList.get(1) / total);
                individualProbability.add(tempList.get(2) / total);

                ArrayList<Double> cumulativeProbability = new ArrayList<>();

                //添加累计概率list
                for (int j = 0; j < individualProbability.size(); j++) {
                    cumulativeCount = cumulativeCount + individualProbability.get(j);
                    cumulativeProbability.add(cumulativeCount);
                }
                Double value = random.nextDouble();

                //轮盘赌,按权重选择操作符
                for (int j = 0; j < cumulativeProbability.size(); j++) {
                    if (value < cumulativeProbability.get(0)) {
                        return new SumNode(generateRecursiveTree(cap, depth, cumulativeProbability), generateRecursiveTree(cap, depth, cumulativeProbability));
                    } else if (value < cumulativeProbability.get(1)) {
                        return new SubNode(generateRecursiveTree(cap, depth, cumulativeProbability), generateRecursiveTree(cap, depth, cumulativeProbability));
                    } else if (value < cumulativeProbability.get(2)) {
                        return new MultNode(generateRecursiveTree(cap, depth, cumulativeProbability), generateRecursiveTree(cap, depth, cumulativeProbability));
                    }
                }
                tempList = null;
            }
        }
        return null;

    }

    /**
     * 生成剩下节点
     *
     * @param cap
     * @param depth
     * @param cumulativeProbability 轮盘赌的累计概率
     * @return
     */
    private OperatorNode generateRecursiveTree(int cap, int depth, ArrayList<Double> cumulativeProbability) {
        if (depth == 0) {
            return new TerminalNode(random.nextInt(cap), null, null);
        }

        int valueOfTerminate = random.nextInt(4);

        //有四分之一概率为末节点
        if (valueOfTerminate == 0) {
            return new TerminalNode(random.nextInt(cap), null, null);
        } else {
            //轮盘赌
            Double value = random.nextDouble();
            for (int j = 0; j < cumulativeProbability.size(); j++) {
                if (value < cumulativeProbability.get(0)) {
                    return new SumNode(generateRecursiveTree(cap, depth - 1, cumulativeProbability), generateRecursiveTree(cap, depth - 1, cumulativeProbability));
                } else if (value < cumulativeProbability.get(1)) {
                    return new SubNode(generateRecursiveTree(cap, depth - 1, cumulativeProbability), generateRecursiveTree(cap, depth - 1, cumulativeProbability));
                } else if (value < cumulativeProbability.get(2)) {
                    return new MultNode(generateRecursiveTree(cap, depth - 1, cumulativeProbability), generateRecursiveTree(cap, depth - 1, cumulativeProbability));
                }
            }
        }
        return null;
    }


    /**
     * 随机取logist mapping 的初值， μ = 4（完全混沌）。 随机从mapping 中选3个作为操作符的权重，
     * 通过轮盘赌算法找到每个被选的概率。
     * <p>
     *
     * @return: 不同映射的Arraylist[每个映射的ArrayList[]]
     */
    public ArrayList getChaoticFactor() {

        double μ = 4;
        ArrayList mappingList = new ArrayList();
        for (int j = 0; j < 10; j++) {
            ArrayList subList = new ArrayList();

            for (int i = 0; i <= 100; i++) {
                Random random = new Random();
                double x = random.nextDouble();
//                x = x * μ * (1 - x);
                subList.add(x);

            }
            mappingList.add(subList);
        }
        ArrayList resultList = new ArrayList();

        // 遍历生成的logistic mapping， 对每个映射随机取30个元素（之后按顺序分配给运算符的概率）
        for (Object mapping : mappingList) {
            ArrayList probabilityList = new ArrayList();
            ArrayList temp = (ArrayList) mapping;
            for (int i = 0; i < 3; i++) {
                probabilityList.add(temp.get(random.nextInt(100)));
            }
            resultList.add(probabilityList);
        }
        System.out.println(resultList);
        return resultList;
    }

    /**
     * 算平均fitness
     *
     * @return
     */
    public Double calculateAverageFitness(ArrayList<OperatorNode> chaoticPopulationList) {
        Double total = 0.;
        ArrayList fitnessList = new ArrayList();
        for (OperatorNode chaoticPopulation : chaoticPopulationList
        ) {
            total = total + chaoticPopulation.getFitness();
            fitnessList.add(chaoticPopulation.getFitness());
        }
        return total / chaoticPopulationList.size();
    }

    /**
     * 计算fitness
     *
     * @param population
     * @param solution
     * @return
     */
    public void evaluateFitnessChaotic(OperatorNode population, int solution) {
        int result = population.operate();

        //绝对值越小，离目标越近
        population.setFitness(Math.abs(result - solution));
//        population.setFitness((int) (Math.pow(result, 2) * -1 + solution));

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
     * 生成全局混沌factor
     */
    public void evaluateChaoticFactors() {
        WithOutChaotic withOutChaotic = new WithOutChaotic();
        //生成混沌映射
        ArrayList chaoticList = withOutChaotic.getChaoticFactor();
        HashMap<ArrayList, ArrayList> chaoticMap = new HashMap<>();
        //对不同的映射进行50次，看平均值
        for (Object probabilityList_ : chaoticList) {
            ArrayList<OperatorNode> chaoticPopulationList = new ArrayList();
            ArrayList probabilityList = (ArrayList) probabilityList_;
            for (int i = 0; i < 100; i++) {
                OperatorNode chaoticPopulation = withOutChaotic.generateTree(10, 3, probabilityList);
                withOutChaotic.evaluateFitnessChaotic(chaoticPopulation, 10);

//                System.out.println(chaoticPopulation.printContent());
//                System.out.println("result = " + chaoticPopulation.operate());
//                System.out.println("fitness = " + chaoticPopulation.getFitness());

                chaoticPopulationList.add(chaoticPopulation);
            }
            chaoticMap.put(probabilityList, chaoticPopulationList);
        }

        // 算平均fitness,保存最好的3个到全局变量
        Iterator iter = chaoticMap.keySet().iterator();
        Double bestFitness = 1000000.;
        while (iter.hasNext()) {
            Object key = iter.next();
            Object val = chaoticMap.get(key);
            ArrayList<OperatorNode> chaoticPopulationList = (ArrayList<OperatorNode>) val;

            Double average = withOutChaotic.calculateAverageFitness(chaoticPopulationList);
//            System.out.println("average = " + average);

            //fitness越接近0越符合
            if (average <= bestFitness) {
                //若已满3个，则删掉最小那个（index = 3）
                if (betterChaoticMapping.size() == 3) {
                    bestFitness = average;
                    betterChaoticMapping.add(0, (ArrayList<Double>) key);
                    betterChaoticMapping.remove(3);
                } else {
                    bestFitness = average;
                    betterChaoticMapping.add((ArrayList<Double>) key);
                }
            }
        }
        System.out.println("123");
    }

    public OperatorNode[] initiatePopulation(int size, int solution) {
        OperatorNode[] population = new Node[size];

        for (int i = 0; i < size; i++) {
            //随机选一种混沌mapping
            population[i] = generateTree(solution, 3, betterChaoticMapping.get(random.nextInt(betterChaoticMapping.size())));
            System.out.println("new population: " + population[i].printContent());

        }

        return population;
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
     * 交叉
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
            int index = random.nextInt(betterChaoticMapping.size());
            if (decision == 0) {
                dadStopPoint.setLeft(generateTree(solution, 1, betterChaoticMapping.get(index)));
            } else {
                dadStopPoint.setRight(generateTree(solution, 1, betterChaoticMapping.get(index)));
            }
        }

        return cloneDad;
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

    public static void main(String[] args) {
        long totalStartTime = System.currentTimeMillis();
        WithOutChaotic withOutChaotic = new WithOutChaotic();
        withOutChaotic.evaluateChaoticFactors();


        ArrayList timeList = new ArrayList();
        ArrayList generationList = new ArrayList();

        for (int i = 0; i < 1; i++) {
            long startTime = System.currentTimeMillis();
            withOutChaotic.geneticAlgorithm(200, 5670, null);
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + withOutChaotic.getNumberOfGenerations());

            timeList.add(stopTime - startTime);
            generationList.add(withOutChaotic.getNumberOfGenerations());
            withOutChaotic.totalGenerations = 1;
        }
        System.out.println("Time: " + timeList);
        System.out.println("Number of generations : " + generationList);
        long totalStopTime = System.currentTimeMillis();

        System.out.println("total time= " + (totalStopTime - totalStartTime));
    }

}

