package edu.hw3;

import edu.hw3.task5.Person;
import edu.hw3.task6.Market;
import edu.hw3.task6.Stock;
import edu.hw3.task7.NullTolerateComparator;
import edu.hw3.task8.BackwardIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
    }

    //1. Шифр Атбаш
    static final HashMap<Character, Character> CIPHER_TABLE = createCipherTable(createAlphabet('A', 'Z'));

    private static String createAlphabet(char startChar, char endChar) {
        StringBuilder sb = new StringBuilder();
        for (char ch = startChar; ch <= endChar; ch++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    private static HashMap<Character, Character> createCipherTable(String alphabet) {
        var res = new HashMap<Character, Character>();
        var len = alphabet.length();

        for (int i = 0; i < len; i++) {
            res.put(alphabet.charAt(i), alphabet.charAt(len - i - 1));
        }

        return res;
    }

    public static String atbash(String message) {
        if (message == null) {

            throw new IllegalArgumentException("Invalid message");
        }

        StringBuilder res = new StringBuilder();

        for (char ch : message.toCharArray()) {
            if (CIPHER_TABLE.containsKey(Character.toUpperCase(ch))) {
                if (Character.isUpperCase(ch)) {
                    res.append(CIPHER_TABLE.get(ch));
                } else {
                    res.append(Character.toLowerCase(CIPHER_TABLE.get(Character.toUpperCase(ch))));
                }
            } else {
                res.append(ch);
            }
        }

        return res.toString();
    }

    //2. Кластеризация скобок
    public static String[] clusterize(String input) {
        if (input == null) {

            throw new IllegalArgumentException("Invalid input");
        }

        List<String> res = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        int lvl = 0;

        for (char ch : input.toCharArray()) {
            if (ch == '(') {
                temp.append(ch);
                lvl++;
            } else {
                if (ch == ')') {
                    temp.append(ch);
                    lvl--;
                    if (lvl == 0) {
                        res.add(temp.toString());
                        temp = new StringBuilder();
                    }
                } else { //Если допускаем ввод других символов, помимо скобок
                    temp.append(ch);
                }
            }
        }

        if (lvl != 0) {

            throw new IllegalArgumentException("Clusters are not balanced");
        }

        return res.toArray(new String[0]);
    }

    //3. Частота слов
    public static <T> HashMap<T, Integer> freqDict(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("List is null");
        }

        HashMap<T, Integer> res = new HashMap<>();

        for (T item : list) {
            if (res.containsKey(item)) {
                res.put(item, res.get(item) + 1);
            } else {
                res.put(item, 1);
            }
        }

        return res;
    }

    //4. Римские цифры
    static final TreeMap<Integer, String> ROMAN_VALUES = new TreeMap<>(Collections.reverseOrder()) {{
        put(1000, "M");
        put(900, "CM");
        put(500, "D");
        put(400, "CD");
        put(100, "C");
        put(90, "XC");
        put(50, "L");
        put(40, "XL");
        put(10, "X");
        put(9, "IX");
        put(5, "V");
        put(4, "IV");
        put(1, "I");
    }};

    public static String convertToRoman(int number) {
        if (number <= 0) {

            throw new IllegalArgumentException("It is not possible to convert this number");
        }

        StringBuilder res = new StringBuilder();

        for (var item : ROMAN_VALUES.keySet()) {
            while (number >= item) {
                var amount = number / item;
                number %= item;
                res.append(ROMAN_VALUES.get(item).repeat(Math.max(0, amount)));
            }
        }

        return res.toString();
    }

    //5. Список контактов
    public static Person[] parseContacts(String[] contacts, String mode) {
        if (contacts == null || contacts.length == 0) {

            return new Person[0];
        }

        if (!mode.equals("ASC") && !mode.equals("DESC")) {

            throw new IllegalArgumentException("Illegal sorting order");
        }

        List<Person> sortedContacts = new LinkedList<>();

        for (var item : contacts) {
            var str = item.split(" ");

            if (str.length == 1) {
                sortedContacts.add(new Person(str[0], null));
            } else if (str.length == 2) {
                sortedContacts.add(new Person(str[0], str[1]));
            } else {
                throw new IllegalArgumentException("Illegal argument");
            }
        }

        if (mode.equals("ASC")) {
            sortedContacts = sortedContacts.stream().sorted(Comparator.comparing(Person::getValue)).
                collect(Collectors.toList());
        }
        if (mode.equals("DESC")) {
            sortedContacts = sortedContacts.stream().sorted(Comparator.comparing(Person::getValue).reversed()).
                collect(Collectors.toList());
        }

        return sortedContacts.toArray(new Person[0]);
    }

    //7. Дерево и null
    static final Comparator<String> NULL_TOLERATE_COMPARATOR = Comparator.nullsFirst(
        Comparator.comparingInt(String::length)
    );
}
