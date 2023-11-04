package edu.project2;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        var renderer = new ConsoleRenderer();
        var generator = new PrimGenerator();
        var maze = new Maze(generator.generate(19, 29));
        var solver = new DfsSolver();
        var start = new Coordinate(1, 1);
        var end = new Coordinate(17, 27);

        System.out.println(renderer.render(maze, solver.solve(maze, start, end)));

//        # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
//        # . . . #   # . . . #   #   #           #   #   #   #   #
//        #   # . #   # . # . #   #   #   # # # # #   #   #   #   #
//        #   # . . . . . # . . . #   #   #   #   #       #       #
//        #   # # # # #   # # # . #   #   #   #   #   # # # # #   #
//        #   #   #   #   #   # . . . . . . . . . . . #   #   #   #
//        #   #   #   #   #   # # # # #   # # # # # . #   #   #   #
//        #       #           #   #   #   #   #   # .             #
//        #   #   # # #   #   #   #   #   #   #   # . #   #   #   #
//        #   #   #   #   #           #           # . #   #   #   #
//        #   #   #   #   #   #   #   # # #   #   # . #   #   # # #
//        #   #       #   #   #   #   #   #   #   # . #   #   #   #
//        # # #   # # # # #   #   #   #   # # # # # . # # #   #   #
//        #   #   #   #   #   #   #   #   #   #   # . #   #       #
//        #   #   #   #   # # # # #   #   #   #   # . #   # # # # #
//        #       #   #   #   #   #               # . . . #   #   #
//        #   #   #   #   #   #   #   #   #   #   #   # . #   #   #
//        #   #               #       #   #   #   #   # . . . . . #
//        # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    }
}
