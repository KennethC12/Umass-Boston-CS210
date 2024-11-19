import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.
public class ArrayPercolation implements Percolation {
    private final int n;
    private final boolean[][] open;
    private int openSites;

    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }

        this.n = n;
        open = new boolean[n][n];
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if ((i < 0) || (j < 0) || (i > n - 1) || (j > n - 1)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }

        if (!open[i][j]) { // Checks if site is closed
            open[i][j] = true; // Opens the site
            openSites++; // Increments the site by one
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if ((i < 0) || (j < 0) || (i > n - 1) || (j > n - 1)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Return whether site (i, j) is open or not
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if ((i < 0) || (j < 0) || (i > n - 1) || (j > n - 1)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Create an n × n array of booleans called full.
        boolean[][] full = new boolean[n][n];
        // Call floodFill() on every site in the first row of the percolation system,
        // passing full as the first argument
        for (int k = 0; k < n; k++) {
            floodFill(full, 0, k);
        }
        // return full[i][j]
        return full[i][j];
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // Return whether the system percolates or
        // not — a system percolates if the last row contains at least one full site
        for (int i = 0; i < n; i++) {
            if (isFull(n - 1, i)) {
                return true;
            }
        }
        return false;
    }

    // Recursively flood fills full[][] using depth-first exploration, starting at
    // (i, j).
    private void floodFill(boolean[][] full, int i, int j) {
        if ((i < 0) || (i >= n) || (j < 0) || (j >= n) || (!isOpen(i, j)) || (full[i][j])) {
            return;

        }
        full[i][j] = true;

        // Call floodFill() recursively on the sites to the north,
        // east, west, and south of site (i, j)
        floodFill(full, i, j - 1);
        floodFill(full, i, j + 1);
        floodFill(full, i - 1, j);
        floodFill(full, i + 1, j);
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
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