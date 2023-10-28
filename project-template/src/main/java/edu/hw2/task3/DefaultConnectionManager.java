package edu.hw2.task3;

public class DefaultConnectionManager implements ConnectionManager {
    private final double failureProbability;

    public DefaultConnectionManager(double failureRate) {
        this.failureProbability = failureRate;
    }

    @Override
    public Connection getConnection() {
        return new FaultyConnection(failureProbability);
    }
}
