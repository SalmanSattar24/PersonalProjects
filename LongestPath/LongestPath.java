/*
 * This code does the same thing as Project3.java but it uses brute force to find the longest path. 
 */

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
            // Read each row
            for (int i = 0; i < rows; i++) {
                // Read all the values in the row and split them by space and store them in an array String[]
                String[] rowValues = br.readLine().split(" ");
                // Loop through the String[] and convert each number to integers and store them in the 2D array
                for (int j = 0; j < cols; j++) {
                    array[i][j] = Integer.parseInt(rowValues[j]);
                }
            }

            // Print the array
            // printArray(array);

            // Find the length of the longest path
            int result = findLongestPath(array);
            
            // Print the result
            System.out.println("Length of the longest path: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method prints the 2D array
    @SuppressWarnings("unused")
    private static void printArray(int[][] array) {
        int rows = array.length;
        int cols = array[0].length;

        // Loop through the array and print each element
        for (int i = 0; i < rows; i++) {
            // Loop through each row
            for (int j = 0; j < cols; j++) {
                // Print each element
                System.out.print(array[i][j] + " ");
            }
            // Print a new line after each row
            System.out.println();
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

        // Start from each cell and find the length of the longest path
        int maxLength = 0;
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
    
        int right = 0;
        if (j < cols - 1 && array[i][j] > array[i][j + 1]) {
            right = findLongestPathFromCell(array, dp, i, j + 1);
        }
    
        int down = 0;
        if (i < rows - 1 && array[i][j] > array[i + 1][j]) {
            down = findLongestPathFromCell(array, dp, i + 1, j);
        }
    
        dp[i][j] = Math.max(right, down) + 1;
    
        return dp[i][j];
    }
    
    
}
