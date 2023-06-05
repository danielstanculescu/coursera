import java.util.Comparator;

public class Point2D {
    public final double x;
    public final double y;
    public static final Comparator<Point2D> Y_ORDER = new ByYOrder();
    public final Comparator<Point2D> BY_POLAR_ORDER = new ByPolarOrder();

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);  // TODO: danger of floating-point roundoff error
        if (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else return 0;
    }

    private static class ByYOrder implements Comparator<Point2D>{
        public int compare(Point2D a, Point2D b){
            if(a.y < b.y)
                return -1;
            else if(a.y > b.y)
                return +1;
            return 0;
        }
    }

    private class ByPolarOrder implements Comparator<Point2D>{
        public int compare(Point2D q1, Point2D q2){

            double dy1 = q1.y - y;
            double dy2 = q2.y - y;

            if( dy1 == 0 && dy2 == 0) {
                if(q1.x > Point2D.this.x && q2.x < Point2D.this.x)
                    return -1;
                else return 0;
            }
            else if(dy1 >= 0 && dy2 < 0) return -1;
            else if(dy2 >= 0 && dy1 < 0) return +1;
            else return -ccw(Point2D.this, q1, q2);
        }
    }
}
