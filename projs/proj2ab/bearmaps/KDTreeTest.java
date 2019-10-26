package bearmaps;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private LinkedList<Point> l;
    private KDTree t;

    public KDTreeTest() {
        l = new LinkedList<>();
        l.add(new Point(2, 3));
        l.add(new Point(4, 2));
        l.add(new Point(4, 5));
        l.add(new Point(3, 3));
        l.add(new Point(1, 5));
        l.add(new Point(4, 4));
        t = new KDTree(l);
    }

    @Test
    public void sanityPutTest() {
        KDTree.Node n = t.getRoot();
        assertEquals(n.p, new Point(2, 3));
        assertEquals(n.right.p, new Point(4, 2));
        assertEquals(n.left.p, new Point(1, 5));
        assertEquals(n.right.right.p, new Point(4, 5));
        assertEquals(n.right.right.left.p, new Point(3, 3));
        assertEquals(n.right.right.right.p, new Point(4, 4));
    }

    @Test
    public void sanitySimpleNearestTest() {
        KDTree.Node n = t.getRoot();
        assertEquals(n.p, t.nearest(2, 3));
        assertEquals(n.right.p, t.nearest(4, 2));
        assertEquals(n.left.p, t.nearest(1, 5));
        assertEquals(n.right.right.p, t.nearest(4, 5));
        assertEquals(n.right.right.left.p, t.nearest(3, 3));
        assertEquals(n.right.right.right.p, t.nearest(4, 4));
    }

    @Test
    public void sanityRandomNearestTest() {
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            l.add(new Point(r.nextDouble() * 100, r.nextDouble() * 100));
        }
        t = new KDTree(l);
        NaivePointSet s = new NaivePointSet(l);

        double x, y;
        for (int i = 0; i < 455; i++) {
            x = r.nextDouble() * 100; y = r.nextDouble() * 100;
            Point pt = t.nearest(x, y);
            Point ps = s.nearest(x, y);
            assertEquals(pt, ps);
        }
    }
}
