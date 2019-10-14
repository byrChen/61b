package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStatsU {
    private int Times;
    private double m;
    private double s;

    public PercolationStatsU(int N, int T, PercolationFactoryU pf) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("Illegal input");
        Times = T;
        double[] x = new double[T];
        for (int i = 0; i < T; i++) {
            PercolationU p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            x[i] = (double) p.numberOfOpenSites() / (N*N);
        }
        m = StdStats.mean(x);
        s = StdStats.stddev(x);
    }

    public double mean() {
        return m;
    }

    public double stddev() {
        return s;
    }

    public double confidenceLow() {
        return m - 1.96 * s / Math.sqrt(Times);
    }

    public double confidenceHigh() {
        return m + 1.96 * s / Math.sqrt(Times);
    }
}
