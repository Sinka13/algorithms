/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        if (isEmpty()) {
            last = new Node();
            last.item = item;
            first = last;
        }
        else {
            Node newlast = new Node();
            newlast.item = item;
            newlast.previous = last;
            last.next = newlast;
            last = newlast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        raiseErrorIfNoItems();
        Item item = first.item;
        first = first.next;
        if (first != null) first.previous = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    // pop remove last item and relink last to be the one before last
    public Item removeLast() {
        raiseErrorIfNoItems();
        Item item = last.item;
        if (last.previous != null) {
            last = last.previous;
            last.next = null;
        }
        else {
            last = null;
            first = null;
        }
        size--;
        return item;
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be empty");
        }
    }

    private void raiseErrorIfNoItems() {
        if (size() == 0) {
            throw new java.util.NoSuchElementException("Deque is empty");
        }
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

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException("No next element");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        StdOut.println(deque.removeLast());
        StdOut.println(deque.size());
        deque.addFirst(3);
        deque.removeLast();
    }


}
