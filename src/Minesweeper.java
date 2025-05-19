import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Minesweeper {
    private JFrame frame;
    private Mine grid = new Mine();

    JButton[][] buttons;
    private Tile[][] main = grid.getGrid();


    public Minesweeper() {
        grid.setRows(10);
        grid.setCols(10);
        grid.setTotalMines(10);
        grid.generateGrid();
        grid.generateMines();
        buttons = new JButton[grid.getRows()][grid.getCols()];
        main = grid.getGrid();
        calculateNeighbors();
        createAndShowGUI(); 
        }

        private void calculateNeighbors() {
        int[] d = {-1, 0, 1};
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                if (main[r][c].isMine()) continue;
                int count = 0;
                for (int dr : d) {
                    for (int dc : d) {
                        int nr = r + dr, nc = c + dc;
                        if (nr >= 0 && nr < grid.getRows() && nc >= 0 && nc < grid.getCols() && main[nr][nc].isMine()) {
                            count++;
                        }
                    }
                }
                main[r][c].setAdjacentMines(count);
            }
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(grid.getRows(), grid.getCols()));

        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(60, 60));
                buttons[r][c] = btn;
                int finalR = r;
                int finalC = c;

                btn.addActionListener(e -> reveal(finalR, finalC));
                frame.add(btn);
            }
        }

        frame.setVisible(true);
    }

    private void reveal(int r, int c) {
        if (main[r][c].isRevealed()) return;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{r, c});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int row = pos[0], col = pos[1];
            Tile tile = main[row][col];
            JButton btn = buttons[row][col];

            if (tile.isRevealed()) continue;

            tile.setRevealed(true);
            btn.setEnabled(false);

            if (tile.isMine()) {
                btn.setText("💣");
                gameOver();
                return;
            } else if (tile.getAdjacentMines() > 0) {
                btn.setText(Integer.toString(tile.getAdjacentMines()));
            } else {
                btn.setText("");
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = row + dr, nc = col + dc;
                        if ((dr != 0 || dc != 0) &&
                            nr >= 0 && nr < grid.getRows() &&
                            nc >= 0 && nc < grid.getCols() &&
                            !main[nr][nc].isRevealed()) {
                            queue.add(new int[]{nr, nc});
                        }
                    }
                }
            }
        }
    }

    private void gameOver() {
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                if (main[r][c].isMine()) {
                    buttons[r][c].setText("💣");
                }
                buttons[r][c].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(frame, "Game Over!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}

