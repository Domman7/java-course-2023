package edu.hw8;

import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.ThreadPool;
import edu.hw8.task3.PasswordCracker;
import edu.hw8.task3.PasswordCrackerMultiThread;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var alphabet = createAlphabet('a', 'z') + createAlphabet('0', '9');
        PasswordCracker cracker = new PasswordCracker(alphabet, 6);
        PasswordCrackerMultiThread crackerMF = new PasswordCrackerMultiThread(alphabet, 6);
        cracker.put("e10adc3949ba59abbe56e057f20f883e", "a.v.petrov");
        crackerMF.put("e10adc3949ba59abbe56e057f20f883e", "a.v.petrov");

        System.out.println("Параллельная версия:");
        long startTime = System.nanoTime();
        var res = crackerMF.decryptMultiThread(8);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Время: " + duration);

        for (var item : res.keySet()) {
            System.out.println(item + " " + res.get(item));
        }

        System.out.println("Последовательная версия:");
        startTime = System.nanoTime();
        res = cracker.decrypt();
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Время: " + duration);

        for (var item : res.keySet()) {
            System.out.println(item + " " + res.get(item));
        }

//        Параллельная версия:
//        Время: 117653297000
//        a.v.petrov 123456
//        Последовательная версия:
//        Время: 300421480500
//        a.v.petrov 123456
    }

    public static String createAlphabet(char startChar, char endChar) {
        StringBuilder sb = new StringBuilder();
        for (char ch = startChar; ch <= endChar; ch++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
