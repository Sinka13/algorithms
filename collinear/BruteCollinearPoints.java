/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segmentsArray = new LineSegment[0];
    private final ArrayList<LineSegment> sgm = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        checkPointsArray(points);
        int n = points.length;
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double slope1 = sortedPoints[i].slopeTo(points[j]);
                for (int k = j + 1; k < n; k++) {
                    double slope2 = sortedPoints[j].slopeTo(points[k]);
                    for (int m = k + 1; m < n; m++) {
                        double slope3 = sortedPoints[k].slopeTo(points[m]);
                        if (Double.compare(slope1, slope2) == 0
                                && Double.compare(slope2, slope3) == 0) {
                            LineSegment ls = new LineSegment(sortedPoints[i], sortedPoints[m]);
                            sgm.add(ls);
                        }
                    }
                }
            }
        }
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return segmentsArray.length;
    }   // the number of line segments

    public LineSegment[] segments() {
        segmentsArray = sgm.toArray(new LineSegment[sgm.size()]);
        return Arrays.copyOf(segmentsArray, numberOfSegments());
    }                 // the line segments

    private void checkPointsArray(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        else {
            for (int i = 0; i < points.length; i++) {
                if (points[i] == null) throw new IllegalArgumentException();
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                }
            }
        }
    }

    // private LineSegment createLineSegmentFromFourPoints(Point p1, Point p2, Point p3, Point p4) {
    //     Point min, max, min1, min2, max1, max2;
    //     if (p1.compareTo(p2) == -1) {
    //         min1 = p1;
    //         max1 = p2;
    //     }
    //     else {
    //         min1 = p2;
    //         max1 = p1;
    //     }
    //
    //     if (p3.compareTo(p4) == -1) {
    //         min2 = p3;
    //         max2 = p4;
    //     }
    //     else {
    //         min2 = p4;
    //         max2 = p3;
    //     }
    //
    //     if (min1.compareTo(min2) == -1) {
    //         min = min1;
    //     }
    //     else {
    //         min = min2;
    //     }
    //     if (max1.compareTo(max2) == -1) {
    //         max = max2;
    //     }
    //     else {
    //         max = max1;
    //     }
    //     LineSegment ls = new LineSegment(min, max);
    //     return ls;
    // }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        //
        // // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        // BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // collinear.numberOfSegments();
    }
}
