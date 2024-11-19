import dsa.MinPQ;
import stdlib.StdOut;

public class Ramanujan2 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        MinPQ<Pair> queue = new MinPQ<Pair>();
        int x = 1;
        while (x * x * x < n) {
            queue.insert(new Pair(x, x + 1));
            x++;
        }

        Pair a;
        Pair b = null;

        while (!queue.isEmpty()) {
            a = b;
            b = queue.delMin();

            if (a != null && a.compareTo(b) == 0 && b.sumOfCubes <= n) {
                StringBuilder string = new StringBuilder();
                string.append(a.sumOfCubes + " = " + a.i + "^3 + " + a.j + "^3 = " + b.i + "^3 + " + b.j + "^3");
                StdOut.println(string);
            }

            if (b.j * b.j * b.j < n) {
                queue.insert(new Pair(b.i, b.j + 1));
            }
        }

    }

    // A data type that encapsulates a pair of numbers (i, j) and the sum of their
    // cubes.
    private static class Pair implements Comparable<Pair> {
        private int i; // first number in the pair
        private int j; // second number in the pair
        private int sumOfCubes; // i^3 + j^3

        // Constructs a pair (i, j).
        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
            sumOfCubes = i * i * i + j * j * j;
        }

        // Returns a comparison of pairs this and other based on their sum-of-cubes
        // values.
        public int compareTo(Pair other) {
            return sumOfCubes - other.sumOfCubes;
        }
    }
}
