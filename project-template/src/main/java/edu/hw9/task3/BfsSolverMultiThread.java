package edu.hw9.task3;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Solver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BfsSolverMultiThread implements Solver {
    private static final int NUM_THREADS = 8;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        var grid = maze.getGrid();
        var visited = new boolean[maze.getHeight()][maze.getWidth()];
        var pred = new Coordinate[maze.getHeight()][maze.getWidth()];
        var startRow = start.row();
        var startCol = start.col();
        var endRow = end.row();
        var endCol = end.col();

        if (grid[startRow][startCol].type() == Cell.Type.WALL || grid[endRow][endCol].type() == Cell.Type.WALL) {
            throw new IllegalArgumentException("The start or end point is mounted on the wall");
        }

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Coordinate[][]>> futures = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            Callable<Coordinate[][]> worker = new BfsWorker(maze, start, end, pred, visited);
            Future<Coordinate[][]> future = executor.submit(worker);
            futures.add(future);
        }

        for (Future<Coordinate[][]> future : futures) {
            try {
                Coordinate[][] result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        if (visited[end.row()][end.col()]) {

            return getBfsPath(pred, start, end);
        } else {
            throw new RuntimeException("Path is not existed");
        }
    }

    private static List<Coordinate> getBfsPath(Coordinate[][] pred, Coordinate start, Coordinate end) {
        List<Coordinate> path = new LinkedList<>();
        var current = end;
        var curRow = end.row();
        var curCol = end.col();

        while (!(curRow == start.row() && curCol == start.col())) {
            path.add(current);
            current = pred[curRow][curCol];
            curRow = current.row();
            curCol = current.col();
        }

        path.add(start);

        return path;
    }
}
