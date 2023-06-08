import java.util.Arrays;

public class BruteCollinearPoints_v1 {
    private LineSegment[] lineSegments;
    private int numberOfSegments = 0;

    private int[][] possibleSegments = new int[1][4];
    private final int[] tampon = new int[4];

    public BruteCollinearPoints_v1(Point[] points)    // finds all line segments containing 4 points
    {
        double slope1, slope2, slope3;
        //int compare1, compare2, compare3;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    slope1 = points[i].slopeTo(points[j]);

                    for (int k = 0; k < points.length; k++) {
                        if (k != i && k != j) {
                            slope2 = points[j].slopeTo(points[k]);

                            if (slope1 == slope2) {
                                for (int l = 0; l < points.length; l++) {
                                    if (l != i && l != j && l != k) {
                                        slope3 = points[k].slopeTo(points[l]);

                                        if (slope1 == slope3) {

                                            tampon[0] = i;
                                            tampon[1] = j;
                                            tampon[2] = k;
                                            tampon[3] = l;
                                            Arrays.sort(tampon);

                                            if (numberOfSegments == 0) {
                                                possibleSegments[0][0] = tampon[0];
                                                possibleSegments[0][1] = tampon[1];
                                                possibleSegments[0][2] = tampon[2];
                                                possibleSegments[0][3] = tampon[3];
                                                numberOfSegments++;
                                            } else {
                                                boolean isFound = false;
                                                for (int m = 0; m < possibleSegments.length; m++) {
                                                    if (possibleSegments[m][0] == tampon[0] && possibleSegments[m][1] == tampon[1] && possibleSegments[m][2] == tampon[2] && possibleSegments[m][3] == tampon[3]) {
                                                        isFound = true;
                                                        break;
                                                    }
                                                }
                                                if (!isFound) {
                                                    resize(numberOfSegments+1);  // use maybe stack push pop?
                                                    possibleSegments[numberOfSegments][0] = tampon[0];
                                                    possibleSegments[numberOfSegments][1] = tampon[1];
                                                    possibleSegments[numberOfSegments][2] = tampon[2];
                                                    possibleSegments[numberOfSegments][3] = tampon[3];
                                                    numberOfSegments++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
        lineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            LineSegment ls = new LineSegment(points[possibleSegments[i][0]], points[possibleSegments[i][3]]);
            lineSegments[i] = ls;
        }
    }
    private void resize(int capacity) {

        int[][] copy = new int[capacity][4];
        for (int i = 0; i < possibleSegments.length; i++) {
            System.arraycopy(possibleSegments[i], 0, copy[i], 0, 4);
        }
        possibleSegments = copy;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return numberOfSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        return this.lineSegments;
    }


}