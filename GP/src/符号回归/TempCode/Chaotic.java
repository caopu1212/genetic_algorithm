package src.符号回归.TempCode;

import src.符号回归.tree.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * Given a function p(x), find h(x) such as p(x) = h(x) for x in [-1,1].
 * Will only test for x = 0, x = 1, x = -1.
 * <p>
 * 对于p(x), 通过给定一定范围内一定数量x的结果， 来找符合的公式。
 * 树结构：
 * 操作符：+，-，*
 * 变量：x
 */

/**
 *
 *
 */


public class Chaotic {
    private int totalGenerations = 1;
    private Random random = new Random();
    public static ArrayList<ArrayList<Double>> betterChaoticMapping = new ArrayList<>();
    //key：x
    //value：结果
    public static HashMap<Double, Double> inputValue = new HashMap<>();

    public static Double min = 1.7976931348623157E308;

    public static OperatorNode bestIndividual;


    public void evaluateChaoticFactors(int amountOfChaosMapping, int amountOfTree, int amountOfChaosFactor,int sizeOfTree , int[] set){
        double μ = 4;
        ArrayList mappingList = new ArrayList();
        ArrayList mappingList_ = new ArrayList();
        for (int j = 0; j < amountOfChaosMapping; j++) {
            ArrayList subList = new ArrayList();
            Random random = new Random();
            double x = random.nextDouble();
            for (int i = 0; i < amountOfChaosMapping; i++) {
                x = x * μ * (1 - x);
                subList.add(x);
            }
            mappingList.add(subList);
        }

        ArrayList weightForOperatorList = new ArrayList();

        // 遍历生成的logistic mapping，随机取一定数量的数据作为操作符的权重（之后按顺序分配给运算符的概率）
        for (Object mapping : mappingList) {
            ArrayList probabilityList = new ArrayList();
            ArrayList temp = (ArrayList) mapping;
            for (int i = 0; i < 3; i++) {
                probabilityList.add(temp.get(random.nextInt(temp.size())));
            }
            weightForOperatorList.add(probabilityList);
        }

        Chaotic chaotic = new Chaotic();
        ArrayList chaoticList = weightForOperatorList;
        HashMap<ArrayList, ArrayList> chaoticMap = new HashMap<>();
        //对不同的映射进行amountOfTree次树生成，看平均值
        for (Object probabilityList_ : chaoticList) {
            ArrayList<OperatorNode> chaoticPopulationList = new ArrayList();
            ArrayList probabilityList = (ArrayList) probabilityList_;
            for (int i = 0; i < amountOfTree; i++) {
                OperatorNode chaoticPopulation = chaotic.generateTree(10, 3, probabilityList);

                chaotic.evaluateFitnessChaotic(chaoticPopulation, set);


//                System.out.println(chaoticPopulation.printContent());
//                System.out.println("result = " + chaoticPopulation.operate());
//                System.out.println("fitness = " + chaoticPopulation.getFitness());

                chaoticPopulationList.add(chaoticPopulation);
            }
            chaoticMap.put(probabilityList, chaoticPopulationList);
        }

        // 算平均fitness,保存最好的n个到全局变量
        Iterator iter = chaoticMap.keySet().iterator();
        Double bestFitness = 100000000000000.;
        ArrayList<Double> bestFitessList = new ArrayList();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object val = chaoticMap.get(key);
            ArrayList<OperatorNode> chaoticPopulationList = (ArrayList<OperatorNode>) val;

            Double average = chaotic.calculateAverageFitness(chaoticPopulationList);
//            System.out.println("average = " + average);


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
        System.out.println("bestFitessList : " + bestFitessList);
    }


