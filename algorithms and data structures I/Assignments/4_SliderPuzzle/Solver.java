import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {

    private final Queue<Board> queueOfBoardsSolution = new Queue<Board>();
    private final Queue<Board> queueOfBoardsSolutionTwin = new Queue<Board>();

    private boolean solvable;
    private int numberOfMovesToReachTheBoard = 0;

    private class SearchNode {
        public final Comparator<SearchNode> BY_MANHATTAN_DISTANCE = new ByPriorityWithManhattanDistance();
        public Board currentBoard;
        public int numberOfMovesMadeToReachTheBoard;
        public Board previousBoard;
        public int manhattanDistance;

        public SearchNode(Board currentBoard, int numberOfMovesMadeToReachTheBoard, Board previousBoard) {
            this.currentBoard = currentBoard;
            this.numberOfMovesMadeToReachTheBoard = numberOfMovesMadeToReachTheBoard;
            this.previousBoard = previousBoard;
            this.manhattanDistance = currentBoard.manhattan();
        }

        private class ByPriorityWithManhattanDistance implements Comparator<SearchNode> {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int priority1 = o1.manhattanDistance + numberOfMovesMadeToReachTheBoard;
                int priority2 = o2.manhattanDistance + numberOfMovesMadeToReachTheBoard;

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


        SearchNode sn = new SearchNode(initial, numberOfMovesToReachTheBoard, null);
        SearchNode snTwin = new SearchNode(initial.twin(), numberOfMovesToReachTheBoard, null);

        MinPQ<SearchNode> minPQ = new MinPQ(sn.BY_MANHATTAN_DISTANCE);
        MinPQ<SearchNode> minPQTwin = new MinPQ(snTwin.BY_MANHATTAN_DISTANCE);
        minPQ.insert(sn);
        minPQTwin.insert(snTwin);

        SearchNode snSolution = minPQ.delMin();
        SearchNode snSolutionTwin = minPQTwin.delMin();

        queueOfBoardsSolution.enqueue(snSolution.currentBoard);
        queueOfBoardsSolutionTwin.enqueue(snSolution.currentBoard);

        solvable = snSolution.currentBoard.isGoal();
        boolean twinSolvable = snSolutionTwin.currentBoard.isGoal();
        while (!solvable && !twinSolvable) {

            for (Board neighbor : snSolution.currentBoard.neighbors()) {
                if (snSolution.previousBoard != null && snSolution.previousBoard.equals(neighbor))
                    continue;
                sn = new SearchNode(neighbor, numberOfMovesToReachTheBoard, initial);
                minPQ.insert(sn);
            }

            snSolution = minPQ.delMin();
            queueOfBoardsSolution.enqueue(snSolution.currentBoard);
            solvable = snSolution.currentBoard.isGoal();

            for (Board neighbor : snSolutionTwin.currentBoard.neighbors()) {
                if (snSolutionTwin.previousBoard != null && snSolutionTwin.previousBoard.equals(neighbor))
                    continue;
                snTwin = new SearchNode(neighbor, numberOfMovesToReachTheBoard, initial);
                minPQTwin.insert(snTwin);
            }

            snSolutionTwin = minPQTwin.delMin();
            queueOfBoardsSolutionTwin.enqueue(snSolution.currentBoard);
            twinSolvable = snSolutionTwin.currentBoard.isGoal();

            numberOfMovesToReachTheBoard++;
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
        return numberOfMovesToReachTheBoard;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;

        return new IterableBoardOfSolutions();
    }

    private class IterableBoardOfSolutions implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return queueOfBoardsSolution.iterator();
        }
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

        Board twin = initial.twin();

        StdOut.println(twin.toString());

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