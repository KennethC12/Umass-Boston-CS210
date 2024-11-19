import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {
        // Accepts lo (int), hi (int), k (int), and mode (String) as command-line
        // arguments.
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        String mode = args[3];

        // Creates a random queue q that contains integers from the interval [lo, hi].
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<Integer>();
        for (int i = lo; i <= hi; i++) {
            q.enqueue(i);
        }

        // If mode is + (sampling with replacement), sample and write k
        // integers from q to standard output.
        if (!mode.equals("+") && !mode.equals("-")) {
            throw new IllegalArgumentException("Illegal mode");
        } else if (mode.equals("+")) {
            for (int x = 0; x < k; x++) {
                StdOut.println(q.sample());
            }
        } else if (mode.equals("-")) {
            // If mode is - (sampling without replacement),
            // dequeue and write k integers from q to standard output.
            for (int x = 0; x < k; x++) {
                StdOut.println(q.dequeue());
            }
        }

    }
}
