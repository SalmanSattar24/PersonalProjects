/**
 * This code is written by Salman Sattar.
 * For COMP 482, Prof: Mrs. Lord
 * This code reads job details from a file and implements three different algorithms for job scheduling:
 * Earliest Deadline First (EDF), Shortest Job First (SJF), and Least Slack First (LSF).
 * The code can print the jobs and the late jobs for each algorithm after scheduling them.
 * @version 1.0
 */

// Import the required packages
import java.io.*;
import java.util.*;

// Create the Project2 class
public class Project2 {
    public static void main(String[] args) {
        
        if (args.length != 1) {
            System.err.println("Usage: java project2 input2.txt");
            System.exit(1);
        }
    
        String inputFileName = args[0]; // Get the input file name from command line arguments
        
        int n = 0; // Number of jobs
        int[][] jobs = null; // Array to store the jobs

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFileName));
    
            // Read and process the file content here
            n = Integer.parseInt(br.readLine().trim());
            jobs = new int[n][2];

            // Read job details from input file
            for (int i = 0; i < n; i++) {
                String[] jobData = br.readLine().split(" ");
                jobs[i][0] = Integer.parseInt(jobData[0]); // Processing time (pi)
                jobs[i][1] = Integer.parseInt(jobData[1]); // Deadline (di)
            }
    
            br.close(); // Make sure to close the file reader when done
        } catch (IOException e) {
            System.err.println("Error reading the input file.");
            e.printStackTrace();
        }

        // Print the jobs 
        // printJobs(jobs);

        // Call the scheduling algorithms

        // Call the Earliest Deadline First (EDF) algorithm
        earliestDeadlineFirst(jobs);
        // Print the late jobs

        // Call the Shortest Job First (SJF) algorithm
        shortestJobFirst(jobs);
        // Print the late jobs

        // Call the Least Slack First (LSF) algorithm
        leastSlackFirst(jobs);
        // Print the late jobs



    }

    // Method to print the jobs
        /**
         * This method prints the jobs.
         * @param jobs a 2D array of jobs, where each row represents a job and has two columns: length and deadline.
         */
        public static void printJobs(int[][] jobs) {
            System.out.println("Length" + "\t" + "Deadline");
            for (int i = 0; i < jobs.length; i++) {
                if (jobs[i][0] != 0) {
                    System.out.println(jobs[i][0] + "\t" + jobs[i][1]);
                }
            }
        } // End of printJobs method


    // Method to sort the jobs by deadline (di) in ascending order
        /**
         * This method implements the Earliest Deadline First (EDF) algorithm for job scheduling.
         * It takes a 2D array of jobs as input and returns a 2D array of late jobs.
         * @param jobs a 2D array of jobs, where each row represents a job and has two columns: length and deadline.
         * @return a 2D array of late jobs, where each row represents a late job and has two columns: length and deadline.
         */
        public static int earliestDeadlineFirst(int[][] jobs) {
            // Copy the jobs array to a new array to work with
            int[][] jobsCopy = new int[jobs.length][jobs[0].length];
            // Copy the values from jobs to jobsCopy
            for (int i = 0; i < jobs.length; i++) {
                for (int j = 0; j < jobs[0].length; j++) {
                    jobsCopy[i][j] = jobs[i][j];
                }
            }
            
            // Create a late jobs array to store the late jobs
            int[][] lateJobs = new int[jobsCopy.length][jobsCopy[0].length];

            int lateJobsCount = 0; // Initialize the late jobs count to 0

            // Sort the jobs by deadline (di) in ascending order
            Arrays.sort(jobsCopy, Comparator.comparingInt(job -> job[1]));
            int currentTime = 0; // Initialize the current time to 0

            // Iterate through the jobsCopy array and check if the current time
            // plus the length of the job is less than or equal to the deadline
            for (int i = 0; i < jobsCopy.length; i++) {
                // If the current time plus the length of the job is less than or equal
                //  to the deadline, then add the length of the job to the current time
                if (currentTime + jobsCopy[i][0] <= jobsCopy[i][1]) {
                    currentTime += jobsCopy[i][0];
                } else {
                    // If the current time plus the length of the job is greater than the deadline,
                    // then add the length of the job to the late jobs array
                    // and increment the late jobs count, and add the job to the late jobs array
                    currentTime += jobsCopy[i][0];
                    lateJobs[i][0] = jobsCopy[i][0];
                    lateJobs[i][1] = jobsCopy[i][1];
                    lateJobsCount++;
                }
            }
            // Print the reordered jobsCopy and the late jobsCopy
            System.out.println("\nEarliest Deadline First: " + lateJobsCount);

            // Optional print statements
            // printJobs(jobsCopy);
            // System.out.println("\nLate jobs: ");
            // printJobs(lateJobs);

            // Print the late jobs
            // Print the length of the late jobs array
            // System.out.println("\nLate jobs (EDF): ");
            // printJobs(lateJobs);

            return lateJobsCount;
        } // End of earliestDeadlineFirst method


    // Method to sort the jobs by processing time (pi) in ascending order
        /**
         * This method implements the Shortest Job First (SJF) algorithm for job scheduling.
         * It takes a 2D array of jobs as input and returns a 2D array of late jobs.
         * @param jobs a 2D array of jobs, where each row represents a job and has two columns: length and deadline.
         * @return a 2D array of late jobs, where each row represents a late job and has two columns: length and deadline.
         */
        public static int shortestJobFirst(int[][] jobs) {
            // Copy the jobs array to a new array to work with
            int[][] jobsCopy = new int[jobs.length][jobs[0].length];
            // Copy the values from jobs to jobsCopy
            for (int i = 0; i < jobs.length; i++) {
                for (int j = 0; j < jobs[0].length; j++) {
                    jobsCopy[i][j] = jobs[i][j];
                }
            }
            
            // Create a late jobs array to store the late jobs
            int[][] lateJobs = new int[jobsCopy.length][jobsCopy[0].length];

            int lateJobsCount = 0; // Initialize the late jobs count to 0

            // Sort the jobs by processing time (pi) in ascending order
            Arrays.sort(jobsCopy, Comparator.comparingInt(job -> job[0]));
            int currentTime = 0; // Initialize the current time to 0

            // Iterate through the jobsCopy array and check if the current time
            // plus the length of the job is less than or equal to the deadline
            for (int i = 0; i < jobsCopy.length; i++) {
                // If the current time plus the length of the job is less than or equal
                //  to the deadline, then add the length of the job to the current time
                if (currentTime + jobsCopy[i][0] <= jobsCopy[i][1]) {
                    currentTime += jobsCopy[i][0];
                } else {
                    // If the current time plus the length of the job is greater than the deadline,
                    // then add the length of the job to the late jobs array
                    // and increment the late jobs count, and add the job to the late jobs array
                    currentTime += jobsCopy[i][0];
                    lateJobs[i][0] = jobsCopy[i][0];
                    lateJobs[i][1] = jobsCopy[i][1];
                    lateJobsCount++;
                }
            }
            // Print the reordered jobsCopy and the late jobsCopy
            System.out.println("\nShortest Job First: " + lateJobsCount);

            // Optional print statements
            // printJobs(jobsCopy);

            // Print the late jobs
            // System.out.println("\nLate jobs (SJF): ");
            // printJobs(lateJobs);

            return lateJobsCount;
        } // End of shortestJobFirst method


    // Method to sort the jobs by slack time (di - pi) in ascending order
        /**
         * This method implements the Least Slack First (LSF) algorithm for job scheduling.
         * It takes a 2D array of jobs as input and returns a 2D array of late jobs.
         * @param jobs a 2D array of jobs, where each row represents a job and has two columns: length and deadline.
         * @return a 2D array of late jobs, where each row represents a late job and has two columns: length and deadline.
         */
        public static int leastSlackFirst(int[][] jobs) {
            // Copy the jobs array to a new array to work with
            int[][] jobsCopy = new int[jobs.length][jobs[0].length];
            // Copy the values from jobs to jobsCopy
            for (int i = 0; i < jobs.length; i++) {
                for (int j = 0; j < jobs[0].length; j++) {
                    jobsCopy[i][j] = jobs[i][j];
                }
            }
            
            // Create a late jobs array to store the late jobs
            int[][] lateJobs = new int[jobsCopy.length][jobsCopy[0].length];

            int lateJobsCount = 0; // Initialize the late jobs count to 0

            // Calculate slack time (di - pi) for each job and sort them in ascending order of slack
            Arrays.sort(jobsCopy, Comparator.comparingInt(job -> job[1] - job[0]));
            int currentTime = 0; // Initialize the current time to 0

            // Iterate through the jobsCopy array and check if the current time
            // plus the length of the job is less than or equal to the deadline
            for (int i = 0; i < jobsCopy.length; i++) {
                // If the current time plus the length of the job is less than or equal
                //  to the deadline, then add the length of the job to the current time
                if (currentTime + jobsCopy[i][0] <= jobsCopy[i][1]) {
                    currentTime += jobsCopy[i][0];
                } else {
                    // If the current time plus the length of the job is greater than the deadline,
                    // then add the length of the job to the late jobs array
                    // and increment the late jobs count, and add the job to the late jobs array
                    currentTime += jobsCopy[i][0];
                    lateJobs[i][0] = jobsCopy[i][0];
                    lateJobs[i][1] = jobsCopy[i][1];
                    lateJobsCount++;
                }
            }
            // Print the reordered jobsCopy and the late jobsCopy
            System.out.println("\nLeast Slack First: " + lateJobsCount + "\n");

            // Optional print statements
            // printJobs(jobsCopy);

            // Print the late jobs
            // System.out.println("\nLate jobs: ");
            // printJobs(lateJobs);

            return lateJobsCount;
        } // End of leastSlackFirst method

} // End of Project2 class
