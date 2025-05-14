/* 
package Landmine_Types;
import Gameplay.Landmine;

public class HEMine extends Landmine {
    public HEMine(int rows, int cols, int maxMineCount) {
        super(rows, cols, maxMineCount);
    }

    @Override
    public int getMineType() {
        return 5; // High-Explosive Mine type
    }

    @Override
    public int getDamage() {
        return 70; // Damage for stepping on the mine
    }

    @Override
    public void explode(int[][] grid, int row, int col) {
        // Explode in a 5x5 area
        for (int i = row - 2; i <= row + 2; i++) {
            for (int j = col - 2; j <= col + 2; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols) {
                    grid[i][j] = -2; // Mark affected tiles (e.g., -2 for explosion damage)
                }
            }
        }
        // Leave a crater at the center
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            grid[row][col] = -3; // Mark the crater tile (e.g., -3 for crater)
        }
    }
}
*/