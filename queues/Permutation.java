/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        String[] words = StdIn.readAllStrings();
        int stringsAmount = words.length;

        for (int i = 0; i < stringsAmount; i++) {
            r.enqueue(words[i]);
        }
        if (k > stringsAmount) k = stringsAmount;
        for (int i = 0; i < k; i++) {
            StdOut.println(r.dequeue());
        }
    }
}
