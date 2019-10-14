package hw2;

import edu.princeton.cs.algs4.Stopwatch;

public class Analysis {
    
    public static void testW(int N, int T, PercolationFactory pf) {
        Stopwatch s = new Stopwatch();
        PercolationStats p = new PercolationStats(N, T, pf);
        System.out.println(s.elapsedTime());
    }

    public static void testU(int N, int T, PercolationFactoryU pf) {
        Stopwatch s = new Stopwatch();
        PercolationStatsU p = new PercolationStatsU(N, T, pf);
        System.out.println(s.elapsedTime());
    }

    public static void main(String[] args) {
        int N = 5;
        int T = 10000;
        PercolationFactory pf = new PercolationFactory();
        PercolationFactoryU pfU = new PercolationFactoryU();
        Analysis.testW(N, T, pf);
        Analysis.testU(N, T, pfU);

        N = 10;
        T = 10000;
        pf = new PercolationFactory();
        pfU = new PercolationFactoryU();
        Analysis.testW(N, T, pf);
        Analysis.testU(N, T, pfU);

        N = 5;
        T = 20000;
        pf = new PercolationFactory();
        pfU = new PercolationFactoryU();
        Analysis.testW(N, T, pf);
        Analysis.testU(N, T, pfU);
    }
}
