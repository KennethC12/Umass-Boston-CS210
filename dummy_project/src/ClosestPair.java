import java.util.Arrays;
import stdlib.StdIn;
import stdlib.StdOut;

public class ClosestPair {
    // Entry point.
    public static void main(String[] args) {
        int[] a = StdIn.readAllInts(); // Read all integers from StdIn
        closestPair(a); // Find and print the closest pair of integers
    }

    private static void closestPair(int[] a) {
        if (a == null || a.length < 2) {
            StdOut.println("Array must contain at least two elements.");
            return;
        }

        Arrays.sort(a); // Sort the array

        int minDiff = Integer.MAX_VALUE;
        int pair1 = a[0], pair2 = a[1]; // Initialize with the first two elements

        // Traverse the sorted array to find the closest pair
        for (int i = 0; i < a.length - 1; i++) {
            int diff = a[i + 1] - a[i];
            if (diff < minDiff) {
                minDiff = diff;
                pair1 = a[i];
                pair2 = a[i + 1];
            }
        }

        StdOut.println(pair1 + " " + pair2); // Print the closest pair
    }
}
