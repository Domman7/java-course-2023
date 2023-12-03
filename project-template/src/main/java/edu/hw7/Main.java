package edu.hw7;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Последовательная версия:");
        long startTime = System.nanoTime();
        var first = monteCarloPiSingleThread(1000000000);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Время: " + duration);
        System.out.println("Результат: " + first);

        System.out.println("Параллельная версия:");
        startTime = System.nanoTime();
        var second = monteCarloPiMultiThread(1000000000, 8);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Время: " + duration);
        System.out.println("Результат: " + second);
    }

    //Задание 1
    public static int getCount(int n, int numThreads) throws InterruptedException {
        if (n < 0 || numThreads < 1) {

            throw new IllegalArgumentException("Invalid input");
        }

        AtomicInteger counter = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            new Thread(() -> {
                for (int j = 0; j < n; j++) {
                    counter.getAndIncrement();
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        return counter.get();
    }

    //Задание 2
    public static long factorial(int n) {
        if (n < 0) {

            throw new IllegalArgumentException("Invalid input number");
        }

        return LongStream.rangeClosed(1, n)
            .parallel()
            .reduce(1, (long a, long b) -> a * b);
    }

    //Задание 4
    private static double monteCarloPiSingleThread(int numSimulations) {
        if (numSimulations < 0) {

            throw new IllegalArgumentException("Invalid input");
        }

        int totalCount = 0;
        int circleCount = 0;

        Random random = new Random();

        for (int i = 0; i < numSimulations; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1) {
                circleCount++;
            }

            totalCount++;
        }

        return 4 * (circleCount * 1.0 / totalCount);
    }

    private static double monteCarloPiMultiThread(int numSimulations, int numThreads) {
        if (numSimulations < 0 || numThreads < 1) {

            throw new IllegalArgumentException("Invalid input");
        }

        AtomicInteger totalCount = new AtomicInteger();
        AtomicInteger circleCount = new AtomicInteger();

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        var numSimulationsPerThread = numSimulations / numThreads;

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                int localCounter = 0;
                int localCircleCounter = 0;

                for (int j = 0; j < numSimulationsPerThread; j++) {
                    double x = ThreadLocalRandom.current().nextDouble();
                    double y = ThreadLocalRandom.current().nextDouble();

                    if (x * x + y * y <= 1) {
                        localCircleCounter++;
                    }
                    localCounter++;
                }

                circleCount.addAndGet(localCircleCounter);
                totalCount.addAndGet(localCounter);
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
        }

        return 4 * (circleCount.get() * 1. / totalCount.get());
    }
}
