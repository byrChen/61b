package bearmaps;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {

    class Node {
        Point p;
        Node left, right;
        boolean compareX = true;

        Node(Point p, Node left, Node right) {
            this.p = p; this.left = left; this.right = right;
        }
    }

    private Node root;

    Comparator<Node> nodeComparator = (child, parent) -> {
        if (parent.compareX) {
            child.compareX = false;
            return Double.compare(child.p.getX(), parent.p.getX());
        } else {
            child.compareX = true;
            return Double.compare(child.p.getY(), parent.p.getY());
        }
    };

    public KDTree(List<Point> points) {
        if (points.size() < 1) throw new IllegalArgumentException();
        root = new Node(points.get(0), null, null);
        for (int i = 1; i < points.size(); i++) {
            put(new Node(points.get(i), null, null));
        }
    }

    Node getRoot() {
        return root;
    }

    public void put(Node n) {
        if (n == null) throw new IllegalArgumentException("calls put() with a null Node");
        root = put(root, n);
    }

    private Node put(Node parent, Node child) {
        if (parent == null) return child;
        int cmp = nodeComparator.compare(child, parent);
        if (cmp < 0) parent.left = put(parent.left, child);
        else         parent.right = put(parent.right, child);
        return parent;
    }

    @Override
    public Point nearest(double x, double y) {
        Node goal = new Node(new Point(x, y), null, null);
        return nearest(root, goal, root).p;
    }

    private Node nearest(Node n, Node goal, Node best) {
        if (n == null) return best;
        int cmp = Double.compare(disBetween(n, goal), disBetween(best, goal));
        if (cmp < 0) best = n;
        cmp = nodeComparator.compare(goal, n);
        Node goodSide, badSide;
        goodSide = cmp < 0 ? n.left : n.right;
        badSide= cmp < 0 ? n.right : n.left;
        best = nearest(goodSide, goal, best);
        if (needBad(n, goal, disBetween(best, goal))) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    private boolean needBad (Node n, Node goal, double min) {
        Double d;
        if (n.compareX) d = Math.pow(n.p.getX() - goal.p.getX(), 2);
        else d = Math.pow(n.p.getY() - goal.p.getY(), 2);
        int cmp = Double.compare(d, min);
        return cmp < 0 ? true : false;
    }

    private static double disBetween(Node n1, Node goal) {
        return Point.distance(n1.p, goal.p);
    }
}
