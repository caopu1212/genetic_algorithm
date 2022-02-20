package src.通过输入输出求公式.gp;

import src.通过输入输出求公式.Elements.FileData;
import src.通过输入输出求公式.tools.fileOperator;
import src.通过输入输出求公式.tree.Node;
import src.通过输入输出求公式.tree.OperatorNode;
import src.通过输入输出求公式.tree.TerminalX0;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * <p>
 * 通过入参拟合多项式
 * 树结构：
 * 操作符：+，-，*...
 * 变量：x
 */

public class BaseGenetic_多维数据集 {
    public int totalGenerations = 1;
    public Random random = new Random();
    public static ArrayList<ArrayList<Double>> betterChaoticMapping = new ArrayList<>();
    //key：x
    //value：结果
//    public static HashMap<Double, Double> inputValue = new HashMap<>();
    public static ArrayList<FileData> inputValue = new ArrayList<>();
    public static Double min = 100000000.0;

    public static OperatorNode bestIndividual;

    private static List<Class<? extends Node>> nodeClassList = Node.getNodeClassList();

    /**
     * 获得一维混沌映射
     *
     * @param amountOfChaosMapping
     * @return mapping list
     */
    public ArrayList chaosMapping(int amountOfChaosMapping) {
        double μ = 4;
        ArrayList mappingList = new ArrayList();
        for (int j = 0; j < amountOfChaosMapping; j++) {
            ArrayList subList = new ArrayList();
            Random random = new Random();
            double x = random.nextDouble();
            for (int i = 0; i < 100; i++) {
                x = x * μ * (1 - x);
                subList.add(x);
            }
            mappingList.add(subList);
        }
        return mappingList;
    }

    public void evaluateChaoticFactors(int amountOfChaosMapping, int amountOfTree, int amountOfChaosFactor, int sizeOfTree) {

        ArrayList mappingList = chaosMapping(amountOfChaosMapping);
        ArrayList weightForOperatorList = new ArrayList();

        Double count = 0.0;
        // 遍历生成的logistic mapping，随机取一定数量的数据作为操作符的权重（之后按顺序分配给运算符的概率）
        for (Object mapping : mappingList) {
            System.out.println("part1 训练进度：" + count++ / amountOfChaosMapping * 100 + "%");

            ArrayList probabilityList = new ArrayList();
            ArrayList temp = (ArrayList) mapping;
            //随机取值
//            for (int i = 0; i < 3; i++) {
//                probabilityList.add(temp.get(random.nextInt(temp.size())));
//            }
            //前n个值
            for (int i = 0; i < nodeClassList.size(); i++) {
                probabilityList.add(temp.get(i));
            }
//            for (int i = 0; i < 4; i++) {
//                probabilityList.add(temp.get(i));
//            }


            weightForOperatorList.add(probabilityList);
        }

        BaseGenetic_多维数据集 chaotic = new BaseGenetic_多维数据集();
        ArrayList chaoticList = weightForOperatorList;
        HashMap<ArrayList, ArrayList> chaoticMap = new HashMap<>();
        count = 0.0;
        //对不同的映射进行amountOfTree次树生成，看平均值
        for (Object probabilityList_ : chaoticList) {
            System.out.println("part2 训练进度：" + count++ / amountOfChaosMapping * 100 + "%");


            ArrayList<OperatorNode> chaoticPopulationList = new ArrayList();
            ArrayList probabilityList = (ArrayList) probabilityList_;
            for (int i = 0; i < amountOfTree; i++) {
                OperatorNode chaoticPopulation = chaotic.generateTree(sizeOfTree, probabilityList);
                System.out.println(chaoticPopulation.printContent());
                chaotic.evaluateFitnessChaotic(chaoticPopulation);


//                System.out.println(chaoticPopulation.printContent());
//                System.out.println("result = " + chaoticPopulation.operate());
//                System.out.println("fitness = " + chaoticPopulation.getFitness());


                chaoticPopulationList.add(chaoticPopulation);
            }
            chaoticMap.put(probabilityList, chaoticPopulationList);
        }

        // 算平均fitness,保存最好的n个到全局变量
        Iterator iter = chaoticMap.keySet().iterator();
        ArrayList<Double> bestFitessList = new ArrayList();
        Double totalAverage = 0.0;
        while (iter.hasNext()) {
            Object key = iter.next();
            Object val = chaoticMap.get(key);
            ArrayList<OperatorNode> chaoticPopulationList = (ArrayList<OperatorNode>) val;

            Double average = chaotic.calculateAverageFitness(chaoticPopulationList);
//            System.out.println("average = " + average);
            totalAverage = totalAverage + average;
//            System.out.println(totalAverage);


            int size = amountOfChaosFactor;
            if (bestFitessList.size() != size) {
                bestFitessList.add(average);
                betterChaoticMapping.add((ArrayList<Double>) key);
            } else {
                for (int i = 0; i < bestFitessList.size(); i++) {
                    if (average < bestFitessList.get(i)) {
                        //替換相應位置元素
                        bestFitessList.set(i, average);
                        betterChaoticMapping.set(i, (ArrayList<Double>) key);
                        break;
                    }
                }
            }
        }

        System.out.println("平均平均值为：" + totalAverage / amountOfChaosMapping);
        System.out.println("bestFitessList : " + bestFitessList);
    }


