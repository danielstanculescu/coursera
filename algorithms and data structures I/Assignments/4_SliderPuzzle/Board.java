import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Board {

    private int n;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.tiles[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString(){

        String result = n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                if(j != n-1)
                    result += tiles[i][j] + "\t";
                else
                    result += tiles[i][j];
            }
            if(i != n-1)
                result += "\n";
        }
        return result;
    }

    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j] != 0)
                    result += isTileGoal(tiles[i][j], i, j);
            }
        }
        return n*n-1-result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j] != 0)
                    result += distanceToGoal(tiles[i][j], i, j);
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming()==0?true:false;
    }

    // does this board equal y?
    public boolean equals(Object y){

        if (y == this) {
            return true;
        }
        if (!(y instanceof Board)) {
            return false;
        }

        Board b = (Board)y;
        if(n != b.n)
            return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j] != b.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        Iterable<Board> it = new IterableBoard();
        return it;
    }

    private class IterableBoard implements Iterable<Board>{

        @Override
        public Iterator<Board> iterator() {
            Iterator<Board> bi = new BoardIterator();
            return bi;
        }
    }

    private class BoardIterator implements Iterator<Board>{

        private int position = 0;
        private Board[] neighbors;

        public BoardIterator(){
            int i=0, j=0;

            // find x position and y position of the tile 0
            outer: for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if(tiles[i][j] == 0)
                        break outer;
                }
            }

            int numberOfNeighbors = 4;
            int indexNeighbour = 0;
            // depending of the position of 0 => number of neighbors
            if(i == 0 || i == n - 1)
                numberOfNeighbors --;
            if(j == 0 || j == n - 1)
                numberOfNeighbors --;

            neighbors = new Board[numberOfNeighbors];

            // tile 0 with right
            if(i < n-1){
                int[][] tilesClone = new int[n][n];
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        tilesClone[k][l] = tiles[k][l];
                    }
                }

                exch(tilesClone, i, j, i+1, j);
                Board neighbour = new Board(tilesClone);
                neighbors[indexNeighbour++] = neighbour;
            }

            // tile 0 with left
            if(i>0) {
                int[][] tilesClone = new int[n][n];
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        tilesClone[k][l] = tiles[k][l];
                    }
                }
                exch(tilesClone, i, j, i - 1, j);
                Board neighbour = new Board(tilesClone);
                neighbors[indexNeighbour++] = neighbour;
            }

            // tile 0 with up
            if(j > 0) {
                int[][] tilesClone = new int[n][n];
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        tilesClone[k][l] = tiles[k][l];
                    }
                }
                exch(tilesClone, i, j, i, j - 1);
                Board neighbour = new Board(tilesClone);
                neighbors[indexNeighbour++] = neighbour;
            }

            // tile 0 with down
            if(j < n-1){
                int[][] tilesClone = new int[n][n];
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        tilesClone[k][l] = tiles[k][l];
                    }
                }
                exch(tilesClone, i, j, i, j+1);
                Board neighbour = new Board(tilesClone);
                neighbors[indexNeighbour++] = neighbour;
            }
        }

        @Override
        public boolean hasNext() {

            return position < neighbors.length;
        }

        @Override
        public Board next() {
            return neighbors[position++];
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){

        int i, j, x, y;
        while(true) {
            i = StdRandom.uniformInt(n);
            j = StdRandom.uniformInt(n);
            if(tiles[i][j]!=0)
                break;
        }

        while(true) {
            x = StdRandom.uniformInt(n);
            y = StdRandom.uniformInt(n);
            if (tiles[x][y] != 0 && (x != i || y != j))
                break;
        }

        int[][] tilesClone = new int[n][n];
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < n; l++) {
                tilesClone[k][l] = tiles[k][l];
            }
        }
        exch(tilesClone,i,j,x,y);
        Board twinBoard = new Board(tilesClone);
        return twinBoard;
    }

    private int goalX(int tile){
        if(tile == 0)
            throw new NumberFormatException();
        return (tile-1) / n;
    }

    private int goalY(int tile){
        if(tile == 0)
            throw new NumberFormatException();
        return (tile-1) % n;
    }

    private int distanceToGoal(int tile, int x, int y){
        return Math.abs(x-goalX(tile)) + Math.abs(y-goalY(tile));
    }

    private int isTileGoal(int tile, int x, int y){
        if(x == goalX(tile) && y == goalY(tile))
            return 1;
        return 0;
    }

    private void exch(int[][] a, int ai, int aj, int bi, int bj) {
        int swap = a[ai][aj];
        a[ai][aj] = a[bi][bj];
        a[bi][bj] = swap;
    }

    // unit testing (not graded)
    public static void main(String[] args){

    }
}