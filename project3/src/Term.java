import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class Term implements Comparable<Term> {
    private String query; // Declares instance variable query.
    private long weight; // Declares instance variable weight.

    // Constructs a term given the associated query string, having weight 0.
    public Term(String query) {
        if (query == null)
            throw new NullPointerException("query is null");

        // Initializes instance var.
        this.query = query;
        this.weight = 0; // Sets weight to 0.
    }

    // Constructs a term given the associated query string and weight.
    public Term(String query, long weight) {
        if (query == null)
            throw new NullPointerException("query is null");

        if (weight < 0)
            throw new IllegalArgumentException("Illegal weight");

        // Initializes instance var.
        this.query = query;
        this.weight = weight;
    }

    // Returns a string representation of this term.
    public String toString() {
        return this.weight + "\t" + this.query;
    }

    // Returns a comparison of this term and other by query.
    public int compareTo(Term other) {
        return this.query.compareTo(other.query);
    }

    // Returns a comparator for comparing two terms in reverse order of their
    // weights.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // Returns a comparator for comparing two terms by their prefixes of length r.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0)
            throw new IllegalArgumentException("Illegal r");
        return new PrefixOrder(r);
    }

    // Reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        // Returns a comparison of terms v and w by their weights in reverse order.
        public int compare(Term v, Term w) {
            if (w.weight < v.weight) {
                return -1;
            } else if (w.weight == v.weight) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    // Prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int r; // Declare prefix length in r.

        // Constructs a new prefix order given the prefix length.
        PrefixOrder(int r) {
            this.r = r;
        }

        // Returns a comparison of terms v and w by their prefixes of length r.
        public int compare(Term v, Term w) {
            String a;
            String b;

            if (v.query.length() < r)
                a = v.query;

            else
                a = v.query.substring(0, r);

            if (w.query.length() < r)
                b = w.query;

            else
                b = w.query.substring(0, r);

            return a.compareTo(b);
        }
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
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);

        }
    }
}
