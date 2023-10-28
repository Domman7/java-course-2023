package edu.hw2.task3;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;
    private Throwable cause = null;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() throws Throwable {
        tryExecute("apt update && apt upgrade -y");
    }

    public void tryExecute(String command) throws Throwable {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);

                return;
            } catch (Exception e) {
                if (cause == null) {
                    cause = e;
                } else {
                    cause.addSuppressed(e);
                }

                if (attempt == maxAttempts) {
                    throw new ConnectionException("Failed to execute command after " + maxAttempts + " attempts");
                }

                if (cause != null) {
                    throw cause;
                }
            }
        }
    }
}
