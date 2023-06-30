import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {

    private final Queue<Board> solution = new Queue<Board>();
    private boolean solvable;

    private class SearchNode {
        private final Board board;
        private final int numberOfMovesMadeToReachTheBoard;
        private final SearchNode previousSearchNode;
        private final int priority;

        public SearchNode(Board board, int numberOfMovesMadeToReachTheBoard, SearchNode previousSearchNode) {
            this.board = board;
            this.numberOfMovesMadeToReachTheBoard = numberOfMovesMadeToReachTheBoard;
            this.previousSearchNode = previousSearchNode;
            this.priority = board.manhattan() + numberOfMovesMadeToReachTheBoard;

        }

        private Comparator<SearchNode> getComparatorByPriorityWithManhattanDistance() {
            return new SearchNode.ByPriorityWithManhattanDistance();
        }

        private class ByPriorityWithManhattanDistance implements Comparator<SearchNode> {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int priority1 = o1.priority;
                int priority2 = o2.priority;

                if (priority1 == priority2)
                    return 0;
                else if (priority1 < priority2)
                    return -1;
                return 1;
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();


        int numberOfMovesMadeToReachTheBoard = 0;
        Stack<Board> boardsSolution = new Stack<Board>();

        SearchNode sn = new SearchNode(initial, numberOfMovesMadeToReachTheBoard, null);
        SearchNode snTwin = new SearchNode(initial.twin(), numberOfMovesMadeToReachTheBoard, null);

        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>(sn.getComparatorByPriorityWithManhattanDistance());
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>(snTwin.getComparatorByPriorityWithManhattanDistance());
        minPQ.insert(sn);
        minPQTwin.insert(snTwin);

        SearchNode snSolution = minPQ.delMin();
        SearchNode snSolutionTwin = minPQTwin.delMin();

        solvable = snSolution.board.isGoal();
        boolean twinSolvable = snSolutionTwin.board.isGoal();

        while (!solvable && !twinSolvable) {
            numberOfMovesMadeToReachTheBoard = snSolution.numberOfMovesMadeToReachTheBoard + 1;

            for (Board neighbor : snSolution.board.neighbors()) {
                if (snSolution.previousSearchNode != null && snSolution.previousSearchNode.board.equals(neighbor))
                    continue;
                sn = new SearchNode(neighbor, numberOfMovesMadeToReachTheBoard, snSolution);
                minPQ.insert(sn);
            }

            snSolution = minPQ.delMin();
            solvable = snSolution.board.isGoal();

            for (Board neighbor : snSolutionTwin.board.neighbors()) {
                if (snSolutionTwin.previousSearchNode != null && snSolutionTwin.previousSearchNode.board.equals(neighbor))
                    continue;
                snTwin = new SearchNode(neighbor, numberOfMovesMadeToReachTheBoard, snSolutionTwin);
                minPQTwin.insert(snTwin);
            }

            snSolutionTwin = minPQTwin.delMin();
            twinSolvable = snSolutionTwin.board.isGoal();
        }

        if (solvable) {
            boardsSolution.push(snSolution.board);
            SearchNode previousSearchNode = snSolution.previousSearchNode;
            while (previousSearchNode != null) {
                boardsSolution.push(previousSearchNode.board);
                previousSearchNode = previousSearchNode.previousSearchNode;

            }

            while (!boardsSolution.isEmpty())
                solution.enqueue(boardsSolution.pop());

        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;

        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}