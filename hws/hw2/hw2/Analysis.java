package hw2;

import edu.princeton.cs.algs4.Stopwatch;

public class Analysis {
    
    public static double testW(int N, int T, PercolationFactory pf) {
        Stopwatch s = new Stopwatch();
        PercolationStats p = new PercolationStats(N, T, pf);
        return s.elapsedTime();
    }

    public static double testU(int N, int T, PercolationFactoryU pf) {
        Stopwatch s = new Stopwatch();
        PercolationStatsU p = new PercolationStatsU(N, T, pf);
        return s.elapsedTime();
    }

    public static void main(String[] args) {
        int N = 100;
        int T = 30;
        PercolationFactory pf = new PercolationFactory();
        PercolationFactoryU pfU = new PercolationFactoryU();
        double rW = Analysis.testW(N, T, pf);
        double rU = Analysis.testU(N, T, pfU);
        System.out.println("N = " + N + ", T =" + T +", the runtime of WeightedQuickUnionUF.class is " + rW);
        System.out.println("N = " + N + ", T =" + T +", the runtime of UnionFind.class is " + rU);

        N = 100;
        T = 60;
        rW = Analysis.testW(N, T, pf);
        rU = Analysis.testU(N, T, pfU);
        System.out.println("N = " + N + ", T =" + T +", the runtime of WeightedQuickUnionUF.class is " + rW);
        System.out.println("N = " + N + ", T =" + T +", the runtime of UnionFind.class is " + rU);

        N = 200;
        T = 60;
        rW = Analysis.testW(N, T, pf);
        rU = Analysis.testU(N, T, pfU);
        System.out.println("N = " + N + ", T =" + T +", the runtime of WeightedQuickUnionUF.class is " + rW);
        System.out.println("N = " + N + ", T =" + T +", the runtime of UnionFind.class is " + rU);
    }
}
