package edu.hw11;

import edu.hw11.task3.FibonacciByteCodeAppender;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> fibClass = new ByteBuddy()
            .subclass(Object.class)
            .name("Fibonacci")
            .defineMethod("fib", long.class, Visibility.PUBLIC)
            .withParameter(int.class, "n")
            .intercept(MethodDelegation.to(new FibonacciByteCodeAppender()))
            .make()
            .load(Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        Method fibMethod = fibClass.getDeclaredMethod("fib", int.class);

        Object instance = fibClass.getConstructor().newInstance();
        long result = (long) fibMethod.invoke(instance, 1);

        //Неправильно работает, не могу найти причину
        System.out.println(result);
    }
}
