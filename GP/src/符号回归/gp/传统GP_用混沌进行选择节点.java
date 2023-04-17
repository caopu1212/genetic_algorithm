package src.符号回归.gp;

import lombok.SneakyThrows;
import src.符号回归.tree.Node;
import src.符号回归.tree.OperatorNode;

import javax.script.ScriptException;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static src.符号回归.gp.BaseGenetic_GaForPreProcessing.myRandom;

public class 传统GP_用混沌进行选择节点 extends BaseGenetic_多维数据集 {


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
    public OperatorNode crossOver(OperatorNode dad, OperatorNode mom, int mutationRate) throws IOException, ClassNotFoundException {
        //计算变异概率
        int mutation = random.nextInt(100), decision = random.nextInt(2);
        //取一个随机数用这个数 用混沌映射 去map的前两位作为父母的位置
        double[] chaosList = new double[2];
        double x = random.nextDouble();

        //logistic映射
//        for (int i = 0; i < 2; i++) {
//            x = x * 4 * (1 - x);
//            chaosList[i] = x;
//        }


        //sin映射
//        for (int i = 0; i < 2; i++) {
//            x = Math.abs(Math.sin(Math.PI * x));
//            chaosList[i] = x;
//        }
//
        //tent映射
//        for (int i = 0; i < 2; i++) {
//            if (x <= 0.5) {
//                x = 1.5 * x;
//            } else {
//                x = 1.5 * (1 - x);
//            }
//            chaosList[i] = x;
//        }


        //随机选择
//        chaosList[0] =random.nextDouble();
//        chaosList[1] =random.nextDouble();



        //伪随机,分布类似logistic
        for (int i = 0; i < 2; i++) {
            x = random.nextDouble();
            if (x > 0.1 && x < 0.9) {
                int temp = random.nextInt(4);
                if (temp == 0) {
                    temp = random.nextInt(2);
                    if (temp == 0) {
                        x = myRandom.makeRandom(0.1f, 0, 2);
                    } else if (temp == 1) {
                        x = myRandom.makeRandom(1, 0.9f, 2);
                    }
                }
            }
            chaosList[i] = x;
        }



        OperatorNode cloneDad = dad.cloneTree(), cloneMom = mom.cloneTree();

//        int i = cloneDad.countSubnodes(cloneDad);
//        int qwe = cloneMom.countSubnodes(cloneDad);


        long targetDad = Math.round(chaosList[0] * cloneDad.countSubnodes(cloneDad));
        long targetMom = Math.round(chaosList[1] * cloneMom.countSubnodes(cloneMom));

        if (targetDad == 0)
            targetDad++;
        if (targetMom == 0)
            targetMom++;

        count = 0;
        OperatorNode dadStopPoint = pickStop(cloneDad, (int) targetDad);
//        OperatorNode dadStopPoint = pickStop(cloneDad);
        //全局变量清零
        count = 0;
        OperatorNode momStopPoint = pickStop(cloneMom, (int) targetMom);
//        OperatorNode momStopPoint = pickStop(cloneMom);

        if (mutation > mutationRate) {

//            dadStopPoint.setLeft(momStopPoint);
//            dadStopPoint.setRight(momStopPoint);
//            dadStopPoint.setRight(momStopPoint.getRight());

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


    public static Object deepClone(Object object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }


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


    @SneakyThrows
    public static void main(String[] args) throws ScriptException, IOException {
        //  画折线图
//        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
//        line_chart_dataset.addValue( 15 , "schools" , "1970" );
//        line_chart_dataset.addValue( 30 , "schools" , "1980" );
//        line_chart_dataset.addValue( 60 , "schools" , "1990" );
//        line_chart_dataset.addValue( 120 , "schools" , "2000" );
//        line_chart_dataset.addValue( 240 , "schools" , "2010" );
//        line_chart_dataset.addValue( 300 , "schools" , "2014" );
//
//        JFreeChart lineChartObject = ChartFactory.createLineChart(
//                "Schools Vs Years","Year",
//                "Schools Count",
//                line_chart_dataset, PlotOrientation.VERTICAL,
//                true,true,false);
//
//        int width = 640; /* Width of the image */
//        int height = 480; /* Height of the image */
//        File lineChart = new File( "LineChartLineChartLineChartLineChartLineChartLineChart.jpeg" );
//        ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);

        传统GP_用混沌进行选择节点 gp = new 传统GP_用混沌进行选择节点();

//        String formula = "x*x*x*x*x*x*x*x*x*x*x*x*x*x*x";//x^15
//        String formula = "x*x*x*x*x*x*x*x*x*x*x+x*x*x*x*x*x*x+x*x*x*x*x*x+x*x*x*x*x+x*x*x*x+x*x*x+x*x+x";//x^yacht-train-0+x^7+x^6+x^5+x^4 + x^3 + x^2 + x

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
        gp.initializeSolution();

        long totalStartTime = System.currentTimeMillis();

        ArrayList<Long> timeList = new ArrayList();
        ArrayList<Integer> generationList = new ArrayList();
        ArrayList<Double> RSquareList = new ArrayList();

        for (int i = 0; i < 50; i++) {
            gp.inputValue.clear();
            gp.initializeSolution();

            long startTime = System.currentTimeMillis();
            gp.geneticAlgorithm(100, 3, null, 5 , 100);

            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time is: " + (stopTime - startTime));
            System.out.println("Number of generations was: " + gp.getNumberOfGenerations());
            System.out.println("r^2 =  " + gp.getRSquare());
            System.out.println("adjusted r^2 =  " + gp.getAdjustedRSquare(gp.getRSquare()));

            timeList.add(stopTime - startTime);
            generationList.add(gp.getNumberOfGenerations());
            RSquareList.add(gp.getRSquare());

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


// TODO: 2023/2/1  通过混沌映射找到对应的node   done