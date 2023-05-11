import lombok.Data;
import lombok.ToString;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class test2 {
    public static void main(String[] args) {
        // 定义三个准则的权重
        double weight1 = 0.3;
        double weight2 = 0.4;
        double weight3 = 0.3;

        // 定义三个决策方案的数据
        double[][] decisionData = {{3, 5, 7}, {1, 8, 6}, {2, 4, 9}};

        // 归一化数据
        double[][] normalizedData = normalizeData(decisionData);

        // 计算每个决策方案的综合评分
        for(int i = 0; i < decisionData.length; i++) {
            double weightedSum = 0;
            for(int j = 0; j < decisionData[i].length; j++) {
                weightedSum += normalizedData[i][j] * getWeight(j, weight1, weight2, weight3);
            }
            System.out.println("Decision " + (i+1) + " weighted sum: " + weightedSum);
        }
    }



    // 归一化数据
    public static double[][] normalizeData(double[][] data) {
        double[][] normalizedData = new double[data.length][data[0].length];
        for(int i = 0; i < data.length; i++) {
            double min = getMinValue(data[i]);
            double max = getMaxValue(data[i]);
            for(int j = 0; j < data[i].length; j++) {
                normalizedData[i][j] = (data[i][j] - min) / (max - min);
            }
        }
        return normalizedData;
    }

    // 获取最小值
    public static double getMinValue(double[] data) {
        double minValue = data[0];
        for(int i = 1; i < data.length; i++) {
            if(data[i] < minValue) {
                minValue = data[i];
            }
        }
        return minValue;
    }

    // 获取最大值
    public static double getMaxValue(double[] data) {
        double maxValue = data[0];
        for(int i = 1; i < data.length; i++) {
            if(data[i] > maxValue) {
                maxValue = data[i];
            }
        }
        return maxValue;
    }

    // 获取准则的权重
    public static double getWeight(int criteria, double weight1, double weight2, double weight3) {
        switch(criteria) {
            case 0:
                return weight1;
            case 1:
                return weight2;
            case 2:
                return weight3;
            default:
                return 0;
        }
    }

}
// TODO: 2022/2/2




