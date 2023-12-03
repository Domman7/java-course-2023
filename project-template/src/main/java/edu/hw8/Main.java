package edu.hw8;

import com.mifmif.common.regex.Generex;
import com.mifmif.common.regex.util.Iterator;
import edu.hw8.task3.PasswordCracker;
import edu.hw8.task3.PasswordCrackerMultiThread;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PasswordCracker cracker = new PasswordCracker("[a-z0-9]{6}");
        PasswordCrackerMultiThread crackerMF = new PasswordCrackerMultiThread("[a-z0-9]{6}");
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
    }
}
