import stdlib.In;
import stdlib.StdOut;

public class Stats {
    public static void main(String[] args) {
        StrSet setA = new StrSet();
        StrSet setB = new StrSet();

        In a = new In(args[0]);
        In b = new In(args[1]);

        while (!a.isEmpty()) {

            String C = a.readString();
            setA.add(C);
        }

        while (!b.isEmpty()) {
            String C = b.readString();
            setB.add(C);
        }

        StrSet Difference = new StrSet();
        for (String s : setA) {
            if (!setB.contains(s))
                Difference.add(s);
        }

        for (String s : setB) {
            if (!setA.contains(s))
                Difference.add(s);
        }
        StdOut.println(setA.size());
        StdOut.println(setB.size());
        StdOut.println(Difference.size());
    }
}
