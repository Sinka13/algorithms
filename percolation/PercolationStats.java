/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private int trials;
  private int n;
  private int size;
  private double[] results;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    this.n = n;
    this.trials = trials;
    validateInput(n, trials);
    this.results = new double[trials];
    simulation();
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(results);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(results);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return (mean() - (1.96 * stddev() / Math.sqrt(trials)));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return (mean() + (1.96 * stddev() / Math.sqrt(trials)));
  }

  private void simulation() {
    int max = n + 1;
    int row = StdRandom.uniform(1, max);
    int col = StdRandom.uniform(1, max);
    for (int i = 0; i < trials; i++) {
      Percolation perc = new Percolation(n);
      while (!perc.percolates()) {
        perc.open(row, col);
        row = StdRandom.uniform(1, max);
        col = StdRandom.uniform(1, max);
      }
      results[i] = (double) perc.numberOfOpenSites() / (n * n);
    }
  }

  private void validateInput(int i, int t) {
    if (i <= 0 || t <= 0) {
      throw new IndexOutOfBoundsException("Out of bounds");
    }
  }

  // test client (see below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n, t);
    System.out.println("mean = " + stats.mean());
    System.out.println("stddev = " + stats.stddev());
    System.out.println(
            "95% confidence interval = [" + stats.confidenceLo() + ", " + +stats.confidenceHi()
                    + "]");
  }
}