    //Generate base of the tree, avoids generating a single node
    public OperatorNode generateTree(int depth, ArrayList probabilityList) {
        //将混沌映射n个一组遍历分配给运算符，并进行轮盘赌计算选择概率
//        for (Object probabilityList_ : chaoticList) {
        ArrayList<Double> tempList = new ArrayList();
        for (int i = 0; i < probabilityList.size(); i++) {
//            tempList.add((Double) probabilityList.get(random.nextInt(probabilityList.size())));
            tempList.add((Double) probabilityList.get(i));
//            if (tempList.size() == 4) {
//                break;
//            }
        }
//                Double total = tempList.get(0) + tempList.get(1) + tempList.get(2) + tempList.get(3);
        Double total = tempList.stream().mapToDouble(a -> a).sum();
        //累计概率
        Double cumulativeCount = 0.0;
        ArrayList<Double> individualProbability = new ArrayList();
        for (int i = 0; i < tempList.size(); i++) {
            individualProbability.add(tempList.get(i) / total);
        }


        ArrayList<Double> cumulativeProbability = new ArrayList<>();


        //添加累计概率list
        for (int j = 0; j < individualProbability.size(); j++) {
            cumulativeCount = cumulativeCount + individualProbability.get(j);
            cumulativeProbability.add(cumulativeCount);
        }
        Double value = random.nextDouble();

        //轮盘赌,按权重选择操作符
        return generateNewNode(value, cumulativeProbability, depth);
    }

    //Generate rest of the tree

    public OperatorNode generateRecursiveTree(int depth, ArrayList<Double> cumulativeProbability) {
        //特征数量
        int featureSize = inputValue.get(0).getFeatures().size();

        int mark = random.nextInt(featureSize);
        if (depth == 0) {
            return new TerminalX0(mark);
        }

        //   为terminal node的概率为1/4
        int valueOfTerminate = random.nextInt(3);
        if (valueOfTerminate == 0) {
            return new TerminalX0(mark);
        } else {
            //轮盘赌 +-* sin
            Double value = random.nextDouble();
            return generateNewNode(value, cumulativeProbability, depth);
        }
    }

