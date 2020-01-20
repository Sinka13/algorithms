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
        Arrays.sort(points);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double slope1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < n; k++) {
                    double slope2 = points[j].slopeTo(points[k]);
                    for (int m = k + 1; m < n; m++) {
                        double slope3 = points[k].slopeTo(points[m]);
                        if (Double.compare(slope1, slope2) == 0
                                && Double.compare(slope2, slope3) == 0) {
                            LineSegment ls = new LineSegment(points[i], points[m]);
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
        } else {
            for (int i = 0; i < points.length; i++) {
                if (points[i] == null) throw new IllegalArgumentException();
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
                }
            }
        }
    }

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
    }
}
