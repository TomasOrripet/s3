package com.company;

/*************************************************************************
 *************************************************************************/

import java.awt.*;
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

        public Node(Point2D p, RectHV rect, Node l, Node r, boolean v) {
            this.p = p;
            this.rect = rect;
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

        root = insert(root, p, true, 0.0, 0.0, 1.0, 1.0);

    }

    ;

    private Node insert(Node n, Point2D p, boolean isvertical, double x0, double y0, double x1, double y1) {

        // create node
        if (n == null) {
            size++;
            RectHV rect = new RectHV(x0, y0, x1, y1);
            return new Node(p, rect, null, null, isvertical);
        }
        if (n.p.x() == p.x() && n.p.y() == p.y()) {
            return n;
        }

        if (n.vertical) {
            double cmp = p.x() - n.p.x();
            //if (p.x() < n.p.x()) {
            if (cmp < 0) {
                n.l = insert(n.l, p, !isvertical, x0, y0, n.p.x(), y1);
            } else {
                n.r = insert(n.r, p, !isvertical, n.p.x(), y0, x1, y1);
            }
        } else {
            double cmp = p.y() - n.p.y();
            if (cmp < 0) {
                //if (p.y() < n.p.y()) {
                n.l = insert(n.l, p, !isvertical, x0, y0, x1, n.p.y());
            } else {
                n.r = insert(n.r, p, !isvertical, x0, n.p.y(), x1, y1);
            }
        }

        /*if (n.vertical && p.x() > n.p.x() || !n.vertical && p.y() < n.p.y()) {
            n.l = insert(n.l, p, !n.vertical);
        } else {
            n.r = insert(n.r, p, !n.vertical);
        }
         */
        return n;
    }


    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p.x(), p.y());
    }

    private boolean contains(Node n, double x, double y) {

        if (n == null) {
            return false;

        }
        else if (n.p.x() == x && n.p.y() == y) {
            return true;
        }
        else{

        }
        if (n.vertical) {
            if (x < n.p.x()) {
                return contains(n.l, x, y);
            }
            else{
                return contains(n.r, x, y);
            }
        }
        else {
            if (y < n.p.y()) {
                return contains(n.l, x, y);
            }
            else {
                return contains(n.r, x, y);
            }
        }
    }


    // draw all of the points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean drawVert) {
        if (node == null) return;
        // Draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        // Draw vertical line with x-coordinates of the point and y-coordinates
        // of the parent rectangle
        if (drawVert) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        // Draw horizontal line with y-coordinates of the point and x-coordinates
        // of the parent rectangle
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        // Draw subtrees
        draw(node.l, !drawVert);
        draw(node.r, !drawVert);
    }
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> allPoint = new SET<Point2D>();
        Iterable(root, rect, allPoint);
        return allPoint;
    }

    private void Iterable(Node n, RectHV rect, SET<Point2D> allPoint){
        if (n == null){
            return;
        }
        if (rect.contains(n.p))
            allPoint.add(n.p);
        if (rect.intersects(n.rect)){
            Iterable(n.l,rect,allPoint);
            Iterable(n.r,rect,allPoint);
        }
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (root == null){
            return null;
        }
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node n, Point2D p, Point2D closest, boolean vertical){
        if(n == null) {
            return closest;
        }

        if (n.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p)){
            closest = n.p;
        }
        if (n.rect.distanceSquaredTo(p) < closest.distanceSquaredTo(p)){
            //closest = findsubtree(n.l.p, n.r.p);
            if (!vertical && p.x() < n.p.x() || !vertical && p.y() < n.p.y()){
                closest = nearest(n.l,p,closest,!vertical);
                closest = nearest(n.r,p,closest, !vertical);
            }
            else{
                closest = nearest(n.r,p,closest,!vertical);
                closest = nearest(n.l,p,closest, !vertical);
            }

        }
        return closest;
    }



    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In in = new In("C:/Users/Tómas Orri/Downloads/KDtrees.txt");
        Out out = new Out();
        KdTree kdtree = new KdTree();
        Stopwatch timer = new Stopwatch();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            //brute.insert(p);
        }
        double time = timer.elapsedTime();
        StdOut.print(time);
        kdtree.draw();
        Point2D p = new Point2D(0.73,0.29);

        StdOut.print(kdtree.contains(p)+"\n");
        StdOut.print(kdtree.nearest(p));
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
