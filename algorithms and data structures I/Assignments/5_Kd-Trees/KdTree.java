import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class KdTree {
    private Node root;
    private int size = 0;
    private double distance = Double.POSITIVE_INFINITY;
    private Point2D nearest = null;

    private static class Node {
        private final Point2D point;
        private double pointLimitFromX = -1.0, pointLimitFromY = -1.0, pointLimitToX = -1.0, pointLimitToY = -1.0;
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
            if (key.x() < h.point.x())
                h.left = insert(h.left, key, level);
            else
                h.right = insert(h.right, key, level);

        } else {
            if (key.y() < h.point.y())
                h.left = insert(h.left, key, level);
            else
                h.right = insert(h.right, key, level);
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

    private HashMap<Double, MinMax> h = new HashMap<Double, MinMax>();
    private HashMap<Double, MinMax> v = new HashMap<Double, MinMax>();

    private void setMinDistance(Point2D reference, Point2D point) {
        if (reference.distanceSquaredTo(point) < distance) {
            distance = reference.distanceSquaredTo(point);
            nearest = point;
        }
    }

    private double distanceSquaredTo(Point2D p, double thatX, double thatY) {
        double dx = p.x() - thatX;
        double dy = p.y() - thatY;
        return dx * dx + dy * dy;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("calls nearest() with a null key");
        if (isEmpty())
            return null;

        distance = Double.POSITIVE_INFINITY;

        // init
        if (root.pointLimitFromX == -1) {
            v.put(0.0, new MinMax(0.0, 1.0));
            v.put(1.0, new MinMax(0.0, 1.0));

            h.put(0.0, new MinMax(0.0, 1.0));
            h.put(1.0, new MinMax(0.0, 1.0));
        }

        computeNearest(root, p, 0);

        return nearest;
    }

    private void computeNearest(Node current, Point2D p, int level) {

        if (current == null)
            return;

        double currentX = current.point.x();
        double currentY = current.point.y();
        double pX = p.x();
        double pY = p.y();

        level++;

        // vertical
        if (level % 2 == 1) {
            if (current.pointLimitFromX == -1) {
                ArrayList<Double> sortedKeys = new ArrayList<Double>(h.keySet());

                Collections.sort(sortedKeys);

                if (sortedKeys.contains(currentY)) {
                    int indexCurrentValue = sortedKeys.indexOf(currentY);
                    if (currentY == 1.0) {
                        current.pointLimitFromX = currentX;
                        current.pointLimitFromY = getValidLess(indexCurrentValue, sortedKeys, currentX, true);
                        current.pointLimitToX = currentX;
                        current.pointLimitToY = 1.0;
                    } else {
                        current.pointLimitFromX = currentX;
                        current.pointLimitFromY = getValidLess(indexCurrentValue, sortedKeys, currentX, true);
                        current.pointLimitToX = currentX;
                        current.pointLimitToY = getValidNext(indexCurrentValue, sortedKeys, currentX, true);
                    }
                } else {
                    sortedKeys.add(currentY);
                    Collections.sort(sortedKeys);
                    int indexCurrentValue = sortedKeys.indexOf(currentY);
                    current.pointLimitFromX = currentX;
                    current.pointLimitFromY = getValidLess(indexCurrentValue, sortedKeys, currentX, true);
                    current.pointLimitToX = currentX;
                    current.pointLimitToY = getValidNext(indexCurrentValue, sortedKeys, currentX, true);
                    sortedKeys.remove(currentY);
                }

                if (!v.containsKey(currentX)) {
                    v.put(currentX, new MinMax(current.pointLimitFromY, current.pointLimitToY));
                }
            }
            // left
            if (pX < currentX) {
                if (isLeaf(current)) {
                    setMinDistance(p, current.point);
                } else {
                    computeNearest(current.left, p, level);

                    setMinDistance(p, current.point);

                    // direct line
                    if (pY >= current.pointLimitFromY && pY <= current.pointLimitToY) {
                        double d = pX - currentX;
                        if (distance > d * d) {
                            computeNearest(current.right, p, level);
                        }
                    }
                    // line to the edge
                    else {
                        if (pY > current.pointLimitToY) {
                            double distanceToSurface = distanceSquaredTo(p, currentX, current.pointLimitToY);
                            if (distance > distanceToSurface)
                                computeNearest(current.right, p, level);
                        } else if (pY < current.pointLimitFromY) {
                            double distanceToSurface = distanceSquaredTo(p, currentX, current.pointLimitFromY);
                            if (distance > distanceToSurface)
                                computeNearest(current.right, p, level);
                        }
                    }

                }
            }
            // right
            else {
                if (isLeaf(current)) {
                    setMinDistance(p, current.point);
                } else {
                    computeNearest(current.right, p, level);

                    setMinDistance(p, current.point);

                    // direct line
                    if (pY >= current.pointLimitFromY && pY <= current.pointLimitToY) {
                        double d = pX - currentX;
                        if (distance > d * d) {
                            computeNearest(current.left, p, level);
                        }
                    }
                    // line to the edge
                    else {
                        if (pY > current.pointLimitToY) {
                            double distanceToSurface = distanceSquaredTo(p, currentX, current.pointLimitToY);
                            if (distance > distanceToSurface)
                                computeNearest(current.left, p, level);
                        } else if (pY < current.pointLimitFromY) {
                            double distanceToSurface = distanceSquaredTo(p, currentX, current.pointLimitFromY);
                            if (distance > distanceToSurface)
                                computeNearest(current.left, p, level);
                        }
                    }
                }
            }
        }

        // horizontal
        else {
            if (current.pointLimitFromX == -1) {
                ArrayList<Double> sortedKeys = new ArrayList<Double>(v.keySet());
                Collections.sort(sortedKeys);

                if (sortedKeys.contains(currentX)) {
                    int indexCurrentValue = sortedKeys.indexOf(currentX);
                    if (currentX == 1.0) {
                        current.pointLimitFromX = getValidLess(indexCurrentValue, sortedKeys, currentY, false);
                        current.pointLimitFromY = currentY;
                        current.pointLimitToX = 1.0;
                        current.pointLimitToY = currentY;
                    } else {
                        current.pointLimitFromX = getValidLess(indexCurrentValue, sortedKeys, currentY, false);
                        current.pointLimitFromY = currentY;
                        current.pointLimitToX = getValidNext(indexCurrentValue, sortedKeys, currentY, false);
                        current.pointLimitToY = currentY;
                    }
                } else {
                    sortedKeys.add(currentX);
                    Collections.sort(sortedKeys);
                    int indexCurrentValue = sortedKeys.indexOf(currentX);
                    current.pointLimitFromX = getValidLess(indexCurrentValue, sortedKeys, currentY, false);
                    current.pointLimitFromY = currentY;
                    current.pointLimitToX = getValidNext(indexCurrentValue, sortedKeys, currentY, false);
                    current.pointLimitToY = currentY;
                    sortedKeys.remove(currentX);
                }

                if (!h.containsKey(currentY)) {
                    h.put(currentY, new MinMax(current.pointLimitFromX, current.pointLimitToX));
                }
            }
            // down
            if (pY < currentY) {
                if (isLeaf(current)) {
                    setMinDistance(p, current.point);

                } else {
                    computeNearest(current.left, p, level);
                    // current node is closer than the current best
                    setMinDistance(p, current.point);

                    // direct line
                    if (pX >= current.pointLimitFromX && pX <= current.pointLimitToX) {
                        double d = pY - currentY;
                        if (distance > d * d) {
                            computeNearest(current.right, p, level);
                        }
                    }
                    // line to the edge
                    else {
                        if (pX > current.pointLimitToX) {
                            double distanceToSurface = distanceSquaredTo(p, current.pointLimitToX, currentY);
                            if (distance > distanceToSurface)
                                computeNearest(current.right, p, level);
                        } else if (pX < current.pointLimitFromX) {
                            double distanceToSurface = distanceSquaredTo(p, current.pointLimitFromX, currentY);
                            if (distance > distanceToSurface)
                                computeNearest(current.right, p, level);
                        }
                    }

                }
            }
            // up
            else {
                if (isLeaf(current)) {
                    setMinDistance(p, current.point);

                } else {
                    computeNearest(current.right, p, level);
                    // current node is closer than the current best
                    setMinDistance(p, current.point);

                    // direct line
                    if (pX >= current.pointLimitFromX && pX <= current.pointLimitToX) {
                        double d = pY - currentY;
                        if (distance > d * d) {
                            computeNearest(current.left, p, level);
                        }
                    }
                    // line to the edge
                    else {
                        if (pX > current.pointLimitToX) {
                            double distanceToSurface = distanceSquaredTo(p, current.pointLimitToX, currentY);
                            if (distance > distanceToSurface)
                                computeNearest(current.left, p, level);
                        } else if (pX < current.pointLimitFromX) {
                            double distanceToSurface = distanceSquaredTo(p, current.pointLimitFromX, currentY);
                            if (distance > distanceToSurface)
                                computeNearest(current.left, p, level);
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

        // points.draw();

        // StdOut.println(points.contains(p));

        // RectHV rhv = new RectHV(0.205, 0.01, 0.38, 0.26);
        RectHV rhv = new RectHV(0.6, 0.5, 0.98, 0.91);

        for (Point2D pInRange :
                points.range(rhv)) {
            StdOut.println(pInRange);
        }

        Point2D targetPoint = new Point2D(0.43, 0.52);
        Point2D nearestPoint = points.nearest(targetPoint);
        StdOut.println("Nearest : " + nearestPoint);


        Point2D targetPoint2 = new Point2D(0.40, 0.52);
        Point2D nearestPoint2 = points.nearest(targetPoint2);
        StdOut.println("Nearest : " + nearestPoint2);

        Point2D targetPoint3 = new Point2D(0.40, 0.52);
        Point2D nearestPoint3 = points.nearest(targetPoint3);
        StdOut.println("Nearest : " + nearestPoint3);


        //points.doWorkVertical();
        //points.doWorkHorizontal();


    }


    private void doWorkHorizontal() {
        v = new HashMap<Double, MinMax>();
        v.put(0.0, new MinMax(0.0, 1.0));
        v.put(1.0, new MinMax(0.0, 1.0));
        v.put(0.5, new MinMax(0.0, 1.0));
        v.put(0.75, new MinMax(0.0, 1.0));

        ArrayList<Double> sortedKeys = new ArrayList<Double>(v.keySet());

        Collections.sort(sortedKeys);

        double currentValueX = 0.76;
        double currentValueY = 1;

        double limitLeft = 0.0;
        double limitRight = 1.0;

        if (sortedKeys.contains(currentValueX)) {
            int indexCurrentValue = sortedKeys.indexOf(currentValueX);
            if (currentValueX == 1.0) {
                limitLeft = getValidLess(indexCurrentValue, sortedKeys, currentValueY, false);
                limitRight = 1.0;
            } else {
                limitLeft = getValidLess(indexCurrentValue, sortedKeys, currentValueY, false);
                limitRight = getValidNext(indexCurrentValue, sortedKeys, currentValueY, false);
            }
        } else {

            sortedKeys.add(currentValueX);
            Collections.sort(sortedKeys);
            int indexCurrentValue = sortedKeys.indexOf(currentValueX);
            limitLeft = getValidLess(indexCurrentValue, sortedKeys, currentValueY, false);
            limitRight = getValidNext(indexCurrentValue, sortedKeys, currentValueY, false);
            sortedKeys.remove(currentValueX);
        }

        StdOut.println("limitLeft : " + limitLeft + " limitRight : " + limitRight);

    }


    private void doWorkVertical() {
        h = new HashMap<Double, MinMax>();
        h.put(0.0, new MinMax(0.0, 1.0));
        h.put(1.0, new MinMax(0.0, 1.0));
        // h.put(0.25, new MinMax(0.5,1.0));
        // h.put(0.75, new MinMax(0.0,0.49));

        ArrayList<Double> sortedKeys = new ArrayList<Double>(h.keySet());

        Collections.sort(sortedKeys);

        double currentValueX = 0.5;
        double currentValueY = 1.0;

        double limitLeft = 0.0;
        double limitRight = 1.0;

        if (sortedKeys.contains(currentValueY)) {
            int indexCurrentValue = sortedKeys.indexOf(currentValueY);
            if (currentValueY == 1.0) {
                limitLeft = getValidLess(indexCurrentValue, sortedKeys, currentValueX, true);
                limitRight = 1.0;
            } else {
                limitLeft = getValidLess(indexCurrentValue, sortedKeys, currentValueX, true);
                limitRight = getValidNext(indexCurrentValue, sortedKeys, currentValueX, true);
            }
        } else {

            sortedKeys.add(currentValueY);
            Collections.sort(sortedKeys);
            int indexCurrentValue = sortedKeys.indexOf(currentValueY);
            limitLeft = getValidLess(indexCurrentValue, sortedKeys, currentValueX, true);
            limitRight = getValidNext(indexCurrentValue, sortedKeys, currentValueX, true);
            sortedKeys.remove(currentValueY);
        }

        StdOut.println("limitLeft : " + limitLeft + " limitRight : " + limitRight);
    }

    private double getValidNext(int indexCurrentValue, ArrayList<Double> sortedKeys, double currentValue, boolean vertical) {
        double returnValue = 1.0;
        for (int i = indexCurrentValue; i < sortedKeys.size(); i++) {
            double nextValue = sortedKeys.get(i + 1);
            double min = 0.0;
            double max = 1.0;
            if (vertical) {
                if (!h.containsKey(nextValue))
                    continue;
                min = h.get(nextValue).min;
                max = h.get(nextValue).max;
            } else {
                if (!v.containsKey(nextValue))
                    continue;
                min = v.get(nextValue).min;
                max = v.get(nextValue).max;
            }
            if (currentValue >= min && currentValue <= max)
                return nextValue;
        }
        return returnValue;
    }

    private double getValidLess(int indexCurrentValue, ArrayList<Double> sortedKeys, double currentValue, boolean vertical) {
        double returnValue = 0.0;
        for (int i = indexCurrentValue; i > 0; i--) {
            double lessValue = sortedKeys.get(i);
            if (lessValue == 1.0)
                continue;

            double min = 0.0;
            double max = 1.0;
            if (vertical) {
                if (!h.containsKey(lessValue))
                    continue;
                min = h.get(lessValue).min;
                max = h.get(lessValue).max;
            } else {
                if (!v.containsKey(lessValue))
                    continue;
                min = v.get(lessValue).min;
                max = v.get(lessValue).max;
            }

            if (currentValue >= min && currentValue <= max)
                return lessValue;
        }
        return returnValue;
    }

    private static class MinMax {
        private final double min;
        private final double max;

        private MinMax(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }


}

