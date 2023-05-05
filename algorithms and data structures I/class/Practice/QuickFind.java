public class QuickFind {

    private int[] id;
    private int n;
    private int count;

    public QuickFind(int n) {
        this.n = n;
        id = new int[n];

        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        count = n;
    }

    public void union(int p, int q){
        int idp = id[p];
        int idq = id[q];
        if(idp == idq)
            return;

        for (int i = 0; i < n; i++) {
            if(id[i] == idp)
                id[i] = idq;
        }
        count--;
    }

    public int count(){
        return this.count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        return id[p];
    }

    public static void main(String[] args){
        QuickFind qf = new QuickFind(10);
        qf.union(0, 5);
        qf.union(5, 6);

        qf.union(1, 2);
        qf.union(7, 2);

        qf.union(8, 3);
        qf.union(3, 4);
        qf.union(4, 9);

        qf.union(1, 6);

        System.out.println(qf.count());
    }


    //System.out.println();

}
