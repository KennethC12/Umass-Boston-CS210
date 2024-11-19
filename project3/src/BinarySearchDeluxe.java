import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1,
    // according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null)
            throw new NullPointerException("a, key, or c is null");

        // Declares and initializes lo, hi, and index variables.
        int lo = 0;
        int hi = a.length - 1;
        int index = -1;

        // instead of returning mid, remember it in, say index, and adjust hi
        // appropriately.
        // While the value of lo is less than or equal to hi
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int x = c.compare(key, a[mid]);
            // If x is less than 0:
            if (x < 0) {
                hi = mid - 1;
                // If x is greater than 0:
            } else if (x > 0) {
                lo = mid + 1;
                // Otherwise:
            } else {
                index = mid;
                hi = mid - 1;
            }
        }
        // Returns the index.
        return index;
    }

    // Returns the index of the first key in a that equals the search key, or -1,
    // according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null)
            throw new NullPointerException("a, key, or c is null");

        // Declares and initializes variables lo, hi, and index.
        int lo = 0;
        int hi = a.length - 1;
        int index = -1;

        // While the value of lo is less than or equal to hi...
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int x = c.compare(key, a[mid]);
            // If x is less than 0:
            if (x < 0) {
                hi = mid - 1;
                // If x is greater than 0:
            } else if (x > 0) {
                lo = mid + 1;
                // Otherwise:
            } else {
                lo = mid + 1;
                index = mid;
            }
        }
        return index;
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
