
import dsa.LinkedQueue;
import dsa.MinPQ;
import dsa.Point2D;
import dsa.RectHV;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.StdIn;
import stdlib.StdOut;

public class BrutePointST<Value> implements PointST<Value> {

    // Creates an instance variable of RedBlackBinarySearchTreeST
    private RedBlackBinarySearchTreeST<Point2D, Value> bst;

    // Constructs an empty symbol table.
    public BrutePointST() {
        // Initializes the instance variable bst appropriately.
        this.bst = new RedBlackBinarySearchTreeST<Point2D, Value>();
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        // Returns whether bst is empty.
        return size() == 0;
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        // Returns the size of bst.
        return bst.size();
    }

    // Inserts the given point and value into this symbol table.
    public void put(Point2D p, Value value) {
        // Corner cases.
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        // Inserts the given point and value into bst.
        bst.put(p, value);
    }

    // Returns the value associated with the given point in this symbol table, or
    // null.
    public Value get(Point2D p) {
        // Corner case.
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        // Returns the value associated with the given point in bst, or null.
        return bst.get(p);
    }

    // Returns true if this symbol table contains the given point, and false
    // otherwise.
    public boolean contains(Point2D p) {
        // Corner case.
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        // Returns whether bst contains the given point.
        return bst.contains(p);
    }

    // Returns all the points in this symbol table.
    public Iterable<Point2D> points() {
        // Returns an iterable object containing all the points in bst.
        return bst.keys();
    }

    // Returns all the points in this symbol table that are inside the given
    // rectangle.
    public Iterable<Point2D> range(RectHV rect) {
        // Corner cases.
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        // Iterates over the keys.
        for (Point2D p : bst.keys()) {
            if (rect.contains(p)) { // if the rectangle contains p, enqueue p
                queue.enqueue(p);
            }
        }
        // Returns an iterable object containing all the points in
        // bst that are inside the given rectangle.
        return queue;
    }

    // Returns the point in this symbol table that is different from and closest to
    // the given point,
    // or null.
    public Point2D nearest(Point2D p) {
        // Corner case.
        if (isEmpty()) {
            throw new NullPointerException("p is null");
        }
        // Returns a point from bst that is different from
        // and closest to the given point.
        return nearest(p, 1).iterator().next();
    }

    // Returns up to k points from this symbol table that are different from and
    // closest to the
    // given point.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // Corner case.
        if (isEmpty()) {
            throw new NullPointerException("p is null");
        }
        // Creates new minPQ.
        MinPQ<Point2D> pq = new MinPQ<Point2D>(size(), p.distanceToOrder());
        // Iterates over the keys.
        for (Point2D a : bst.keys()) {
            if (a.equals(p)) {
                continue;
            }
            pq.insert(a);
        }
        // Creates new LinkedQueue.
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        for (int i = 0; i < k; i++) {
            Point2D temp = pq.delMin();
            queue.enqueue(temp);
        }
        return queue;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}