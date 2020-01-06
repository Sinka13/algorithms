/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        In in = new In(args[1]);      // input file
        RandomizedQueue<String> r = new RandomizedQueue<String>();

        while (!in.isEmpty()) {
            String s = in.readString();
            r.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(r.dequeue());
        }
    }
}
