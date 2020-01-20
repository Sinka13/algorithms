/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    private int[] expectedXY(int tileValue) {
        int i = tileValue / dimension;
        if (tileValue % dimension == 0) {
            i--;
        }
        int j = tileValue - (dimension * i) - 1;
        int[] result = { i, j };
        return result;
    }

    private Board buildNewBoard(int[] zeroIndex, int newI, int newJ) {
        int zeroI = zeroIndex[0];
        int zeroJ = zeroIndex[1];
        int[][] newTiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == newI && j == newJ) continue;
                newTiles[i][j] = tiles[i][j];
                if (i == zeroI && j == zeroJ) {
                    newTiles[i][j] = tiles[newI][newJ];
                    newTiles[newI][newJ] = 0;
                }

            }
        }
        return new Board(newTiles);
    }

    private boolean validIndex(int i) {
        return (0 <= i && i < dimension);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles.length; j++) {
                if (this.tiles[i][j] == 0) continue;
                int[] expected = expectedXY(this.tiles[i][j]);
                if (expected[0] != i || expected[1] != j) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles.length; j++) {
                if (this.tiles[i][j] == 0) continue;
                int[] expected = expectedXY(this.tiles[i][j]);
                int movesToCorrect = Math.abs(expected[0] - i) + Math.abs(expected[1] - j);
                count += movesToCorrect;
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] zeroIndex = new int[2];
        Stack<Board> result = new Stack<Board>();
        outerloop:
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    zeroIndex[0] = i;
                    zeroIndex[1] = j;
                    break outerloop;
                }
            }
        }
        if (validIndex(zeroIndex[0] + 1)) {
            Board b1 = buildNewBoard(zeroIndex, zeroIndex[0] + 1, zeroIndex[1]);
            result.push(b1);
        }
        if (validIndex(zeroIndex[0] - 1)) {
            Board b2 = buildNewBoard(zeroIndex, zeroIndex[0] - 1, zeroIndex[1]);
            result.push(b2);
        }
        if (validIndex(zeroIndex[1] + 1)) {
            Board b3 = buildNewBoard(zeroIndex, zeroIndex[0], zeroIndex[1] + 1);
            result.push(b3);
        }
        if (validIndex(zeroIndex[1] - 1)) {
            Board b4 = buildNewBoard(zeroIndex, zeroIndex[0], zeroIndex[1] - 1);
            result.push(b4);
        }
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        boolean swap = true;
        int[][] newTiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newTiles[i][j] = tiles[i][j];
                if (swap && j + 1 < dimension
                        && this.tiles[i][j] != 0
                        && this.tiles[i][j + 1] != 0
                        && this.tiles[i][j] != this.tiles[i][j + 1]) {
                    swap = false;
                    newTiles[i][j] = tiles[i][j + 1];
                    newTiles[i][j + 1] = tiles[i][j];
                    j++;
                }
            }
        }
        return new Board(newTiles);
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = { { 0, 1, 3 }, { 4, 8, 2 }, { 7, 6, 5 } };
        int[][] kj = { { 7, 3, 2, 8 }, { 6, 9, 1, 5 }, { 4, 11, 0, 13 }, { 12, 10, 14, 15 } };
        int[][] goal = { { 1, 6, 4 }, { 7, 8, 0 }, { 2, 3, 5 } };
        Board b = new Board(a);
        Board b2 = new Board(goal);
        Board b3 = new Board(kj);
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println("b2");
        StdOut.println(b2.hamming());
        StdOut.println(b2.manhattan());
        StdOut.println(b.equals(b2));
        StdOut.println(b.toString());
        StdOut.println(b.twin());
        StdOut.println(b.twin());
        StdOut.println("----------");
        StdOut.println(b3.toString());
        StdOut.println(b2.toString());
        StdOut.println(b2.neighbors().toString());
    }

}
