import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    private final int[][] tiles;
    private final int n;
    private int hamming;
    private int manhattan;
    private int blankPos;

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and
    // column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0)
                    blankPos = (n * i) + j + 1;
            }
        }

        this.hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                if (tiles[i][j] != (i * n + (j + 1))) { // if it isnt the right pos
                    this.hamming++;
                }
            }
        }
        // Checks if the tile is out of place and compares with row-major order.
        this.manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != (i * n + (j + 1))) { // if it isnt the right pos
                    int y = (tiles[i][j] - 1) / n; // row gap
                    int x = (tiles[i][j] - 1) - (n * y); /// column gap
                    this.manhattan += (Math.abs(i - y) + Math.abs(j - x)); // add the change between the
                    // original spot and goal destination in both dimensions
                }
            }
        }
    }

    // Returns the size of this board.
    public int size() {
        return n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return this.hamming;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return this.manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        return this.manhattan == 0 && this.hamming() == 0; // no moves needed to create goal
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        // Creates an array of size n^2 - 1 containing the tiles of the board in
        // row-major order.
        int[] array = new int[n * n - 1];
        int idx = 0; // counter
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) // skip blank tile
                    continue;
                array[idx++] = tiles[i][j];
            }
        }
        int inversions = (int) Inversions.count(array);

        if (n % 2 != 0) {
            if (inversions % 2 == 0) { // if inversions even
                return true;
            }
            return false;
        } else {
            int sum = ((blankPos - 1) / n) + inversions;
            if (sum % 2 == 0) {
                return false;
            }
            return true;
        }

    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> q = new LinkedQueue<Board>();
        int i = (blankPos - 1) / n; // row of blankPos
        int j = (blankPos - 1) % n; // col of blankPos

        if (i + 1 < n) {
            // Clones the tiles of the board.
            int[][] clone = cloneTiles();
            // Exchanges an appropriate tile with the blank tile in the clone.
            int blank = clone[i + 1][j];
            clone[i + 1][j] = clone[i][j];
            clone[i][j] = blank;
            // Constructs a Board object from the clone, and enqueues it into q.
            q.enqueue(new Board(clone));
        }
        if (i - 1 >= 0) {
            // Clone the tiles of the board.
            int[][] clone = cloneTiles();
            int blank = clone[i - 1][j];
            // Exchange an appropriate tile with the blank tile in the clone.
            clone[i - 1][j] = clone[i][j];
            clone[i][j] = blank;
            // Construct a Board object from the clone, and enqueues it into q.
            q.enqueue(new Board(clone));
        }
        if (j + 1 < n) {
            // Clones the tiles of the board.
            int[][] clone = cloneTiles();
            int blank = clone[i][j + 1];
            // Exchanges an appropriate tile with the blank tile in the clone.
            clone[i][j + 1] = clone[i][j];
            clone[i][j] = blank;
            // Constructs a Board object from the clone, and enqueues it into q.
            q.enqueue(new Board(clone));
        }
        if (j - 1 >= 0) {
            // Clones the tiles of the board.
            int[][] clone = cloneTiles();
            int blank = clone[i][j - 1];
            // Exchanges an appropriate tile with the blank tile in the clone.
            clone[i][j - 1] = clone[i][j];
            clone[i][j] = blank;
            // Constructs a Board object from the clone, and enqueues it into q.
            q.enqueue(new Board(clone));
        }
        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        if (this.n != ((Board) (other)).n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != ((Board) (other)).tiles[i][j]) // Returns true if the board is the same as other
                                                                       // and false if otherwise.
                    return false;
            }
        }
        return true;
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}