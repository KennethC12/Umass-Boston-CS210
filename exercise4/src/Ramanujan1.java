import stdlib.StdOut;

public class Ramanujan1 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int a = 1; a <= Math.cbrt(n); a++) {
            for (int b = a + 1; b <= Math.cbrt(n - a * a * a); b++) {
                for (int c = a + 1; c <= Math.cbrt(n); c++) {
                    for (int d = c + 1; d <= Math.cbrt(n - c * c * c); d++) {
                        // Check if a^3 + b^3 = c^3 + d^3 and ensure they are within the limit n.
                        if (a * a * a + b * b * b == c * c * c + d * d * d && a * a * a + b * b * b <= n) {
                            StdOut.println(
                                    a * a * a + b * b * b + " = " + a + "^3 + " + b + "^3 = " + c + "^3 + " + d + "^3");
                            break; // Found a match, no need to continue with this 'd'
                        }
                    }
                }
            }
        }
    }
}
