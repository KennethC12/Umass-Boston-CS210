import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    private Term[] terms;

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
            throw new NullPointerException("terms is null");

        // Initializes this.terms to a defensive copy.
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }

        // Sorts the terms in lexicographic order.
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their
    // weights.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new NullPointerException("prefix is null");

        // Finds index of first term.
        Term t = new Term(prefix);

        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, t, prefixOrder);
        int j = numberOfMatches(prefix);

        // Finds number of terms in terms that stat with the prefix.
        Term[] matches = new Term[j];

        // Constructs an array matches that contains n elements from terms,
        // starting at index i.
        for (int n = 0; n < j; n++) {
            matches[n] = terms[i + n];
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new NullPointerException("prefix is null");

        Term p = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, p, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, p, prefixOrder);

        if (i == -1 || j == -1) {
            return 0;
        }
        return j - i + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
