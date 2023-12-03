package edu.hw8;

import edu.hw8.task3.PasswordCracker;
import edu.hw8.task3.PasswordCrackerMultiThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw7.Main.getCount;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {
    @DisplayName("Задание 3")
    @Test
    public void passwordCrackerTest() {
        PasswordCrackerMultiThread cracker = new PasswordCrackerMultiThread("[a-z0-9]{6}");
        cracker.put("e10adc3949ba59abbe56e057f20f883e", "a.v.petrov");
        var res = cracker.decryptMultiThread(8);

        assertEquals("123456", res.get("a.v.petrov"));
    }
}
