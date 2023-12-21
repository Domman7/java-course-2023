package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import net.bytebuddy.implementation.FixedValue;

public class SampleTest {
    //Задание 1
    @DisplayName("Задание 1")
    @Test
    public void helloByteBuddyTest()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.named("toString"))
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        assertEquals("Hello, ByteBuddy!", dynamicType.getDeclaredConstructor().newInstance().toString());
    }

    //Задание 2
    @DisplayName("Задание 2")
    @Test
    public void arithmeticUtilsTest()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum"))
            .intercept(MethodDelegation.to(new SumInterceptor()))
            //.intercept(FixedValue.value(0))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        assertEquals(6, ((ArithmeticUtils) dynamicType.getDeclaredConstructor().newInstance()).sum(2, 3));
        //assertEquals(0, ((ArithmeticUtils) dynamicType.getDeclaredConstructor().newInstance()).sum(2, 3));
    }

    public static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }

    public static class SumInterceptor {
        public int sum(int a, int b) {

            return a * b;
        }
    }
}
