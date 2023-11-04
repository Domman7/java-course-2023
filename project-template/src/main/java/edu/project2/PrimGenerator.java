package edu.project2;

import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class PrimGenerator implements Generator {
    private static final int[][] DIRECTIONS = new int[][] {
        {1, 0},
        {-1, 0},
        {0, 1},
        {0, -1}
    };

    @Override
    public Maze generate(int height, int width) {
        if (width < 3 || height < 3) {

            throw new IllegalArgumentException("Illegal size parameters");
        }

        var grid = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(i, j, Cell.Type.WALL);
            }
        }

        HashSet<Cell> set = new HashSet<>();
        set.add(new Cell(1, 1, Cell.Type.WALL));

        while (set.size() != 0) {
            var current = getRandomCell(set);
            set.remove(current);
            var curRow = current.row();
            var curCol = current.col();
            var curType = current.type();
            if (curType == Cell.Type.WALL) {
                grid[curRow][curCol] = new Cell(curRow, curCol, Cell.Type.PASSAGE);
                connect(grid, curRow, curCol, height, width);

                for (var item : getNeighbors(grid, new Cell(curRow, curCol, curType), height, width)) {
                    if (isValidPassage(item, height, width) && grid[item.row()][item.col()].type() == Cell.Type.WALL) {
                        set.add(item);
                    }
                }
            }
        }

        return new Maze(height, width, grid);
    }

    private static boolean isValidPassage(Cell cell, int height, int width) {

        return cell.row() > 0 && cell.row() < height - 1 && cell.col() > 0 &&
            cell.col() < width - 1;
    }

    private static List<Cell> getNeighbors(Cell[][] grid, Cell cell, int height, int width) {
        var res = new LinkedList<Cell>();
        var row = cell.row();
        var col = cell.col();
        var list = new ArrayList<>(Arrays.asList(DIRECTIONS));
        Collections.shuffle(list);

        for (int[] direction : list) {
            var newRow = row + 2 * direction[0];
            var newCol = col + 2 * direction[1];
            var temp = new Cell(newRow, newCol, Cell.Type.WALL);
            if (isValidPassage(temp, height, width) && grid[newRow][newCol].type() != Cell.Type.PASSAGE) {
                res.add(temp);
            }
        }

        return res;
    }

    private static void connect(Cell[][] grid, int row, int col, int height, int width) {
        for (int[] direction : DIRECTIONS) {
            var newRow = row + 2 * direction[0];
            var newCol = col + 2 * direction[1];

            if (isValidPassage(new Cell(newRow, newCol, Cell.Type.WALL), height, width) &&
                grid[newRow][newCol].type() == Cell.Type.PASSAGE) {
                grid[newRow - direction[0]][newCol - direction[1]] =
                    new Cell(newRow - direction[0], newCol - direction[1], Cell.Type.PASSAGE);
                break;
            }
        }
    }

    private static Cell getRandomCell(HashSet<Cell> cells) {
        Cell[] array = cells.toArray(new Cell[0]);
        Random random = new Random();
        int ind = random.nextInt(cells.size());

        return array[ind];
    }
}
