package edu.hw10.task1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Random;

public class RandomObjectGenerator {
    private final Random random;

    public RandomObjectGenerator() {
        random = new Random();
    }

    public <T> T nextObject(Class<T> clazz)
        throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            args[i] = generateValue(parameters[i]);
        }

        return (T) constructor.newInstance(args);
    }

    public <T> T nextObject(Class<T> clazz, String factoryMethod)
        throws IllegalAccessException, InvocationTargetException {
        Method method = null;
        for (var item : clazz.getDeclaredMethods()) {
            if (item.getName().equals(factoryMethod)) {
                method = item;
            }
        }

        if (method == null) {
            throw new IllegalArgumentException("No factory method found: " + factoryMethod);
        }

        Object[] args = new Object[method.getParameterCount()];
        for (int i = 0; i < method.getParameterCount(); i++) {
            args[i] = generateValue(method.getParameters()[i]);
        }

        return (T) method.invoke(null, args);
    }

    private Object generateValue(Parameter parameter) {
        var type = parameter.getType();

        if (type == int.class || type == Integer.class) {

            return generateInt(parameter);
        } else if (type == double.class || type == Double.class) {

            return generateDouble(parameter);
        } else if (type == boolean.class || type == Boolean.class) {

            return generateBoolean(parameter);
        } else if (type == String.class) {

            return generateString(parameter);
        } else {

            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    private int generateInt(Parameter parameter) {
        var res = random.nextInt();

        if (parameter.isAnnotationPresent(Min.class)) {

            res = Math.max(parameter.getAnnotation(Min.class).value(), res);
        }
        if (parameter.isAnnotationPresent(Max.class)) {

            res = Math.min(parameter.getAnnotation(Max.class).value(), res);
        }

        return res;
    }

    private double generateDouble(Parameter parameter) {
        var isMinAnnotation = parameter.isAnnotationPresent(Min.class);
        var isMaxAnnotation = parameter.isAnnotationPresent(Max.class);
        var minValue = parameter.getAnnotation(Min.class).value();
        var maxValue = parameter.getAnnotation(Max.class).value();
        double res;

        if (isMinAnnotation && isMaxAnnotation) {
            res = random.nextDouble(minValue, maxValue);
        } else {
            res = random.nextDouble();

            if (isMinAnnotation) {

                res = Math.max(minValue, res);
            }
            if (isMaxAnnotation) {

                res = Math.min(maxValue, res);
            }
        }

        return res;
    }

    private boolean generateBoolean(Parameter parameter) {

        return random.nextBoolean();
    }

    private String generateString(Parameter parameter) {
        int length = random.nextInt(10);

        if (length == 0 && !parameter.isAnnotationPresent(NotNull.class)) {

            return null;
        } else if (length == 0) {

            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }

        return sb.toString();
    }
}
