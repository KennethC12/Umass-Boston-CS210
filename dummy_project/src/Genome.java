import stdlib.StdOut;
import java.util.Comparator;
import java.util.Iterator;

public class Genome implements Comparable<Genome>, Iterable<Character> {
    private String s; // the genome sequence

    public Genome(String s) {
        this.s = s;
    }

    public double gcContent() {
        int gcCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'G' || s.charAt(i) == 'C') {
                gcCount++;
            }
        }
        return ((double) gcCount / s.length()) * 100;
    }

    public String toString() {
        return s.length() + ":" + s;
    }

    public int compareTo(Genome other) {
        return Integer.compare(this.s.length(), other.s.length());
    }

    public static Comparator<Genome> gcOrder() {
        return new GCOrder();
    }

    public Iterator<Character> iterator() {
        return new ReverseIterator();
    }

    private static class GCOrder implements Comparator<Genome> {
        public int compare(Genome g1, Genome g2) {
            return Double.compare(g1.gcContent(), g2.gcContent());
        }
    }

    private class ReverseIterator implements Iterator<Character> {
        private int i; // index of current letter

        public ReverseIterator() {
            this.i = s.length() - 1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public Character next() {
            return s.charAt(i--);
        }
    }

    // Unit tests the data type.
    public static void main(String[] args) {
        Genome g1 = new Genome(args[0]);
        Genome g2 = new Genome(args[1]);
        StdOut.println(g1);
        StdOut.println(g2);
        StdOut.println(g1.gcContent());
        StdOut.println(g2.gcContent());
        StdOut.println(g1.compareTo(g2));
        StdOut.println(Genome.gcOrder().compare(g1, g2));
        for (char c : g1) {
            StdOut.print(c);
        }
        StdOut.println();
    }
}