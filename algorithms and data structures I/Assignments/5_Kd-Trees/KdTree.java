import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size = 0;
    private int count = -1;
    private double distance = Double.POSITIVE_INFINITY;
    private Point2D nearest = null;

    private static class Node {
        private final Point2D point;
        private Point2D from;
        private Point2D to;
        private Node left, right;

        public Node(Point2D point) {
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
    }

    private Node insert(Node h, Point2D key, int level) {
        if (h == null) {
            size++;
            return new Node(key);
        }

        if (key.compareTo(h.point) == 0)
            return h;

        level++;

        if (level % 2 == 1) {
            if (key.x() < h.point.x()) {
                h.left = insert(h.left, key, level);
            } else {
                h.right = insert(h.right, key, level);
            }
        } else {
            if (key.y() < h.point.y()) {
                h.left = insert(h.left, key, level);
            } else {
                h.right = insert(h.right, key, level);
            }
        }
        return h;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls contains() with a null key");

        return get(root, p, 0) != null;
    }

    private Point2D get(Node x, Point2D key, int level) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;

        if (key.compareTo(x.point) == 0)
            return x.point;

        level++;

        if (level % 2 == 1) {
            if (key.x() < x.point.x()) {
                return get(x.left, key, level);
            } else {
                return get(x.right, key, level);
            }
        } else {
            if (key.y() < x.point.y()) {
                return get(x.left, key, level);
            } else {
                return get(x.right, key, level);
            }
        }
    }

    private void setNodeFromTo() {
        setNodeFromTo(root, 0, true, 0.0, 1.0);
    }

    private void setNodeFromTo(Node h, int level, boolean left, double hX, double hY) {

        if (h == null)
            return;
        level++;

        double x = h.point.x();
        double y = h.point.y();

        if (level % 2 == 1) {
            if (left) {
                double pointY = searchOddLeft(h.point);
                h.from = new Point2D(x, pointY);
                h.to = new Point2D(x, hY);
            } else {
                double pointY = searchOddRight(h.point);
                h.from = new Point2D(x, hY);
                h.to = new Point2D(x, pointY);
            }
        } else {
            if (left) {
                double pointX = searchEvenLeft(h.point);
                h.from = new Point2D(pointX, y);
                h.to = new Point2D(hX, y);
            } else {
                double pointX = searchEvenRight(h.point);
                h.from = new Point2D(hX, y);
                h.to = new Point2D(pointX, y);
            }
        }

        setNodeFromTo(h.left, level, true, x, y);
        setNodeFromTo(h.right, level, false, x, y);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0, true, 0.0, 1.0);
    }

    private void draw(Node h, int level, boolean left, double hX, double hY) {

        if (h == null)
            return;
        level++;

        double x = h.point.x();
        double y = h.point.y();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(x, y, 0.005);

        if (level % 2 == 1) {
            StdDraw.setPenColor(StdDraw.RED);
            if (left) {
                double pointY = searchOddLeft(h.point);
                StdDraw.line(x, pointY, x, hY);
            } else {
                double pointY = searchOddRight(h.point);
                StdDraw.line(x, hY, x, pointY);
            }
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            if (left) {
                double pointX = searchEvenLeft(h.point);
                StdDraw.line(pointX, y, hX, y);
            } else {
                double pointX = searchEvenRight(h.point);
                StdDraw.line(hX, y, pointX, y);
            }
        }

        draw(h.left, level, true, x, y);
        draw(h.right, level, false, x, y);
    }

    private double searchOddRight(Point2D p) {
        double min = 1.0;
        return searchOddRight(root, p, 0, min);
    }

    private double searchOddRight(Node node, Point2D point, int level, double min) {
        if (point == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if (node == null) return 0.0;

        if (node.point.compareTo(point) == 0)
            return min;

        double keyX = point.x();
        double keyY = point.y();
        double nodeX = node.point.x();
        double nodeY = node.point.y();

        level++;

        if (level % 2 == 1) {
            if (keyX < nodeX) {
                return searchOddRight(node.left, point, level, min);
            } else {
                return searchOddRight(node.right, point, level, min);
            }
        } else {
            if (nodeY == keyY)
                return nodeY;
            if (nodeY > keyY && nodeY < min)
                min = nodeY;
            if (keyY < nodeY) {
                return searchOddRight(node.left, point, level, min);
            } else {
                return searchOddRight(node.right, point, level, min);
            }
        }
    }

    private double searchOddLeft(Point2D p) {
        double max = 0.0;
        return searchOddLeft(root, p, 0, max);
    }

    private double searchOddLeft(Node node, Point2D point, int level, double max) {
        if (point == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if (node == null) return 0.0;

        if (node.point.compareTo(point) == 0)
            return max;

        double keyX = point.x();
        double keyY = point.y();
        double nodeX = node.point.x();
        double nodeY = node.point.y();

        level++;

        if (level % 2 == 1) {
            if (keyX < nodeX) {
                return searchOddLeft(node.left, point, level, max);
            } else {
                return searchOddLeft(node.right, point, level, max);
            }
        } else {
            if (nodeY == keyY)
                return nodeY;
            if (nodeY < keyY && nodeY > max)
                max = nodeY;
            if (keyY < nodeY) {
                return searchOddLeft(node.left, point, level, max);
            } else {
                return searchOddLeft(node.right, point, level, max);
            }
        }
    }

    private double searchEvenRight(Point2D p) {
        double min = 1.0;
        return searchEvenRight(root, p, 0, min);
    }

    private double searchEvenRight(Node node, Point2D point, int level, double min) {
        if (point == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if (node == null) return 0.0;

        if (node.point.compareTo(point) == 0)
            return min;

        double keyX = point.x();
        double keyY = point.y();
        double nodeX = node.point.x();
        double nodeY = node.point.y();

        level++;

        if (level % 2 == 1) {
            if (keyX == nodeX)
                return nodeX;
            if (nodeX > keyX && nodeX < min)
                min = nodeX;
            if (keyX < nodeX) {
                return searchEvenRight(node.left, point, level, min);
            } else {
                return searchEvenRight(node.right, point, level, min);
            }
        } else {
            if (keyY < nodeY) {
                return searchEvenRight(node.left, point, level, min);
            } else {
                return searchEvenRight(node.right, point, level, min);
            }
        }
    }

    private double searchEvenLeft(Point2D p) {
        double max = 0.0;
        return searchEvenLeft(root, p, 0, max);
    }

    private double searchEvenLeft(Node node, Point2D point, int level, double max) {
        if (point == null) throw new IllegalArgumentException("calls searchNodeXY() with a null key");
        if (node == null) return 0.0;

        if (node.point.compareTo(point) == 0)
            return max;

        double pX = point.x();
        double pY = point.y();
        double nodeX = node.point.x();
        double nodeY = node.point.y();

        level++;

        if (level % 2 == 1) {
            if (nodeX == pX)
                return nodeX;
            if (nodeX < pX && nodeX > max)
                max = nodeX;
            if (pX < nodeX) {
                return searchEvenLeft(node.left, point, level, max);
            } else {
                return searchEvenLeft(node.right, point, level, max);
            }
        } else {
            if (pY < nodeY) {
                return searchEvenLeft(node.left, point, level, max);
            } else {
                return searchEvenLeft(node.right, point, level, max);
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("calls range() with a null key");

        Queue<Point2D> points = new Queue<Point2D>();

        rangeRect(root, rect, 0, points);

        return points;
    }

    private void rangeRect(Node x, RectHV rect, int level, Queue<Point2D> queue) {

        if (x == null)
            return;

        double nodeX = x.point.x();
        double nodeY = x.point.y();

        level++;

        // vertical
        if (level % 2 == 1) {
            // rect is on the left of the slide bar
            if (rect.xmax() < nodeX) {
                rangeRect(x.left, rect, level, queue);
            } else {
                // rect is on the right of the slide bar
                if (rect.xmin() > nodeX) {
                    rangeRect(x.right, rect, level, queue);
                } else {
                    // rect intersects the slide bar
                    if (rect.xmin() <= nodeX && rect.xmax() >= nodeX) {
                        rangeRect(x.left, rect, level, queue);
                        rangeRect(x.right, rect, level, queue);
                        if (rect.contains(x.point))
                            queue.enqueue(x.point);
                    }
                }
            }
        }

        // horizontal
        else {
            // rect is below of the slide bar
            if (rect.ymax() < nodeY) {
                rangeRect(x.left, rect, level, queue);
            } else {
                // rect is above of the slide bar
                if (rect.ymin() > nodeY) {
                    rangeRect(x.right, rect, level, queue);
                } else {
                    // rect intersects the slide bar
                    if (rect.ymin() <= nodeY && rect.ymax() >= nodeY) {
                        rangeRect(x.left, rect, level, queue);
                        rangeRect(x.right, rect, level, queue);
                        if (rect.contains(x.point))
                            queue.enqueue(x.point);
                    }
                }
            }
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("calls nearest() with a null key");
        if (isEmpty())
            return null;

        distance = Double.POSITIVE_INFINITY;
        if (count != size) {
            setNodeFromTo();
            count = size;
        }

        computeNearest(p);
        return nearest;
    }

    private void computeNearest(Point2D p) {

        double pX = p.x();
        double pY = p.y();
        nearP(root, p, pX, pY, 0);
    }

    private void computeMinDistance(double tmpDistance, Node current) {
        if (tmpDistance < distance) {
            distance = tmpDistance;
            nearest = current.point;
        }
    }


    private void nearP(Node current, Point2D p, double pX, double pY, int level) {

        if (current == null)
            return;

        StdOut.println(current.point);

        double currentX = current.point.x();
        double currentY = current.point.y();
        double tmpDistance;

        level++;

        // vertical
        if (level % 2 == 1) {
            // left
            if (pX < currentX) {
                if (isLeaf(current)) {
                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);

                } else {
                    nearP(current.left, p, pX, pY, level);

                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);

                    if (pY >= current.from.y() && pY <= current.to.y()) {
                        double d = pX - currentX;
                        if (distance > d * d) {
                            nearP(current.right, p, pX, pY, level);
                        }
                    } else {
                        double distanceToSurface = p.distanceSquaredTo(current.to);
                        if (distance > distanceToSurface) {
                            nearP(current.right, p, pX, pY, level);
                        }
                    }
                }
            }
            // right
            else {
                if (isLeaf(current)) {
                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);

                } else {
                    nearP(current.right, p, pX, pY, level);

                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);

                    if (pY >= current.from.y() && pY <= current.to.y()) {
                        double d = pX - currentX;
                        if (distance > d * d) {
                            nearP(current.left, p, pX, pY, level);
                        }
                    } else {
                        double distanceToSurface = p.distanceSquaredTo(current.to);
                        if (distance > distanceToSurface) {
                            nearP(current.left, p, pX, pY, level);
                        }
                    }
                }
            }
        }

        // horizontal
        else {
            // down
            if (pY < currentY) {
                if (isLeaf(current)) {
                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);
                } else {
                    nearP(current.left, p, pX, pY, level);

                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);

                    if (pX >= current.from.x() && pX <= current.to.x()) {
                        double d = pY - currentY;
                        if (distance > d * d) {
                            nearP(current.right, p, pX, pY, level);
                        }
                    } else {
                        double distanceToSurface = p.distanceSquaredTo(current.to);
                        if (distance > distanceToSurface) {
                            nearP(current.right, p, pX, pY, level);
                        }
                    }
                }
            }
            // up
            else {
                if (isLeaf(current)) {
                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);
                } else {
                    nearP(current.right, p, pX, pY, level);

                    tmpDistance = p.distanceSquaredTo(current.point);
                    computeMinDistance(tmpDistance, current);

                    if (pX >= current.from.x() && pX <= current.to.x()) {
                        double d = pY - currentY;
                        if (distance > d * d) {
                            nearP(current.left, p, pX, pY, level);
                        }
                    } else {
                        double distanceToSurface = p.distanceSquaredTo(current.to);
                        if (distance > distanceToSurface) {
                            nearP(current.left, p, pX, pY, level);
                        }
                    }
                }
            }
        }
    }

    private boolean isLeaf(Node n) {
        return n.left == null && n.right == null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);

        KdTree points = new KdTree();
        double[] allDoubles = in.readAllDoubles();

        for (int i = 0; i < allDoubles.length; i = i + 2) {
            Point2D p = new Point2D(allDoubles[i], allDoubles[i + 1]);
            points.insert(p);
        }

        Point2D p = new Point2D(0.6, 0.9);
        // StdOut.println(points.contains(p));

        //points.draw();

        // StdOut.println(points.contains(p));

        // RectHV rhv = new RectHV(0.205, 0.01, 0.38, 0.26);
        RectHV rhv = new RectHV(0.6, 0.5, 0.98, 0.91);

        for (Point2D pInRange :
                points.range(rhv)) {
            StdOut.println(pInRange);
        }

        Point2D targetPoint = new Point2D(0.43, 0.52);
        //StdDraw.filledCircle(targetPoint.x(), targetPoint.y(), 0.005);
        Point2D nearestPoint = points.nearest(targetPoint);
        StdOut.println("Nearest : " + nearestPoint);

        //points.insert(new Point2D(0.1, 0.8));

        Point2D targetPoint2 = new Point2D(0.40, 0.52);
        Point2D nearestPoint2 = points.nearest(targetPoint2);
        StdOut.println("Nearest : " + nearestPoint2);

        Point2D targetPoint3 = new Point2D(0.40, 0.52);
        Point2D nearestPoint3 = points.nearest(targetPoint3);
        StdOut.println("Nearest : " + nearestPoint2);
    }
}
