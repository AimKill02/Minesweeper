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
    private int remainingMines;
    private JLabel mineLabel, timerLabel;
    private Timer timer;
    private int seconds = 0;

    public Minesweeper() {
        String[] options = {"Beginner", "Intermediate", "Expert", "Custom"};
        int choice = JOptionPane.showOptionDialog(null, "Select Difficulty:", "Minesweeper Difficulty", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        int rows = 9, cols = 9, mines = 10;
        if (choice == 1) { rows = 16; cols = 16; mines = 40; }
        else if (choice == 2) { rows = 16; cols = 30; mines = 99; }
        else if (choice == 3) {
            String rowStr = JOptionPane.showInputDialog("Rows:", "10");
            String colStr = JOptionPane.showInputDialog("Columns:", "10");
            String mineStr = JOptionPane.showInputDialog("Mines:", "10");
            try {
                rows = Math.max(1, Integer.parseInt(rowStr));
                cols = Math.max(1, Integer.parseInt(colStr));
                mines = Math.max(1, Integer.parseInt(mineStr));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Using Beginner settings.");
                rows = 9; cols = 9; mines = 10;
            }
        }
        grid.setRows(rows);
        grid.setCols(cols);
        grid.setTotalMines(mines);
        grid.generateGrid();
        main = grid.getGrid();
        buttons = new JButton[grid.getRows()][grid.getCols()];
        remainingMines = mines;
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
                        if (nr >= 0 && nr < grid.getRows() && nc >= 0 && nc < grid.getCols() && main[nr][nc].isMine()) count++;
                    }
                }
                main[r][c].setAdjacentMines(count);
            }
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Rectangle usableBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int frameWidth = usableBounds.width;
        int frameHeight = usableBounds.height;
        int btnWidth = frameWidth / grid.getCols();
        int btnHeight = frameHeight / grid.getRows();

        JPanel topPanel = new JPanel(new BorderLayout());
        mineLabel = new JLabel("Mines: " + remainingMines, SwingConstants.LEFT);
        timerLabel = new JLabel("Time: 0", SwingConstants.RIGHT);
        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener(e -> restartGame());
        topPanel.add(mineLabel, BorderLayout.WEST);
        topPanel.add(restartBtn, BorderLayout.CENTER);
        topPanel.add(timerLabel, BorderLayout.EAST);

        JPanel panel = new JPanel(new GridLayout(grid.getRows(), grid.getCols()));
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(btnWidth, btnHeight));
                int fontSize = Math.max(14, btnHeight * 2 / 3);
                btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
                buttons[r][c] = btn;
                int finalR = r, finalC = c;
                btn.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        if (gameOver) return;
                        if (SwingUtilities.isRightMouseButton(e)) {
                            flag(finalR, finalC);
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            if (firstClick) startTimer();
                            Tile tile = main[finalR][finalC];
                            if (tile.isRevealed() && tile.getAdjacentMines() > 0) {
                                if (countAdjacentFlags(finalR, finalC) == tile.getAdjacentMines()) {
                                    for (int dr = -1; dr <= 1; dr++) {
                                        for (int dc = -1; dc <= 1; dc++) {
                                            int nr = finalR + dr, nc = finalC + dc;
                                            if ((dr != 0 || dc != 0) && nr >= 0 && nr < grid.getRows() && nc >= 0 && nc < grid.getCols() && !main[nr][nc].isFlagged() && !main[nr][nc].isRevealed()) {
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
                panel.add(btn);
            }
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(panel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(usableBounds.x, usableBounds.y);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void startTimer() {
        if (timer != null) timer.stop();
        seconds = 0;
        timerLabel.setText("Time: 0");
        timer = new Timer(1000, e -> {
            seconds++;
            timerLabel.setText("Time: " + seconds);
        });
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    private int countAdjacentFlags(int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int nr = r + dr, nc = c + dc;
                if ((dr != 0 || dc != 0) && nr >= 0 && nr < grid.getRows() && nc >= 0 && nc < grid.getCols() && main[nr][nc].isFlagged()) count++;
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
            remainingMines++;
        } else {
            tile.setFlagged(true);
            btn.setText("🚩");
            remainingMines--;
        }
        mineLabel.setText("Mines: " + remainingMines);
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
                        if ((dr != 0 || dc != 0) && nr >= 0 && nr < grid.getRows() && nc >= 0 && nc < grid.getCols() && !main[nr][nc].isRevealed()) {
                            queue.add(new int[]{nr, nc});
                        }
                    }
                }
            }
        }
        if (checkWin()) winGame();
    }

    private boolean checkWin() {
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                Tile tile = main[r][c];
                if (!tile.isMine() && !tile.isRevealed()) return false;
            }
        }
        return true;
    }

    private void winGame() {
        stopTimer();
        gameOver = true;
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                buttons[r][c].setEnabled(false);
                Tile tile = main[r][c];
                if (tile.isMine()) buttons[r][c].setText("🚩");
            }
        }
        JOptionPane.showMessageDialog(frame, "Congratulations! You win!\nTime: " + seconds + "s");
    }

    private void gameOver() {
        stopTimer();
        gameOver = true;
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                Tile tile = main[r][c];
                JButton btn = buttons[r][c];
                if (tile.isMine()) btn.setText("💣");
                if (tile.isFlagged() && !tile.isMine()) {
                    btn.setText("❌");
                    btn.setBackground(Color.RED);
                    btn.setOpaque(true);
                    btn.setBorderPainted(false);
                }
                btn.setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(frame, "Game Over!\nTime: " + seconds + "s");
    }

    private void restartGame() {
        if (timer != null) timer.stop();
        frame.dispose();
        SwingUtilities.invokeLater(Minesweeper::new);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minesweeper::new);
    }
}