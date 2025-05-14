/* 
package Landmine_Types;
import java.util.HashMap;
import java.util.Map;

import Gameplay.Landmine;

public class NuclearMine extends Landmine {
    private Map<Integer, Integer> previousTiles = new HashMap<>();
    public NuclearMine(int rows, int cols, int maxMineCount) {
        super(rows, cols, maxMineCount);
    }

    @Override
    public int getMineType() {
        return 8; // Nuclear Mine type
    }

    @Override
    public int getDamage() {
        return 150; // Damage for stepping on the mine
    }

    @Override
    public void explode(int[][] grid, int row, int col) {
        previousTiles.clear();
        int startExplosionRow = Math.max(0, row - 4);
        int endExplosionRow = Math.min(rows - 1, row + 4);
        int startExplosionCol = Math.max(0, col - 4);
        int endExplosionCol = Math.min(cols - 1, col + 4);

        for (int i = startExplosionRow; i <= endExplosionRow; i++) {
            for (int j = startExplosionCol; j <= endExplosionCol; j++) {
                int key = i * cols + j;
                previousTiles.put(key, grid[i][j]);
                grid[i][j] = -2; // Mark explosion tiles
            }
        }

        // 5x5 crater tile
        int startCraterRow = Math.max(0, row - 2);
        int endCraterRow = Math.min(rows - 1, row + 2);
        int startCraterCol = Math.max(0, col - 2);
        int endCraterCol = Math.min(cols - 1, col + 2);

        for (int i = startCraterRow; i <= endCraterRow; i++) {
            for (int j = startCraterCol; j <= endCraterCol; j++) {
                grid[i][j] = -3; // Mark crater tiles
            }
        }

        // 7x7 placeholder tile
        int startPlaceholderRow = Math.max(0, row - 3);
        int endPlaceholderRow = Math.min(rows - 1, row + 3);
        int startPlaceholderCol = Math.max(0, col - 3);
        int endPlaceholderCol = Math.min(cols - 1, col + 3);

        for (int i = startPlaceholderRow; i <= endPlaceholderRow; i++) {
            for (int j = startPlaceholderCol; j <= endPlaceholderCol; j++) {
                if (grid[i][j] != -3) { // Avoid overwriting crater tiles
                    grid[i][j] = -6; // Mark placeholder tiles
                }
            }
        }
    }

    public void restore(int[][] grid) {
        for (Map.Entry<Integer, Integer> entry : previousTiles.entrySet()) {
            int key = entry.getKey();
            int i = key / cols;
            int j = key % cols;
            grid[i][j] = entry.getValue();
        }
        previousTiles.clear();
    }

    public int damageOverReveal() {
        return 20; // Damage dealt every time a placeholder tile is revealed
    }
}
*/