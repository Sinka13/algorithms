/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private int n;
  private WeightedQuickUnionUF union;
  private int[] openSites;
  private int topRoot;
  private int bottomRoot;
  private int openSitesCounter;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    this.n = n;
    int size = n * n;
    this.topRoot = 0;
    this.bottomRoot = size + 1;
    this.openSitesCounter = 0;
    this.union = new WeightedQuickUnionUF(size + 2);
    this.openSites = new int[size];
  }

  ;

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    int element = element(row, col);
    validateRowOrColumn(row, col);
    if (isOpen(row, col)) {
      return;
    }
    ;

    int[][] adjacentSites = new int[4][2];
    adjacentSites[0] = new int[] { (row - 1), col }; // top
    adjacentSites[1] = new int[] { row + 1, col }; // bottom
    adjacentSites[2] = new int[] { row, col - 1 }; // left
    adjacentSites[3] = new int[] { row, col + 1 }; // right

    if (row == 1) {
      union.union(topRoot, element);
    }
    else if (row == n) {
      union.union(bottomRoot, element);
    }
    ;
    openSites[(element - 1)] = 1;
    ++openSitesCounter;
    for (int[] site : adjacentSites) {
      if (site[0] <= 0 || site[1] <= 0 || site[0] > n || site[1] > n) continue;
      if (isOpen(site[0], site[1])) {
        int siteElement = element(site[0], site[1]);
        union.union(element, siteElement);
      }
    }
  }

  ;

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    validateRowOrColumn(row, col);
    return openSites[(element(row, col) - 1)] != 0;
  }

  ;

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    return union.connected(element(row, col), topRoot);
  }


  // returns the number of open sites
  public int numberOfOpenSites() {
    return openSitesCounter;
  }

  ;

  // does the system percolate?
  public boolean percolates() {
    // int row = StdRandom.uniform(1, n);
    // int col = StdRandom.uniform(1, n);
    return union.connected(topRoot, bottomRoot);
  }

  ;

  private int element(int row, int col) {
    return (row - 1) * n + col;
  }

  ;

  private void validateRowOrColumn(int row, int col) {
    if (row <= 0 || row > n || col <= 0 || col > n) {
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
    ;
  }

  ;

  // test client (optional)
  public static void main(String[] args) {
  }

  ;
}
