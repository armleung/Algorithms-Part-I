import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Data Strcuture : Array Store n-by-n grid's cell + one virtual head + one virtual tail
    // Array Coordate : a[0] -> Virtual Head , a[n*n+1] -> Virtual Tail
    // a[(x-1)*n+y] -> (x,y) cell , i.e. (1,1)-> a[1] , (5,5) -> a[25] (5-by-5 grid)
    
    // Rule of Open : Check adjacent cell is open , if yes, connect them
    // Top Row : Connect To Virtual head + adjacent opened cell
    // Last Row : Connect To Virtual Tail + adjacent opened cell
    
    private WeightedQuickUnionUF uf;
    private int openSite;
    private int gridSize;
    private boolean[] openSiteArray;

    private void validate(int row,int col) {
        if ((row <= 0) || (col <= 0) || (row > gridSize) || (col > gridSize)){
            throw new IllegalArgumentException("Outside prescribed range");  
        }
    }
    private int GetArrayIndex(int row,int col){
        return (row-1) * gridSize + col;
    }

    private int GetHeadIndex(){
        return 0;
    }

    private int GetTailIndex(){
        return gridSize * gridSize + 1 ;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        gridSize = n;
        openSite = 0;

        int arraylength = gridSize*gridSize+2;
        uf = new WeightedQuickUnionUF(arraylength);

        // Init for checking which site is open
        openSiteArray = new boolean[arraylength];
        for (int i = 0; i < arraylength; i++) {
            openSiteArray[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        validate(row,col);
        if (isOpen(row,col)){
            return;
        }
        
        // update the open array
        int index = GetArrayIndex(row,col);
        openSiteArray[index] = true;
        openSite++;

        // Check Adjacent Cell is open , then connect
        // Up
        if (row == 1){
            uf.union(GetHeadIndex(),GetArrayIndex(row, col));
        }
        else if (isOpen(row -1, col)){
            uf.union(GetArrayIndex(row, col),GetArrayIndex(row-1,col));
        }
        // Left 
        if ((col > 1) && (isOpen(row,col-1))){
            uf.union(GetArrayIndex(row, col),GetArrayIndex(row,col-1));
        }
        // Right 
        if ((col < gridSize) && (isOpen(row,col+1))){
            uf.union(GetArrayIndex(row, col),GetArrayIndex(row,col+1));
        }
        // Down
        if (row == gridSize){
            uf.union(GetTailIndex(),GetArrayIndex(row, col));
        }
        else if (isOpen(row + 1, col)){
            uf.union(GetArrayIndex(row, col),GetArrayIndex(row+1,col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validate(row,col);
        return openSiteArray[GetArrayIndex(row,col)] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validate(row,col);
        return true;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSite;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(GetHeadIndex()) == uf.find(GetTailIndex());
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation test = new Percolation(5);
        // Test Case (1,1) / (2,3) / (5,5) isOpen
        System.out.println("(1,1) is " + test.isOpen(1, 1));
        System.out.println("(2,3) is " + test.isOpen(2, 3));
        System.out.println("(5,5) is " + test.isOpen(5, 5));
        System.out.println("System percolate ? " + test.percolates());

        // Open Size
        test.open(2,3);
        System.out.println("(2,3) is " + test.isOpen(2, 3));
        test.open(2,2);
        System.out.println("(2,2) is " + test.isOpen(2, 2));
        test.open(2,1);
        test.open(1,1);
        test.open(3,3);
        test.open(3,4);
        test.open(4,4);
        test.open(5,5);
        System.out.println("System percolate ? " + test.percolates());
        test.open(5,4);
        System.out.println("System percolate ? " + test.percolates());

        // Start New Test
        System.out.println("************************");
        System.out.println("Ready to Random Walk!");
        System.out.println("************************");
        int n = StdIn.readInt();
        Percolation randomWalk = new Percolation(n);
        int trial = 0; 
        while (!randomWalk.percolates()){
            int row = 1 + StdRandom.uniform(n); //random [0,n)
            int col = 1 + StdRandom.uniform(n); //random [0,n)
            if (!randomWalk.isOpen(row, col)){
                System.out.println("Row: " + row + " Col: " + col + " Open!");
                randomWalk.open(row, col);
                trial++;
            }
        }
        System.out.println("A Block with Size : " + n*n);
        System.out.println("Number of trial: " + trial);
    }
}