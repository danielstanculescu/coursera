import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> setOfPoints;

    // construct an empty set of points
    public PointSET() {
        setOfPoints = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return setOfPoints.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("calls insert() with a null key");

        setOfPoints.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("calls contains() with a null key");

        return setOfPoints.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : setOfPoints) {
            point2D.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("calls range() with a null key");

        Queue<Point2D> points = new Queue<Point2D>();
        for (Point2D point2D : setOfPoints) {
            if (rect.contains(point2D))
                points.enqueue(point2D);
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("calls nearest() with a null key");

        if (isEmpty())
            return null;

        Point2D nearestPoint = null;
        //double minDistance = 1.0;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D point2D : setOfPoints) {
            double tmpDistance = point2D.distanceSquaredTo(p);
            if (tmpDistance < distance) {
                distance = tmpDistance;
                nearestPoint = point2D;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);

        PointSET points = new PointSET();
        double[] allDoubles = in.readAllDoubles();
        for (int i = 0; i < allDoubles.length; i = i + 2) {
            Point2D p = new Point2D(allDoubles[i], allDoubles[i + 1]);
            points.insert(p);
        }

        points.draw();

        RectHV rhv = new RectHV(0.392, 0.200, 0.574, 0.492);
        StdDraw.setPenColor(StdDraw.BLUE);
        rhv.draw();
        for (Point2D p2d : points.range(rhv)) {
            StdOut.println(p2d.toString());
        }

        Point2D p2d = new Point2D(0.392, 0.200);
        StdOut.println(points.nearest(p2d).toString());


        PointSET set2 = new PointSET();
        set2.insert(new Point2D(1.0, 1.0));
        set2.insert(new Point2D(1.0, 0.0));
        StdOut.println(set2.nearest(new Point2D(0.0, 1.0)));
    }
}
