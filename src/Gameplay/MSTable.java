package Gameplay;
import java.util.HashMap;

public class MSTable {
    int row;
    int col;
    int[][] grids;
    HashMap<Integer, Boolean> flagged = new HashMap<>();
    HashMap<Integer, Boolean> revealed = new HashMap<>();
}
