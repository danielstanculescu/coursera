public class WeightedQuickUnion {
    private final int[] id;
    private final int[] sz;

    private final int[] max;
    private final int n;
    private int count;

    public WeightedQuickUnion(int n) {
        this.n = n;
        count = n;
        id = new int[n];
        sz = new int[n];
        max = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
            max[i] = i;
        }
    }

    public int find(int i) {
        return max[root(i)];
    }

    public int count() {
        return this.count;
    }

    public void union(int p, int q) {
        int rootp = root(p);
        int rootq = root(q);
        if (rootp == rootq)
            return;
        int maxp = max[rootp];
        int maxq = max[rootq];

        if(sz[rootp] < sz[rootq]) {
            id[rootp] = rootq;
            sz[rootq] += sz[rootp];

            if(maxp > maxq)
                max[rootq] = maxp;
            // else no change
        }
        else {
            id[rootq] = rootp;
            sz[rootp] += sz[rootq];

            if(maxq > maxp)
                max[rootp]= maxq;
            // else no change
        }

        /*
        if(maxp<maxq)
            max[id[p]] = maxq;
        else
            max[id[p]] = maxp;
        */
        count--;
    }

    public boolean isConected(int p, int q) {
        return root(p) == root(q);
    }

    public int root(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    public static void main(String[] args) {

    }
}
