package edu.project2;

import java.util.Arrays;

public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width, Cell[][] grid) {
        if (height < 3 || width < 3 || grid.length < 3 || grid[0].length < 3 || height != grid.length ||
            width != grid[0].length) {
            throw new IllegalArgumentException("Illegal size parameters");
        }
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    public Maze(Maze maze) {
        this.height = maze.height;
        this.width = maze.width;
        this.grid = maze.grid;
    }

    public int getHeight() {

        return height;
    }

    public int getWidth() {

        return width;
    }

    public Cell[][] getGrid() {

        return Arrays.stream(grid).map(Cell[]::clone).toArray(Cell[][]::new);
    }

    public boolean isValidCell(Cell cell) {

        return cell.row() >= 0 && cell.row() < this.height && cell.col() >= 0 &&
            cell.col() < this.width;
    }
}
