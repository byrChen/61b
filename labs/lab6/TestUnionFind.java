import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class TestUnionFind {

    @Test
    public void testConstructor() {
        UnionFind actual = new UnionFind(5);
        int[] expect = new int[5];
        Arrays.fill(expect, -1);
        assertArrayEquals(expect, actual.getSet());
    }

    @Test
    public void testUnion() {
        UnionFind actual = new UnionFind(5);
        actual.union(1, 0);
        int[] expect1 = {-2, 0, -1, -1, -1};
        assertArrayEquals(expect1, actual.getSet());
        actual.union(3, 2);
        int[] expect2 = {-2, 0, -2, 2, -1};
        assertArrayEquals(expect2, actual.getSet());
        actual.union(4, 3);
        int[] expect3 = {-2, 0, -3, 2, 2};
        assertArrayEquals(expect3, actual.getSet());
        actual.union(0, 3);
        int[] expect4 = {2, 0, -5, 2, 2};
        assertArrayEquals(expect4, actual.getSet());
        actual.union(0, 0);
        actual.union(1, 1);
        assertArrayEquals(expect4, actual.getSet());
        actual.union(1, 3);
        int[] expect5 = {2, 0, -5, 2, 2};
        assertArrayEquals(expect5, actual.getSet());
    }

    @Test
    public void testUnionPC() {
        UnionFind actual = new UnionFind(5);
        actual.union(1, 0);
        actual.union(3, 2);
        actual.union(4, 3);
        actual.union(0, 3);
        actual.union(0, 0);
        actual.union(1, 1);
        actual.union(1, 3);
        int[] expect5 = {2, 2, -5, 2, 2};
        assertArrayEquals(expect5, actual.getSet());
    }
}
