import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        BST<Integer> bst = new BST<>();
        List<Double> y = new ArrayList<>();
        List<Double> yOptimal = new ArrayList<>();
        List<Integer> x = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            while (true) {
                int item = RandomGenerator.getRandomInt(100000);
                if (bst.contains(item)) continue;
                else {
                    bst.add(item);
                    break;
                }
            }
            x.add(i);
            y.add(bst.averageDepth());
            yOptimal.add(ExperimentHelper.optimalAverageDepth(i));
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("random average depth", x, y);
        chart.addSeries("optimal average depth", x, yOptimal);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        BST<Integer> bst = new BST<>();
        List<Integer> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            while (true) {
                int item = RandomGenerator.getRandomInt(10000000);
                if (bst.contains(item)) continue;
                else {
                    bst.add(item);
                    break;
                }
            }
        }
        x.add(0);
        y.add(bst.averageDepth());
        for (int i = 1; i <= 100000; i++) {
            ExperimentHelper.randomAsymmetric(bst);
            if (i > 99000) {
                x.add(i);
                y.add(bst.averageDepth());
            }
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("random asymmetric average depth", x, y);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        BST<Integer> bst = new BST<>();
        List<Integer> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            while (true) {
                int item = RandomGenerator.getRandomInt(10000000);
                if (bst.contains(item)) continue;
                else {
                    bst.add(item);
                    break;
                }
            }
        }
        x.add(0);
        y.add(bst.averageDepth());
        for (int i = 1; i <= 100000; i++) {
            ExperimentHelper.randomSymmetric(bst);
            if (i > 99000) {
                x.add(i);
                y.add(bst.averageDepth());
            }
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("random symmetric average depth", x, y);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        Experiments.experiment1();
//        Experiments.experiment2();
//        Experiments.experiment3();
    }
}
