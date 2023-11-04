package edu.project2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DfsSolver implements Solver {
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

        return getDfsPath(dfs(maze, start, end), start, end);
    }

    private static Coordinate[][] dfs(Maze maze, Coordinate start, Coordinate end) {
        Coordinate[][] pred = new Coordinate[maze.getHeight()][maze.getWidth()];
        boolean[][] visited = new boolean[maze.getHeight()][maze.getWidth()];
        dfsRec(maze, start, end, pred, visited);

        if (visited[end.row()][end.col()]) {

            return pred;
        } else {

            throw new RuntimeException("Path is not existed");
        }
    }

    private static void dfsRec(
        Maze maze,
        Coordinate current,
        Coordinate end,
        Coordinate[][] pred,
        boolean[][] visited
    ) {
        var curRow = current.row();
        var curCol = current.col();
        var grid = maze.getGrid();
        visited[curRow][curCol] = true;

        if (curRow == end.row() && curCol == end.col()) {

            return;
        }

        for (var item : DIRECTIONS) {
            var newRow = curRow + item[0];
            var newCol = curCol + item[1];
            if (maze.isValidCell(new Cell(newRow, newCol, Cell.Type.PASSAGE)) &&
                grid[newRow][newCol].type() == Cell.Type.PASSAGE && !visited[newRow][newCol]) {
                pred[newRow][newCol] = current;
                dfsRec(maze, new Coordinate(newRow, newCol), end, pred, visited);
            }
        }
    }

    private static List<Coordinate> getDfsPath(Coordinate[][] pred, Coordinate start, Coordinate end) {
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
