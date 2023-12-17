package edu.hw9;

import edu.hw9.task1.MetricStats;
import edu.hw9.task1.StatsCollector;
import edu.hw9.task2.FileSystemParallel;
import edu.hw9.task3.BfsSolverMultiThread;
import edu.project2.BfsSolver;
import edu.project2.Cell;
import edu.project2.ConsoleRenderer;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.PrimGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static edu.hw7.Main.getCount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class SampleTest {
    @DisplayName("Задание 1")
    @Test
    public void metricStatsTest() {
        StatsCollector collector = new StatsCollector();
        var input = new double[] {0.1, 0.05, 1.4, 5.1, 0.3};

        collector.push("metric_name", input);
        var res = collector.stats();
        var metric = res.get(0);

        assertEquals("metric_name", metric.metricName());
        assertEquals(Arrays.stream(input).sum(), metric.sum());
        assertEquals(input.length, metric.count());
        assertEquals(Arrays.stream(input).average().getAsDouble(), metric.average());
        assertEquals(Arrays.stream(input).max().getAsDouble(), metric.maximum());
        assertEquals(Arrays.stream(input).min().getAsDouble(), metric.minimum());
    }

    @DisplayName("Задание 2")
    @Test
    public void fileSystemParallelTest() {
        var actual = FileSystemParallel.getDirectoriesByPredicate("D:\\");

        assertEquals(395, actual.size());
    }

    @DisplayName("Задание 3")
    @Test
    void mazeSolverTest() {
        var renderer = new ConsoleRenderer();
        var grid = new Cell[5][3];
        var generator = new PrimGenerator();
        var solver = new BfsSolverMultiThread();

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
}
