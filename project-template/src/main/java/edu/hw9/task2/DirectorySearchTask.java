package edu.hw9.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DirectorySearchTask extends RecursiveTask<List<String>> {
    private final String path;

    public DirectorySearchTask(String path) {
        this.path = path;
    }

    @Override
    protected List<String> compute() {
        List<String> directoriesWithLargeFiles = new ArrayList<>();

        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files != null) {
            List<DirectorySearchTask> tasks = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    DirectorySearchTask task =
                        new DirectorySearchTask(file.getAbsolutePath());
                    task.fork();
                    tasks.add(task);
                } else {
                    directoriesWithLargeFiles.add(directory.getAbsolutePath());
                }
            }

            for (DirectorySearchTask task : tasks) {
                directoriesWithLargeFiles.addAll(task.join());
            }
        }

        return directoriesWithLargeFiles;
    }
}
