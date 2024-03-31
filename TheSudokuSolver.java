import java.util.Arrays;

public class TheSudokuSolver {
    private int[][] board; // 2D array representing the Sudoku board

    // Constructor to initialize the Sudoku board
    public TheSudokuSolver(int[][] board) {
        this.board = board;
    }

    // Method to solve the Sudoku puzzle
    public void solve() {
        long startTime = System.currentTimeMillis(); // Record the starting time
        
        if (solveSudoku()) { // If Sudoku puzzle is solved successfully
            long endTime = System.currentTimeMillis(); // Record the ending time
            System.out.println("Sudoku puzzle solved:");
            printBoard(); // Print the solved puzzle
            System.out.println("Time taken: " + (endTime - startTime) + " milliseconds"); // Print time taken to solve
        } else {
            System.out.println("No solution exists."); // If no solution exists
        }
    }

    // Recursive method to solve the Sudoku puzzle
    private boolean solveSudoku() {
        int[] emptyCell = findEmptyCell(); // Find the next empty cell
        if (emptyCell == null) {
            return true; // If no empty cell is found, puzzle is solved
        }

        int row = emptyCell[0]; // Get row index of empty cell
        int col = emptyCell[1]; // Get column index of empty cell

        // Try numbers from 1 to 9 for the empty cell
        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) { // If number is valid for the cell
                board[row][col] = num; // Place the number in the cell

                if (solveSudoku()) { // Recursively solve the remaining puzzle
                    return true; // If solution is found, return true
                }

                board[row][col] = 0; // If solution is not found, backtrack and reset the cell
            }
        }
        return false; // If no valid number can be placed, return false
    }

    // Method to find the next empty cell on the board
    private int[] findEmptyCell() {
        int[] minCell = null; // Initialize minimum cell as null
        int minRemainingValues = Integer.MAX_VALUE; // Initialize minimum remaining values

        // Iterate through the board to find empty cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) { // If cell is empty
                    int remainingValues = countRemainingValues(i, j); // Count remaining possible values for the cell
                    if (remainingValues < minRemainingValues) { // If remaining values are less than minimum
                        minRemainingValues = remainingValues; // Update minimum remaining values
                        minCell = new int[]{i, j}; // Update minimum cell coordinates
                    }
                }
            }
        }
        return minCell; // Return coordinates of the minimum cell
    }

    // Method to count the remaining possible values for a cell
    private int countRemainingValues(int row, int col) {
        int count = 0; // Initialize count of remaining values
        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) { // If number is valid for the cell
                count++; // Increment count of remaining values
            }
        }
        return count; // Return count of remaining values
    }

    // Method to check if a move is valid for a cell
    private boolean isValidMove(int row, int col, int num) {
        return !usedInRow(row, num) && !usedInColumn(col, num) && !usedInBox(row - row % 3, col - col % 3, num); // Check if number is not used in row, column, or box
    }

    // Method to check if a number is already used in a row
    private boolean usedInRow(int row, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) { // If number is found in the row
                return true; // Return true
            }
        }
        return false; // If number is not found in the row, return false
    }

    // Method to check if a number is already used in a column
    private boolean usedInColumn(int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) { // If number is found in the column
                return true; // Return true
            }
        }
        return false; // If number is not found in the column, return false
    }

    // Method to check if a number is already used in a 3x3 box
    private boolean usedInBox(int startRow, int startCol, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) { // If number is found in the box
                    return true; // Return true
                }
            }
        }
        return false; // If number is not found in the box, return false
    }

    // Method to print the Sudoku board
    private void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(board[i])); // Print each row of the board
        }
    }

    // Main method to create a SudokuSolver object and solve the puzzle
    public static void main(String[] args) {
        // Initial Sudoku puzzle
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

        TheSudokuSolver solver = new TheSudokuSolver(puzzle);
        solver.solve(); // Solve the Sudoku puzzle
    }
}
