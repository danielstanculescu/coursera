public class WeightedQuickUnion {
    private final int[] id;

    private final int[] sz;
    private final int n;
    private int count;

    public WeightedQuickUnion(int n) {
        this.n = n;
        count = n;
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int count() {
        return this.count;
    }

    public void union(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);
        if (rootp == rootq)
            return;
        if (sz[rootp] < sz[rootq]) {
            id[rootp] = rootq;
            sz[rootq] += sz[rootp];
        } else {
            id[rootq] = rootp;
            sz[rootp] += sz[rootq];
        }
        count--;
    }

    public boolean isConected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    public static void main(String[] args) {
        QuickUnion uf = new QuickUnion(10);
        uf.union(2, 9);
        uf.union(3, 4);
        uf.union(4, 9);
        uf.union(5, 6);
        uf.union(9, 6);

        System.out.println(uf.count());
    }
}
