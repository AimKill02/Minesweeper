/*
package Landmine_Types;
import Gameplay.Landmine;

public class IncendiaryMine extends Landmine {
    public IncendiaryMine(int rows, int cols, int maxMineCount) {
        super(rows, cols, maxMineCount);
    }

    @Override
    public int getMineType() {
        return 6; // Incendiary Mine type
    }

    @Override
    public int getDamage() {
        return 20; // Initial damage when stepping on the mine
    }

    public void explode(int[][] grid, int row, int col) {
        // Spread fire in a 7x7 square area
        int startRow = Math.max(0, row - 2);
        int endRow = Math.min(rows - 1, row + 2);
        int startCol = Math.max(0, col - 2);
        int endCol = Math.min(cols - 1, col + 2);

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                grid[i][j] = -4; // Mark affected tiles (e.g., -4 for fire)
            }
        }
    }

    public int damageOverReveal() {
        return 10; // Damage dealt every time a tile in the fire area is revealed
    }
}
*/