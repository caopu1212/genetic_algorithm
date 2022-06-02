package src.通过输入输出求公式.gp;

import src.通过输入输出求公式.Elements.FileData;
import src.通过输入输出求公式.Elements.PreProcessing;
import src.通过输入输出求公式.tools.MyRandom;
import src.通过输入输出求公式.tree.Node;
import src.通过输入输出求公式.tree.OperatorNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BaseGenetic_GaForPreProcessing extends BaseGenetic_多维数据集 {
    public static MyRandom myRandom = new MyRandom();

    public static PreProcessing bestPreProcessing = new PreProcessing();


    public static int totalGenerations = 1;
    public Random random = new Random();
    public static ArrayList<PreProcessing> betterPreprocessing = new ArrayList<>();
    public static ArrayList<ArrayList<Double>> betterChaoticMapping = new ArrayList<>();
    //key：x
    //value：结果
//    public static HashMap<Double, Double> inputValue = new HashMap<>();
    public static ArrayList<FileData> inputValue = new ArrayList<>();


    public static Double min = 1.7976931348623157E308;

    public static OperatorNode bestIndividual = null;

    public static List<Class<? extends Node>> nodeClassList = Node.getNodeClassList();


    public void BaseGenetic_GaForPreProcessing() {

    }


    /**
     * 初始化数据 for GA_preprocess
     *
     * @param populationSize
     * @param sizeOfTree
     * @return 目标 size 的ArrayList<PreProcessing>
     */
    public ArrayList<PreProcessing> initializationsForPreProcessing(int populationSize, int sizeOfTree) {

        ArrayList mappingList = chaosMapping(populationSize);
        ArrayList weightForOperatorList = new ArrayList();
        ArrayList<PreProcessing> initializationPopulation = new ArrayList<>();

        Double count = 0.0;
        // 遍历生成的logistic mapping，随机取一定数量的数据作为操作符的权重（之后按顺序分配给运算符的概率）
        for (Object mapping : mappingList) {

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

            // 为映射添加fitness
            BaseGenetic_GaForPreProcessing baseGenetic_gaForPreProcessing = new BaseGenetic_GaForPreProcessing();
            initializationPopulation.add(baseGenetic_gaForPreProcessing.calculateFitnessForPreProcessing(probabilityList, sizeOfTree));


        }
        return initializationPopulation;
    }


    /**
     * 为预处理计算fitness 并转为preprocessing格式
     * <p>
     * param 需要计算fitness的一个mappinglist
     * param 树的size
     *
     * @return preprocessing(double fitness, arrayList mappingList)
     */
    public PreProcessing calculateFitnessForPreProcessing(ArrayList mappinglist, int sizeOfTree) {

        BaseGenetic_GaForPreProcessing chaotic = new BaseGenetic_GaForPreProcessing();

        // 使用目标混沌映射生成树
        OperatorNode chaoticPopulation = chaotic.generateFullTree(sizeOfTree, mappinglist);
        // 为生成的树计算fitness
        chaotic.evaluateFitnessChaotic(chaoticPopulation);
//                System.out.println(chaoticPopulation.printContent());
//                System.out.println("result = " + chaoticPopulation.operate());
//                System.out.println("fitness = " + chaoticPopulation.getFitness());

        PreProcessing result = new PreProcessing();
        result.setFitness(chaoticPopulation.getFitness());
        result.setMappingList(mappinglist);
        return result;
    }


    /**
     * 选择 for 预学习
     *
     * @param depth
     * @param oldGeneration * @return 新世代
     */
    public ArrayList<PreProcessing> selectionForPreprocessing(int amountOfBetterPreprocessing, int mutationRate, int depth, ArrayList<PreProcessing> oldGeneration) {


        ArrayList<PreProcessing> newGeneration = new ArrayList<>();

        BaseGenetic_GaForPreProcessing gp = new BaseGenetic_GaForPreProcessing();


        //方案一 : 竞标赛(Tournament ) size 4
//        while (true) {
//            // 若新世代数量达到一定量时候停止循环
//            if (newGeneration.size() == oldGeneration.size()) break;
//            PreProcessing father = pickTwoElementThenComparison(oldGeneration);
//            PreProcessing mother = pickTwoElementThenComparison(oldGeneration);
//            //交叉
//            newGeneration = crossoverForPreProcessing(amountOfBetterPreprocessing, depth, mutationRate, newGeneration, father, mother);
//        }

        //方案二 : 轮盘赌, 用fitness, 之后反向处理(因为绝对值越小越好)


        //赋值累加概率
        ArrayList<PreProcessing> assiginedProbabilityPreProcessingList = assignCumulativeProbability(amountOfBetterPreprocessing, oldGeneration);


        while (true) {
            // 若新世代数量达到一定量时候停止循环
            if (newGeneration.size() >= oldGeneration.size() / 2) break;
            //轮盘赌选父母
            PreProcessing father = gp.rouletteSelection(assiginedProbabilityPreProcessingList);
            PreProcessing mother = gp.rouletteSelection(assiginedProbabilityPreProcessingList);

//            System.out.println(father+"  "+ mother);
            //交叉
            newGeneration = crossoverForPreProcessing(depth, mutationRate, newGeneration, father, mother);
        }

        //用最好的填充新世代
        while (true) {
            // 若新世代满了后停止
            if (newGeneration.size() == oldGeneration.size()) break;

            newGeneration.add(bestPreProcessing);

        }


        //重新计算fitness, 替换原有fitness
//        newGeneration.forEach(preProcessing ->
//                preProcessing.setFitness(gp.calculateFitnessForPreProcessing(preProcessing.getMappingList(), depth).getFitness()));


        //根据fitness排序
//        newGeneration.sort(Comparator.comparingDouble(PreProcessing::getFitness));


        return newGeneration;
    }

    public PreProcessing rouletteSelection(ArrayList<PreProcessing> preProcessingArrayList) {
        double randomNum = random.nextDouble();
        for (PreProcessing preProcessing : preProcessingArrayList
        ) {


            if (preProcessing.getCumulativeProbability() <= randomNum) {
                continue;
            } else {
                return preProcessing;
            }
        }
        return null;

    }


    /**
     * 交叉 for 预学习
     *
     * @param
     * @param
     * @return 将子代添加进去了的新世代(算好fitness后的)
     */
    public ArrayList<PreProcessing> crossoverForPreProcessing(int depth, int mutationRate, ArrayList<PreProcessing> newGeneration, PreProcessing father, PreProcessing mother) {
        BaseGenetic_GaForPreProcessing chaotic = new BaseGenetic_GaForPreProcessing();

        //随机选一个操作符, 交换父母的这个操作符的权重.
        int tempIndex = random.nextInt(father.getMappingList().size());
//        Double temp =(Double) father.getMappingList().get(tempIndex);


        //深拷贝
        PreProcessing son1 = new PreProcessing();
        son1.setMappingList(new ArrayList<>(father.getMappingList()));

        PreProcessing son2 = new PreProcessing();
        son2.setMappingList(new ArrayList<>(mother.getMappingList()));


        //变异, roll一个数,
        if (random.nextInt(100) <= mutationRate) {
            //若比变异率参数小, 则将一个子代的相应操作符的权重随机一个数 (用当前随机数发生器)
            ArrayList tempList = (ArrayList) chaosMapping(1).get(0);
            son1.getMappingList().set(tempIndex, (double) tempList.get(0));
            son2.getMappingList().set(tempIndex, father.getMappingList().get(tempIndex));
        } else {
            //不然正常交叉
            son1.getMappingList().set(tempIndex, mother.getMappingList().get(tempIndex));
            son2.getMappingList().set(tempIndex, father.getMappingList().get(tempIndex));
        }


        //计算fitness
        // 使用目标混沌映射生成树
        OperatorNode tempTree1 = chaotic.generateFullTree(depth, son1.getMappingList());
        OperatorNode tempTree2 = chaotic.generateFullTree(depth, son2.getMappingList());
        // 为生成的树计算fitness
        chaotic.evaluateFitnessChaotic(tempTree1);
        chaotic.evaluateFitnessChaotic(tempTree2);

        //将fitness赋给子代
        son1.setFitness(tempTree1.getFitness());
        son2.setFitness(tempTree2.getFitness());

        //验证更新最优混沌factor
//        updateBetterpreprocessing(amountOfBetterPreprocessing, son1);
//        updateBetterpreprocessing(amountOfBetterPreprocessing, son2);
//        updateBetterpreprocessing(amountOfBetterPreprocessing, bestPreProcessing);

        //加到新世代
        newGeneration.add(son1);
        newGeneration.add(son2);


        return newGeneration;
    }


    /**
     * 验证更新最优混沌factor
     *
     * @param amountOfBetterPreprocessing 最后保存多少个混沌变量
     * @param targetPreprocessing         目标检验preprocessing
     */
    public boolean updateBetterpreprocessing(int amountOfBetterPreprocessing, PreProcessing targetPreprocessing) {

        BaseGenetic_GaForPreProcessing gp = new BaseGenetic_GaForPreProcessing();

        //若发现更好的则替换到全局变量

        //若比当前最优要好,直接替换
        int size = amountOfBetterPreprocessing;
        if (betterPreprocessing.size() != size) {
            BaseGenetic_多维数据集.betterChaoticMapping.add(targetPreprocessing.getMappingList());
            betterPreprocessing.add(targetPreprocessing);
        } else {
            for (int i = 0; i < betterPreprocessing.size(); i++) {
                if (betterPreprocessing.get(i).getFitness() > targetPreprocessing.getFitness()) {
                    betterPreprocessing.set(i, targetPreprocessing);
                    BaseGenetic_多维数据集.betterChaoticMapping.set(i, targetPreprocessing.getMappingList());
                    return true;
//                    break;
                }
            }
        }

        return false;

    }


    /**
     * 为preprocessing list 赋上 累加概率
     */
    public ArrayList<PreProcessing> assignCumulativeProbability(int amountOfBetterPreprocessing, ArrayList<PreProcessing> preProcessingArrayList) {


        // fitness倒序排列
        preProcessingArrayList.sort(Comparator.comparingDouble(PreProcessing::getFitness));

        if (bestPreProcessing.getMappingList() == null) {
            //深拷贝

            bestPreProcessing = new PreProcessing(preProcessingArrayList.get(0).getMappingList(), preProcessingArrayList.get(0).getFitness());
            System.out.println("当前最优: " + bestPreProcessing.getFitness());
            updateBetterpreprocessing(amountOfBetterPreprocessing, bestPreProcessing);
            System.out.println("更新最优: " + preProcessingArrayList.get(0).getFitness());

        }
        if (bestPreProcessing.getFitness() > preProcessingArrayList.get(0).getFitness()) {
//            bestPreProcessing = preProcessingArrayList.get(0);
            //深拷贝
            bestPreProcessing = new PreProcessing(preProcessingArrayList.get(0).getMappingList(), preProcessingArrayList.get(0).getFitness());
            System.out.println("当前最优: " + bestPreProcessing.getFitness());
//            updateBetterpreprocessing(amountOfBetterPreprocessing, bestPreProcessing);
//            System.out.println("更新最优: " + preProcessingArrayList.get(0).getFitness());
        }

        //每一轮的最高都进行一次更新betterPreprocessing
        if (updateBetterpreprocessing(amountOfBetterPreprocessing,
                new PreProcessing(preProcessingArrayList.get(0).getMappingList(),
                        preProcessingArrayList.get(0).getFitness())) == true) {
            System.out.println("更新最优: " + preProcessingArrayList.get(0).getFitness());
        }


        //反比例函数
        preProcessingArrayList
                .forEach(preProcessing -> preProcessing.setInverseFunctionedFitness(1 / preProcessing.getFitness()));


        //求所有反比例fitness的和
        double totalFitness = preProcessingArrayList.stream()
                .mapToDouble(preProcessing -> preProcessing.getInverseFunctionedFitness())
                .sum();


        //为元素赋值个体概率, 目前取反比例函数(因为fitness越小越好).
        preProcessingArrayList
                .forEach(preProcessing -> preProcessing.setIndividualProbability((preProcessing.getInverseFunctionedFitness() / totalFitness)));


        for (int i = 0; i < preProcessingArrayList.size(); i++) {

            if (i == 0) {
                preProcessingArrayList.get(i).setCumulativeProbability(preProcessingArrayList.get(i).getIndividualProbability());
                continue;
            }
            //计算累加概率
            preProcessingArrayList.get(i).setCumulativeProbability(preProcessingArrayList.get(i).getIndividualProbability() + preProcessingArrayList.get(i - 1).getCumulativeProbability());

        }


        return preProcessingArrayList;
    }


    /**
     * 竞标赛选择 从入参list中选两个 fitness比大小
     *
     * @param preProcessingArrayList
     * @return 返回小的那个
     */
    public PreProcessing pickTwoElementThenComparison(ArrayList<PreProcessing> preProcessingArrayList) {
        int randomNum1, randomNum2;
        //避免重复
        while (true) {
            randomNum1 = random.nextInt(preProcessingArrayList.size());
            randomNum2 = random.nextInt(preProcessingArrayList.size());
            if (randomNum1 != randomNum2) break;
        }

        //比fitness大小 返回大的
        PreProcessing element1 = preProcessingArrayList.get(randomNum1);
        PreProcessing element2 = preProcessingArrayList.get(randomNum2);
        if (element1.getFitness() <= element2.getFitness()) {
            return element1;
        } else {
            return element2;
        }
    }


    /**
     * 预学习的遗传算法主循环, 最后会赋给全局变量值
     *
     * @param size
     * @param depth
     * @param mutationRate
     * @param generation
     * @param amountOfBetterPreprocessing
     */
    public void geneticAlgorithmForPreProcessing(int size, int depth, int mutationRate, int generation, int amountOfBetterPreprocessing) {
        BaseGenetic_GaForPreProcessing baseGenetic_gaForPreProcessing = new BaseGenetic_GaForPreProcessing();
        //初始化 初始人口
        ArrayList<PreProcessing> initializedPopulations = baseGenetic_gaForPreProcessing.initializationsForPreProcessing(size, depth);
        ArrayList<PreProcessing> newGeneration = baseGenetic_gaForPreProcessing.selectionForPreprocessing(amountOfBetterPreprocessing, mutationRate, depth, initializedPopulations);
        //主循环 i=1 因为循坏外已经跑了一遍
        for (int i = 1; i < generation; i++) {
            System.out.println("generation: " + i);
            newGeneration = baseGenetic_gaForPreProcessing.selectionForPreprocessing(amountOfBetterPreprocessing, mutationRate, depth, newGeneration);
        }
    }


    public static void main(String[] args) {
        BaseGenetic_GaForPreProcessing gp = new BaseGenetic_GaForPreProcessing();
//        ArrayList<PreProcessing> aa = gp.initializationsForPreProcessing(50, 6);
//        ArrayList<PreProcessing> b = demo.selectionForPreprocessing(3,5, 6, a);

//        ArrayList<PreProcessing> c = gp.calcilateRSquareForAllPreprocessing(aa);


        // 遗传算法预学习
//        demo.geneticAlgorithmForPreProcessing(50,6,5,5,10);


//        LogisticMapping gp = new LogisticMapping();
        String formula = "10000*x";


//        String formula = "x*x*x*x*x*x*x*x*x*x*x*x*x*x*x";//x^15
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
//        String formula = "15*x*x*x*x+20*x*x*x-120*x*x+200*x";//15x^4 + 20x^3 -120x^2 + 200x

//        String formula = "x*x*x+x*x+x";//x^3 + x^2 + x
//        String formula = "(x*x*x)+(x*x*2)+5*x";//x^3 + 2x^2 + 5x
//        String formula = "(x*x*2)+3*x";// 2x^2 + 3x

//        String formula = "x*x+x*x*x-6*x";

        // 控制 目标公式，入参数量，参数区间
//        gp.initializeSolution(formula,20,-10.0,10.0);

        long totalStartTime = System.currentTimeMillis();

        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();
        ArrayList<Double> RSquareList = new ArrayList();

        for (int i = 0; i < 10; i++) {
            gp.inputValue.clear();
            // 控制 目标公式，入参数量，参数区间
            gp.initializeSolution();

//            gp.evaluateChaoticFactors(10, 1, 3, 6);

            ArrayList<ArrayList<Double>> tempBetterChaoticMapping = new ArrayList<>();

            //j = 最后保存的betterMapping的数量
            for (int j = 0; j < 1; j++) {

                gp.geneticAlgorithmForPreProcessing(200, 6, 60, 50, 1);

                for ( ArrayList<Double> tempList :BaseGenetic_多维数据集.betterChaoticMapping
                     ) {
                    tempBetterChaoticMapping.add(tempList);
                }

                BaseGenetic_多维数据集.betterChaoticMapping = new ArrayList<>();
                betterPreprocessing = new ArrayList<>();
                bestPreProcessing = new PreProcessing();
            }
            BaseGenetic_多维数据集.betterChaoticMapping = tempBetterChaoticMapping;


            long startTime = System.currentTimeMillis();

            gp.geneticAlgorithm(200, 6, null, 60);
            long stopTime = System.currentTimeMillis();
            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());
            System.out.println("r^2 =  " + gp.getRSquare());

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());
            RSquareList.add(gp.getRSquare());

            //初始化
            gp.totalGenerations = 1;
            BaseGenetic_多维数据集.totalGenerations = 1;
            BaseGenetic_GaForPreProcessing.betterChaoticMapping = new ArrayList<>();
            BaseGenetic_多维数据集.betterChaoticMapping = new ArrayList<>();
            betterPreprocessing = new ArrayList<>();
            BaseGenetic_多维数据集.min = 1.7976931348623157E308;
            min = 1.7976931348623157E308;
            BaseGenetic_多维数据集.bestIndividual = null;
            bestIndividual = null;
            bestPreProcessing = new PreProcessing();


            System.out.println("这是第" + i + "次迭代啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");

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


    // TODO: 2022/5/13  完成初始化         done
    // TODO: 2022/5/16  计算fitness    done
    // TODO: 2022/5/16  交叉     done(待验证)
    // TODO: 2022/5/16   选择  done(待验证)
    // TODO: 2022/5/16   变异  done(待验证)
    // TODO: 2022/5/17  轮盘赌选择 (竞标赛效果很差?),  done
    // TODO: 2022/5/17  遗传算法主体  done


    // TODO: 2022/5/27  问题: 遗传算法收缩的太快, 导致最后保存的过于相似, 若结果只是指定深度相对较好的话, 容易产生过长答案..内存溢出
    // TODO: 2022/5/27   好的时候结果很好, 但容易答案过长..
    // TODO: 2022/5/27  解决方案:1. 在指定数量的betterPreprocessing后, 人工添加一些随机mapping进去
    // TODO: 2022/5/27  解决方案:2. 跑多轮遗传算法, 每次只存1个


}
