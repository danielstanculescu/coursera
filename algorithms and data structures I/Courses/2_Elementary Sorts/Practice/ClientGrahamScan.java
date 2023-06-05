public class ClientGrahamScan {
    public static void main(String[] args){
        Point2D[] points = new Point2D[6];
        points[0] = new Point2D(20,1);
        points[1] = new Point2D(19,8);
        points[2] = new Point2D(18,7);
        points[3] = new Point2D(17,5);
        points[4] = new Point2D(16,9);
        points[5] = new Point2D(10,10);
        GrahamScan gs = new GrahamScan(points);
        gs.computeHull();
        gs.displayHull();
    }
}
