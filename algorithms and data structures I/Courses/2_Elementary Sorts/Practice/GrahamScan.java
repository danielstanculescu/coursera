import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class GrahamScan {

    private final Point2D[] p;
    private Stack<Point2D> hull;

    private final int N;

    public GrahamScan(Point2D[] p){
        this.p = p;
        N = p.length;
    }

    public void computeHull(){
        hull = new Stack<Point2D>();

        Arrays.sort(p, Point2D.Y_ORDER);
        // the polar order comparator is specific to the point, not on the class(global)
        Arrays.sort(p, p[0].BY_POLAR_ORDER);
        hull.push(p[0]);
        hull.push(p[1]);

        for (int i = 2; i < N; i++) {
            Point2D top = hull.pop();
            while(Point2D.ccw(hull.peek(), top, p[i]) <= 0){
                top = hull.pop();
            }
            hull.push(top);
            hull.push(p[i]);
        }
    }
    public void displayHull(){
        while(!hull.empty()){
            Point2D point = hull.pop();
            StdOut.println(point.x + " " + point.y);
        }
    }
}
