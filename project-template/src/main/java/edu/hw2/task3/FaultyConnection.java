package edu.hw2.task3;

import java.util.Random;

public class FaultyConnection implements Connection {
    private final double failureProbability;

    public FaultyConnection(double failureRate) {
        this.failureProbability = failureRate;
    }

    @Override
    public void execute(String command) {
        Random random = new Random();

        if (random.nextDouble() < failureProbability) {
            throw new ConnectionException("Connection failure");
        }
        System.out.println("Executing: " + command);
    }

    @Override
    public void close() {
        System.out.println("Connection closed");
    }
}
