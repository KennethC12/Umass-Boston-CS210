import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

// A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations.
public class Solver {
    // Declares instance variables.
    private int moves; // Min number of moves needed to solve initial board.
    private LinkedStack<Board> solution; // Sequence of boards in a shortest
    // solution of initial board.

    // Finds a solution to the initial board using the A* algorithm.
    public Solver(Board board) {
        // Corner cases:
        if (board == null) {
            throw new NullPointerException("board is null");
        }
        if (!board.isSolvable()) {
            throw new IllegalArgumentException("board is unsolvable");
        }
        // Creates a MinPQ<SearchNode> object pq and inserts the initial search node
        // into it.
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(board, 0, null));
        // As long as pq is not empty:
        while (!pq.isEmpty()) {
            // Removes the smallest node from pq.
            SearchNode node = pq.delMin();

            // If the board in node is the goal board, extract from node the number of moves
            // in
            // solution and the solution and store values in instance variables moves and
            // solution
            // respectively, and break.
            if (node.board.isGoal()) {
                moves = node.moves;
                solution = new LinkedStack<Board>();
                while (node.previous != null) {
                    solution.push(node.board);
                    node = node.previous;
                }
                break;
            }
            // Otherwise, iterate over neighboring boards of node.board, and for each
            // neighbor that
            // is different from node.previous.board, insert a new SearchNode object into
            // pq,
            // constructed using appropriate values.
            for (Board neighbor : node.board.neighbors()) {
                Board prevBoard = null;
                if (node.previous != null) {
                    prevBoard = node.previous.board;
                }
                if (!neighbor.equals(prevBoard)) {
                    pq.insert(new SearchNode(neighbor, node.moves + 1, node));
                }
            }
        }
    }

    // Returns the minimum number of moves needed to solve the initial board.
    public int moves() {
        return moves;
    }

    // Returns a sequence of boards in a shortest solution of the initial board.
    public Iterable<Board> solution() {
        return solution;
    }

    // A data type that represents a search node in the grame tree. Each node
    // includes a
    // reference to a board, the number of moves to the node from the initial node,
    // and a
    // reference to the previous node.
    private class SearchNode implements Comparable<SearchNode> {
        // Declares instance variables.
        private Board board; // The board represented by this node.
        private int moves; // Number of moves it took to get this node from initial node.
        private SearchNode previous; // The previous search node.

        // Constructs a new search node.
        public SearchNode(Board board, int moves, SearchNode previous) {
            // Initializes instance variables appropriately.
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        // Returns a comparison of this node and other based on the following sum:
        // Manhattan distance of the board in the node + the # of moves to the node
        public int compareTo(SearchNode other) {
            return Integer.compare(this.board.manhattan() + this.moves,
                    other.board.manhattan() + other.moves);
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}