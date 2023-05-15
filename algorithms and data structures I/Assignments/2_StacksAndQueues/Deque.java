import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int N;

    // construct an empty deque
    public Deque() {
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        N++;
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        newFirst.prev = null;
        first = newFirst;
        if (isEmpty())
            last = first;
        else
            newFirst.next.prev = newFirst;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        N++;
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        last.prev = oldLast;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        N--;
        Item i = first.item;
        first = first.next;
        if (isEmpty())
            last = null;
        else
            first.prev = null;

        return i;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        N--;
        Item i = last.item;
        Node lastPrev = last.prev;
        if (isEmpty())
            first = null;
        else
            lastPrev.next = null;
        last = lastPrev;

        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException();
            Item i = current.item;
            current = current.next;
            return i;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addLast(1);
        d.addLast(2);
        d.addFirst(3);
        d.addLast(4);

        d.removeFirst();
        d.removeLast();
        d.removeFirst();


        Iterator<Integer> it = d.iterator();
        while (it.hasNext()) {
            int s = it.next();
            StdOut.println(s);
        }
    }
}
