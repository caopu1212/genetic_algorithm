import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import src.符号回归.tools.FileOperator;
import src.符号回归.tools.MyRandom;

import javax.swing.*;
import java.util.Random;

public class 混沌映射散点图 {

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
        for (int i = 0; i < 6000; i++) {


            // pseudo random
            x = random.nextDouble();
            y = random.nextDouble();
            if (y > 0.1 && y < 0.9) {
                count++;
                int temp = random.nextInt(4);
                if (temp == 0) {
                    count1++;
                    temp = random.nextInt(2);
                    if (temp == 0) {
                        y = myRandom.makeRandom(0.1f, 0, 2);
                    } else if(temp == 1){
                        y = myRandom.makeRandom(1, 0.9f, 2);
                    }
                }
            }

            //sine
//
//            x = random.nextDouble();
//            y = Math.abs(Math.sin(Math.PI * y));
//            System.out.println(y);

            //logistc

//             x = random.nextDouble();
//             y = y * 4 * (1 - y);
//            System.out.println(y);


            //tent
//            x = random.nextDouble();
//            y = random.nextDouble();
//            if (y <= 0.5) {
//                y = 2 * y;
//            } else {
//                y = 2 * (1 - y);
//            }

            series.add(x, y);
        }


        System.out.println(count + " " + count1);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "pseudo random", // chart title
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
