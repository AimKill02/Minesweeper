public class GridTable {
    private int rows;
    private int cols;
    private Tile[][] grid;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void generateGrid() {
        grid = new Tile[rows][cols];
        //Time complexity (Best/Worst Case): O(n^2) (O(rows * cols))
        //Space complexity: O(n^2) (O(rows * cols))
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Tile();
            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }
}
