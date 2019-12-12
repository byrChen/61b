public class BubbleGrid {
    private int[][] grid;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        // TODO
        if (grid == null || grid.length == 0 || grid[0].length == 0) return null;
        if (darts == null || darts.length == 0 || darts[0].length != 2) return null;
        int row = grid.length, col = grid[0].length, dartsNum = darts.length;
        for (int[] dart: darts) {
            if (dart[0] < 0 || dart[0] >= row ||
                    dart[1] < 0 || dart[1] >= col) return null;
        }

        int[] ur = {1, 0, -1, 0};
        int[] uc = {0, 1, 0, -1};
        int root = row * col;
        UnionFind union = new UnionFind(root + 1);
        int[][] copy = new int[row][col];

        for (int i = 0; i < row; i++) {
            copy[i] = grid[i].clone();
        }

        for (int[] dart: darts) {
            copy[dart[0]][dart[1]] = 0;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (copy[i][j] == 1) {
                    int index = xyToint(i, j);
                    if (i == 0) {
                        union.union(index, root);
                        continue;
                    }
                    if (i - 1 >= 0 && copy[i-1][j] == 1) {
                        union.union(index, xyToint(i-1, j));
                    }
                    if (j - 1 >= 0 && copy[i][j-1] == 1) {
                        union.union(index, xyToint(i, j-1));
                    }
                }
            }
        }


        int[] bubbles = new int[dartsNum];

        for (int i = dartsNum - 1; i >= 0; i--) {
            int prePop = union.sizeOf(root);
            int r = darts[i][0];
            int c = darts[i][1];
            int index = xyToint(r, c);
            if (grid[r][c] == 0) continue;
            if (r == 0) union.union(index, root);
            for (int j = 0; j < 4; j++) {
                int nr = r + ur[j];
                int nc = c + uc[j];
                if (nr >= 0 && nr < row &&
                        nc >=0 && nc < col && copy[nr][nc] == 1) {
                    union.union(index, xyToint(nr, nc));
                }
            }
            int pop = union.sizeOf(root) - prePop - 1;
            bubbles[i] = pop > 0 ? pop : 0;
            copy[r][c] = 1;
        }
        return bubbles;
    }


    private int xyToint(int row, int col) {
        return row * grid[0].length + col;
    }
}
