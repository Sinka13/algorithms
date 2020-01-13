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
import java.util.Collections;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> sgm = new ArrayList<LineSegment>();
    private LineSegment[] segmentsArray;

    public FastCollinearPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            Point[] sortedBySlope = Arrays.copyOf(points, points.length);
            Arrays.sort(sortedBySlope);
            Arrays.sort(sortedBySlope, points[i].slopeOrder());
            ArrayList<Point> collinear = new ArrayList<Point>();

            int linesCount = 0;
            for (int j = 0; j < sortedBySlope.length - 1; j++) {
                if (Double.compare(points[i].slopeTo(sortedBySlope[j]),
                        points[i].slopeTo(sortedBySlope[j + 1])) == 0) {
                    if (linesCount == 0) {
                        collinear.add(sortedBySlope[j]);
                        linesCount++;
                    }
                    collinear.add(sortedBySlope[j + 1]);
                    linesCount++;

                    if (j == sortedBySlope.length - 2) {
                        if (linesCount >= 3) {
                            collinear.add(points[i]);
                            Collections.sort(collinear);
                            if (points[i].compareTo(collinear.get(0)) == 0) {
                                LineSegment ls = new LineSegment(collinear.get(0),
                                        collinear.get(collinear.size()
                                                - 1));
                                sgm.add(ls);

                            }

                        }
                        linesCount = 0;
                        collinear = new ArrayList<Point>();
                    }
                } else {
                    if (linesCount >= 3) {
                        collinear.add(points[i]);
                        Collections.sort(collinear);
                        if (points[i].compareTo(collinear.get(0)) == 0) {
                            LineSegment ls = new LineSegment(collinear.get(0),
                                    collinear.get(collinear.size() - 1));
                            sgm.add(ls);

                        }
                    }
                    collinear = new ArrayList<Point>();
                    linesCount = 0;
                }
            }
        }
    } // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return segmentsArray.length;
    }       // the number of line segments

    public LineSegment[] segments() {
        segmentsArray = sgm.toArray(new LineSegment[sgm.size()]);
        return Arrays.copyOf(segmentsArray, numberOfSegments());
    }               // the line segments


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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
