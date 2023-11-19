package edu.project3;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        var parser = new ConsoleArgsParser();
        //var logStatsArgs = parser.parse(args);
        var logStatsArgs = parser.parse(
            ("java -jar nginx-log-stats.jar " +
                "--path C:\\Users\\Egor\\IdeaProjects\\java-course-2023\\project-template\\src\\main\\java\\edu\\project3\\logs " +
                "--to 2023-08-31 " +
                "--format markdown").split(
                " "));
        var logStats = new NginxLogStats();

        logStats.getTotalRequestCount(
            logStatsArgs.get("path"),
            logStatsArgs.get("format"),
            logStatsArgs.get("from"),
            logStatsArgs.get("to")
        );

        logStats.getMostPopularResources(
            logStatsArgs.get("path"),
            logStatsArgs.get("format"),
            logStatsArgs.get("from"),
            logStatsArgs.get("to")
        );

        logStats.getMostPopularStatuses(
            logStatsArgs.get("path"),
            logStatsArgs.get("format"),
            logStatsArgs.get("from"),
            logStatsArgs.get("to")
        );

        logStats.getAvgBytesSent(
            logStatsArgs.get("path"),
            logStatsArgs.get("format"),
            logStatsArgs.get("from"),
            logStatsArgs.get("to")
        );

        logStats.getMostPopularRemoteAddresses(
            logStatsArgs.get("path"),
            logStatsArgs.get("format"),
            logStatsArgs.get("from"),
            logStatsArgs.get("to")
        );

        logStats.getMostPopularRequestTypes(
            logStatsArgs.get("path"),
            logStatsArgs.get("format"),
            logStatsArgs.get("from"),
            logStatsArgs.get("to")
        );
    }
}
