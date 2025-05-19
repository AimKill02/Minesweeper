import java.util.Random;

public class Mine extends GridTable {
    private int totalMines;

    public void setTotalMines(int totalMines) {
        this.totalMines = totalMines;
    }
    public int getTotalMines() {
        return totalMines;
    }

    public synchronized void generateMines(int safeRow, int safeCol) {
    Random random = new Random();
    int minesPlaced = 0;

    // Time Complexity (Best Case): O(TotalMines)
    // Time Complexity (Worst Case): O(Rows * Cols)
    // Space Complexity: O(1)

    while (minesPlaced < totalMines) {
        int row = random.nextInt(getRows());
        int col = random.nextInt(getCols());
        if ((row == safeRow && col == safeCol) || getGrid()[row][col].isMine()) continue;
            getGrid()[row][col].setMine(true);
            minesPlaced++;
        }
    }
}
