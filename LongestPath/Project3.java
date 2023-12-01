/*
 * This code is written by Salman Sattar
 * For COMP 482, Instructor: Mrs. Lord
 * The purpose of this code is to find the length of the longest path in a 2D array
 * The input is a text file that contains the dimensions of the array and the values in the array
 * The output is the length of the longest path
 * The algorithm used to solve this problem is dynamic programming
 * Problem: You have a 2-dimensional array of integers. You may start at any location and can only move 
 * down or to the right and only if the value is smaller. You want to find the number of entries that make 
 * the longest path.
 * Output Format: The output will be the length (number of entries) of the longest path which starts on 
 * any square of the array, can only continue to a smaller numerical value, and can only continue to the 
 * adjacent square directly to the right or directly below. You must find this value using dynamic 
 * programming (not brute force).
 */


// All the imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Begin of class Project3
public class Project3 {
    public static void main(String[] args) {
        // Check if the user entered the file name
        if (args.length != 1) {
            System.out.println("Usage: java LongestPath input3.txt");
            return;
        }
        
        // Get the file name from the user
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
    } // End of main

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
    } // End of printArray

    /*
     * Design of algorithm:
     * 1. Create a 2D array to store the length of the longest path from each cell
     * 2. Innitiate the 2D array with -1
     * 3. Innitialize the first row and first column with 1
     * 4. Move to the next column and check if the cell to the left is greater than the current cell
     * 5. If the cell to the left is greater than the current cell,
     *    then the length of the longest path from the current cell is the length of the longest path from the cell to the left + 1
     * 6. Check if the cell to the top is greater than the current cell
     * 7. If the cell to the top is greater than the current cell,
     *   then the length of the longest path from the current cell is the length of the longest path from the cell to the top + 1
     * 8. If the cell to the left and the cell to the top are both greater than the current cell,
     *    then the length of the longest path from the current cell is the maximum of the length of the longest path 
     *    from the cell to the left and the length of the longest path from the cell to the top + 1
     * 9. If the cell to the left and the cell to the top are both smaller than the current cell,
     *    then the length of the longest path from the current cell is 1
     * 10. After the first row is done, move to the next row and repeat step 4 to 9
     * 11. After all the rows and columns are done, the length of the longest path is the maximum value in the 2D array
     */

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

        // Initialize the first row and first column with 1
        dp[0][0] = 1;

        // Initialize the first row
        for (int j = 1; j < cols; j++) {
            if (array[0][j - 1] > array[0][j]) {
                dp[0][j] = dp[0][j - 1] + 1;
            } else {
                dp[0][j] = 1;
            }
        }

        // Initialize the first column
        for (int i = 1; i < rows; i++) {
            if (array[i - 1][0] > array[i][0]) {
                dp[i][0] = dp[i - 1][0] + 1;
            } else {
                dp[i][0] = 1;
            }
        }

        // Start from the second row and second column and find the length of the longest path
        int maxLength = 0;

        // Loop through each row
        for (int i = 1; i < rows; i++) {
            // Loop through each column
            for (int j = 1; j < cols; j++) {
                // Check if the cell to the left is greater than the current cell
                if (array[i][j - 1] > array[i][j]) {
                    // Check if the cell to the top is greater than the current cell
                    if (array[i - 1][j] > array[i][j]) {
                        // If the cell to the left and the cell to the top are both greater than the current cell,
                        // then the length of the longest path from the current cell is the maximum of the length of the longest path 
                        // from the cell to the left and the length of the longest path from the cell to the top + 1
                        dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]) + 1;
                    } else {
                        // If the cell to the left is greater than the current cell and the cell to the top is smaller than the current cell,
                        // then the length of the longest path from the current cell is the length of the longest path from the cell to the left + 1
                        dp[i][j] = dp[i][j - 1] + 1;
                    }
                } else {
                    // Check if the cell to the top is greater than the current cell
                    if (array[i - 1][j] > array[i][j]) {
                        // If the cell to the left is smaller than the current cell and the cell to the top is greater than the current cell,
                        // then the length of the longest path from the current cell is the length of the longest path from the cell to the top + 1
                        dp[i][j] = dp[i - 1][j] + 1;
                    } else {
                        // If the cell to the left and the cell to the top are both smaller than the current cell,
                        // then the length of the longest path from the current cell is 1
                        dp[i][j] = 1;
                    }
                }

                // Update the maximum length
                maxLength = Math.max(maxLength, dp[i][j]);
            }
        } // End of loop through each row and each column

        return maxLength;
        
    } // End of findLongestPath
} // End of class Project3


