package edu.hw5;

import edu.hw5.task3.DateParser;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        //Задание 7
        //Содержит не менее 3 символов, причем третий символ равен 0:
        String regex1 = "^[01]{2}0[01]*$";
        //Начинается и заканчивается одним и тем же символом:
        String regex2 = "^(0|1)[01]*\\1$";
        //Длина не менее 1 и не более 3:
        String regex3 = "^[01]{1,3}$";

        //Задание 8
        //Нечетной длины:
        String regex4 = "^([01]{2})*[01]$";
        //Начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину:
        String regex5 = "^(0([01]{2})*|1[01]([01]{2})*)$";
        //Количество 0 кратно 3:
        String regex6 = "^((1*01*){3})+$";
        //Любая строка, кроме 11 или 111:
        String regex7 = "^(?!1{2,3}$)[01]*$";
        //Каждый нечетный символ равен 1:
        String regex8 = "^((1[01])+1*|1)$";
        //Содержит не менее двух 0 и не более одной 1:
        String regex9 = "^(0+[01]0+|[01]00+|00+[01]|00+)$";
        //Нет последовательных 1:
        String regex10 = "^(?![01]*11)[01]+$";
    }

    //Задание 1
    public static Duration avgDurationParser(List<String> inputs) {
        if (inputs.size() == 0) {

            throw new IllegalArgumentException("The input list is empty");
        }

        Duration totalDuration = Duration.ZERO;

        for (String input : inputs) {
            String[] parts = input.split(" - ");
            if (parts.length != 2) {

                throw new IllegalArgumentException("Invalid input format");
            }

            String start = parts[0];
            String end = parts[1];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
            Duration sessionDuration = Duration.between(startDateTime, endDateTime);

            if (sessionDuration.isNegative()) {

                throw new IllegalArgumentException("The start or end time of the session is incorrect");
            }

            totalDuration = totalDuration.plus(sessionDuration);
        }

        return totalDuration.dividedBy(inputs.size());
    }

    //Задание 2
    public static List<LocalDate> findFridaysThe13(int year) {
        List<LocalDate> res = new LinkedList<>();

        for (int month = 1; month <= 12; month++) {
            LocalDate date = LocalDate.of(year, month, 13);

            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                res.add(date);
            }
        }

        return res;
    }

    public static LocalDate findNextFridayThe13(LocalDate date) {
        LocalDate res = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        while (res.getDayOfMonth() != 13) {
            res = res.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        }

        return res;
    }

    //Задание 4
    public static boolean isSpecialSymbolPresent(String password) {
        String regex = ".*[~!@#$%^&*|].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    //Задание 5
    public static boolean isValidCarNumber(String number) {
        //Если использовать все буквы
        String regex = "^[А-Я]\\d{3}[А-Я]{2}\\d{3}$";
        //Более реальный вариант
        //String regex = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    //Задание 6
    public static boolean isSubsequence(String S, String T) {
        String regex = ".*" + Pattern.quote(S) + ".*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(T);

        return matcher.matches();
    }
}
