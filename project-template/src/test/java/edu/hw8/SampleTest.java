package edu.hw8;

import edu.hw8.task2.FixedThreadPool;
import edu.hw8.task2.ThreadPool;
import edu.hw8.task3.PasswordCracker;
import edu.hw8.task3.PasswordCrackerMultiThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw8.Main.createAlphabet;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {
    @DisplayName("Задание 2")
    @Test
    public void fixedThreadPoolTest() {
        int n = 10;
        var actual = new int[n];

        try (ThreadPool threadPool = FixedThreadPool.create(8)) {
            threadPool.start();
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    actual[finalI] = fib(finalI);
                });
            }

            Thread.sleep(1000);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        for (int i = 0; i < n; i++) {
            assertEquals(fib(i), actual[i]);
        }
    }

    public static int fib(int n) {
        if (n < 0) {

            throw new IllegalArgumentException("Invalid n");
        }

        if (n == 0) {

            return 0;
        } else if (n == 1) {

            return 1;
        } else {

            return fib(n - 1) + fib(n - 2);
        }
    }

    @DisplayName("Задание 3")
    @Test
    public void passwordCrackerTest() {
        var alphabet = createAlphabet('a', 'z') + createAlphabet('0', '9');
        PasswordCrackerMultiThread cracker = new PasswordCrackerMultiThread(alphabet, 6);
        cracker.put("e10adc3949ba59abbe56e057f20f883e", "a.v.petrov");
        var res = cracker.decryptMultiThread(8);

        assertEquals("123456", res.get("a.v.petrov"));
    }
}
