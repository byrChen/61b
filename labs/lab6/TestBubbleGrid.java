import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class TestBubbleGrid {

    @Test
    public void testConstructor() {
        int[][] input = {{1,0,1}, {0,0,0}, {1,1,1}, {1,0,1}};
        BubbleGrid g = new BubbleGrid(input);
        assertArrayEquals(input, g.getGrid());
    }

    @Test
    public void testUpdate() {
        int[][] input = {{1,1,0}, {1,0,0}, {1,1,0}, {1,1,1}};
        BubbleGrid g = new BubbleGrid(input);

        int[] dart = {2, 2};
        int actualPop = g.updateGrid(dart);
        int expectPop = 0;
        int[][] actualGrid = g.getGrid();
        int[][] expectGrid = input;
        assertEquals(expectPop, actualPop);
        assertArrayEquals(expectGrid, actualGrid);

        dart = new int[] {0, 0};
        actualPop = g.updateGrid(dart);
        expectPop = 7;
        actualGrid = g.getGrid();
        expectGrid = new int[][] {{0,1,0}, {0,0,0}, {0,0,0}, {0,0,0}};
        assertEquals(expectPop, actualPop);
        assertArrayEquals(expectGrid, actualGrid);

        dart = new int[] {0, 1};
        actualPop = g.updateGrid(dart);
        expectPop = 1;
        actualGrid = g.getGrid();
        expectGrid = new int[][] {{0,0,0}, {0,0,0}, {0,0,0}, {0,0,0}};
        assertEquals(expectPop, actualPop);
        assertArrayEquals(expectGrid, actualGrid);
    }
    
    @Test 
    public void testPopBubbles() {
        int[][] input = {{1,1,1,1,1,1}, {1,1,1,1,1,1}, {1,1,1,1,1,1}, {1,1,1,1,1,1}, {1,1,1,1,1,1}, {1,1,1,1,1,1}};
        BubbleGrid g = new BubbleGrid(input);
        int[][] darts = {{5,0}, {4,1}, {3,2}, {2,3}, {1,4}, {0,5}};
        int[] actualPop = g.popBubbles(darts);
        int[] expectPop = {1, 2, 3, 4, 5, 6};
        int[][] actualGrid = g.getGrid();
        int[][] expectGrid = {{1,1,1,1,1,0}, {1,1,1,1,0,0}, {1,1,1,0,0,0}, {1,1,0,0,0,0}, {1,0,0,0,0,0}, {0,0,0,0,0,0}};
        assertArrayEquals(expectPop, actualPop);
        assertArrayEquals(expectGrid, actualGrid);
    }
}
