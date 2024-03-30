import java.util.Arrays;

public class SudokuSolver2 {
    private int[][] board;

    public SudokuSolver2(int[][] board) {
        this.board = board;
    }

    public void solve() {
        long startTime = System.nanoTime(); // Start timing in nanoseconds
        if (solveSudoku()) {
            long endTime = System.nanoTime(); // End timing in nanoseconds
            long elapsedTime = endTime - startTime;
            System.out.println("Sudoku puzzle solved in " + elapsedTime + " nanoseconds:");
            printBoard();
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
        
        // Undo assignment if no valid move found
        board[row][col] = 0;
        return false;
    }

    private int[] findEmptyCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null; // No empty cell found
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
