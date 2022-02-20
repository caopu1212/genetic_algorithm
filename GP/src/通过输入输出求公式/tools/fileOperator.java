package src.通过输入输出求公式.tools;


import org.junit.Test;
import src.通过输入输出求公式.Elements.FileData;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 文本操作 工具类
 */
public class fileOperator {
    @Test
    public ArrayList<FileData> readFile() {
        String pathname = "F:\\python\\genetic_algorithm\\GP\\dataset\\yacht-test-0.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        String line = "";
        ArrayList<FileData> totalList = new ArrayList<>();

        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言


            int count = 0;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                //读取每一行，用，分割
                String[] splitListString = line.split(",");
                //将String 转为Double
                Object[] SplitListDoubleTemp = Arrays
                        .stream(splitListString)
                        .map(x -> Double.parseDouble(x))
                        .toArray();

                ArrayList<Double> SplitArrayListDouble = new ArrayList<>();

                for (int i = 0; i < SplitListDoubleTemp.length; i++) {
                    SplitArrayListDouble.add((Double) SplitListDoubleTemp[i]);
                }


                //list转arraylist。
//                ArrayList<Double> splitList = new ArrayList<>(Arrays.asList(splitListDouble));


                //将每一行最后一个作为自变量。剩下的做特征量存入对象。
                FileData fileData = new FileData();
                fileData.setIndependentVariable(SplitArrayListDouble.get(SplitArrayListDouble.size() - 1));
                SplitArrayListDouble.remove(SplitArrayListDouble.size() - 1);
                fileData.setFeatures(SplitArrayListDouble);

                totalList.add(fileData);
                System.out.println(count++);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(totalList);
        return totalList;
    }

}

