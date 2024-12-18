import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private int n;
    private final boolean[][] open; // Declares array of booleans called open.
    private int openSites; // Declares int called openSites.

    private int source; // Declares uf object.
    private int sink; // Declares uf2 object.

    WeightedQuickUnionUF uf;

    WeightedQuickUnionUF bw;
    // Use virtual source and sink sites introduces what is called the back wash
    // problem

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        // Initialize instance variables
        this.n = n;
        this.openSites = 0;
        open = new boolean[n][n];
        source = 0;
        sink = (n * n) + 1;
        uf = new WeightedQuickUnionUF((n * n) + 2);
        bw = new WeightedQuickUnionUF((n * n) + 1);

    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if ((i < 0) || (j < 0) || (i > n - 1) || (j > n - 1)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!open[i][j]) {
            // Open the site
            open[i][j] = true;
            // Increment openSites by one
            openSites++;

            // If the site is in the first (or last) row, connect the corresponding uf site
            // with the source (or sink)
            if (i == 0) {
                uf.union(encode(i, j), source);
                bw.union(encode(i, j), source);
            }
            if (i == n - 1) {
                uf.union(encode(i, j), sink);
            }
            // If any of the neighbors to the north, east, west, and south of site (i, j) is
            // open, connect the uf site corresponding to site (i, j) with the uf site
            // corresponding to that neighbor.
            if ((i - 1) >= 0 && open[i - 1][j]) {
                uf.union(encode(i, j), encode(i - 1, j));
                bw.union(encode(i, j), encode(i - 1, j));
            }
            if ((i + 1) < n && open[i + 1][j]) {
                uf.union(encode(i, j), encode(i + 1, j));
                bw.union(encode(i, j), encode(i + 1, j));
            }
            if ((j - 1) >= 0 && open[i][j - 1]) {
                uf.union(encode(i, j), encode(i, j - 1));
                bw.union(encode(i, j), encode(i, j - 1));
            }
            if ((j + 1) < n && open[i][j + 1]) {
                uf.union(encode(i, j), encode(i, j + 1));
                bw.union(encode(i, j), encode(i, j + 1));
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if ((i < 0) || (j < 0) || (i > n - 1) || (j > n - 1)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if ((i < 0) || (j < 0) || (i > n - 1) || (j > n - 1)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return isOpen(i, j) && uf.connected(encode(i, j), source) && bw.connected(encode(i, j), source);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return uf.connected(sink, source);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return n * i + j + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}