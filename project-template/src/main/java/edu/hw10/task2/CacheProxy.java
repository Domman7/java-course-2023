package edu.hw10.task2;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    private final Object target;
    private final Map<String, Object> cache;

    private CacheProxy(Object target) {
        this.target = target;
        this.cache = new HashMap<>();
    }

    public static <T> T create(T target, Class<?> targetInterface) {

        CacheProxy cacheProxy = new CacheProxy(target);

        return (T) Proxy.newProxyInstance(targetInterface.getClassLoader(),
            new Class<?>[] {targetInterface}, cacheProxy
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            String cacheKey = generateCacheKey(method.getName(), args);

            if (cacheAnnotation.persist()) {
                if (!cache.containsKey(cacheKey) && isCacheFileExists(cacheKey)) {
                    cache.put(cacheKey, readFromCacheFile(cacheKey));
                }
                if (!cache.containsKey(cacheKey)) {
                    Object result = method.invoke(target, args);
                    cache.put(cacheKey, result);
                    writeToCacheFile(cacheKey, result);

                    return result;
                }
            } else {
                if (!cache.containsKey(cacheKey)) {
                    cache.put(cacheKey, method.invoke(target, args));
                }
            }

            return cache.get(cacheKey);
        } else {

            return method.invoke(target, args);
        }
    }

    private String generateCacheKey(String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodName);

        for (Object arg : args) {
            sb.append(arg.toString());
        }

        return sb.toString();
    }

    private boolean isCacheFileExists(String cacheKey) {
        File cacheFile = new File(getCacheFilePath(cacheKey));

        return cacheFile.exists();
    }

    private Object readFromCacheFile(String cacheKey) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getCacheFilePath(cacheKey)))) {

            return ois.readObject();
        }
    }

    private void writeToCacheFile(String cacheKey, Object value) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getCacheFilePath(cacheKey)))) {

            oos.writeObject(value);
        }
    }

    private String getCacheFilePath(String cacheKey) {
        String tempDirPath = System.getProperty("java.io.tmpdir");

        return tempDirPath + cacheKey + ".cache";
    }
}
