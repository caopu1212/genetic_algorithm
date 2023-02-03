
import com.sun.xml.internal.ws.util.StringUtils;
import lombok.Data;
import lombok.ToString;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.junit.Test;
import src.符号回归.tools.LineChart;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class test  {
    public static Random random = new Random();

    public static void main(String[] args) {
//        test test = new test();
//        test.BaseGenetic_多维数据集();

    }

    @Test
    public void demo() {
//        for (int j = 0; j < 10; j++) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            if ( random.nextInt(100)> 80) {
                System.out.println("yes");
            }else{
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
    public void calculate(){
        System.out.println(Math.exp(121.));
        System.out.println(Math.tan(Math.exp(121.)) );
        System.out.println(Math.log(Math.tan(Math.exp(121.))));

        System.out.println(Math.log(Math.tan(Math.exp(121.))));

    }




    @Test
    public void createPhoneNumber() throws IOException {


        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        line_chart_dataset.addValue( 15 , "schools" , "1970" );
        line_chart_dataset.addValue( 30 , "schools" , "1980" );
        line_chart_dataset.addValue( 60 , "schools" , "1990" );
        line_chart_dataset.addValue( 120 , "schools" , "2000" );
        line_chart_dataset.addValue( 240 , "schools" , "2010" );
        line_chart_dataset.addValue( 300 , "schools" , "2014" );

        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Schools Vs Years","Year",
                "Schools Count",
                line_chart_dataset,PlotOrientation.VERTICAL,
                true,true,false);

        int width = 640; /* Width of the image */
        int height = 480; /* Height of the image */
        File lineChart = new File( "LineChart.jpeg" );
        ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
//更多请阅读：https://www.yiibai.com/jfreechart/jfreechart_line_chart.html



        System.out.println("123");


    }



    @Data
    @ToString
    class human {
        private String name;
        private String gender;
    }


}
// TODO: 2022/2/2




