package edu.hw6.task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DiskMap implements Map<String, String> {

    private final String filePath;

    public DiskMap(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int size() {

        return loadData().size();
    }

    @Override
    public boolean isEmpty() {

        return loadData().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {

        return loadData().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        return loadData().containsValue(value);
    }

    @Override
    public String get(Object key) {

        return loadData().get(key);
    }

    @Override
    public String put(String key, String value) {
        Map<String, String> map = loadData();
        String oldValue = map.put(key, value);
        saveData(map);

        return oldValue;
    }

    @Override
    public String remove(Object key) {
        Map<String, String> map = loadData();
        String oldValue = map.remove(key);
        saveData(map);

        return oldValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        Map<String, String> map = loadData();
        map.putAll(m);
        saveData(map);
    }

    @Override
    public void clear() {
        saveData(new HashMap<>());
    }

    @Override
    public Set<String> keySet() {
        return loadData().keySet();
    }

    @Override
    public Collection<String> values() {
        return loadData().values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {

        return loadData().entrySet();
    }

    private Map<String, String> loadData() {
        Map<String, String> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");

                if (parts.length == 2) {
                    map.put(parts[0].trim(), parts[1].trim());
                } else {

                    throw new IllegalArgumentException("Wrong number of arguments");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return map;
    }

    private void saveData(Map<String, String> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
