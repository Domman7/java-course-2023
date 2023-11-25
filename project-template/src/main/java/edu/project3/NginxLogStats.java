package edu.project3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NginxLogStats {
    private static int reportCount = 0;

    private static Stream<LogRecord> readLogs(String path) {
        boolean isUrl = path.startsWith("http://") || path.startsWith("https://");
        Stream<Path> filePathStream = null;

        try {
            if (isUrl) {
                URL url = new URL(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                return reader.lines().map(LogRecord::parse);
            } else {
                Path filePath = Paths.get(path);

                if (Files.isDirectory(filePath)) {
                    DirectoryStream<Path> dirStream = Files.newDirectoryStream(filePath);
                    filePathStream = StreamSupport.stream(dirStream.spliterator(), false);
                } else {
                    filePathStream = Stream.of(filePath);
                }
            }
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

        if (filePathStream != null) {

            return filePathStream.flatMap(filePath -> {
                try {
                    BufferedReader reader = Files.newBufferedReader(filePath);

                    return reader.lines().map(LogRecord::parse);
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                    return Stream.empty();
                }
            });
        } else {

            throw new RuntimeException("File stream is empty");
        }
    }

    public String getTotalRequestCount(String path, String format, String from, String to) {
        Stream<LogRecord> logStream = readLogs(path);
        Map<String, String> resultsTable = new HashMap<>();

        AtomicLong totalRequestCount = new AtomicLong();

        logStream.forEach(logRecord -> {
            if (isValidByDate(logRecord, from, to)) {
                totalRequestCount.getAndIncrement();
            }
        });
        logStream.close();

        resultsTable.put("Общее число запросов", totalRequestCount.toString());

        return generateReport
            (
                new LogReport("Метрика", "Значение", resultsTable),
                format,
                "Общее количество запросов"
            );
    }

    public String getMostPopularResources(String path, String format, String from, String to) {
        Stream<LogRecord> logStream = readLogs(path);
        Map<String, String> resultsTable = new LinkedHashMap<>();

        Map<String, Integer> resourceCounts = new HashMap<>();

        logStream.forEach(logRecord -> {
            if (isValidByDate(logRecord, from, to)) {
                var request = logRecord.request();
                var resource = request.substring(request.indexOf('/'), request.lastIndexOf(' '));
                resourceCounts.put(resource, resourceCounts.getOrDefault(resource, 0) + 1);
            }
        });
        logStream.close();

        var mostPopularResources =
            resourceCounts.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3)
                .toList();

        for (var item : mostPopularResources) {
            resultsTable.put(item.getKey(), item.getValue().toString());
        }

        return generateReport
            (
                new LogReport("Ресурс", "Количество", resultsTable),
                format,
                "Запрашиваемые ресурсы"
            );
    }

    public String getMostPopularStatuses(String path, String format, String from, String to) {
        Stream<LogRecord> logStream = readLogs(path);
        Map<String, String> resultsTable = new LinkedHashMap<>();

        Map<Integer, Integer> statusCounts = new HashMap<>();

        logStream.forEach(logRecord -> {
            if (isValidByDate(logRecord, from, to)) {
                var status = logRecord.status();
                statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
            }
        });
        logStream.close();

        var mostPopularStatuses =
            statusCounts.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(5)
                .toList();

        for (var item : mostPopularStatuses) {
            resultsTable.put(item.getKey().toString(), item.getValue().toString());
        }

        return generateReport
            (
                new LogReport("Код", "Количество", resultsTable),
                format,
                "Коды ответа"
            );
    }

    public String getAvgBytesSent(String path, String format, String from, String to) {
        Stream<LogRecord> logStream = readLogs(path);
        Map<String, String> resultsTable = new LinkedHashMap<>();

        AtomicLong totalRequestCount = new AtomicLong();
        AtomicLong totalBytesSent = new AtomicLong();

        logStream.forEach(logRecord -> {
            if (isValidByDate(logRecord, from, to)) {
                totalRequestCount.getAndIncrement();
                totalBytesSent.getAndAdd(logRecord.bytesSent());
            }
        });
        logStream.close();

        int avgBytesSent = (int) (totalBytesSent.doubleValue() / totalRequestCount.doubleValue());
        resultsTable.put("Средний размер ответа", String.valueOf(avgBytesSent));

        return generateReport
            (
                new LogReport("Метрика", "Значение", resultsTable),
                format,
                "Ответы сервера"
            );
    }

    public String getMostPopularRemoteAddresses(String path, String format, String from, String to) {
        Stream<LogRecord> logStream = readLogs(path);
        Map<String, String> resultsTable = new LinkedHashMap<>();

        Map<String, Integer> addressesCounts = new HashMap<>();

        logStream.forEach(logRecord -> {
            if (isValidByDate(logRecord, from, to)) {
                var address = logRecord.remoteAddr();
                addressesCounts.put(address, addressesCounts.getOrDefault(address, 0) + 1);
            }
        });
        logStream.close();

        var mostPopularRemoteAddresses =
            addressesCounts.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(5)
                .toList();

        for (var item : mostPopularRemoteAddresses) {
            resultsTable.put(item.getKey(), item.getValue().toString());
        }

        return generateReport
            (
                new LogReport("Адрес", "Количество", resultsTable),
                format,
                "Удаленные адреса"
            );
    }

    public String getMostPopularRequestTypes(String path, String format, String from, String to) {
        Stream<LogRecord> logStream = readLogs(path);
        Map<String, String> resultsTable = new LinkedHashMap<>();

        Map<String, Integer> requestTypeCounts = new HashMap<>();

        logStream.forEach(logRecord -> {
            if (isValidByDate(logRecord, from, to)) {
                var request = logRecord.request();
                var type = request.substring(0, request.indexOf(' '));
                requestTypeCounts.put(type, requestTypeCounts.getOrDefault(type, 0) + 1);
            }
        });
        logStream.close();

        var mostPopularRequestTypes =
            requestTypeCounts.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3)
                .toList();

        for (var item : mostPopularRequestTypes) {
            resultsTable.put(item.getKey(), item.getValue().toString());
        }

        return generateReport
            (
                new LogReport("Запрос", "Количество", resultsTable),
                format,
                "Типы запросов"
            );
    }

    private static String generateReport(LogReport report, String format, String name) {
        StringBuilder result = new StringBuilder();
        if (format.equals("markdown")) {
            format = "md";
        } else if (format.equals("adoc")) {
            format = "adoc";
        } else {
            format = "txt";
        }

        result.append("#### ").append(name).append("\n\n");

        int keyNamesMaxLength = 0;
        int valueNamesMaxLength = 0;

        if (report.keyName().length() > keyNamesMaxLength) {
            keyNamesMaxLength = report.keyName().length();
        }
        if (report.valueName().length() > valueNamesMaxLength) {
            valueNamesMaxLength = report.valueName().length();
        }

        var resTable = report.resultsTable();
        for (var item : resTable.keySet()) {
            if (item.length() > keyNamesMaxLength) {
                keyNamesMaxLength = item.length();
            }
            if (resTable.get(item).length() > valueNamesMaxLength) {
                valueNamesMaxLength = resTable.get(item).length();
            }
        }

        result.append("|")
            .append(center(report.keyName(), keyNamesMaxLength + 2, ' '))
            .append("|")
            .append(center(report.valueName(), valueNamesMaxLength + 2, ' '))
            .append("|\n")
            .append("|")
            .append("-".repeat(keyNamesMaxLength + 2))
            .append("|")
            .append("-".repeat(valueNamesMaxLength + 2))
            .append("|\n");
        ;

        for (var item : resTable.keySet()) {
            result.append("|")
                .append(center(item, keyNamesMaxLength + 2, ' '))
                .append("|")
                .append(center(resTable.get(item), valueNamesMaxLength + 2, ' '))
                .append("|\n");
        }

        String localDir = System.getProperty("user.dir");
        String filePath =
            localDir + "\\project-template\\src\\test\\java\\edu\\project3\\log_reports\\report" + reportCount + "." +
                format;

        reportCount++;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(result.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return result.toString();
    }

    private static boolean isValidByDate(LogRecord logRecord, String from, String to) {
        if (!from.equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(from, formatter);

            OffsetDateTime fromDate = OffsetDateTime.of(date, LocalTime.now(), ZoneOffset.UTC);

            if (logRecord.timeLocal().isBefore(fromDate)) {

                return false;
            }
        }

        if (!to.equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(to, formatter);

            OffsetDateTime toDate = OffsetDateTime.of(date, LocalTime.now(), ZoneOffset.UTC);

            if (logRecord.timeLocal().isAfter(toDate)) {

                return false;
            }
        }

        return true;
    }

    private static String center(String s, int size, char pad) {
        if (s == null || size <= s.length()) {

            return s;
        }

        StringBuilder result = new StringBuilder(size);
        result.append(String.valueOf(pad).repeat((size - s.length()) / 2));
        result.append(s);
        while (result.length() < size) {
            result.append(pad);
        }

        return result.toString();
    }
}
