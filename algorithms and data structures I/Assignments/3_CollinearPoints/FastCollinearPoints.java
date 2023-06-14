import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class FastCollinearPoints {
    private static final int MIN_LENGTH_SEGMENTS = 4;
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("argument to FastCollinearPoints constructor is null");

        Queue<Point> allPointsQueue = new Queue<Point>();
        for (int x = 0; x < points.length; x++) {
            Point p = points[x];
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

        Point[] sortedPointsNaturally = points.clone();
        Arrays.sort(sortedPointsNaturally);

        Queue<Point> segmentsQueue = new Queue<Point>();

        for (int x = 0; x < sortedPointsNaturally.length; x++) {
            Point p = sortedPointsNaturally[x];

            Point[] sortedPointsBySlope = sortedPointsNaturally.clone();
            Arrays.sort(sortedPointsBySlope, p.slopeOrder());

            Point lo = p;
            Point hi = null;
            int number = 2;
            boolean ignore = false;
            for (int j = 0; j < sortedPointsBySlope.length; j++) {
                if (j == 0)
                    continue;
                if (p.slopeTo(sortedPointsBySlope[j]) == p.slopeTo(sortedPointsBySlope[j - 1])) {
                    if (ignore)
                        continue;
                    if (p.compareTo(sortedPointsBySlope[j - 1]) > 0) {
                        ignore = true;
                        continue;
                    }

                    number++;

                    if (number >= MIN_LENGTH_SEGMENTS) {
                        hi = sortedPointsBySlope[j];
                    }

                    // last element
                    if (j == sortedPointsBySlope.length - 1) {
                        if (number >= MIN_LENGTH_SEGMENTS) {
                            segmentsQueue.enqueue(lo);
                            segmentsQueue.enqueue(hi);
                        }
                    }
                }
                // when next non equal slope found
                else {
                    if (number >= MIN_LENGTH_SEGMENTS) {
                        segmentsQueue.enqueue(lo);
                        segmentsQueue.enqueue(hi);
                    }
                    number = 2;
                    ignore = false;
                }
            }
        }

        numberOfSegments = segmentsQueue.size() / 2;
        lineSegments = new LineSegment[numberOfSegments];
        int i = 0;
        while (!segmentsQueue.isEmpty()) {
            Point q1 = segmentsQueue.dequeue();
            Point q2 = segmentsQueue.dequeue();
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