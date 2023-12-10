package edu.hw9.task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.locks.ReentrantLock;

public class StatsCollector {
    private final Map<String, DoubleAdder> sums;
    private final Map<String, AtomicLong> counts;
    private final Map<String, Double> max;
    private final Map<String, Double> min;
    private final ReentrantLock lock;

    public StatsCollector() {
        sums = new HashMap<>();
        counts = new HashMap<>();
        max = new HashMap<>();
        min = new HashMap<>();
        lock = new ReentrantLock();
    }

    public void push(String metricName, double[] data) {
        ExecutorService executorService = Executors.newFixedThreadPool(data.length);

        for (double d : data) {
            executorService.submit(() -> {
                lock.lock();
                try {
                    sums.computeIfAbsent(metricName, k -> new DoubleAdder()).add(d);
                    counts.computeIfAbsent(metricName, k -> new AtomicLong()).incrementAndGet();
                    max.compute(metricName, (k, v) -> v == null ? d : Math.max(v, d));
                    min.compute(metricName, (k, v) -> v == null ? d : Math.min(v, d));
                } finally {
                    lock.unlock();
                }
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
        }
    }

    public List<MetricStats> stats() {
        List<MetricStats> result = new ArrayList<>();

        lock.lock();
        try {
            for (Map.Entry<String, DoubleAdder> entry : sums.entrySet()) {
                String metricName = entry.getKey();
                double sum = entry.getValue().sum();
                long count = counts.get(metricName).get();
                double average = sum / count;
                double maximum = max.get(metricName);
                double minimum = min.get(metricName);

                result.add(new MetricStats(metricName, sum, count, average, maximum, minimum));
            }
        } finally {
            lock.unlock();
        }

        return result;
    }
}
