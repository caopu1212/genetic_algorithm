
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
import java.util.*;

public class test {
    public static Random random = new Random();

    static final int MOD = 1000000007;


    @Test
    public void demo() {
//        for (int j = 0; j < 10; j++) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            if (random.nextInt(100) > 80) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }


        }

    }


    @Test
    public void print() {
        ArrayList<Double> list = new ArrayList<>();
        list.add(13.2);
        list.add(12.2);
        list.add(3.3);


        double sum = list.stream()
                .mapToDouble(num -> num)
                .sum();
        System.out.println(sum);

//        if (list.get(0).contains("5")) {

//        }
    }

    @Test
    public void demo1() {
        ArrayList<String> list = new ArrayList<>();
        list.add("3");
        list.add("5");
        list.add("1");
        list.add("2");
        list.add("4");

        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);


    }

    @Test
    public void calculate() {
        System.out.println(Math.exp(121.));
        System.out.println(Math.tan(Math.exp(121.)));
        System.out.println(Math.log(Math.tan(Math.exp(121.))));

        System.out.println(Math.log(Math.tan(Math.exp(121.))));

    }


    @Test
    public void createPhoneNumber() throws IOException {


        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        line_chart_dataset.addValue(15, "schools", "1970");
        line_chart_dataset.addValue(30, "schools", "1980");
        line_chart_dataset.addValue(60, "schools", "1990");
        line_chart_dataset.addValue(120, "schools", "2000");
        line_chart_dataset.addValue(240, "schools", "2010");
        line_chart_dataset.addValue(300, "schools", "2014");

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Schools Vs Years", "Year",
                "Schools Count",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640; /* Width of the image */
        int height = 480; /* Height of the image */
        File lineChart = new File("LineChart.jpeg");
        ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
//更多请阅读：https://www.yiibai.com/jfreechart/jfreechart_line_chart.html


        System.out.println("123");


    }


    public static int solution() {
        int[] A = {1, 3, 6, 4, 1, 2};

        int n = A.length;
        boolean[] seen = new boolean[n + 1];

        // mark each positive number seen in A
        for (int i = 0; i < n; i++) {
            if (A[i] > 0 && A[i] <= n) {
                seen[A[i]] = true;
            }
        }

        // return the smallest positive number not seen in A
        for (int i = 1; i <= n; i++) {
            if (!seen[i]) {
                return i;
            }
        }

        // if all positive numbers are seen, return the next positive number
        return n + 1;
    }



    public static void main(String[] args){

    }


@Test
    public  void solution1() {
    String S = "BAR";
        int result = 0;
        List<String> strings = generatePermutations(S);
        for (int i = 0; i < strings.size(); i++) {
            char firstStr = strings.get(i).charAt(0);
            if (firstStr == 'A' || firstStr == 'E' || firstStr == 'I' || firstStr == 'O' || firstStr == 'U') {
                for (int j = 0; j < strings.get(i).length(); j++) {
                    if (j != strings.get(i).length() - 1) {
                        //判断连续两个字母相同
                        if (strings.get(i).charAt(j) == strings.get(i).charAt(j + 1)) {

                            if (strings.get(i).charAt(j) == 'A' || strings.get(i).charAt(j) == 'E' || strings.get(i).charAt(j) == 'I' || strings.get(i).charAt(j) == 'O' || strings.get(i).charAt(j) == 'U') {
                                if (strings.get(i).charAt(j + 1) == 'A' || strings.get(i).charAt(j + 1) == 'E' || strings.get(i).charAt(j + 1) == 'I' || strings.get(i).charAt(j + 1) == 'O' || strings.get(i).charAt(j + 1) == 'U') {
                                    break;
                                }
                            }
                            if (strings.get(i).charAt(j) != 'A' || strings.get(i).charAt(j) != 'E' || strings.get(i).charAt(j) != 'I' || strings.get(i).charAt(j) != 'O' || strings.get(i).charAt(j) != 'U') {
                                if (strings.get(i).charAt(j + 1) != 'A' || strings.get(i).charAt(j + 1) != 'E' || strings.get(i).charAt(j + 1) != 'I' || strings.get(i).charAt(j + 1) != 'O' || strings.get(i).charAt(j + 1) != 'U') {
                                    break;
                                }
                            }
                        }
                    }

                    result += 1;
                }
            }else{

                result += 1;
            }


        }


        System.out.println(result);

    }

    public  List<String> generatePermutations(String str) {
        List<String> result = new ArrayList<>();
        char[] chars = str.toCharArray();
        backtrack(chars, new boolean[chars.length], "", result);
        return result;
    }

    private  void backtrack(char[] chars, boolean[] used, String permutation, List<String> result) {
        if (permutation.length() == chars.length) {
            result.add(permutation);
            return;
        }
        for (int i = 0; i < chars.length; i++) {
            if (used[i]) continue;
            if (i > 0 && chars[i] == chars[i - 1] && !used[i - 1]) continue;
            used[i] = true;
            backtrack(chars, used, permutation + chars[i], result);
            used[i] = false;
        }
    }


    @Data
    @ToString
    class human {
        private String name;
        private String gender;
    }


}
// TODO: 2022/2/2




