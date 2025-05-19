import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Minesweeper {
    private JFrame frame;
    private Mine grid = new Mine();
    private boolean firstClick = true;
    JButton[][] buttons;
    private Tile[][] main = grid.getGrid();
    private boolean gameOver = false;


    public Minesweeper() {
        grid.setRows(10);
        grid.setCols(10);
        grid.setTotalMines(10);
        grid.generateGrid();
        main = grid.getGrid();
        buttons = new JButton[grid.getRows()][grid.getCols()];
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

                btn.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        if (gameOver) return;
                        if (SwingUtilities.isRightMouseButton(e)) {
                            flag(finalR, finalC);
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            Tile tile = main[finalR][finalC];
                            if (tile.isRevealed() && tile.getAdjacentMines() > 0) {
                                // Chording: if number of adjacent flags equals the number, reveal all adjacent
                                if (countAdjacentFlags(finalR, finalC) == tile.getAdjacentMines()) {
                                    for (int dr = -1; dr <= 1; dr++) {
                                        for (int dc = -1; dc <= 1; dc++) {
                                            int nr = finalR + dr, nc = finalC + dc;
                                            if ((dr != 0 || dc != 0) &&
                                                nr >= 0 && nr < grid.getRows() &&
                                                nc >= 0 && nc < grid.getCols() &&
                                                !main[nr][nc].isFlagged() &&
                                                !main[nr][nc].isRevealed()) {
                                                reveal(nr, nc);
                                            }
                                        }
                                    }
                                }
                            } else {
                                reveal(finalR, finalC);
                            }
                        }
                    }
                });
                frame.add(btn);
            }
        }

        frame.setVisible(true);
    }

    private int countAdjacentFlags(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = r + dr, nc = c + dc;
                if ((dr != 0 || dc != 0) &&
                    nr >= 0 && nr < grid.getRows() &&
                    nc >= 0 && nc < grid.getCols() &&
                    main[nr][nc].isFlagged()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private void flag(int r, int c) {
        if (gameOver) return;
        Tile tile = main[r][c];
        JButton btn = buttons[r][c];

        if (tile.isRevealed()) return;

        if (tile.isFlagged()) {
            tile.setFlagged(false);
            btn.setText("");
        } else {
            tile.setFlagged(true);
            btn.setText("🚩");
        }
    }

    private void reveal(int r, int c) {
        if (main[r][c].isRevealed() || main[r][c].isFlagged()) return;
        if (firstClick) {
            grid.generateMines(r, c);
            calculateNeighbors();
            firstClick = false;
            }
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
        gameOver = true;
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                Tile tile = main[r][c];
                JButton btn = buttons[r][c];
                if (tile.isMine()) {
                    btn.setText("💣");
                }
                if (tile.isFlagged() && !tile.isMine()) {
                    btn.setText("❌");
                    btn.setBackground(Color.RED);
                    btn.setOpaque(true);
                    btn.setBorderPainted(false);
                }
                btn.setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(frame, "Game Over!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}

