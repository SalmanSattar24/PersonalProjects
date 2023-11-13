import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This code is written by Salman Sattar.
 * For COMP 482, Instructor: Mrs. Lord
 * The GaleShapleyAlgo class implements the Gale-Shapley algorithm for solving the stable marriage problem.
 * The algorithm takes in two preference lists, one for the proposers and one for the receivers, and outputs a stable set of pairs.
 * The class contains a main method that reads in an input file, runs the algorithm twice (once with men proposing and once with women proposing), and prints the resulting pairs.
 */
public class Project1 {

    // Number of proposers and receivers
    private static int N;
    // Men's preferences
    private static int[][] menPref;
    // Women's preferences
    private static int[][] womenPref;
    // Resulting pairs when men propose
    private static int[][] menPairs;
    // Resulting pairs when women propose
    private static int[][] womenPairs;

    public static void main(String[] args) throws FileNotFoundException {
        // Create a Scanner to read the input file
        Scanner scanner = new Scanner(new File(args[0]));

        // Read the number of proposers and receivers
        N = scanner.nextInt();

        // Read the men's preferences
        menPref = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                menPref[i][j] = scanner.nextInt() - 1;
            }
        }

        // Read the women's preferences
        womenPref = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                womenPref[i][j] = scanner.nextInt() - 1;
            }
        }

        // Run the Gale-Shapley algorithm with men as proposers
        menPairs = galeShapley(menPref, womenPref);
        // Sort the resulting pairs
        sortPairs(menPairs);
        // Print the resulting pairs
        System.out.println("Output when men propose:");
        printPairs(menPairs);

        // Run the Gale-Shapley algorithm with women as proposers
        womenPairs = galeShapley(womenPref, menPref);
        // Sort the resulting pairs
        sortPairs(womenPairs);
        // Print the resulting pairs
        System.out.println("Output when women propose:");
        printPairs(womenPairs);
    } // End of main

    // NOT USED, Method to print a 2D array mainly to see the preference lists
    @SuppressWarnings("unused")
    private static void printList(int[][] arr) { 
        // Iterate over each row
        for (int i = 0; i < arr.length; i++) {
            // Iterate over each column in the current row
            for (int j = 0; j < arr[i].length; j++) {
                // Print the current element followed by a space
                System.out.print(arr[i][j] + " ");
            }
            // Print a newline to move to the next row
            System.out.println();
        }
    }

    // Method to check if a receiver prefers a new proposer over her current partner
    private static boolean prefersNewProposer(int receiver, int newProposer, int currentPartner, int[][] receiversPref) {
        // Iterate over the receiver's preference list
        for (int i = 0; i < N; i++) {
            // If the new proposer is found before the current partner in the preference list, return true
            if (receiversPref[receiver][i] == newProposer) return true; 

            // If the current partner is found before the new proposer in the preference list, return false
            if (receiversPref[receiver][i] == currentPartner) return false;
        }
        // If neither the new proposer nor the current partner is found in the preference list, return false
        return false;
    }

    // Method to sort the pairs in ascending order of the proposer
    private static void sortPairs(int[][] pairs) {
        // Use Arrays.sort with a lambda expression to sort the pairs
        Arrays.sort(pairs, (pair1, pair2) -> Integer.compare(pair1[0], pair2[0]));
    }

    // This method is used to print the pairs
    private static void printPairs(int[][] pairs) {
        for (int i = 0; i < N; i++) {
            System.out.println("(" + pairs[i][0] + ", " + pairs[i][1] + ")");
        }
    }

    // This method is used to implement the Gale-Shapley algorithm
    private static int[][] galeShapley(int[][] proposersPref, int[][] receiversPref) {
        // Initialize the pairs array, receiversPartners array, proposersEngaged array, and proposersNextProposal array
        int[][] pairs = new int[N][2];
        int[] receiversPartners = new int[N];
        boolean[] proposersEngaged = new boolean[N];
        int[] proposersNextProposal = new int[N];

        // While there is a free proposer
        while (true) {
            // Find the first free proposer
            int freeProposer = -1;
            // Go through the proposersEngaged array and find the first free proposer
            for (int i = 0; i < N; i++) {
                // If the proposer is not engaged, set freeProposer to the index of the proposer
                if (!proposersEngaged[i]) {
                    freeProposer = i;
                    break;
                }
            } 
            // If there is no free proposer, break out of the while loop
            if (freeProposer == -1) break;

            // Otherwise, if there is a free proposer, find a receiver for the proposer
            // Find the first receiver in the free proposer's preference list
            int receiver = proposersPref[freeProposer][proposersNextProposal[freeProposer]++];
            // Find the current partner of the receiver
            int currentPartner = receiversPartners[receiver] - 1; // Subtract 1 to adjust for 0-based indexing
            // If the receiver has no current partner or prefers the new proposer over the current partner
            if (currentPartner == -1 || prefersNewProposer(receiver, freeProposer, currentPartner, receiversPref)) {
                // If the receiver has a current partner, set the current partner to not engaged
                if (currentPartner != -1) proposersEngaged[currentPartner] = false;
                // Set the partner of the receiver to the new proposer
                receiversPartners[receiver] = freeProposer + 1;
                // Set the new proposer to engaged
                proposersEngaged[freeProposer] = true;
            }
        }

        // Go through the receiversPartners array and set the pairs array
        for (int i = 0; i < N; i++) {
            pairs[i][0] = receiversPartners[i];
            pairs[i][1] = i + 1;
        }
        return pairs;
    } // End of galeShapley method


} // End of class GaleShapleyAlgo

