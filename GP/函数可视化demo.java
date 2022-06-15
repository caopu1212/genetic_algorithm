import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import src.通过输入输出求公式.tools.FileOperator;
import src.通过输入输出求公式.tools.MyRandom;

import javax.swing.*;
import java.util.Random;

public class 函数可视化demo {

    private static FileOperator fileOperator = new FileOperator();
    private static Random random = new Random();
    private static MyRandom myRandom = new MyRandom();


    public static void main(String[] args) {

//        ArrayList<FileData> fileData = fileOperator.readFile();

        XYSeries series = new XYSeries("xySeries");
        int count = 0;
        int count1 = 0;

        double x;
        double y = random.nextDouble();
        for (int i = 0; i < 5000; i++) {
            //sine
             x = random.nextDouble();
            y = Math.abs(Math.sin(Math.PI * y));
            System.out.println(y);
            series.add(x, y);
        }


        System.out.println(count + " " + count1);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "tent", // chart title
                "x", // x axis label
                "y", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                false, // tooltips
                false // urls
        );

        ChartFrame frame = new ChartFrame("my picture", chart);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
