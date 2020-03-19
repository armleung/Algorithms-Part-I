import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] thresholdValues;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if ((n<=0) || (trials<=0)){
            throw new IllegalArgumentException("Outside prescribed range");
        }
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

        mean         = StdStats.mean(thresholdValues);
        stddev       = StdStats.stddev(thresholdValues);
        confidenceLo = mean - (1.96*stddev/Math.sqrt(thresholdValues.length));
        confidenceHi = mean + (1.96*stddev/Math.sqrt(thresholdValues.length));
    }

    // sample mean of percolation threshold
    public double mean(){
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return confidenceHi;
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