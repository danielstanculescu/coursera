import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private Queue<Board> queueOfBoardsSolution = new Queue<Board>();
    private class SearchNode{
        public final Comparator<SearchNode> BY_MANHATTAN_DISTANCE = new ByManhattanDistance();
        public Board thisBoard;
        public int numberOfMovesMadeToReachTheBoard;
        public Board previousBoard;

        public SearchNode(Board thisBoard, int numberOfMovesMadeToReachTheBoard, Board previousBoard){
            this.thisBoard = thisBoard;
            this.numberOfMovesMadeToReachTheBoard = numberOfMovesMadeToReachTheBoard;
            this.previousBoard = previousBoard;
        }

        private class ByManhattanDistance implements Comparator<SearchNode>{
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int manhattan1 = o1.thisBoard.manhattan();
                int manhattan2 = o2.thisBoard.manhattan();

                if( manhattan1 == manhattan2)
                    return 0;
                else if(manhattan1 < manhattan2)
                    return -1;
                return 1;
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){

        int numberOfMovesToReachTheBoard = 0;

        SearchNode sn = new SearchNode(initial,numberOfMovesToReachTheBoard, null);

        MinPQ<SearchNode> minPQ = new MinPQ(sn.BY_MANHATTAN_DISTANCE);
        minPQ.insert(sn);

        SearchNode snSolution = minPQ.delMin();
        queueOfBoardsSolution.enqueue(snSolution.thisBoard);
        numberOfMovesToReachTheBoard ++;

        while(!snSolution.thisBoard.isGoal()) {
            for (Board neighbor : snSolution.thisBoard.neighbors()) {
                if (snSolution.previousBoard != null && snSolution.previousBoard.equals(neighbor))
                    continue;
                sn = new SearchNode(neighbor, numberOfMovesToReachTheBoard, initial);
                minPQ.insert(sn);
            }

            snSolution = minPQ.delMin();
            queueOfBoardsSolution.enqueue(snSolution.thisBoard);
            numberOfMovesToReachTheBoard++;
        }
    }
/*
    // is the initial board solvable? (see below)
    public boolean isSolvable(){

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {

    }
*/
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

        StdOut.println(initial.manhattan());
        StdOut.println(initial.hamming());
        StdOut.println(initial.isGoal());

        for(Board b : initial.neighbors()){
            StdOut.println(b);
        }

        Board b2 = initial.twin();
        StdOut.println(b2);



        // solve the puzzle
        Solver solver = new Solver(initial);
/*
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

 */
    }

}