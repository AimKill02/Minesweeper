/* 
package Landmine_Types;
import Gameplay.Landmine;

public class ATMine extends Landmine {
    private int damage = 35; // Damage value for ATMine

    public ATMine(int rows, int cols, int maxMineCount) {
        super(rows, cols, maxMineCount);
    }

    @Override
    public int getMineType() {
        return 2; // Type identifier for ATMine
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
