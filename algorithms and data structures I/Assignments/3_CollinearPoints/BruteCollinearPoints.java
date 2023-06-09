import java.util.Stack;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private int numberOfSegments = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("argument to FastCollinearPoints constructor is null");

        double slope1, slope2, slope3;

        Stack<Point> segmentsStack = new Stack<Point>();
        Stack<Double> slopesStack = new Stack<Double>();
        Stack<Point> allPointsStack = new Stack<Point>();

        for (int x = 0; x < points.length; x++) {
            if (points[x] == null)
                throw new IllegalArgumentException("point in the array is null");

            Point p = points[x];

            for (int i = 0; i < allPointsStack.size(); i++) {
                if (allPointsStack.elementAt(i).compareTo(p) == 0)
                    throw new IllegalArgumentException("the argument to the constructor contains a repeated point");
            }
            allPointsStack.push(p);
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    slope1 = points[i].slopeTo(points[j]);
                    if (points[i].compareTo(points[j]) >= 0)
                        continue;
                    third:
                    for (int k = 0; k < points.length; k++) {
                        if (k != i && k != j) {
                            if (points[j].compareTo(points[k]) >= 0)
                                continue;
                            slope2 = points[j].slopeTo(points[k]);

                            if (slope1 == slope2) {
                                for (int l = 0; l < points.length; l++) {
                                    if (l != i && l != j && l != k) {
                                        if (points[k].compareTo(points[l]) >= 0)
                                            continue;
                                        slope3 = points[k].slopeTo(points[l]);

                                        if (slope1 == slope3) {

                                            numberOfSegments++;
                                            slopesStack.push(slope3);
                                            segmentsStack.push(points[i]);
                                            segmentsStack.push(points[l]);
                                            break third;
                                        }
                                    }
                                }
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
        return this.lineSegments;
    }
}