import java.util.Arrays;

public class TheSudokuSolver {
    private int[][] board;

    public SudokuSolver(int[][] board) {
        this.board = board;
    }

    public void solve() {
        long startTime = System.currentTimeMillis(); // Start time
        
        if (solveSudoku()) {
            long endTime = System.currentTimeMillis(); // End time
            System.out.println("Sudoku puzzle solved:");
            printBoard();
            System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
        } else {
            System.out.println("No solution exists.");
        }
    }

    private boolean solveSudoku() {
        int[] emptyCell = findEmptyCell();
        if (emptyCell == null) {
            return true; // Puzzle solved successfully
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                board[row][col] = num;

                if (solveSudoku()) {
                    return true;
                }

                board[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    private int[] findEmptyCell() {
        int[] minCell = null;
        int minRemainingValues = Integer.MAX_VALUE;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    int remainingValues = countRemainingValues(i, j);
                    if (remainingValues < minRemainingValues) {
                        minRemainingValues = remainingValues;
                        minCell = new int[]{i, j};
                    }
                }
            }
        }
        return minCell;
    }

    private int countRemainingValues(int row, int col) {
        int count = 0;
        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                count++;
            }
        }
        return count;
    }

    private boolean isValidMove(int row, int col, int num) {
        return !usedInRow(row, num) && !usedInColumn(col, num) && !usedInBox(row - row % 3, col - col % 3, num);
    }

    private boolean usedInRow(int row, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInColumn(int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(int startRow, int startCol, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }

    public static void main(String[] args) {
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        SudokuSolver solver = new SudokuSolver(puzzle);
        solver.solve();
    }
}
