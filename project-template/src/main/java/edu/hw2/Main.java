package edu.hw2;

import edu.hw2.task3.DefaultConnectionManager;
import edu.hw2.task3.PopularCommandExecutor;

public class Main {
    public static void main(String[] args) throws Throwable {
        PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(0.1), 5);
        executor.updatePackages();
    }

    //4. Кто вызвал функцию?
    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length > 2) {
            StackTraceElement caller = stackTrace[2];
            String className = caller.getClassName();
            String methodName = caller.getMethodName();

            return new CallingInfo(className, methodName);
        } else {

            return new CallingInfo("Unknown", "Unknown");
        }
    }

    public record CallingInfo(String className, String methodName) {
    }
}
