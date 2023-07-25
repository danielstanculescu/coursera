import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

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
    }
}
