package edu.hw9.task3;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Solver;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

public class BfsWorker implements Callable<Coordinate[][]> {
    private static final int[][] DIRECTIONS = new int[][] {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };
    private final Maze maze;
    private final Coordinate start;
    private final Coordinate end;
    private final Coordinate[][] pred;
    private final boolean[][] visited;

    public BfsWorker(Maze maze, Coordinate start, Coordinate end, Coordinate[][] pred, boolean[][] visited) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        this.pred = pred;
        this.visited = visited;
    }

    @Override
    public Coordinate[][] call() {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        var grid = maze.getGrid();

        while (queue.size() != 0) {
            var current = queue.poll();
            var curRow = current.row();
            var curCol = current.col();
            visited[curRow][curCol] = true;

            if (curRow == end.row() && curCol == end.col()) {
                break;
            }

            for (var item : DIRECTIONS) {
                var newRow = curRow + item[0];
                var newCol = curCol + item[1];
                if (maze.isValidCell(new Cell(newRow, newCol, Cell.Type.PASSAGE)) &&
                    grid[newRow][newCol].type() == Cell.Type.PASSAGE && !visited[newRow][newCol]) {
                    queue.add(new Coordinate(newRow, newCol));
                    pred[newRow][newCol] = current;
                }
            }
        }

        return pred;
    }
}

