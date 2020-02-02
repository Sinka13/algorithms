/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private int size = 0;
    private final SET<Point2D> set;

    public PointSET() {
        this.set = new SET<Point2D>();
    }                          // construct an empty set of points

    public boolean isEmpty() {
        return this.size == 0;
    }                  // is the set empty?

    public int size() {
        return this.size;
    }                    // number of points in the set

    public void insert(Point2D p) {
        if (!set.contains(p)) {
            size++;
            set.add(p);
        }
    }            // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return set.contains(p);
    }         // does the set contain point p?

    public void draw() {
        for (Point2D p : set)
            p.draw();
    }            // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle can't be null");
        SET<Point2D> rectSet = new SET<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                rectSet.add(p);
            }
        }
        return rectSet;
    }           // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");
        if (set.isEmpty()) return null;
        Point2D nearest = null;
        double minDistance = Double.NaN;
        for (Point2D that : set) {
            double distance = p.distanceSquaredTo(that);
            if (Double.isNaN(minDistance) || Double.compare(minDistance, distance) > 0) {
                minDistance = distance;
                nearest = that;
            }
        }
        return nearest;

    }           // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        PointSET s = new PointSET();
        s.insert(new Point2D(1, 0));
    }           // unit testing of the methods (optional)
}
