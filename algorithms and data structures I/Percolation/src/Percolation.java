import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private boolean[] sites;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF completeGrid;
    private WeightedQuickUnionUF topGrid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException();

        this.n = n;
        int sitesNumber = n * n;

        completeGrid = new WeightedQuickUnionUF(sitesNumber + 2);
        topGrid = new WeightedQuickUnionUF(sitesNumber + 1);

        sites = new boolean[sitesNumber + 1];

        for (int i = 1; i <= n; i++) {
            completeGrid.union(0, i);
            completeGrid.union(n*n+1, n*n+1-i);

            topGrid.union(0, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        checkRowColumn(row, col);

        if (isOpen(row, col))
            return;

        numberOfOpenSites++;

        int siteIndex = map2Dto1DStartingFrom1(row, col);

        sites[siteIndex] = true;

        // Left
        if (col > 1 && isOpen(row, col-1)) {
            // StdOut.println("Left: " + (siteIndex - 1) + " " + siteIndex);
            completeGrid.union(siteIndex - 1, siteIndex);
            topGrid.union(siteIndex - 1, siteIndex);
        }
        // Up
        if (row > 1 && isOpen(row-1, col)) {
            // StdOut.println("Up: " + map2Dto1D(row-1, col) + " " + siteIndex);
            completeGrid.union(map2Dto1DStartingFrom1(row-1, col), siteIndex);
            topGrid.union(map2Dto1DStartingFrom1(row-1, col), siteIndex);
        }
        // Down
        if (row < n && isOpen(row+1, col)) {
            // StdOut.println("Down: " + map2Dto1D(row+1, col) + " " + siteIndex);
            completeGrid.union(map2Dto1DStartingFrom1(row+1, col), siteIndex);
            topGrid.union(map2Dto1DStartingFrom1(row+1, col), siteIndex);
        }
        // Right
        if (col < n && isOpen(row, col+1)) {
            // StdOut.println("Right: " + map2Dto1D(row, col+1) + " " + siteIndex);
            completeGrid.union(map2Dto1DStartingFrom1(row, col+1), siteIndex);
            topGrid.union(map2Dto1DStartingFrom1(row, col+1), siteIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        checkRowColumn(row, col);

        if (sites[map2Dto1DStartingFrom1(row, col)])
            return true;

        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        checkRowColumn(row, col);

        int rootOfSite = topGrid.find(map2Dto1DStartingFrom1(row, col));

        if (isOpen(row, col) && (rootOfSite == topGrid.find(0)))
            return true;

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        if (completeGrid.find(0) == completeGrid.find(n*n+1)) {
            if (n > 1 || (n == 1 && isOpen(1, 1)))
                return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation p  = new Percolation(3);

        p.open(1,1);
        p.open(2,1);
        p.open(3,1);
        p.open(3,3);
        StdOut.println(p.isFull(3, 3));

    }

    private void test(Percolation p, int r, int c) {
        p.open(r, c);
        boolean isOpen = p.isOpen(r, c);
        StdOut.println("-----" + r + "-----" + c + "------");
        StdOut.println("isOpen(" + r + "," + c + ") = " + isOpen);
        boolean percolates = p.percolates();
        StdOut.println("percolates? " + percolates);
        int number = p.numberOfOpenSites();
        StdOut.println("number of open sites : " + number);
        boolean isFull = p.isFull(r, c);
        StdOut.println("isFull(" + r + "," + c + ") = " + isFull);
    }

    private void checkRowColumn(int row, int col) {
        if (row < 1 || row > n)
            throw new IllegalArgumentException();
        if (col < 1 || col > n)
            throw new IllegalArgumentException();
    }

    private int map2Dto1DStartingFrom1(int row, int col) {
        return (row-1) * n + col;
    }
}

