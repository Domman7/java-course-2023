package edu.project2;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import static java.lang.Math.abs;

public class AStarSolver implements Solver {
    private static final int[][] DIRECTIONS = new int[][] {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        var grid = maze.getGrid();
        var startRow = start.row();
        var startCol = start.col();
        var endRow = end.row();
        var endCol = end.col();

        if (grid[startRow][startCol].type() == Cell.Type.WALL || grid[endRow][endCol].type() == Cell.Type.WALL) {
            throw new IllegalArgumentException("The start or end point is mounted on the wall");
        }

        return getAStarPath(bfs(maze, start, end), start, end);
    }

    private static Coordinate[][] bfs(Maze maze, Coordinate start, Coordinate end) {
        Queue<EuristicPair> queue = new PriorityQueue<>();
        queue.add(new EuristicPair(0, start));
        var grid = maze.getGrid();
        Coordinate[][] pred = new Coordinate[maze.getHeight()][maze.getWidth()];
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];

        while (queue.size() != 0) {
            var current = queue.poll().value();
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
                    var newCoordinate = new Coordinate(newRow, newCol);
                    queue.add(new EuristicPair(manhattanMetric(newCoordinate, end), newCoordinate));
                    pred[newRow][newCol] = current;
                }
            }
        }
        if (visited[end.row()][end.col()]) {

            return pred;
        } else {

            throw new RuntimeException("Path is not existed");
        }
    }

    private static List<Coordinate> getAStarPath(Coordinate[][] pred, Coordinate start, Coordinate end) {
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

    private static double manhattanMetric(Coordinate current, Coordinate target) {

        return abs(current.row() - target.row()) + abs(current.col() - target.col());
    }
}

record EuristicPair(double key, Coordinate value) implements Comparable<EuristicPair> {
    @Override
    public int compareTo(EuristicPair other) {

        return Double.compare(this.key, other.key);
    }
}
