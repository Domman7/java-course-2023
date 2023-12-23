package edu.hw10;

import edu.hw10.task1.MyClass;
import edu.hw10.task1.MyRecord;
import edu.hw10.task1.RandomObjectGenerator;
import edu.hw10.task2.Cache;
import edu.hw10.task2.CacheProxy;
import edu.hw10.task2.FibCalculator;
import edu.hw10.task2.FibonacciCalculator;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args)
        throws InvocationTargetException, IllegalAccessException, InstantiationException {
        RandomObjectGenerator rog = new RandomObjectGenerator();

        var myClass = rog.nextObject(MyClass.class, "create");
        var myRecord = rog.nextObject(MyRecord.class);

        System.out.println(myClass);
        System.out.println(myRecord);
    }
}
