/* 
package Landmine_Types;
import Gameplay.Landmine;

public class APMine extends Landmine {
    private int damage = 15; // Damage value for APMine

    public APMine(int rows, int cols, int maxMineCount) {
        super(rows, cols, maxMineCount);
    }

    @Override
    public int getMineType() {
        return 1; // Type identifier for APMine
    }

    @Override
    public void explode(int[][] grid, int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            grid[row][col] = -2; // Mark the tile as exploded
        }
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
*/