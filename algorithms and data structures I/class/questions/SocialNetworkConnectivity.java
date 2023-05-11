import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class SocialNetworkConnectivity {

    public static void main(String[] args){
        System.out.println("Start program");
        WeightedQuickUnion wqu = new WeightedQuickUnion(5);

        while(wqu.count()!=1){
            int p = StdRandom.uniformInt(5);
            int q = StdRandom.uniformInt(5);
            StdOut.println("p = " + p + " q = " + q);
            wqu.union(p,q);
        }
    }
}
