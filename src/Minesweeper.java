import javax.swing.*;
import java.awt.*;

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
        Tile tile = main[r][c];
        JButton btn = buttons[r][c];

        if (tile.isRevealed()){
            return;
        }

        tile.setRevealed(true);
        btn.setEnabled(false);

        if (tile.isMine()) {
            btn.setText("💣");
            gameOver();
        } else if (tile.getAdjacentMines() > 0) {
            btn.setText(Integer.toString(tile.getAdjacentMines()));
        } else {
            btn.setText("");
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = r + dr, nc = c + dc;
                    if ((dr != 0 || dc != 0) && nr >= 0 && nr < grid.getRows() && nc >= 0 && nc < grid.getCols()) {
                        reveal(nr, nc);
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

