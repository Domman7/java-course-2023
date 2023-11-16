package edu.hw5;

import edu.hw4.Animal;
import edu.hw5.task3.DateParser;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import static edu.hw4.Main.sortByHeight;
import static edu.hw5.Main.avgDurationParser;
import static edu.hw5.Main.findFridaysThe13;
import static edu.hw5.Main.findNextFridayThe13;
import static edu.hw5.Main.isSpecialSymbolPresent;
import static edu.hw5.Main.isSubsequence;
import static edu.hw5.Main.isValidCarNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @DisplayName("Задание 1")
    @Test
    public void avgDurationParserTest() {
        List<String> inputs = new LinkedList<>();
        inputs.add("2022-03-12, 20:20 - 2022-03-12, 23:50");
        inputs.add("2022-04-01, 21:30 - 2022-04-02, 01:20");

        Duration actual = avgDurationParser(inputs);
        Duration expected = Duration.ofHours(3).plus(Duration.ofMinutes(40));

        assertEquals(expected, actual);
    }

    @DisplayName("Задание 2.1")
    @Test
    public void findFridaysThe13Test() {
        var actual = findFridaysThe13(2024);
        List<LocalDate> expected = new LinkedList<>();
        expected.add(LocalDate.of(2024, 9, 13));
        expected.add(LocalDate.of(2024, 12, 13));

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @DisplayName("Задание 2.2")
    @Test
    public void findNextFridayThe13Test() {
        var actual = findNextFridayThe13(LocalDate.of(2023, 10, 10));

        assertEquals(LocalDate.of(2023, 10, 13), actual);
    }

    @DisplayName("Задание 3")
    @Test
    public void parseDateTest() {
        DateParser parser = new DateParser();

        assertEquals(LocalDate.of(2020, 10, 10), parser.parseDate("2020-10-10").get());
        assertEquals(LocalDate.of(2020, 12, 2), parser.parseDate("2020-12-2").get());
        assertEquals(LocalDate.of(1976, 3, 1), parser.parseDate("1/3/1976").get());
        assertEquals(LocalDate.of(2020, 3, 1), parser.parseDate("1/3/20").get());
        assertEquals(LocalDate.now(), parser.parseDate("today").get());
        assertEquals(Optional.empty(), parser.parseDate("invalid format"));
    }

    @DisplayName("Задание 4")
    @Test
    public void isSpecialSymbolPresentTest() {

        assertTrue(isSpecialSymbolPresent("password!"));
        assertTrue(isSpecialSymbolPresent("pas@sword"));
        assertTrue(isSpecialSymbolPresent("^password!"));
    }

    @DisplayName("Задание 5")
    @Test
    public void isValidCarNumberTest() {

        assertTrue(isValidCarNumber("А123ВЕ777"));
        assertTrue(isValidCarNumber("О777ОО177"));
        assertFalse(isValidCarNumber("123АВЕ777"));
        assertFalse(isValidCarNumber("А123ВГ77"));
        assertFalse(isValidCarNumber("А123ВЕ7777"));
    }

    @DisplayName("Задание 6")
    @Test
    public void isSubsequenceTest() {

        assertTrue(isSubsequence("abc", "achfdbaabgabcaabg"));
    }
}
