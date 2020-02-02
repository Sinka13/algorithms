/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int size = 0;
    private Node root;
    private final Stack<Point2D> range = new Stack<Point2D>();

    private class Node {
        Node key;
        Point2D val;
        Node left, right;
        boolean even;
    }

    public KdTree() {
    }                       // construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }                     // is the set empty?

    public int size() {
        return size;
    }                     // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");
        Node n = new Node();
        n.val = p;
        if (root == null) {
            root = n;
            n.even = true;
            size++;
        }
        else {
            Node current = root;
            Node previous = root;
            boolean left = false;
            while (current != null) {
                if (current.val.equals(p)) return;
                previous = current;
                if (current.even) {
                    if (p.x() < current.val.x()) {
                        current = current.left;
                        left = true;
                    }
                    else {
                        current = current.right;
                        left = false;
                    }
                }
                else {
                    if (p.y() < current.val.y()) {
                        current = current.left;
                        left = true;
                    }
                    else {
                        current = current.right;
                        left = false;
                    }
                }

            }
            if (left) {
                previous.left = n;
            }
            else {
                previous.right = n;
            }
            n.key = previous;
            n.even = !previous.even;
            this.size++;

        }
    }           // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");
        Node current = root;
        boolean contains = false;
        while (current != null) {
            if (current.val.equals(p)) {
                contains = true;
                break;
            }
            else {
                if (current.even) {
                    if (p.x() < current.val.x()) {
                        current = current.left;
                    }
                    else {
                        current = current.right;
                    }
                }
                else {
                    if (p.y() < current.val.y()) {
                        current = current.left;
                    }
                    else {
                        current = current.right;
                    }
                }
            }
        }
        return contains;
    }         // does the set contain point p?

    public void draw() {
        loopTree(root);
    }                // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle can't be null");
        Node current = root;
        nextNode(current, rect);
        return this.range;
    }           // all points that are inside the rectangle (or on the boundary)

    private void nextNode(Node current, RectHV rect) {
        if (current == null) return;
        if (rect.contains(current.val)) this.range.push(current.val);
        if (current.even) {
            if (rect.xmax() < current.val.x()) {
                nextNode(current.left, rect);
            }
            else if (rect.xmin() > current.val.x()) {
                nextNode(current.right, rect);
            }
            else {
                nextNode(current.left, rect);
                nextNode(current.right, rect);
            }
        }
        else {
            if (rect.ymax() < current.val.y()) {
                nextNode(current.left, rect);
            }
            else if (rect.ymin() > current.val.y()) {
                nextNode(current.right, rect);
            }
            else {
                nextNode(current.left, rect);
                nextNode(current.right, rect);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Rectangle can't be null");
        if (isEmpty()) return null;
        Node n = root;
        double minDistance = n.val.distanceSquaredTo(p);
        Point2D result = n.val;
        return closestPoint(n, p, minDistance, result);
    }         // a nearest neighbor in the set to point p; null if the set is empty

    private Point2D closestPoint(Node n, Point2D point, double minDistance, Point2D result) {
        if (n == null) return result;
        double currentDistance = point.distanceSquaredTo(n.val);
        if (minDistance > currentDistance && !n.val.equals(point)) {
            minDistance = currentDistance;
            result = n.val;
        }
        Node next = null;
        if (n.even) {
            if (point.x() < n.val.x()) {
                next = n.left;
            }
            else {
                next = n.right;
            }
        }
        else {
            if (point.y() < n.val.y()) {
                next = n.left;
            }
            else {
                next = n.right;
            }
        }

        return closestPoint(next, point, minDistance, result);
    }

    private void loopTree(Node n) {
        if (n == null) {
            return;
        }
        loopTree(n.left);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        n.val.draw();
        if (n.even) {
            StdDraw.setPenColor(StdDraw.RED);
            if (n.key != null) {
                double parentY = n.key.val.y();
                double pointY = n.val.y();
                if (parentY > pointY) {
                    StdDraw.line(n.val.x(), 0.0, n.val.x(), parentY);
                }
                else {
                    StdDraw.line(n.val.x(), parentY, n.val.x(), 1.0);
                }
            }
            else {
                StdDraw.line(n.val.x(), 0.0, n.val.x(), 1.0);
            }
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            if (n.key != null) {
                double parentX = n.key.val.x();
                double pointX = n.val.x();
                if (parentX > pointX) {
                    StdDraw.line(0.0, n.val.y(), parentX, n.val.y());
                }
                else {
                    StdDraw.line(parentX, n.val.y(), 1.0, n.val.y());
                }

            }
            else {
                StdDraw.line(0.0, n.val.y(), 1.0, n.val.y());
            }
        }
        loopTree(n.right);
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();

        tree.insert(new Point2D(0.206107, 0.095492));
        tree.insert(new Point2D(0.975528, 0.654508));
        tree.insert(new Point2D(0.024472, 0.345492));
        tree.insert(new Point2D(0.793893, 0.095492));
        tree.insert(new Point2D(0.793893, 0.904508));
        tree.insert(new Point2D(0.975528, 0.345492));
        tree.insert(new Point2D(0.206107, 0.904508));
        tree.insert(new Point2D(0.500000, 0.000000));
        tree.insert(new Point2D(0.024472, 0.654508));
        tree.insert(new Point2D(0.500000, 1.000000));
        tree.draw();

        KdTree tree1 = new KdTree();
        tree1.size();
        tree1.insert(new Point2D(1.0, 0.0625));
        tree1.insert(new Point2D(0.5625, 0.1875));
        // RectHV r = new RectHV(0.1875, 0.6875, 0.1875, 0.5);
        // tree1.range(r);
        tree1.insert(new Point2D(0.0, 0.9375));
        tree1.isEmpty();
        tree1.contains(new Point2D(0.5, 0.0625));
        tree1.nearest(new Point2D(0.75, 0.5625));
    }             // unit testing of the methods (optional)
}
