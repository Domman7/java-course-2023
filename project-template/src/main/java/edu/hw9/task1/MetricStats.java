package edu.hw9.task1;

public record MetricStats(
    String metricName,
    double sum,
    long count,
    double average,
    double maximum,
    double minimum
) {
}
