public class BubbleGrid {
    private int[][] grid;

    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    public int[] popBubbles(int[][] darts) {
        int[] pop = new int[darts.length];
        for (int i = 0; i < darts.length; i++) {
            pop[i] = updateGrid(darts[i]);
        }
        return pop;
    }

    public int updateGrid(int[] dart) {
        int n = 0;
        if (valid(dart)) {
            if (grid[dart[0]][dart[1]] == 1) {
                grid[dart[0]][dart[1]] = 0;
                n += 1;
                for (int j = 0; j < grid[0].length; j++) {
                    boolean flag = false;
                    for (int i = 0; i < grid.length; i++) {
                        if (grid[i][j] == 0) {
                            flag = true;
                            continue;
                        }
                        if (grid[i][j] == 1 && flag) {
                            grid[i][j] = 0;
                            n += 1;
                        }
                    }
                }
            }
        }
        return n;
    }

    public boolean valid(int[] dart) {
        if (dart.length == 2) {
            int row = dart[0];
            int col = dart[1];
            if ((row >= 0 && row < grid.length) &&
                    (col >= 0 && col < grid[0].length)) {
                return true;
            }
        }
        return false;
    }

    public int[][] getGrid() {
        return grid;
    }
}
