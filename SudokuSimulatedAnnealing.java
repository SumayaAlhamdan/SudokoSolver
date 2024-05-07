// Import statements
import java.util.Arrays;
import java.util.Random;

// Class definition for solving Sudoku puzzles using Simulated Annealing
public class SudokuSimulatedAnnealing {
    private int[][] board; // 2D array representing the Sudoku board
    private final Random random = new Random(); // Random number generator

    // Constructor to initialize the Sudoku board
    public SudokuSimulatedAnnealing(int[][] board) {
        this.board = board;
    }

    // Method to solve the Sudoku puzzle using Simulated Annealing
    public void solve() {
        long startTime =  System.nanoTime(); // Record the starting time
        solveSudoku(board); // Solve the Sudoku puzzle
        long endTime = System.nanoTime(); // Record the ending time
        long timeTaken = endTime - startTime; // Calculate the time taken to solve

        // Print the solution and computational time
        System.out.println("Sudoku puzzle solved:");
        printBoard(board);
        System.out.println("Time taken: " + timeTaken + " nanoseconds");
        System.out.println("Level: " + getLevel(timeTaken)); // Determine puzzle level based on computational time
    }

    // Recursive method to solve the Sudoku puzzle using Simulated Annealing
    private void solveSudoku(int[][] sudoku) {
        // Simulated Annealing parameters
        double temperature = 1.0;
        double coolingRate = 0.003;
        double absoluteTemperature = 0.01;

        // Simulated Annealing loop
        while (temperature > absoluteTemperature) {
            int[] emptyCell = findEmptyCell(sudoku); // Find an empty cell
            if (emptyCell == null) {
                break; // Solution found
            }

            int row = emptyCell[0];
            int col = emptyCell[1];

            int oldValue = sudoku[row][col];
            int newValue = getRandomNumber();

            // Acceptance probability condition
            if (acceptanceProbability(oldValue, newValue, temperature) > Math.random()) {
                sudoku[row][col] = newValue; // Make the move
            }

            temperature *= 1 - coolingRate; // Cool down the temperature
        }
    }

    // Method to calculate the acceptance probability in Simulated Annealing
    private double acceptanceProbability(int oldValue, int newValue, double temperature) {
        if (newValue != 0) {
            return 1.0; // Always accept non-zero values
        }
        return Math.exp((newValue - oldValue) / temperature); // Calculate acceptance probability
    }

    // Method to generate a random number between 1 and 9
    private int getRandomNumber() {
        return random.nextInt(9) + 1;
    }

    // Method to find the next empty cell on the board
    private int[] findEmptyCell(int[][] sudoku) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j] == 0) { // If cell is empty
                    return new int[]{i, j}; // Return coordinates of the empty cell
                }
            }
        }
        return null; // If no empty cell is found
    }

    // Method to print the Sudoku board
    private void printBoard(int[][] sudoku) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("------+-------+------");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
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
        System.out.println("Simulated Annealing formulation");

        // Array of Sudoku puzzles
        int[][][] puzzles = { /* Sudoku puzzles go here */ };
        long totalComputationalTime = 0; // Initialize total computational time

        // Solve each Sudoku puzzle and calculate computational time
        for (int i = 0; i < puzzles.length; i++) {
            System.out.println("Puzzle " + (i + 1) + ":");
            long startTime = System.nanoTime(); // Start time
            SudokuSimulatedAnnealing solver = new SudokuSimulatedAnnealing(puzzles[i]);
            solver.solve(); // Solve the Sudoku puzzle
            totalComputationalTime += (System.nanoTime() - startTime); // Accumulate computational time
            System.out.println();
        }

        // Calculate and print average computational time
        long averageComputationalTime = totalComputationalTime / puzzles.length;
        System.out.println("Average Computational Time: " + averageComputationalTime + " nanoseconds");
    }
}
