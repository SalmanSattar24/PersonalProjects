import java.io.*;
import java.util.*;

public class Project1 {

    public static int[][] menPref;
    public static int[][] womenPref;

    private static void readInput(String fileName) {
        File file = new File(fileName);
        try {
            Scanner sc = new Scanner(file);
            int n = sc.nextInt();
            menPref = new int[n][n];
            womenPref = new int[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    menPref[i][j] = sc.nextInt() - 1; // Adjust preference index to 0-based
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    womenPref[i][j] = sc.nextInt() - 1; // Adjust preference index to 0-based
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void stableMatching(int[][] preferences, String type) {
        int n = preferences.length;
        int[] currentPartner = new int[n];
        Arrays.fill(currentPartner, -1);
        boolean[] menEngaged = new boolean[n];

        Queue<Integer> freeMen = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            freeMen.offer(i);
        }

        while (!freeMen.isEmpty()) {
            int m = freeMen.poll();
            for (int i = 0; i < n && !menEngaged[m]; i++) {
                int w = preferences[m][i];
                if (currentPartner[w] == -1) { // Adjust index here too
                    currentPartner[w] = m;
                    menEngaged[m] = true;
                } else {
                    int m1 = currentPartner[w];
                    if (isBetterPartner(preferences, w, m, m1)) {
                        currentPartner[w] = m;
                        menEngaged[m] = true;
                        menEngaged[m1] = false;
                        freeMen.offer(m1);
                    }
                }
            }
        }

        // Print the stable matching
        System.out.println("Output when " + type + " propose:");
        for (int i = 0; i < n; i++) {
            System.out.println("(" + (currentPartner[i] + 1) + "," + (i + 1) + ")");
        }
    }

    private static boolean isBetterPartner(int[][] preferences, int w, int m, int m1) {
        int n = preferences.length;
        for (int i = 0; i < n; i++) {
            if (preferences[w][i] == m) { // Adjust index here too
                return true;
            }
            if (preferences[w][i] == m1) { // Adjust index here too
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Project1 input1.txt");
            return;
        }
        String fileName = args[0];
        readInput(fileName);

        // Print output for men first
        stableMatching(menPref, "men");

        // Then print output for women
        stableMatching(womenPref, "women");
    }
}
