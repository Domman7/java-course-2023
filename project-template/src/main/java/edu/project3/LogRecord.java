package edu.project3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record LogRecord(String remoteAddr, String remoteUser, OffsetDateTime timeLocal, String request, int status,
                        int bytesSent, String referer, String userAgent) {
    static LogRecord parse(String input) {
        String logPattern = "^(.*)\\s-\\s(.*)\\s\\[(\\d{2}\\/\\S{3}\\/\\d{4}:\\d{2}:\\d{2}:\\d{2}\\s\\+\\d{4})]" +
            "\\s\"(.*)\"\\s(\\d{3})\\s(\\d+)\\s\"(.*)\"\\s\"(.*)\"$";
        Pattern pattern = Pattern.compile(logPattern);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String remoteAddress = matcher.group(1);
            String remoteUser = matcher.group(2);
            String time = matcher.group(3);
            String request = matcher.group(4);
            int status = Integer.parseInt(matcher.group(5));
            int bytesSent = Integer.parseInt(matcher.group(6));
            String referer = matcher.group(7);
            String userAgent = matcher.group(8);

            DateTimeFormatter dateFormat = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
                .toFormatter(Locale.ENGLISH);

            OffsetDateTime timeLocal = OffsetDateTime.parse(time, dateFormat);

            return new LogRecord(remoteAddress, remoteUser, timeLocal, request, status, bytesSent, referer, userAgent);
        } else {

            throw new IllegalArgumentException("Wrong log pattern");
        }
    }
}
