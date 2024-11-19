import java.util.Arrays;

import stdlib.StdIn;
import stdlib.StdOut;

public class Count {

    public static void main(String[] args) {
        int[] a = StdIn.readAllInts();
        StdOut.println(distinct(a));
    }

    private static int distinct(int[] a) {
        if (a.length == 0) // Return 0 if the array is empty
            return 0;

        Arrays.sort(a); // Sorts the array in place

        int distinctCount = 1; // Initializes the count of distinct elements
        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i - 1]) { // Increment count if the current element is not equal to the last one
                distinctCount++;
            }
        }

        return distinctCount; // return total distinctcount
    }
}