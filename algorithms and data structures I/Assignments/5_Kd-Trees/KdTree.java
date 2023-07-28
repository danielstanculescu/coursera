import edu.princeton.cs.algs4.*;

import java.awt.*;

public class KdTree {

    private Node root;
    private int size = 0;

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left, right;

        public Node(Point2D point){
            this.point = point;
        }
    }

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null key");

        root = insert(root, p, 0);
        //check TODO
    }

    private Node insert (Node x, Point2D key, int level) {
        if (x == null) {
            size ++;
            return new Node(key);
        }

        if(key.compareTo(x.point) == 0)
            return x;

        level ++;

        if(level%2 == 1) {
            if (key.x() < x.point.x()) {
                x.left = insert(x.left, key, level);
            }
            else {
                x.right = insert(x.right, key, level);
            }
        }
        else {
            if (key.y() < x.point.y()) {
                x.left = insert(x.left, key, level);
            }
            else {
                x.right = insert(x.right, key, level);
            }
        }

        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls contains() with a null key");

        return get(root, p, 0) != null;
    }

    private Point2D get(Node x, Point2D key, int level){
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if(x == null) return null;

        if(key.compareTo(x.point) == 0)
            return x.point;

        level ++;

        if(level%2 == 1) {
            if (key.x() < x.point.x()) {
                return get(x.left, key, level);
            }
            else {
                return get(x.right, key, level);
            }
        }
        else {
            if (key.y() < x.point.y()) {
                return get(x.left, key, level);
            }
            else {
                return get(x.right, key, level);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0, true, 0.0, 1.0);
    }

    private void draw(Node h, int level, boolean left, double h_x, double h_y) {

        if(h == null)
            return;
        level ++;

        double x = h.point.x();
        double y = h.point.y();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(x,y,0.005);


        if(level%2 == 1) {
            StdDraw.setPenColor(StdDraw.RED);
            if(left) {
                double pointY = searchOddLeft(h.point);
                StdDraw.line(x, pointY, x, h_y);
            }
            else {
                double pointY = searchOddRight(h.point);
                StdDraw.line(x, h_y, x, pointY);
            }

        }
        else{
            StdDraw.setPenColor(StdDraw.BLUE);
            if(left){
                double pointX = searchEvenLeft(h.point);
                StdDraw.line(pointX, y, h_x, y);
            }

            else {
                // does intersect at right? the first intersection -> get x
                // point 0.6, 0.9 to right intersect something? yes 0.7
                // exist one point on x axis > 0.6 ?
                // line between 0.4 -> 0.7
                double pointX = searchEvenRight(h.point);
                StdDraw.line(h_x, y, pointX, y);
            }
        }

        draw(h.left, level, true, x, y);
        draw(h.right, level, false, x, y);

    }

    private double searchOddRight(Point2D p){
        double min = 1.0;
        if(root.point.y() > p.y())
            min = root.point.y();
        return searchOddRight(root, p, 0, min);
    }

    private double searchOddRight(Node x, Point2D key, int level, double min){
        if (key == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if(x == null) return 0.0;

        if(x.point.compareTo(key) == 0)
            return min;

        double keyY = key.y();
        double nodeY = x.point.y();

        level ++;

        if(level%2 == 1) {
            if (key.x() < x.point.x()) {
                return searchOddRight(x.left, key, level, min);
            }
            else {
                return searchOddRight(x.right, key, level, min);
            }
        }
        else {
            if(nodeY > keyY && nodeY < min)
                min = nodeY;
            if (key.y() < x.point.y()) {
                return searchOddRight(x.left, key, level, min);
            }
            else {
                return searchOddRight(x.right, key, level, min);
            }
        }
    }

    private double searchOddLeft(Point2D p){
        double max = 0.0;

        return searchOddLeft(root, p, 0, max);
    }

    private double searchOddLeft(Node x, Point2D key, int level, double max){
        if (key == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if(x == null) return 0.0;

        if(x.point.compareTo(key) == 0)
            return max;

        double keyY = key.y();
        double nodeY = x.point.y();

        level ++;

        if(level%2 == 1) {
            if (key.x() < x.point.x()) {
                return searchEvenLeft(x.left, key, level, max);
            }
            else {
                return searchEvenLeft(x.right, key, level, max);
            }
        }
        else {
            if(nodeY < keyY && nodeY > max)
                max = nodeY;
            if (key.y() < x.point.y()) {
                return searchEvenLeft(x.left, key, level, max);
            }
            else {
                return searchEvenLeft(x.right, key, level, max);
            }
        }
    }

    private double searchEvenRight(Point2D p){
        double min = 1.0;
        if(root.point.x() > p.x())
            min = root.point.x();
        return searchEvenRight(root, p, 0, min);
    }

    private double searchEvenRight(Node x, Point2D key, int level, double min){
        if (key == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if(x == null) return 0.0;

        if(x.point.compareTo(key) == 0)
            return min;

        double keyX = key.x();
        double nodeX = x.point.x();

        level ++;

        if(level%2 == 1) {
            if(nodeX > keyX && nodeX < min)
                min = nodeX;
            if (key.x() < x.point.x()) {
                return searchEvenRight(x.left, key, level, min);
            }
            else {
                return searchEvenRight(x.right, key, level, min);
            }
        }
        else {
            if (key.y() < x.point.y()) {
                return searchEvenRight(x.left, key, level, min);
            }
            else {
                return searchEvenRight(x.right, key, level, min);
            }
        }
    }

    private double searchEvenLeft(Point2D p){
        double max = 0.0;

        return searchEvenLeft(root, p, 0, max);
    }

    private double searchEvenLeft(Node x, Point2D key, int level, double max){
        if (key == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if(x == null) return 0.0;

        if(x.point.compareTo(key) == 0)
            return max;

        double keyX = key.x();
        double nodeX = x.point.x();

        level ++;

        if(level%2 == 1) {
            if(nodeX < keyX && nodeX > max)
                max = nodeX;
            if (key.x() < x.point.x()) {
                return searchEvenLeft(x.left, key, level, max);
            }
            else {
                return searchEvenLeft(x.right, key, level, max);
            }
        }
        else {
            if (key.y() < x.point.y()) {
                return searchEvenLeft(x.left, key, level, max);
            }
            else {
                return searchEvenLeft(x.right, key, level, max);
            }
        }
    }

    /*
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {

    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {

    }
*/
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);

        KdTree points = new KdTree();
        double[] allDoubles = in.readAllDoubles();

        for (int i = 0; i < allDoubles.length; i=i+2) {
            Point2D p = new Point2D(allDoubles[i], allDoubles[i+1]);
            points.insert(p);
        }

        Point2D p = new Point2D(0.6,0.9);
        StdOut.println(points.contains(p));

        points.draw();

        StdOut.println(points.contains(p));
    }
}
