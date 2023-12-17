package edu.hw9.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FileSearchTask extends RecursiveTask<List<String>> {
    private final String path;
    private final FilePredicate predicate;

    public FileSearchTask(String path, FilePredicate predicate) {
        this.path = path;
        this.predicate = predicate;
    }

    @Override
    protected List<String> compute() {
        List<String> filesWithPredicate = new ArrayList<>();

        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files != null) {
            List<FileSearchTask> tasks = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    FileSearchTask task = new FileSearchTask(file.getAbsolutePath(), predicate);
                    task.fork();
                    tasks.add(task);
                } else {
                    if (predicate.test(file)) {
                        filesWithPredicate.add(file.getAbsolutePath());
                    }
                }
            }

            for (FileSearchTask task : tasks) {
                filesWithPredicate.addAll(task.join());
            }
        }

        return filesWithPredicate;
    }
}
