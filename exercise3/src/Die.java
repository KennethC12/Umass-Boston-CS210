import stdlib.StdOut;
import stdlib.StdRandom;

public class Die implements Comparable<Die> {
    private int value; // the face value

    // Constructs a die.
    public Die() {
        value = 0;
    }

    // Rolls this die.
    public void roll() {
        value = StdRandom.uniform(6) + 1;
    }

    // Returns the face value of this die.
    public int value() {
        return value;
    }

    // Returns true if this die is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Die o = (Die) other;
        return this.value == o.value;
    }

    // Returns a comparison of this die with other, by their face values.
    public int compareTo(Die that) {
        return this.value - that.value;
    }

    // Returns a string representation of this die.
    public String toString() {
        String output;
        if (value == 1) {
            output = "     \n  *  \n     ";
        } else if (value == 2) {
            output = "*    \n     \n    *";
        } else if (value == 3) {
            output = "*    \n  *  \n    *";
        } else if (value == 4) {
            output = "*   *\n     \n*   *";
        } else if (value == 5) {
            output = "*   *\n  *  \n*   *";
        } else if (value == 6) {
            output = "* * *\n     \n* * *";
        } else {
            output = "Not rolled yet";
        }
        return output;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        Die a = new Die();
        a.roll();
        while (a.value() != x) {
            a.roll();
        }
        Die b = new Die();
        b.roll();
        while (b.value() != y) {
            b.roll();
        }
        Die c = new Die();
        c.roll();
        while (c.value() != z) {
            c.roll();
        }
        StdOut.println("Dice a, b, and c:");
        StdOut.println(a);
        StdOut.println(b);
        StdOut.println(c);
        StdOut.println("a.equals(b)    = " + a.equals(b));
        StdOut.println("b.equals(c)    = " + b.equals(c));
        StdOut.println("a.compareTo(b) = " + a.compareTo(b));
        StdOut.println("b.compareTo(c) = " + b.compareTo(c));
    }
}
