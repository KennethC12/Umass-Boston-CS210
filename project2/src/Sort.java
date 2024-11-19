import dsa.LinkedStack;
import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        // Create a queue d
        LinkedDeque<String> d = new LinkedDeque<String>();
        d.addFirst(StdIn.readString());

        for (String w : StdIn.readAllStrings()) {
            // Adds w to the front of d if it is less than the first word in d.
            if (!d.isEmpty() && less(w, d.peekFirst())) {
                d.addFirst(w);
                // Adds w to the back of d if it is greater than the last word in d.
            } else if (!d.isEmpty() && less(d.peekLast(), w)) {
                d.addLast(w);
                // Otherwise, removes words that are less than w from
                // the front of and stores them in a temporary stack s.
            } else {
                LinkedStack<String> s = new LinkedStack<>();
                while (!d.isEmpty() && less(d.peekFirst(), w)) {
                    s.push(d.removeFirst());
                }
                // Adds w to the front of d.
                d.addFirst(w);
                // Also adds words from s to the front of d.
                for (String f : s) {
                    d.addFirst(f);
                }
            }
        }
        for (String f : d) {
            StdOut.println(f);
        }
    }

    // Returns true if v is less than w according to their lexicographic order, and
    // false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
