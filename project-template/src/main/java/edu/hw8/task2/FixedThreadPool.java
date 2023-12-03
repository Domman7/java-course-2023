package edu.hw8.task2;

public class FixedThreadPool implements ThreadPool {
    private final Thread[] pool;

    public FixedThreadPool(int threadCount) {

        pool = new Thread[threadCount];
    }

    public static FixedThreadPool create(int threadCount) {

        if (threadCount > 0) {

            return new FixedThreadPool(threadCount);
        } else {

            throw new IllegalArgumentException("Invalid thread count");
        }
    }

    @Override
    public void start() {
        for (Thread thread : pool) {
            if (thread != null) {

                thread.start();
            }
        }
    }

    @Override
    public void execute(Runnable runnable) {
        for (int i = 0; i < pool.length; i++) {

            pool[i] = new Thread(runnable);
        }
    }

    @Override
    public void close() throws InterruptedException {
        for (Thread thread : pool) {

            thread.join();
        }
    }
}
