/* 
package Landmine_Types;
import Gameplay.Landmine;

public class ExplosiveMine extends Landmine {
    public ExplosiveMine(int rows, int cols, int maxMineCount) {
        super(rows, cols, maxMineCount);
    }

    @Override
    public int getMineType() {
        return 4; // Explosive Mine type
    }

    @Override
    public int getDamage() {
        return 50; // Damage for stepping on the mine
    }

    public void explode(int[][] grid, int row, int col) {
        // Explode in a 3x3 area
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols) {
                    grid[i][j] = -2; // Mark affected tiles (e.g., -2 for explosion damage)
                }
            }
        }
    }
}
*/