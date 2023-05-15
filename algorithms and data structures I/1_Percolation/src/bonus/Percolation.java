package bonus;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private static final int OPEN = 4;
    private static final int OPEN_TOP = 5;
    private static final int OPEN_BOTTOM = 6;
    private static final int OPEN_TOP_BOTTOM = 7;
    private final int n;
    private final byte[] sites;
    private final WeightedQuickUnionUF completeGrid;
    private int numberOfOpenSites = 0;
    private boolean doesPercolate;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException();

        this.n = n;

        completeGrid = new WeightedQuickUnionUF(n * n);

        sites = new byte[n * n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        checkRowColumn(row, col);

        if (isOpen(row, col))
            return;

        numberOfOpenSites++;

        int siteIndex = map2Dto1DStartingFrom1(row, col);

        sites[siteIndex] = OPEN;

        if (n == 1) {
            doesPercolate = true;
            sites[siteIndex] = OPEN_TOP;
            return;
        } else {
            if (row == 1) {
                sites[siteIndex] = OPEN_TOP;
            }
            if (row == n) {
                sites[siteIndex] = OPEN_BOTTOM;
            }
        }

        boolean neighbour = false;
        // Left
        if (col > 1 && isOpen(row, col - 1)) {
            // StdOut.println("Left: " + (siteIndex - 1) + " " + siteIndex);
            byte rootStatus = sites[completeGrid.find(map2Dto1DStartingFrom1(row, col - 1))];
            byte orStatus = (byte) (sites[siteIndex] | rootStatus);
            sites[siteIndex] = orStatus;
            if (orStatus == OPEN_TOP_BOTTOM)
                doesPercolate = true;
            completeGrid.union(siteIndex - 1, siteIndex);
            neighbour = true;
        }
        // Up
        if (row > 1 && isOpen(row - 1, col)) {
            // StdOut.println("Up: " + map2Dto1D(row-1, col) + " " + siteIndex);
            byte rootStatus = sites[completeGrid.find(map2Dto1DStartingFrom1(row - 1, col))];
            byte orStatus = (byte) (sites[siteIndex] | rootStatus);
            sites[siteIndex] = orStatus;
            if (orStatus == OPEN_TOP_BOTTOM)
                doesPercolate = true;
            completeGrid.union(map2Dto1DStartingFrom1(row - 1, col), siteIndex);
            neighbour = true;
        }
        // Down
        if (row < n && isOpen(row + 1, col)) {
            // StdOut.println("Down: " + map2Dto1D(row+1, col) + " " + siteIndex);
            byte rootStatus = sites[completeGrid.find(map2Dto1DStartingFrom1(row + 1, col))];
            byte orStatus = (byte) (sites[siteIndex] | rootStatus);
            sites[siteIndex] = orStatus;
            if (orStatus == OPEN_TOP_BOTTOM)
                doesPercolate = true;
            completeGrid.union(map2Dto1DStartingFrom1(row + 1, col), siteIndex);
            neighbour = true;
        }
        // Right
        if (col < n && isOpen(row, col + 1)) {
            // StdOut.println("Right: " + map2Dto1D(row, col+1) + " " + siteIndex);
            byte rootStatus = sites[completeGrid.find(map2Dto1DStartingFrom1(row, col + 1))];
            byte orStatus = (byte) (sites[siteIndex] | rootStatus);
            sites[siteIndex] = orStatus;
            if (orStatus == OPEN_TOP_BOTTOM)
                doesPercolate = true;
            completeGrid.union(map2Dto1DStartingFrom1(row, col + 1), siteIndex);
            neighbour = true;
        }

        if (neighbour)
            sites[completeGrid.find(map2Dto1DStartingFrom1(row, col))] = sites[siteIndex];
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        checkRowColumn(row, col);

        return sites[map2Dto1DStartingFrom1(row, col)] >= OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        checkRowColumn(row, col);

        byte rootStatus = sites[completeGrid.find(map2Dto1DStartingFrom1(row, col))];
        return (rootStatus == OPEN_TOP || rootStatus == OPEN_TOP_BOTTOM);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        return doesPercolate;
    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation p = new Percolation(1);
        p.test(p, 1, 1);
        p.test(p, 2, 3);
        p.open(1, 1);

        StdOut.println(p.percolates());
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
        return (row - 1) * n + (col - 1);
    }
}

