import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class SpecificCanonicalElement {

    public static void main(String[] args){
        System.out.println("Start program");
        WeightedQuickUnion uf = new WeightedQuickUnion(10);
        uf.union(0, 2);
        uf.union(8, 4);
        StdOut.println(uf.find(0) == 2);
        StdOut.println(uf.find(4) == 8);
        uf.union(0, 4);
        StdOut.println(uf.find(0) == 8);
        StdOut.println(uf.find(2) == 8);
        uf.union(0, 6);
        StdOut.println(uf.find(6) == 8);
        uf.union(1, 9);
        uf.union(1, 2);
        StdOut.println(uf.find(4) == 9);

    }
}
