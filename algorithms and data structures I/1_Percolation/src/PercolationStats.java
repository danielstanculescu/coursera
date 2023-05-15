import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private double[] percolatesTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.trials = trials;
        percolatesTrials = new double[trials];

        Percolation p;
        int row, col;
        double openSites;
        for (int t = 0; t < trials; t++) {
            p = new Percolation(n);

            while (!p.percolates()) {
                row = StdRandom.uniformInt(1, n+1);
                col = StdRandom.uniformInt(1, n+1);

                p.open(row, col);
            }
            openSites = p.numberOfOpenSites();
            percolatesTrials[t] = openSites/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolatesTrials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolatesTrials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev())/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev())/Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2)
            return;
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println("mean\t\t\t = " + ps.mean());
        StdOut.println("stddev\t\t\t = " + ps.stddev());
        StdOut.println("95% confidence interval  = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}