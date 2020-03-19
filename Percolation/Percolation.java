import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Data Strcuture : Array Store n-by-n grid's cell + one virtual head + one virtual tail
    // Array Coordate : a[0] -> Virtual Head , a[n*n+1] -> Virtual Tail
    // a[(x-1)*n+y] -> (x,y) cell , i.e. (1,1)-> a[1] , (5,5) -> a[25] (5-by-5 grid)
    
    // Rule of Open : Check adjacent cell is open , if yes, connect them
    // Top Row : Connect To Virtual head + adjacent opened cell
    // Last Row : Connect To Virtual Tail + adjacent opened cell
    
    final WeightedQuickUnionUF uf;
    final int gridSize;

    private int openSite;
    private final boolean[] openSiteArray;

    private void validate(final int row, final int col) {
        if ((row <= 0) || (col <= 0) || (row > gridSize) || (col > gridSize)) {
            throw new IllegalArgumentException("Outside prescribed range");
        }
    }

    private int GetArrayIndex(final int row, final int col) {
        return (row - 1) * gridSize + col;
    }

    final int GetHeadIndex() {
        return 0;
    }

    private int GetTailIndex() {
        return gridSize * gridSize + 1;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(final int n) {
        gridSize = n;
        openSite = 0;

        final int arraylength = gridSize * gridSize + 2;
        uf = new WeightedQuickUnionUF(arraylength);

        // Init for checking which site is open
        openSiteArray = new boolean[arraylength];
        for (int i = 0; i < arraylength; i++) {
            openSiteArray[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }

        // update the open array
        final int index = GetArrayIndex(row, col);
        openSiteArray[index] = true;
        openSite++;

        // Check Adjacent Cell is open , then connect
        // Up
        if (row == 1) {
            uf.union(GetHeadIndex(), GetArrayIndex(row, col));
        } else if (isOpen(row - 1, col)) {
            uf.union(GetArrayIndex(row, col), GetArrayIndex(row - 1, col));
        }
        // Left
        if ((col > 1) && (isOpen(row, col - 1))) {
            uf.union(GetArrayIndex(row, col), GetArrayIndex(row, col - 1));
        }
        // Right
        if ((col < gridSize) && (isOpen(row, col + 1))) {
            uf.union(GetArrayIndex(row, col), GetArrayIndex(row, col + 1));
        }
        // Down
        if (row == gridSize) {
            uf.union(GetTailIndex(), GetArrayIndex(row, col));
        } else if (isOpen(row + 1, col)) {
            uf.union(GetArrayIndex(row, col), GetArrayIndex(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        validate(row, col);
        return openSiteArray[GetArrayIndex(row, col)] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(final int row, final int col) {
        validate(row, col);
        return (isOpen(row, col) && (uf.find(GetHeadIndex()) == uf.find(GetArrayIndex(row, col))));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(GetHeadIndex()) == uf.find(GetTailIndex());
    }

    // test client (optional)
    public static void main(final String[] args) {
        final Percolation test = new Percolation(5);
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
    }
}