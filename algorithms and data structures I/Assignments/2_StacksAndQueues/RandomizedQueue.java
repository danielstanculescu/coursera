import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {

        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (N == items.length)
            resize(2 * items.length);
        items[N++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        if (N >= 0) System.arraycopy(items, 0, copy, 0, N);
        items = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniformInt(N);

        Item item = items[randomIndex];
        items[randomIndex] = items[--N];
        items[N] = null;
        if (N > 0 && N == items.length / 4)
            resize(items.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniformInt(N);

        return items[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = N;
        private final int[] randomIndexes = new int[i];

        public RandomizedQueueIterator() {
            for (int j = 0; j < i; j++) {
                randomIndexes[j] = j;
            }
            if (i > 1)
                StdRandom.shuffle(randomIndexes);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (i == 0)
                throw new NoSuchElementException();

            return items[randomIndexes[--i]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        StdOut.println("isEmpty = " + rq.isEmpty());
        StdOut.println("size = " + rq.size());
        rq.enqueue("1");
        StdOut.println("isEmpty = " + rq.isEmpty());
        StdOut.println("size = " + rq.size());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("isEmpty = " + rq.isEmpty());
        StdOut.println("size = " + rq.size());

        rq.enqueue("1");
        StdOut.println("Sample = " + rq.sample());
        StdOut.println("Deque = " + rq.dequeue());

        rq.enqueue("1");
        rq.enqueue("2");
        rq.enqueue("3");
        rq.enqueue("4");
        rq.enqueue("5");
        rq.enqueue("6");
        rq.enqueue("7");
        rq.enqueue("8");
        rq.enqueue("9");
        rq.enqueue("10");

        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());
        StdOut.println("Deque = " + rq.dequeue());

        Iterator<String> it = rq.iterator();
        while (it.hasNext()) {
            String s = it.next();
            StdOut.println(s);
        }

        rq.enqueue("9");
        rq.enqueue("10");
        it = rq.iterator();
        while (it.hasNext()) {
            String s = it.next();
            StdOut.println(s);
        }


    }
}