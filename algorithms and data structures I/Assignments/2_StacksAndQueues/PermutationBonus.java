import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PermutationBonus {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        String[] allStrings = StdIn.readAllStrings();
        StdRandom.shuffle(allStrings);

        for (int i = 0; i < k; i++) {
            rq.enqueue(allStrings[i]);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}