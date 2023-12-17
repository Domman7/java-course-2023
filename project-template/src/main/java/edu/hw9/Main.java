package edu.hw9;

import edu.hw9.task1.MetricStats;
import edu.hw9.task1.StatsCollector;
import edu.hw9.task2.FileSystemParallel;

public class Main {
    public static void main(String[] args) {
        System.out.println("Directories with more than 1000 files:");
        for (String dir : FileSystemParallel.getLargeDirectories("D:\\")) {
            System.out.println(dir);
        }

        System.out.println("Files with predicate:");
        for (String file : FileSystemParallel.getDirectoriesByPredicate("D:\\")) {
            System.out.println(file);
        }
    }
}
