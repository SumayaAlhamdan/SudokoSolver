import java.util.Arrays;

public class SudokuSolverincremental {
    private int[][] board; // 2D array representing the Sudoku board

    // Constructor to initialize the Sudoku board
    public SudokuSolverincremental(int[][] board) {
        this.board = board;
    }

    // Method to solve the Sudoku puzzle
    public void solve() {
        long startTime = System.nanoTime(); // Record the starting time
        
        if (solveSudoku()) { // If Sudoku puzzle is solved successfully
            long endTime = System.nanoTime(); // Record the ending time
            System.out.println("Sudoku puzzle solved:");
            printBoard(); // Print the solved puzzle
            long timeTaken = endTime - startTime;
            System.out.println("Time taken: " + timeTaken + " nanseconds"); // Print time taken to solve
            System.out.println("Level: " + getLevel(timeTaken)); // Print the level of the puzzle
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
    
    // Method to determine the level of the puzzle based on computational time
    private String getLevel(long timeTaken) {
        if (timeTaken < 1000) {
            return "Easy";
        } else if (timeTaken < 5000) {
            return "Medium";
        } else {
            return "Difficult";
        }
    }

    // Main method to solve the Sudoku puzzles
 public static void main(String[] args) {
     System.out.println(" Incremental formulation");

    // Array of Sudoku puzzles
    int[][][] puzzles = {
        // Easy puzzles
        {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        },
        {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
        },
        // Medium puzzles
        {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
        },
        {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
        },
        // Difficult puzzles
        {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
        },
        {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {9, 7, 8, 3, 1, 2, 6, 4, 5}
        }
    };

    long totalComputationalTime = 0; // Initialize total computational time

    for (int i = 0; i < puzzles.length; i++) {
        System.out.println("Puzzle " + (i + 1) + ":");
        long startTime = System.nanoTime(); // Start time
        SudokuSolverincremental solver = new SudokuSolverincremental(puzzles[i]);
        solver.solve(); // Solve the Sudoku puzzle
        totalComputationalTime += (System.nanoTime() - startTime); // Accumulate computational time
        System.out.println();
    }

    // Calculate and print average computational time
    long averageComputationalTime = totalComputationalTime / puzzles.length;
    System.out.println("Average Computational Time: " + averageComputationalTime + " nanoseconds");
}
}
