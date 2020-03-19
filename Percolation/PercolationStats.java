import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    final double[] thresholdValues;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        thresholdValues = new double[trials];
        int numberOfblock   = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation randomWalk = new Percolation(n);
            while (!randomWalk.percolates()){
                int row = 1 + StdRandom.uniform(n); //random [0,n)
                int col = 1 + StdRandom.uniform(n); //random [0,n)
                randomWalk.open(row, col);
            }
            thresholdValues[i] = randomWalk.numberOfOpenSites() / (double)numberOfblock;
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(thresholdValues);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(thresholdValues);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (1.96*stddev()/Math.sqrt(thresholdValues.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (1.96*stddev()/Math.sqrt(thresholdValues.length));
    }

   // test client (see below)
   public static void main(String[] args){
        if (args.length != 2){
            return;
        }
        int size = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(size,trial);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");
   }

}