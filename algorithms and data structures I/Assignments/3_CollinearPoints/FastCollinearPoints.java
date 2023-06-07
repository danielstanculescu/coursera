import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class FastCollinearPoints {

    private int numberOfSegments = 0;

    private LineSegment[] lineSegments;

    private final int MinLengthSegments = 4;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points)
    {
        double[] slopes = new double[points.length];
        double[] sortedSlopes = new double[points.length];

        Stack<Point> segmentsStack = new Stack<Point>();
        Stack<Double> slopesStack = new Stack<Double>();

        for (int x = 0; x < points.length ; x++) {

            Point p = points[x];
            for (int i = 0; i < points.length ; i++) {
                slopes[i] = p.slopeTo(points[i]);
            }

            for (int i = 0; i < points.length; i++) {
                sortedSlopes[i] = slopes[i];
            }

            Arrays.sort(sortedSlopes);

            Point[] pointsSorted = new Point[points.length];
            System.arraycopy(points, 0, pointsSorted, 0, points.length);
            Arrays.sort(pointsSorted, p.slopeOrder());

            Point lo = null;
            Point hi = null;
            for (int j=0;j<sortedSlopes.length;j++) {
                int number = 0;
                if(j<sortedSlopes.length-1 && !slopesStack.contains(sortedSlopes[j+1]) ) {
                    for (int k = j + 1; k < sortedSlopes.length; k++) {
                        if (k != j && sortedSlopes[k] == sortedSlopes[j]) {
                            if(sortedSlopes[k] == Double.NEGATIVE_INFINITY)
                                break;
                            number++;
                            if (number == MinLengthSegments - 2) {

                                slopesStack.push(sortedSlopes[k]);

                                // p with first
                                if (p.compareTo(pointsSorted[j]) <= 0) {
                                    lo = p;
                                    hi = pointsSorted[j];
                                }
                                else{
                                    lo = pointsSorted[j];
                                    hi = p;
                                }

                                // lo with second
                                if (lo.compareTo(pointsSorted[k-1]) <= 0) {

                                } else {
                                    lo = pointsSorted[k-1];
                                }
                                // hi with second
                                if (hi.compareTo(pointsSorted[k-1]) >= 0) {

                                } else {
                                    hi = pointsSorted[k-1];
                                }

                                // lo with third
                                if (lo.compareTo(pointsSorted[k]) <= 0) {

                                } else {
                                    lo = pointsSorted[k];
                                }
                                // hi with third
                                if (hi.compareTo(pointsSorted[k]) >= 0) {

                                } else {
                                    hi = pointsSorted[k];
                                }
                            }
                            else if (number > 2) {
                                if (lo.compareTo(pointsSorted[k]) <= 0) {

                                } else {
                                    lo = pointsSorted[k];
                                }

                                if (hi.compareTo(pointsSorted[k]) >= 0) {

                                } else {
                                    hi = pointsSorted[k];
                                }
                            }

                            // last element
                            if (k == sortedSlopes.length - 1) {
                                if (number >= 2) {
                                    segmentsStack.push(lo);
                                    segmentsStack.push(hi);
                                }
                            }
                        } else {
                            if (number >= 2) {
                                    segmentsStack.push(lo);
                                    segmentsStack.push(hi);
                                number = 0;
                            }
                            break;
                        }
                    }
                }
            }
        }

        lineSegments = new LineSegment[segmentsStack.size()/2];
        int i=0;
        while(!segmentsStack.isEmpty()){

            Point q1 = segmentsStack.pop();
            Point q2 = segmentsStack.pop();
            LineSegment ls = new LineSegment(q1, q2);

            lineSegments[i] = ls;
            i++;
        }
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments()
    {
        return lineSegments;
    }
}