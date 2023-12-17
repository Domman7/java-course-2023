package edu.hw8.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final Thread[] pool;
    private final int poolSize;
    private final BlockingQueue<Runnable> taskQueue;

    private FixedThreadPool(int poolSize) {

        pool = new Thread[poolSize];
        this.poolSize = poolSize;
        taskQueue = new LinkedBlockingQueue<>();
    }

    public static FixedThreadPool create(int poolSize) {
        if (poolSize > 0) {

            return new FixedThreadPool(poolSize);
        } else {

            throw new IllegalArgumentException("Invalid thread count");
        }
    }

    @Override
    public void start() {
        for (int i = 0; i < poolSize; i++) {
            pool[i] = new Thread(() -> {
                while (true) {
                    try {
                        Runnable task = taskQueue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });

            pool[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            taskQueue.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() throws InterruptedException {
        for (Thread thread : pool) {

            thread.interrupt();
        }
    }
}