    public Node generateNewNode(Double value, ArrayList<Double> cumulativeProbability, int depth) {
        int i;
        for (i = 0; i < cumulativeProbability.size(); i++) {
            if (value < cumulativeProbability.get(i))
                break;
        }

        Constructor con = nodeClassList.get(i).getDeclaredConstructors()[0];
        try {
            OperatorNode leftNode = generateRecursiveTree(depth - 1, cumulativeProbability);
            return (Node) con.newInstance(con.getParameterCount() > 1 ? Arrays.asList(leftNode, generateRecursiveTree(depth - 1, cumulativeProbability)).toArray() : Arrays.asList(leftNode).toArray());
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 将树的变量替换为目标输入
     *
     * @param root
     */
    public void parse(OperatorNode root, ArrayList<Double> replacementList) {
        boolean leftIsTermial = root.getLeft().isTerminal();


        boolean rightIsTermial = false;
        if (root.getRight() != null) {
            rightIsTermial = root.getRight().isTerminal();
        }
        if (leftIsTermial && rightIsTermial) {

            int leftMark = root.getLeft().getMark();
            double leftMarkReplacement = replacementList.get(leftMark);
            root.setLeft(new TerminalX0(leftMarkReplacement, leftMark));

            int rightMark = root.getRight().getMark();
            double rightMarkReplacement = replacementList.get(rightMark);
            root.setRight(new TerminalX0(rightMarkReplacement, rightMark));

        } else if (leftIsTermial && root.getRight() == null) {
            int leftMark = root.getLeft().getMark();
            double leftMarkReplacement = replacementList.get(leftMark);
            root.setLeft(new TerminalX0(leftMarkReplacement, leftMark));
            root.setRight(null);
        } else if (!leftIsTermial && root.getRight() == null) {
            parse(root.getLeft(), replacementList);
            root.setRight(null);
        } else if (leftIsTermial && !rightIsTermial) {
            int leftMark = root.getLeft().getMark();
            double leftMarkReplacement = replacementList.get(leftMark);
            root.setLeft(new TerminalX0(leftMarkReplacement, leftMark));
            parse(root.getRight(), replacementList);
        } else if (rightIsTermial && !leftIsTermial) {
            int rightMark = root.getRight().getMark();
            double rightMarkReplacement = replacementList.get(rightMark);
            root.setRight(new TerminalX0(rightMarkReplacement, rightMark));
            parse(root.getLeft(), replacementList);
        } else {
            parse(root.getLeft(), replacementList);
            parse(root.getRight(), replacementList);
        }
    }

    public Double evaluate(OperatorNode clone) {
        Double evaluation = 0.0;
        Double totalInput = Double.valueOf(inputValue.size());
        for (FileData fileData : inputValue) {
//            int randomIndex = this.random.nextInt(inputValue.size());

            //将树的变量替换为目标输入
            parse(clone, fileData.getFeatures());



//            最小二乘
            evaluation = evaluation + Math.pow(clone.operate() - fileData.getIndependentVariable(), 2);

        }


//        for (Map.Entry<Double, Double> input : inputValue.entrySet()) {
//            //将树的变量替换为目标输入
//            parse(clone, input.getKey());
//            //方案一：每对一个 总数-1
////            if (clone.operate() == input.getValue()) {
////                totalInput = totalInput - 1;
////           }
////            //方案二; 当前input - 目标结果的绝对值，相加，
////            evaluation = evaluation + Math.abs(clone.operate() - input.getValue());
//
////            方案三; 最小二乘
//            evaluation = evaluation + Math.pow(clone.operate() - input.getValue(), 2);
//        }
//
////        evaluation = totalInput;

        return evaluation;
    }

    public OperatorNode pickStop(OperatorNode root) {


        if (root.isTerminal()) {
            return root;
        }
        if (root.getRight() == null) {
            int decision = random.nextInt(2);
            if (decision == 0) {
                return pickStop(root.getLeft());
            } else if (decision == 1) {
                return root;
            }
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
            int index = random.nextInt(betterChaoticMapping.size());
            if (decision == 0) {
                dadStopPoint.setLeft(generateTree(1, betterChaoticMapping.get(index)));
            } else {
                dadStopPoint.setRight(generateTree(1, betterChaoticMapping.get(index)));
            }
        }

        return cloneDad;
    }

    public OperatorNode[] initiatePopulation(int size, int depth) {
        OperatorNode[] population = new Node[size];

        for (int i = 0; i < size; i++) {
            population[i] = generateTree(depth, betterChaoticMapping.get(random.nextInt(betterChaoticMapping.size())));
            System.out.println("new population: " + population[i].printContent());
        }

        return population;
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
     * @return
     */
    public void evaluateFitnessChaotic(OperatorNode population) {
        OperatorNode clone = population.cloneTree();
        Double result = evaluate(clone);
        population.setFitness(Math.abs(result));
    }


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
                System.out.println(population[i].printContent());
            }

        }


        return sequenceFound;
    }


    /**
     * 初始化 验证入参
     */
    public void initializeSolution() {
        inputValue = new fileOperator().readFile();
    }

    //There must be a population of at least 4 for this to work
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

    public OperatorNode[] reproduction(OperatorNode[] population, int size, int mutationRate) {
        OperatorNode[] breed = new OperatorNode[size];
        int filled = 0;

        while (filled < size) {

            //选择
            OperatorNode[] individuals = new OperatorNode[2];
            individuals[0] = tournamentSelection(population);
            individuals[1] = tournamentSelection(population);

            breed[filled++] = crossOver(individuals[0], individuals[1], mutationRate);
        }

        return breed;
    }

    public void geneticAlgorithm(int size, int depth, OperatorNode[] initialPopulation, int mutationRate) {

        if (totalGenerations % 50 == 0) {
            System.out.println("At " + totalGenerations + " gens");
            System.out.println("最优fitness = " + min);
//            System.out.println(initialPopulation[0].printContent());
        }

        if (totalGenerations == 100) {
            System.out.println("Solution not found :" + totalGenerations + " 次以内没找到啊啊啊啊啊");
            System.out.println("best case: "+bestIndividual.printContent());

//            System.out.println(initialPopulation[0].printContent());
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
            geneticAlgorithm(size, depth, breed, mutationRate);
        }
    }


    public int getNumberOfGenerations() {
        return this.totalGenerations;
    }


/**
 * test
 */

    // TODO: 2022/2/15  改成多维的
    // TODO: 2022/2/16  优化计算fitness的方法，用空间换时间计算多维多项式 
}
