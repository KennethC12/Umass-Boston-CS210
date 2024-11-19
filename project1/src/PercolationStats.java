import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    private final int m;
    private final double[] fractions;

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        // Initializes instance variables.
        this.m = m;
        fractions = new double[m];

        for (int i = 0; i < m; i++) {
            UFPercolation p = new UFPercolation(n); // Creates an n-by-n percolation system.
            double openSites = 0;

            while (!p.percolates()) {
                int r = StdRandom.uniform(0, n); // Random selection of a row for site.
                int c = StdRandom.uniform(0, n); // Random selection of a colum for site.

                if (!p.isOpen(r, c)) {
                    p.open(r, c);
                    openSites++;
                }
            }
            // Calculates percolation threshold as the fraction of sites opened, and stored
            // in x[].
            double fraction = openSites / (n * n);
            fractions[i] = fraction;
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(fractions);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - ((1.96 * StdStats.stddev(fractions)) / Math.sqrt(m));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + ((1.96 * StdStats.stddev(fractions)) / Math.sqrt(m));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}