package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        if (points.size() < 1) throw new IllegalArgumentException();
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point p1 = points.get(0);
        Point p2 = new Point(x, y);
        double minDistance = Point.distance(p1, p2);
        for (int i = 1; i < points.size(); i++) {
            if (Point.distance(points.get(i), p2) < minDistance) {
                p1 = points.get(i);
                minDistance = Point.distance(p1, p2);
            }
        }
        return p1;
    }
}
