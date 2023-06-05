import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SimpleClientStringSorter {
    public static void main(String[] args){
        In in = new In(args[0]);
        String[] a = in.readAllStrings();
        Selection.sort(a);
        for (int i = 0; i <a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}
