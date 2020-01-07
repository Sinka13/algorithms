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
        private int currentIndex = 0;
        private final int[] order = order();
        private Item current = q[order[currentIndex]];

        private int[] order() {
            if (capasity > 0) {
                return StdRandom.permutation(capasity);
            }
            else {
                return new int[] { 0 };
            }
        }

        public boolean hasNext() {
            return (currentIndex < order.length) && capasity > 0;
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
        Iterator<String> i = r.iterator();

        r.enqueue("first");
        r.enqueue("first");
        r.enqueue("first");
        r.enqueue("first");
        r.enqueue("first");
        r.sample();
        r.dequeue();
        r.dequeue();
        r.dequeue();
        r.dequeue();
        r.dequeue();
        for (String s : r) {
            StdOut.println(s);
        }

    }

}
