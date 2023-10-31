package edu.hw3;

import edu.hw2.task1.Expr;
import edu.hw3.task5.Person;
import edu.hw3.task6.Market;
import edu.hw3.task6.Stock;
import edu.hw3.task7.NullTolerateComparator;
import edu.hw3.task8.BackwardIterator;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import static edu.hw3.Main.atbash;
import static edu.hw3.Main.clusterize;
import static edu.hw3.Main.convertToRoman;
import static edu.hw3.Main.freqDict;
import static edu.hw3.Main.parseContacts;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @DisplayName("1. Шифр Атбаш")
    @Test
    public void atbashTest() {
        assertEquals("Svool dliow!", atbash("Hello world!"));
        assertEquals(
            "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. " +
                "Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi",
            atbash(
                "Any fool can write code that a computer can understand. " +
                    "Good programmers write code that humans can understand. ― Martin Fowler")
        );
    }

    @DisplayName("2. Кластеризация скобок")
    @Test
    public void clasterizeTest() {
        assertEquals(ArrayToString(new String[] {"()", "()", "()"}), ArrayToString(clusterize("()()()")));
        assertEquals(ArrayToString(new String[] {"((()))"}), ArrayToString(clusterize("((()))")));
        assertEquals(
            ArrayToString(new String[] {"((()))", "(())", "()", "()", "(()())"}),
            ArrayToString(clusterize("((()))(())()()(()())"))
        );
        assertEquals(
            ArrayToString(new String[] {"((())())", "(()(()()))"}),
            ArrayToString(clusterize("((())())(()(()()))"))
        );
    }

    public static String ArrayToString(String[] array) {

        return String.join(", ", array);
    }

    @DisplayName("3. Частота слов")
    @Test
    public void freqDictTest() {
        var res = freqDict(List.of(new String[] {"код", "код", "код", "bug"}));

        assertEquals(res.get("код"), 3);
        assertEquals(res.get("bug"), 1);
    }

    @DisplayName("4. Римские цифры")
    @Test
    public void convertToRomanTest() {
        assertEquals("II", convertToRoman(2));
        assertEquals("XII", convertToRoman(12));
        assertEquals("XVI", convertToRoman(16));
        assertEquals("MMMMMMMMMMMMMMMMMMMMMMMCXXXII", convertToRoman(23132));
    }

    @DisplayName("5. Список контактов")
    @Test
    public void parseContactsTest() {
        var expected = new Person[] {
            new Person("Thomas", "Aquinas"),
            new Person("Rene", "Descartes"),
            new Person("David", "Hume"),
            new Person("John", "Locke")
        };
        var input = new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        assertEquals(ArrayToString(expected), ArrayToString(parseContacts(input, "ASC")));

        expected = new Person[] {
            new Person("Carl", "Gauss"),
            new Person("Leonhard", "Euler"),
            new Person("Paul", "Erdos")
        };
        input = new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"};
        assertEquals(ArrayToString(expected), ArrayToString(parseContacts(input, "DESC")));

        expected = new Person[] {
            new Person("A", null),
            new Person("B", null),
            new Person("A", "D")
        };
        input = new String[] {"B", "A D", "A"};
        assertEquals(ArrayToString(expected), ArrayToString(parseContacts(input, "ASC")));
    }

    public static String ArrayToString(Person[] array) {
        StringBuilder res = new StringBuilder();
        for (var person : array) {
            res.append(person.toString()).append(", ");
        }

        return res.toString();
    }

    @DisplayName("6. Биржа")
    @Test
    public void stockMarketTest() {
        var market = new Market();
        market.add(new Stock("A", 123));
        market.add(new Stock("B", 1234));
        market.add(new Stock("C", 1235));
        market.add(new Stock("D", 321));

        assertEquals(market.mostValuableStock().getName(), "C");
    }

    @DisplayName("7. Дерево и null")
    @Test
    public void nullTolerateTreeTest() {
        var tree = new TreeMap<String, String>(new NullTolerateComparator());
        tree.put(null, "test");

        assertTrue(tree.containsKey(null));
    }

    @DisplayName("8. Обратный итератор")
    @Test
    public void backWardIteratorTest() {
        var it = new BackwardIterator<>(List.of(1, 2, 3));

        assertEquals(it.next(), 3);
        assertEquals(it.next(), 2);
        assertEquals(it.next(), 1);
    }
}
