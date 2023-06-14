import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int numberOfSegments = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("argument to FastCollinearPoints constructor is null");

        Queue<Point> allPointsQueue = new Queue<Point>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if (p == null)
                throw new IllegalArgumentException("point in the array is null");

            for (int j = 0; j < allPointsQueue.size(); j++) {
                Point q = allPointsQueue.dequeue();
                if (p.compareTo(q) == 0) {
                    throw new IllegalArgumentException("the argument to the constructor contains a repeated point");
                } else {
                    allPointsQueue.enqueue(q);
                }
            }
            allPointsQueue.enqueue(p);
        }

        double slope1, slope2, slope3;
        Stack<Point> segmentsStack = new Stack<Point>();

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i == j)
                    continue;
                slope1 = points[i].slopeTo(points[j]);
                if (points[i].compareTo(points[j]) >= 0)
                    continue;
                third:
                for (int k = 0; k < points.length; k++) {
                    if (k == i || k == j)
                        continue;
                    if (points[j].compareTo(points[k]) >= 0)
                        continue;
                    slope2 = points[j].slopeTo(points[k]);

                    if (slope1 == slope2) {
                        for (int m = 0; m < points.length; m++) {
                            if (m == i || m == j || m == k)
                                continue;
                            if (points[k].compareTo(points[m]) >= 0)
                                continue;
                            slope3 = points[k].slopeTo(points[m]);

                            if (slope1 == slope3) {
                                numberOfSegments++;
                                segmentsStack.push(points[i]);
                                segmentsStack.push(points[m]);
                                break third;
                            }
                        }
                    }
                }
            }
        }

        lineSegments = new LineSegment[segmentsStack.size() / 2];
        int i = 0;
        while (!segmentsStack.isEmpty()) {

            Point q1 = segmentsStack.pop();
            Point q2 = segmentsStack.pop();
            LineSegment ls = new LineSegment(q1, q2);

            lineSegments[i] = ls;
            i++;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] auxLineSegments = new LineSegment[lineSegments.length];
        System.arraycopy(lineSegments, 0, auxLineSegments, 0, lineSegments.length);
        return auxLineSegments;
    }
}