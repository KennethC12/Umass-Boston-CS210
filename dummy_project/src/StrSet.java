import dsa.Set;
import java.util.Iterator;
import stdlib.StdOut;

public class StrSet implements Iterable<String> {
    private Set<String> set; // the underlying

    public StrSet() {
        set = new Set<>(); // contruct a empty set
    }

    public StrSet(String[] keys) { // contruct a set from the key
        this();
        for (String k : keys) {
            set.add(k);
        }
    }

    public boolean isEmpty() {
        return set.isEmpty(); // Checking if it is empty
    }

    public int size() {
        return set.size(); // Checking the size
    }

    public void add(String key) {
        set.add(key); // add key to this set, if it is not already present
    }

    public boolean contains(String key) { // return true if this set contains key, and false otherwise
        return set.contains(key);
    }

    public StrSet union(StrSet other) { // return the union of this set and other, ie, the keys that are either tin this
                                        // set or other
        StrSet result = new StrSet();

        for (String s : this.set) {
            result.add(s);
        }

        for (String s : other) {
            result.add(s);
        }

        return result;
    }

    public StrSet symmetricDifference(StrSet other) { // return the summetric difference of this set and other, ie, the
                                                      // keys that are in this set or other but not both
        StrSet Diff = new StrSet();

        for (String s : this.set) {
            if (!other.contains(s)) {
                Diff.add(s);
            }
        }

        for (String s : other) {
            if (!this.contains(s)) {
                Diff.add(s);
            }
        }

        return Diff;
    }

    public Iterator iterator() {
        return set.iterator();
    }

    public String toString() {
        return set.toString();
    }

    public static void main(String[] args) {
        StrSet a = new StrSet(new String[] { "e", "i", "n", "s", "t", "e", "i", "n" });
        StrSet b = new StrSet(new String[] { "e", "i", "n" });
        StdOut.println(a);
        StdOut.println(b);
        StdOut.println(a.union(b));
        StdOut.println(a.symmetricDifference(b));
    }
}
