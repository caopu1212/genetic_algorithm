package src.SymbolicRegression.TempCode;

import src.SymbolicRegression.tree.*;

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


public class 新杂交_传统GP {
    private int totalGenerations = 1;
    private Random random = new Random();
    public static ArrayList<ArrayList<Double>> betterChaoticMapping = new ArrayList<>();
    //key：x
    //value：结果
    public static HashMap<Double, Double> inputValue = new HashMap<>();

    public static Double min = 1.7976931348623157E308;

    public static OperatorNode bestIndividual;

    //Generate BaseGenetic_GaForPreProcessing of the tree, avoids generating a single node
    private OperatorNode generateTree(int depth) {
        int value = random.nextInt(3);

        if (value == 0) {
            return new AddNode(generateRecursiveTree(depth), generateRecursiveTree(depth));
        } else if (value == 1) {
            return new MinNode(generateRecursiveTree(depth), generateRecursiveTree(depth));
        } else {
            return new MultNode(generateRecursiveTree(depth), generateRecursiveTree(depth));
        }
    }

    //Generate rest of the tree
    private OperatorNode generateRecursiveTree(int depth) {
        if (depth == 0) {
            return new TerminalX1();
        }
        int value = random.nextInt(5);

        if (value == 1) {
            return new AddNode(generateRecursiveTree(depth - 1), generateRecursiveTree(depth - 1));
        } else if (value == 2) {
            return new MinNode(generateRecursiveTree(depth - 1), generateRecursiveTree(depth - 1));
        } else if (value == 3) {
            return new MultNode(generateRecursiveTree(depth - 1), generateRecursiveTree(depth - 1));
        } else {
            return new TerminalX1();
        }
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
//            evaluation = evaluation + Math.abs(clone.operate() - input.getValue());

//            方案三; 最小二乘
            evaluation = evaluation + Math.pow(clone.operate() - input.getValue(), 2);

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
        } else {    //Generate random subtree of depth 1
            if (decision == 0) {
                dadStopPoint.setLeft(generateTree(1));
            } else {
                dadStopPoint.setRight(generateTree(1));
            }
        }

        return cloneDad;
    }

    public OperatorNode[] initiatePopulation(int size) {
        OperatorNode[] population = new Node[size];

        for (int i = 0; i < size; i++) {
            population[i] = generateTree(3); //Depth of 3
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
                bestIndividual = population[i];
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

    public OperatorNode[] reproduction(OperatorNode[] population, int size) {
        OperatorNode[] breed = new OperatorNode[size];
        int filled = 0;

        while (filled < size) {

            //选择
            OperatorNode[] individuals = new OperatorNode[2];
            individuals[0] = tournamentSelection(population);
            individuals[1] = tournamentSelection(population);

            breed[filled++] = crossOver(individuals[0], individuals[1], 50);
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
            System.out.println("Solution not found :" + totalGenerations + " 次以内没找到啊啊啊啊啊");

//            System.out.println(initialPopulation[0].printContent());
            return;
        }

        OperatorNode[] population;
        if (initialPopulation == null) population = initiatePopulation(size);
        else population = initialPopulation;

        if (evaluateFitness(population, set)) {
            return;
        } else {


            OperatorNode[] breed = reproduction(population, size);
            ++this.totalGenerations;
            geneticAlgorithm(size, set, breed);
        }
    }


    private int getNumberOfGenerations() {
        return this.totalGenerations;
    }


    public static void main(String[] args) throws ScriptException {
        新杂交_传统GP gp = new 新杂交_传统GP();
        int[] example = {0, 0, 4};
//        String formula = "x*x*x*x*x*x*x*x*x*x*x*x*x*x*x";//x^15

//        String formula = "x*x*x*x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^yacht-train-0+x^7+x^6+x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x - x*x*x*x*x+x*x*x*x+x*x*x+x*x + x";//x^8+x^7+x^6+x^5+x^4 + x^3 + x^2 + x


//        String formula = "x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^7+x^6+x^5+x^4 + x^3 + x^2 + x
        String formula = "2*x*x*x*x*x*x*x+x*x*x*x*x*x-5*x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//2x^7+x^6-5x^5+x^4 + x^3 + x^2 + x
//
//        String formula = "x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^6+x^5+x^4 + x^3 + x^2 + x

//        String formula = "x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^5+x^4 + x^3 + x^2 + x
//        String formula = "(5*x*x*x*x*x)-(x*x*x*x)+(6*x*x*x)+(x*x)-(x)";//5x^5*-x^4 + 6x^3 + x^2 - x

//        String formula = "x*x*x*x+x*x*x+x*x+x";//x^4 + x^3 + x^2 + x
//        String formula = "15*x*x*x*x+20*x*x*x-120*x*x+200*x";//15x^4 + 20x^3 -120x^2 + 200x


//        String formula = "x*x*x+x*x+x";//x^3 + x^2 + x
//        String formula = "(x*x*x)+(x*x*2)+5*x";//x^3 + 2x^2 + 5x
//        String formula = "(x*x*2)+3*x";// 2x^2 + 3x

        // 控制 目标公式，入参数量，参数区间
        gp.initializeSolution(formula, 20, -50.0, 50.0);

        long totalStartTime = System.currentTimeMillis();


        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();

        for (int i = 0; i < 10; i++) {
            gp.inputValue.clear();
            gp.initializeSolution(formula, 20, -50.0, 50.0);

            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(200, example, null);
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());

            //初始化
            gp.totalGenerations = 1;

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
// TODO: 2021/12/18  重写杂交方法！


}
