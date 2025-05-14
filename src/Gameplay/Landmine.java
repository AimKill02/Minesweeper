package Gameplay;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Collections;

abstract public class Landmine {
    protected int[][] mineField;
    protected int rows;
    protected int cols;
    protected int maxMineCount = 0;
    protected final Set<Integer> mineSet = Collections.synchronizedSet(new HashSet<>());
    protected static final int MINE = -1;

    public Landmine(int rows, int cols, int maxMineCount) {
        if (maxMineCount > (rows * cols) * 7 / 8) {
            throw new IllegalArgumentException("Too many mines for the grid size.");
        } else {
            this.maxMineCount = maxMineCount;
        }
        this.rows = rows;
        this.cols = cols;
        mineField = new int[rows][cols];
    }
    public void generateMines() {
        Random random = new Random();
        while (mineSet.size() < maxMineCount) {
            synchronized (mineSet) {            
                int row = random.nextInt(rows);
                int col = random.nextInt(cols);
                int position = row * cols + col;
                if (mineSet.add(position)) {
                    mineField[row][col] = MINE;
                }
            }
        }
    }
    
    public void resetMineSet() {
        mineSet.clear();
    }

    synchronized public void setMine(int row, int col) {
        // Set a mine at the specified position
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            mineField[row][col] = MINE;
            mineSet.add(row * cols + col);
        }
    }

    public synchronized void removeMine(int row, int col) {
        // Remove a mine from the minefield
        // Check if the position is valid and if it contains a mine
        if (row >= 0 && row < rows && col >= 0 && col < cols && mineField[row][col] == MINE) {
            mineField[row][col] = 0;
            mineSet.remove(row * cols + col);
        }
    }

    public synchronized void clearMines() {
        // Clear the minefield
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mineField[i][j] = 0;
            }
        }
        mineSet.clear();
    }

    public synchronized boolean isMine(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && mineField[row][col] == MINE; // Check if the position is a mine
    }

    public synchronized Set<Integer> getMinePositions() {
        return new HashSet<>(mineSet); // Return a copy to avoid exposing internal state
    }

    public void printAllMinePositions() {
        synchronized (mineSet) {
            for (Integer pos : mineSet) {
                int row = pos / cols;
                int col = pos % cols;
                System.out.println("Mine at: (" + row + ", " + col + ")");
            }
        }
    }
/* 
Optional methods for future use
    public abstract int getMineType();

    public abstract int getDamage();

    public abstract void explode(int[][] grid, int row, int col);

    public String getMineFieldDensity() {
    double density = (double) maxMineCount * 100 / (rows * cols);
    return String.format("Mine Density: %.2f%%", density);
    }
*/
}