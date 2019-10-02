/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int n;
  private WeightedQuickUnionUF union;
  private int[] grid;
  private int[] openSites;
  private int topRoot;
  private int bottomRoot;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    this.n = n;
    int size = n * n - 1;
    this.grid = new int[size];
    this.topRoot = 0;
    this.bottomRoot = n + 1;
    for (int i = 1; i <= size; i++) {
      grid[i - 1] = i;
    }
    ;
    this.union = new WeightedQuickUnionUF(size + 1);
    this.openSites = new int[size];
  }

  ;

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    int element = (row - 1) * n + col;
    if (element <= 0 || element > n) {
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
    ;
    if (isOpen(row, col)) {
      return;
    }
    ;

    int[][] adjacent_sites = new int[4][2];
    if (col != 1) {
      int[] rowCol = { (row - 1), col - 1 };
      adjacent_sites[0] = rowCol;
    }
    ;
    if (col != n) {
      int[] rowCol = { row - 1, col + 1 };
      adjacent_sites[1] = rowCol;
    }
    ;
    if (row != n) {
      int[] rowCol = { row, col };
      adjacent_sites[2] = rowCol;
    }
    ;
    if (row != 1) {
      int[] rowCol = { (row - 2), col };
      adjacent_sites[3] = rowCol;
    }
    ;
    if (row == 1) {
      union.union(element, topRoot);
    }
    else if (row == n) {
      union.union(element, bottomRoot);
    }
    ;
    for (int[] site : adjacent_sites) {
      if (isOpen(site[0], site[1])) {
        int siteElement = site[0] * n + site[1];
        union.union(element, siteElement);
      }
    }
  }

  ;

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    int element = (row - 1) * n + col;
    if (element <= 0 || element > n) {
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
    ;
    boolean result = false;

    for (int i : openSites) {
      if (i == element) {
        result = true;
        break;
      }
    }

    return result;
  }

  ;

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    int element = (row - 1) * n + col;
    if (element <= 0 || element > n) {
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
    ;
    return union.connected(element, topRoot);
  }


  // returns the number of open sites
  public int numberOfOpenSites() {
    return openSites.length;
  }

  ;

  // does the system percolate?
  public boolean percolates() {
    StdRandom.discrete(grid);
    return true;
  }

  ;

  // test client (optional)
  public static void main(String[] args) {
    Percolation a = new Percolation(5);
    a.isOpen(1, 1);
  }

  ;
}
