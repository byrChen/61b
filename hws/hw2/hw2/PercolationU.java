package hw2;

public class PercolationU {
    private boolean[][] grid;
    private int [] tOpens;
    private int [] bOpens;
    private int indexT, indexB = 0;
    private int openNum = 0;
    private int Num;
    private UnionFind union;

    public PercolationU(int N) {
        if (N <= 0) throw new IllegalArgumentException("Illegal number");
        else {
            Num = N;
            grid = new boolean[N][N];
            tOpens = new int[N];
            bOpens = new int[N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    grid[i][j] = false;
                }
            }
            union = new UnionFind(Num * Num);
        }
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public UnionFind getUnion() {
        return union;
    }

    public void open(int row, int col) {
        if (validIndex(row, col)) {
            if (!grid[row][col]) {
                grid[row][col] = true;
                openNum += 1;
                unionNeighbor(row, col);

                if (row == 0) {
                    tOpens[indexT] = xyTo1D(row, col);
                    indexT += 1;
                }
                if (row == Num - 1) {
                    bOpens[indexB] = xyTo1D(row, col);
                    indexB += 1;
                }
            }
        }
        else throw new IndexOutOfBoundsException("Index should >= 0 and <= 4");
    }

    private void unionNeighbor(int row, int col) {
        int u = xyTo1D(row, col);
        if (validIndex(row, col-1) && isOpen(row, col-1)) {
            union.union(xyTo1D(row, col-1), u);
        }
        if (validIndex(row, col+1) && isOpen(row, col+1)) {
            union.union(xyTo1D(row, col+1), u);
        }
        if (validIndex(row-1, col) && isOpen(row-1, col)) {
            union.union(xyTo1D(row-1, col), u);
        }
        if (validIndex(row+1, col) && isOpen(row+1, col)) {
            union.union(xyTo1D(row+1, col), u);
        }
    }

    private int xyTo1D(int row, int col) {
        return row * Num + col;
    }

    private boolean validIndex(int row, int col) {
        boolean v = (row-Num+1)*row <= 0 && (col-Num+1)*col <= 0;
        return v;
    }

    public boolean isOpen(int row, int col) {
        if (validIndex(row, col)) {
            return grid[row][col];
        }
        else throw new IndexOutOfBoundsException("Index should >= 0 and <= 4");
    }

    public boolean isFull(int row, int col) {
        if (validIndex(row, col)) {
            for (int i = 0; i < indexT; i++) {
                if (union.connected(tOpens[i], xyTo1D(row, col))) return true;
            }
            return false;
        }
        else throw new IndexOutOfBoundsException("Index should >= 0 and <= 4");
    }

    public int numberOfOpenSites() {
        return openNum;
    }

    public boolean percolates() {
        int row = Num - 1;
        for (int i = 0; i < indexB; i++) {
            int col = bOpens[i] - row * Num;
            if (isFull(row, col)) return true;
        }
        return false;
    }
}
