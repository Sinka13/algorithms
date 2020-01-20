/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> solution;
    private int moves;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException("Board can not be black");
        }
        SearchNode init = new SearchNode(initial);
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>(1);
        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>(1);
        this.solution = new Stack<Board>();
        SearchNode last = null;
        SearchNode twin = null;
        queue.insert(init);
        twinQueue.insert(new SearchNode(init.board.twin()));
        while (true) {
            init = queue.delMin();
            twin = twinQueue.delMin();
            if (init.board.isGoal()) {
                solvable = true;
                last = init;
                this.moves = last.moves;
                break;
            }
            if (twin.board.isGoal()) {
                solvable = false;
                break;
            }
            for (Board b : init.board.neighbors()) {
                if (init.previous != null && b.equals(init.previous.board)) continue;
                SearchNode node = new SearchNode(b);
                node.moves = init.moves + 1;
                node.previous(init);
                queue.insert(node);
            }
            for (Board b : twin.board.neighbors()) {
                if (twin.previous != null && b.equals(twin.previous.board)) continue;
                SearchNode node = new SearchNode(b);
                node.moves = twin.moves + 1;
                node.previous(twin);
                twinQueue.insert(node);
            }
        }
        while (last != null) {
            solution.push(last.board);
            last = last.previous;
        }

    }

    private static class SearchNode implements Comparable<SearchNode> {
        public Board board;
        public SearchNode previous;
        public int moves;
        private final int manhattan;

        public SearchNode(Board board) {
            this.board = board;
            this.moves = 0;
            this.previous = null;
            this.manhattan = this.board.manhattan();
        }

        public int priority() {
            return this.manhattan + this.moves;
        }

        public int compareTo(SearchNode that) {
            if (this.priority() < that.priority()) return -1;
            if (this.priority() == that.priority()) return 0;
            return 1;
        }

        public void previous(SearchNode node) {
            this.previous = node;
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!isSolvable()) return -1;
        return this.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
