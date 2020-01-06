/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int capasity = 0;
    private Item q[] = (Item[]) new Object[1];
    private int last = 0;

    private void resize(int newCapasity) {
        if (newCapasity == 0) newCapasity = 1;
        Item[] copy = (Item[]) new Object[newCapasity];
        for (int i = 0; i < last; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    private void throwErrorIfEmpty() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return q[0] == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return capasity;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item can't be null");
        if (q[last] != null) last++;
        if (last == capasity) resize(capasity * 2);
        q[last] = item;
        capasity++;
    }

    // remove and return a random item
    public Item dequeue() {
        throwErrorIfEmpty();
        int index = StdRandom.uniform(capasity);
        Item item = null;
        if (index != last) {
            item = q[index];
            q[index] = q[last];
        }
        else {
            item = q[last];
        }
        q[last] = null;
        if (last != 0) last--;
        capasity--;
        if (last < capasity / 4) resize(capasity / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        throwErrorIfEmpty();
        int index = StdRandom.uniform(capasity);
        return q[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int[] order = StdRandom.permutation(capasity);
        private int currentIndex = 0;
        private Item current = q[order[currentIndex]];

        public boolean hasNext() {
            return currentIndex < order.length;
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException("No next element");
            }
            current = q[order[currentIndex]];
            Item item = current;
            currentIndex++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        StdOut.println("empty size " + r.size());
        r.enqueue("first");
        r.dequeue();
        r.enqueue("second");
        r.enqueue("third");
        r.enqueue("forth");
        r.enqueue("fifth");
        r.enqueue("sixth");
        r.enqueue("seventh");
        r.enqueue("eighth");
        r.sample();
        StdOut.println("size before " + r.size());
        r.dequeue();
        r.dequeue();
        StdOut.println("size after " + r.size());
        StdOut.println("first iterator");
        for (String s : r) {
            StdOut.println(s);
        }
        StdOut.println("second iterator");
        for (String s : r) {
            StdOut.println(s);
        }
        r.dequeue();
        r.dequeue();
        r.dequeue();
        r.dequeue();
        r.dequeue();
        r.enqueue("first");
        r.enqueue("second");
        StdOut.println("sample " + r.dequeue());
        r.enqueue("third");
        StdOut.println("sample " + r.dequeue());
        r.enqueue("forth");
        StdOut.println("sample " + r.dequeue());
        r.enqueue("fifth");
        StdOut.println("sample " + r.dequeue());
        r.enqueue("sixth");
    }

}
