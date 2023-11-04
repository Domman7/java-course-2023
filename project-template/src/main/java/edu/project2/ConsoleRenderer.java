package edu.project2;

import java.util.List;

public class ConsoleRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        StringBuilder line = new StringBuilder();
        var grid = maze.getGrid();
        var height = maze.getHeight();
        var width = maze.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].type() == Cell.Type.WALL) {
                    line.append("# ");
                } else {
                    line.append("  ");
                }
            }
            line.append("\n");
        }

        return line.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder line = new StringBuilder();
        var grid = maze.getGrid();
        var height = maze.getHeight();
        var width = maze.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean pathCell = false;
                for (var item : path) {
                    if (!maze.isValidCell(new Cell(item.row(), item.col(), Cell.Type.PASSAGE))) {

                        throw new RuntimeException("Incorrect path");
                    }

                    if (item.row() == i && item.col() == j) {
                        if (grid[i][j].type() == Cell.Type.WALL) {

                            throw new RuntimeException("Incorrect path");
                        }

                        line.append(". ");
                        pathCell = true;
                        break;
                    }
                }
                if (!pathCell) {
                    if (grid[i][j].type() == Cell.Type.WALL) {
                        line.append("# ");
                    } else {
                        line.append("  ");
                    }
                }
            }
            line.append("\n");
        }

        return line.toString();
    }
}