    //Generate BaseGenetic_GaForPreProcessing of the tree, avoids generating a single node
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
                        return new AddNode(generateRecursiveTree(cap, depth, cumulativeProbability), generateRecursiveTree(cap, depth, cumulativeProbability));
                    } else if (value < cumulativeProbability.get(1)) {
                        return new MinNode(generateRecursiveTree(cap, depth, cumulativeProbability), generateRecursiveTree(cap, depth, cumulativeProbability));
                    } else if (value < cumulativeProbability.get(2)) {
                        return new MultNode(generateRecursiveTree(cap, depth, cumulativeProbability), generateRecursiveTree(cap, depth, cumulativeProbability));
                    }
                }
                tempList = null;
            }
        }
        return null;
    }

    //Generate rest of the tree
    private OperatorNode generateRecursiveTree(int cap, int depth, ArrayList<Double> cumulativeProbability) {
        if (depth == 0) {
            return new TerminalX1();
        }

        int valueOfTerminate = random.nextInt(4);

        //有四分之一概率为末节点
        if (valueOfTerminate == 0) {
            return new TerminalX1();
        } else {
            //轮盘赌
            Double value = random.nextDouble();
            for (int j = 0; j < cumulativeProbability.size(); j++) {
                if (value < cumulativeProbability.get(0)) {
                    return new AddNode(generateRecursiveTree(cap, depth - 1, cumulativeProbability), generateRecursiveTree(cap, depth - 1, cumulativeProbability));
                } else if (value < cumulativeProbability.get(1)) {
                    return new MinNode(generateRecursiveTree(cap, depth - 1, cumulativeProbability), generateRecursiveTree(cap, depth - 1, cumulativeProbability));
                } else if (value < cumulativeProbability.get(2)) {
                    return new MultNode(generateRecursiveTree(cap, depth - 1, cumulativeProbability), generateRecursiveTree(cap, depth - 1, cumulativeProbability));
                }
            }
        }
        return null;
    }

    /**
     * 将树的变量替换为目标输入
     *
     * @param root
     * @param replacement
     */
    private void parse(OperatorNode root, Double replacement) {
        if (root.getLeft().isTerminal() && root.getRight().isTerminal()) {
            root.setLeft(new TerminalX1(replacement));
            root.setRight(new TerminalX1(replacement));
        } else if (root.getLeft().isTerminal() && !root.getRight().isTerminal()) {
            root.setLeft(new TerminalX1(replacement));
            parse(root.getRight(), replacement);
        } else if (root.getRight().isTerminal() && !root.getLeft().isTerminal()) {
            root.setRight(new TerminalX1(replacement));
            parse(root.getLeft(), replacement);
        } else {
            parse(root.getLeft(), replacement);
            parse(root.getRight(), replacement);
        }
    }

    private Double evaluate(OperatorNode clone, int solutions[]) {
        Double evaluation = 0.0;
        Double totalInput = Double.valueOf(inputValue.size());
        for (Map.Entry<Double, Double> input : inputValue.entrySet()) {
            //将树的变量替换为目标输入
            parse(clone, input.getKey());
            //方案一：每对一个 总数-1
//

//            if (clone.operate() == input.getValue()) {
//                totalInput = totalInput - 1;
//           }
//            //方案二; 当前input - 目标结果的绝对值，相加，
            evaluation = evaluation + Math.abs(clone.operate() - input.getValue());
        }

//        evaluation = totalInput;

        return evaluation;
    }

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

    private OperatorNode crossOver(OperatorNode dad, OperatorNode mom, int mutationRate) {
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
                dadStopPoint.setLeft(generateTree(0, 1, betterChaoticMapping.get(index)));
            } else {
                dadStopPoint.setRight(generateTree(0, 1, betterChaoticMapping.get(index)));
            }
        }

        return cloneDad;
    }

    public OperatorNode[] initiatePopulation(int size) {
        OperatorNode[] population = new Node[size];

        for (int i = 0; i < size; i++) {
            population[i] = generateTree(0, 3, betterChaoticMapping.get(random.nextInt(betterChaoticMapping.size())));

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
     * @param set
     * @return
     */
    public void evaluateFitnessChaotic(OperatorNode population, int[] set) {
        OperatorNode clone = population.cloneTree();
        Double result = evaluate(clone, set);
        population.setFitness(Math.abs(result));
    }


    public boolean evaluateFitness(OperatorNode[] population, int[] set) {
        boolean sequenceFound = false;
        for (int i = 0; i < population.length; i++) {
            OperatorNode clone = population[i].cloneTree();
            Double result = evaluate(clone, set);
            if (result == 0) {
                sequenceFound = true;
                System.out.println("Content of tree (in reverse order): " + population[i].printContent());
            }

            population[i].setFitness(Math.abs(result));
            if (result < min) {
                min = result;
                System.out.println("更新最优: " + min);
                bestIndividual =  population[i];
            }

        }


        return sequenceFound;
    }


    /**
     * 初始化 验证入参
     *
     * @param formula 目标公式
     */
    public void initializeSolution(String formula, int amountOfValue, Double scopeStart, Double scopeEnd) throws ScriptException {
        //java6 脚本转化
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        ArrayList randomNumberList = new ArrayList();

        for (int i = 0; i < amountOfValue; i++) {
            //java只能生成0到1之间随机数，因此手动写范围
            //Math.random() * (Max - Min) + Min
//            String randomNum = String.valueOf(random.nextDouble() * (scopeEnd  - scopeStart)+ scopeStart);
            //保留1位小数
//            String randomNum = String.valueOf(String.format("%.1f", random.nextDouble() * (scopeEnd - scopeStart) + scopeStart));
            //保留整数
            String randomNum;
            //保证不出现重复入参
            while (true) {
                randomNum = String.valueOf(random.nextDouble() * (scopeEnd - scopeStart) + scopeStart);
                randomNum = randomNum.substring(0, randomNum.lastIndexOf(".")) + ".0";
                if (!randomNumberList.contains(randomNum)) {
                    randomNumberList.add(randomNum);
                    break;
                }
            }


            //将X 替换为范围内的一个随机参数
            String newFormula = formula.replace("x", randomNum);

            Object result1 = null;
            result1 = engine.eval(newFormula);
            //计算结果存为全局变量
            inputValue.put(Double.valueOf(randomNum), (Double) result1);
        }
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

    public OperatorNode[] reproduction(OperatorNode[] fittest, int size) {
        OperatorNode[] breed = new OperatorNode[size];
        OperatorNode dad, mom;


        if (fittest[0].getFitness() < fittest[1].getFitness()) {
            dad = fittest[0];
            mom = fittest[1];
        } else {
            mom = fittest[0];
            dad = fittest[1];
        }

        int filled = 0;

        while (filled < size) {
            breed[filled++] = crossOver(dad, mom, 5);
        }

        return breed;
    }

    private void geneticAlgorithm(int size, int[] set, OperatorNode[] initialPopulation) {

        if (totalGenerations % 50 == 0) {
            System.out.println("At " + totalGenerations + " gens");
            System.out.println("最优fitness = " + min);
//            System.out.println(initialPopulation[0].printContent());
        }

        if (totalGenerations == 100) {
            System.out.println("Solution not found :"+ totalGenerations + " 次以内没找到啊啊啊啊啊");

//            System.out.println(initialPopulation[0].printContent());
            return;
        }

        OperatorNode[] population;
        if (initialPopulation == null) population = initiatePopulation(size);
        else population = initialPopulation;

        if (evaluateFitness(population, set)) {
            return;
        } else {

            OperatorNode[] fittest = new OperatorNode[2];
//            fittest[0] = tournamentSelection(population);
            fittest[0] = bestIndividual;
            fittest[1] = tournamentSelection(population);
            OperatorNode[] breed = reproduction(fittest, size);
            ++this.totalGenerations;
            geneticAlgorithm(size, set, breed);
        }
    }


    private int getNumberOfGenerations() {
        return this.totalGenerations;
    }


    public static void main(String[] args) throws ScriptException {
        Chaotic gp = new Chaotic();
        //Function is p(x) = x^2 + x
        //p(-1) = 2
        //p(0) = 0
        //p(1) = 2
//        int[] example = {2, 0, 2};

        //Function is p(x) = x^2 + x + 1
        //p(-1) = 1
        //p(0) = 1
        //p(1) = 3
//         int[] example = {1, 1, 3};

        //Function is p(x) = x^2
        //p(-1) = 1
        //p(0) = 0
        //p(1) = 1
//         int[] example = {1,0,1};


        //Function is p(x) = x^4 + x^3 + x^2 + x
        //p(-1) = 0
        //p(0) = 0
        //p(1) = 4
        int[] example = {0, 0, 4};

//        String formula = "x^4 + x^3 + x^2 + x";

//        String formula = "x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^6+x^5+x^4 + x^3 + x^2 + x
        String formula = "x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^5+x^4 + x^3 + x^2 + x
//        String formula = "x*x*x*x+x*x*x+x*x+x";//x^4 + x^3 + x^2 + x
//        String formula = "x*x*x+x*x+x";//x^3 + x^2 + x
//        String formula = "(x*x*x)+(x*x*2)+5*x";//x^3 + 2x^2 + 5x
//        String formula = "(x*x*2)+3*x";// 2x^2 + 3x

        // 控制 目标公式，入参数量，参数区间
        gp.initializeSolution(formula, 5, -10.0, 10.0);

        long totalStartTime = System.currentTimeMillis();


//        for (int i = 0; i < 10; i++) {
        gp.evaluateChaoticFactors(5000, 10, 10,5, example);
//        }


        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();

        for (int i = 0; i < 20; i++) {
//            gp.evaluateChaoticFactors(50, 100, 10, example);
            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(200, example, null);
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());

            //初始化
            gp.totalGenerations = 1;
//            betterChaoticMapping = new ArrayList<>();

            gp.min = 1.7976931348623157E308;
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


    }


}
