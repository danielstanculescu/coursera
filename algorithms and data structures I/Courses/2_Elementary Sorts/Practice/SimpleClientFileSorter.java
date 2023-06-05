import edu.princeton.cs.algs4.StdOut;

import java.io.File;

public class SimpleClientFileSorter {
    public static void main(String[] args){
        File directory = new File(args[0]);
        File[] files = directory.listFiles();
        Merge.sort(files);
        //Shuffle.shuffle(files);
        for (int i = 0; i < files.length; i++) {
            StdOut.println(files[i].getName());
        }
    }
}
