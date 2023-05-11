import edu.princeton.cs.algs4.StdOut;

public class SuccessorWithDelete {
    private final int n;
    private final boolean[] info;

    private final WeightedQuickUnion wqu;

    public SuccessorWithDelete(int n) {
        this.n = n;
        wqu = new WeightedQuickUnion(n);
        info = new boolean[n];
        for (int i = 0; i < n; i++) {
            info[i] = true;
        }
    }

    public void remove(int p) {
        info[p] = false;
        if (p < n - 1)
            wqu.union(p, p + 1);
    }

    public int successor(int p) {

        int max = wqu.find(p);
        if (max == n - 1 && !info[max])
            return -1;

        return max;
    }

    public static void main(String[] args) {
        SuccessorWithDelete swd = new SuccessorWithDelete(10);
        swd.remove(3);
        swd.remove(4);
        //swd.remove(8);
        swd.remove(9);
        StdOut.println(swd.successor(3));
    }
}