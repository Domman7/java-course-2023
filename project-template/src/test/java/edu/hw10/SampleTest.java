package edu.hw10;

import edu.hw10.task1.MyClass;
import edu.hw10.task1.MyRecord;
import edu.hw10.task1.RandomObjectGenerator;
import edu.hw10.task2.CacheProxy;
import edu.hw10.task2.FibCalculator;
import edu.hw10.task2.FibonacciCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import static edu.hw7.Main.getCount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @DisplayName("Задание 1")
    @Test
    public void randomObjectGeneratorTest()
        throws InvocationTargetException, IllegalAccessException, InstantiationException {

        RandomObjectGenerator rog = new RandomObjectGenerator();

        var myClass = rog.nextObject(MyClass.class, "create");

        assertTrue(myClass.getValue() >= 0 && myClass.getValue() <= 100);
    }

    @DisplayName("Задание 2")
    @Test
    public void fibonacciCalculatorTest() {
        FibCalculator c = new FibonacciCalculator();
        FibCalculator proxy = CacheProxy.create(c, FibCalculator.class);

        proxy.fib(5);
        proxy.fib(10);
        String tempDirPath = System.getProperty("java.io.tmpdir");

        File cacheFile = new File(tempDirPath + "fib10.cache");

        assertTrue(cacheFile.exists());
    }
}
