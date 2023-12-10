package edu.hw9.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FileSystemParallel {
    public static List<String> getLargeDirectories(String rootPath) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        return forkJoinPool.invoke(new DirectorySearchTask(rootPath));
    }

    public static List<String> getDirectoriesByPredicate(String rootPath) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new FileSearchTask(rootPath, new FilePredicate() {
            @Override
            public boolean test(File file) {
                return file.length() > 100000 && file.getName().endsWith(".txt");
            }
        }));
    }
}
