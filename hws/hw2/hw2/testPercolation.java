package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class testPercolation {

    private Object IllegalArgumentException;

    @Test
    public void testConstructor() {
        try {
            Percolation p = new Percolation(0);
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Illegal number"));
        }

        for (int i = 1; i < 101; i++) {
            Percolation p = new Percolation(i);
            boolean[][] expect = new boolean[i][i];
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < i; k++) {
                    expect[j][k] = false;
                }
            }
            boolean[][] actual = p.getGrid();
            assertArrayEquals(expect, actual);
        }
    }

    @Test
    public void testOpens() {
        Percolation p = new Percolation(5);
        try {
            p.open(-1, 0);
            p.open(0, -1);
            p.open(-1, -1);
            p.open(5, 0);
            p.open(0, 5);
            p.open(5, 5);
            p.open(5, -1);
            p.open(-1, 5);
            p.isOpen(-1, 0);
            p.isOpen(0, -1);
            p.isOpen(-1, -1);
            p.isOpen(5, 0);
            p.isOpen(0, 5);
            p.isOpen(5, 5);
            p.isOpen(5, -1);
            p.isOpen(-1, 5);
            p.isFull(-1, 0);
            p.isFull(0, -1);
            p.isFull(-1, -1);
            p.isFull(5, 0);
            p.isFull(0, 5);
            p.isFull(5, 5);
            p.isFull(5, -1);
            p.isFull(-1, 5);
            fail("No exception thrown.");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(e.getMessage().contains("Index should >= 0 and <= 4"));
        }

        p.open(0,1);
        p.open(0,1);
        p.open(1,1);
        p.open(1,1);
        assertTrue(p.getGrid()[0][1]);
        assertTrue(p.getGrid()[1][1]);
        assertFalse(p.getGrid()[2][1]);
        assertFalse(p.getGrid()[3][1]);
        assertTrue(p.isOpen(0, 1));
        assertTrue(p.isOpen(1, 1));
        assertFalse(p.isOpen(2, 1));
        assertFalse(p.isOpen(3, 1));
        assertTrue(p.isFull(0, 1));
        assertTrue(p.isFull(1, 1));
        assertFalse(p.isFull(2, 1));
        assertFalse(p.isFull(3, 1));
        assertTrue(p.numberOfOpenSites() == 2);

        p.open(0,0);
        p.open(2,1);
        assertTrue(p.getUnion().connected(0, 1));
        assertTrue(p.getUnion().connected(1, 6));
        assertTrue(p.getUnion().connected(6, 11));
        assertTrue(p.getUnion().connected(0, 11));
        assertFalse(p.getUnion().connected(1, 3));
    }

    @Test
    public void testPercolates() {
        Percolation p = new Percolation(5);
        p.open(0, 0);
        p.open(0, 1);
        assertFalse(p.percolates());
        p.open(1,2);
        p.open(2,2);
        p.open(3,2);
        p.open(4,3);
        assertFalse(p.percolates());
        p.open(0, 2);
        p.open(4, 2);
        assertTrue(p.percolates());
    }
}
