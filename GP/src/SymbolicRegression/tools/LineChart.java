package src.SymbolicRegression.tools;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.io.File;
import java.io.IOException;

public class LineChart extends ApplicationFrame {
    public LineChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(-0.123, "schools", "1970");
        dataset.addValue(30, "schools", "1980");
        dataset.addValue(60, "schools", "1990");
        dataset.addValue(120, "schools", "2000");
        dataset.addValue(240, "schools", "2010");
        dataset.addValue(300, "schools", "2014");

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Years", "Number of Schools",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(-0.123, "schools", "1970");
        dataset.addValue(30, "schools", "1980");
        dataset.addValue(60, "schools", "1990");
        dataset.addValue(120, "schools", "2000");
        dataset.addValue(240, "schools", "2010");
        dataset.addValue(300, "schools", "2014");

        return dataset;
    }

    public static void main(String[] args) throws IOException {
//        LineChart chart = new LineChart(
////                "School Vs Years",
////                "Numer of Schools vs years");
////        chart.pack();
////        RefineryUtilities.centerFrameOnScreen(chart);
////        chart.setVisible(true);


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






    }
}
