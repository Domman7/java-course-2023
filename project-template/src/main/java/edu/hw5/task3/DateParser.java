package edu.hw5.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateParser {
    public Optional<LocalDate> parseDate(String string) {

        return parseDateFormat1(string);
    }

    private Optional<LocalDate> parseDateFormat1(String string) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(string, formatter);

            return Optional.of(date);
        } catch (Exception e) {

            return parseDateFormat2(string);
        }
    }

    private Optional<LocalDate> parseDateFormat2(String string) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate date = LocalDate.parse(string, formatter);

            return Optional.of(date);
        } catch (Exception e) {

            return parseDateFormat3(string);
        }
    }

    private Optional<LocalDate> parseDateFormat3(String string) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(string, formatter);

            return Optional.of(date);
        } catch (Exception e) {

            return parseDateFormat4(string);
        }
    }

    private Optional<LocalDate> parseDateFormat4(String string) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");
            LocalDate date = LocalDate.parse(string, formatter);

            return Optional.of(date);
        } catch (Exception e) {

            return parseDateFormat5(string);
        }
    }

    private Optional<LocalDate> parseDateFormat5(String string) {
        if (string.matches("^tomorrow$")) {

            return Optional.of(LocalDate.now().plusDays(1));
        } else {

            return parseDateFormat6(string);
        }
    }

    private Optional<LocalDate> parseDateFormat6(String string) {
        if (string.matches("^yesterday$") || string.matches("^1 day ago$")) {

            return Optional.of(LocalDate.now().minusDays(1));
        } else {

            return parseDateFormat7(string);
        }
    }

    private Optional<LocalDate> parseDateFormat7(String string) {
        if (string.matches("^today$")) {

            return Optional.of(LocalDate.now());
        } else {

            return parseDateFormat8(string);
        }
    }

    private Optional<LocalDate> parseDateFormat8(String string) {
        if (string.matches("^\\d+ days ago$")) {
            int daysAgo = Integer.parseInt(string.split(" ")[0]);

            return Optional.of(LocalDate.now().minusDays(daysAgo));
        } else {

            return Optional.empty();
        }
    }
}
