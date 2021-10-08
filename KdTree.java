package com.company;

/*************************************************************************
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.*;
import org.w3c.dom.Node;


public class KdTree {
    private Node root;
    private int size;

    class Node {
        private Point2D p; //the point
        private RectHV rect;  //the axis-aligned rectangle corresponding to this node
        private Node l; //the left/bottom subtree
        private Node r; //the right/top subtree
        private boolean vertical;

        public Node(Point2D p, Node l, Node r, boolean v) {
            this.p = p;
            l = l;
            r = r;
            vertical = v;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {

            root = insert(root, p, true);

    }

    ;

    private Node insert(Node n, Point2D p, boolean isvertical) {

        // create node
        if (n == null) {
            size++;
            return new Node(p, null, null, isvertical);
        }
        if(n.p.x() == p.x() && n.p.y() == p.y()) {
            return n;
        }
        if (n.vertical && p.x() > n.p.x() || !n.vertical && p.y() < n.p.y()) {
            n.l = insert(n.l, p, !n.vertical);
        } else {
            n.r = insert(n.r, p, !n.vertical);
        }

        return n;
    }


    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p.x(), p.y());
    }

    private boolean contains(Node n, double x, double y) {

        if (n == null){
            return false;

        }
        if (n.p.x() == x && n.p.y() == y){
            StdOut.print("TRUE: "+n.p.x()+" = "+x+"\n"+n.p.y()+" = "+y+"\n\n");
            return true;
        }
        StdOut.print(n.p.x()+" = "+x+"\n"+n.p.y()+" = "+y+"\n\n");
        if (n.vertical && x < n.p.x() || !n.vertical && y < n.p.y())
            return contains(n.r , x, y);
        else
            return contains(n.l, x, y);



    }

    // draw all of the points to standard draw
    public void draw() {

    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return p;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In in = new In("C:/Users/Tómas Orri/Downloads/KDtrees.txt");
        Out out = new Out();
        KdTree kdtree = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //brute.insert(p);
        }
        Point2D p = new Point2D(0.19,0.57);
        StdOut.print(kdtree.contains(p));
        /*int nrOfRectangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();

        RectHV[] rectangles = new RectHV[nrOfRectangles];

        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        for (int i = 0; i < nrOfRectangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        KdTree set = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRectangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
        */

    }
}
