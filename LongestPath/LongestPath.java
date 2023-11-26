import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LongestPath {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java LongestPath input3.txt");
            return;
        }

        String fileName = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Read dimensions of the array
            String[] dimensions = br.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            // Read the 2D array
            int[][] array = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                String[] rowValues = br.readLine().split(" ");
                for (int j = 0; j < cols; j++) {
                    array[i][j] = Integer.parseInt(rowValues[j]);
                }
            }

            // Find the length of the longest path
            int result = findLongestPath(array);

            // Print the result
            System.out.println("Length of the longest path: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int findLongestPath(int[][] array) {
        int rows = array.length;
        int cols = array[0].length;

        // Create a DP array to store the length of the longest path from each cell
        int[][] dp = new int[rows][cols];

        // Initialize the DP array with -1
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dp[i][j] = -1;
            }
        }

        int maxLength = 0;
        // Start from each cell and find the length of the longest path
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int length = findLongestPathFromCell(array, dp, i, j);
                maxLength = Math.max(maxLength, length);
            }
        }

        return maxLength;
    }

    private static int findLongestPathFromCell(int[][] array, int[][] dp, int i, int j) {
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int rows = array.length;
        int cols = array[0].length;

        // Initialize the length to 1 for the current cell
        int length = 1;

        // Check right cell
        if (j < cols - 1 && array[i][j] > array[i][j + 1]) {
            length = Math.max(length, 1 + findLongestPathFromCell(array, dp, i, j + 1));
        }

        // Check down cell
        if (i < rows - 1 && array[i][j] > array[i + 1][j]) {
            length = Math.max(length, 1 + findLongestPathFromCell(array, dp, i + 1, j));
        }

        // Update DP array
        dp[i][j] = length;

        return length;
    }
}
