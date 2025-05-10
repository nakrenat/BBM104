import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The MazeSolver class provides methods to solve a maze using a wall-follower algorithm.
 * It reads a maze from a text file, then prints the maze and tries to find an exit.
 */
public class MazeSolver {
    private char[][] mazeGrid;
    private boolean[][] visited;

    /**
     * Constructor to start the maze and visited grid.
     *
     * @param maze A 2D char array representing the maze structure.
     */
    public MazeSolver(char[][] maze) {
        this.mazeGrid = maze;
        this.visited = new boolean[maze.length][maze[0].length]; // Initialize visited array
    }

    /**
     * Reads the maze from a text file.
     *
     * @param input The name of the file containing the maze.
     * @return A 2D char array representing the maze.
     * @throws IOException If an I/O error throws when reading the file.
     */
    public static char[][] readMazeGrid(String input) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line;
        int rows = 0;

        // First pass to count the number of rows in the maze
        while ((line = reader.readLine()) != null) {
            rows++;
        }

        reader.close();
        reader = new BufferedReader(new FileReader(input));

        char[][] mazeGrid = new char[rows][];

        // Second pass to read the actual maze structure
        int row = 0;
        while ((line = reader.readLine()) != null) {
            mazeGrid[row] = line.toCharArray();
            row++;
        }
        reader.close();

        return mazeGrid;
    }

    /**
     * Prints the maze grid to the console.
     *
     * @param mazeGrid A 2D char array representing the maze.
     */
    public static void printMaze(char[][] mazeGrid) {
        for (int i = 0; i < mazeGrid.length; i++) {
            for (int j = 0; j < mazeGrid[i].length; j++) {
                System.out.print(mazeGrid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Checks if a given position is within the bounds of the maze.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return true if the position is within bounds, false otherwise.
     */
    public boolean isInBound(int x, int y) {
        return (x >= 0 && x < mazeGrid.length && y >= 0 && y < mazeGrid[x].length);
    }

    /**
     * Checks if the move to the given coordinates is valid.
     * A valid move is within the bounds of the maze and leads to an empty cell ('.') or the exit ('X').
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int x, int y) {
        return (isInBound(x, y) && (mazeGrid[x][y] == '.' || mazeGrid[x][y] == 'X') && !visited[x][y]);
    }

    /**
     * Checks if the given position is the exit of the maze.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return true if the position is the exit, false otherwise.
     */
    public boolean isExit(int x, int y) {
        return mazeGrid[x][y] == 'X';
    }

    /**
     * Marks the current position as tried by updating the maze grid and marking it as visited.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public void isTried(int x, int y) {
        mazeGrid[x][y] = '*';  // Mark the cell as tried
        visited[x][y] = true;  // Mark as visited
    }

    /**
     * Recursively attempts to find the exit starting from the given coordinates.
     *
     * @param x The x-coordinate of the starting position.
     * @param y The y-coordinate of the starting position.
     * @return true if the exit is reached, false otherwise.
     */
    public boolean reachExit(int x, int y) {
        // Base case: If the current position is the exit
        if (isExit(x, y)) {
            System.out.println("x:" + x + ", y:" + y);
            return true;
        }

        // Mark the current position as tried
        isTried(x, y);

        // Try to move in each of the 4 possible directions (east, south, west, north)
        if (isValidMove(x, y + 1) && reachExit(x, y + 1)) return true; // Move east (right)
        if (isValidMove(x + 1, y) && reachExit(x + 1, y)) return true; // Move south (down)
        if (isValidMove(x, y - 1) && reachExit(x, y - 1)) return true; // Move west (left)
        if (isValidMove(x - 1, y) && reachExit(x - 1, y)) return true; // Move north (up)

        // If no valid moves, return false
        return false;
    }

    /**
     * The main method is the entry point of the program.
     * It validates the command-line arguments and solves the maze using the MazeSolver class.
     *
     * @param args The command-line arguments which should include:
     *             - the x-coordinate of the starting position,
     *             - the y-coordinate of the starting position,
     *             - the name of the maze file.
     * @throws IOException If an error occurs while reading the maze file.
     */
    public static void main(String[] args) throws IOException {
        // Ensure that there are at least 3 arguments: initialX, initialY, and the maze file.
        if (args.length >= 3) {
            try {
                // Parse the starting coordinates and the maze file name.
                int initialX = Integer.parseInt(args[0]);
                int initialY = Integer.parseInt(args[1]);
                String inputFile = args[2];

                // Read the maze from the file and print it.
                char[][] maze = MazeSolver.readMazeGrid(inputFile);
                //MazeSolver.printMaze(maze); // print statement for the grid.

                // Create a new MazeSolver instance to solve the maze.
                MazeSolver solver = new MazeSolver(maze);

                // Attempt to solve the maze. If no solution is found, print a message.
                if (!solver.reachExit(initialX, initialY)) {
                    System.out.println("There is no solution!");
                }

            }
            catch (Exception e) {
                // Handle any errors that occur during maze solving.
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
        else {
            // Print usage instructions if not enough arguments are provided.
            System.out.println("Usage: java MazeSolver <x> <y> <maze_file>");
        }
    }
}


