package edu.project2;

import edu.project1.Hangman;
import edu.project1.HangmanWordsBank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SampleTest {
    @DisplayName("Тест отрисовки")
    @Test
    void mazeConsoleRendererTest() {
        var renderer = new ConsoleRenderer();
        var grid = new Cell[5][3];
        var generator = new PrimGenerator();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 || i == 4 || j == 0 || j == 2) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }

        assertEquals(
            renderer.render(new Maze(5, 3, grid)),
            renderer.render(generator.generate(5, 3))
        );
    }

    @DisplayName("Тест поиска пути")
    @Test
    void mazeSolverTest() {
        var renderer = new ConsoleRenderer();
        var grid = new Cell[5][3];
        var generator = new PrimGenerator();
        var solver = new BfsSolver();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 || i == 4 || j == 0 || j == 2) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }

        var maze = new Maze(5, 3, grid);
        var start = new Coordinate(1, 1);
        var end = new Coordinate(3, 1);
        List<Coordinate> path = new LinkedList<>();
        path.add(new Coordinate(1, 1));
        path.add(new Coordinate(2, 1));
        path.add(new Coordinate(3, 1));
        var res = solver.solve(maze, start, end);

        for (int i = 0; i < path.size(); i++) {
            assertEquals(path.get(i), res.get(res.size() - i - 1));
        }
    }

    @DisplayName("Тест исключений")
    @Test
    void mazeExceptionsTest() {
        Throwable exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Maze(2, 2, new Cell[2][2])
        );
    }
}
