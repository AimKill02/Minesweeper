/* 
package Landmine_Types;

import Gameplay.Landmine;

public class ChemicalMine extends Landmine {
    public ChemicalMine(int rows, int cols, int maxMineCount) {
        super(rows, cols, maxMineCount);
    }

    @Override
    public int getMineType() {
        return 7; // Chemical Mine type
    }

    @Override
    public int getDamage() {
        return 15; // Initial damage when stepping on the mine
    }

    public void explode(int[][] grid, int row, int col) {
        // Spread poison gas in a 7x7 square area
        int startRow = Math.max(0, row - 2);
        int endRow = Math.min(rows - 1, row + 2);
        int startCol = Math.max(0, col - 2);
        int endCol = Math.min(cols - 1, col + 2);

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                grid[i][j] = -5; // Mark affected tiles (e.g., -5 for poison gas)
            }
        }
    }

    public int damageOverReveal() {
        return 5; // Damage dealt every time a tile in the poison gas area is revealed
    }
}
*/
