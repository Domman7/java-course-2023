package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> persons;
    private final Map<String, List<Integer>> nameIndex;
    private final Map<String, List<Integer>> addressIndex;
    private final Map<String, List<Integer>> phoneIndex;

    public InMemoryPersonDatabase() {
        this.persons = new HashMap<>();
        this.nameIndex = new HashMap<>();
        this.addressIndex = new HashMap<>();
        this.phoneIndex = new HashMap<>();
    }

    @Override
    public void add(Person person) {
        synchronized (this) {
            persons.put(person.id(), person);
            addToIndex(nameIndex, person.name(), person.id());
            addToIndex(addressIndex, person.address(), person.id());
            addToIndex(phoneIndex, person.phoneNumber(), person.id());
        }
    }

    @Override
    public void delete(int id) {
        synchronized (this) {
            Person person = persons.remove(id);

            if (person != null) {
                removeFromIndex(nameIndex, person.name(), person.id());
                removeFromIndex(addressIndex, person.address(), person.id());
                removeFromIndex(phoneIndex, person.phoneNumber(), person.id());
            }
        }
    }

    @Override
    public List<Person> findByName(String name) {
        synchronized (this) {
            List<Integer> ids = nameIndex.get(name);

            if (ids != null) {
                List<Person> result = new ArrayList<>();

                for (Integer id : ids) {
                    Person person = persons.get(id);

                    if (person != null) {
                        result.add(person);
                    }
                }

                return result;
            } else {

                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        synchronized (this) {
            List<Integer> ids = addressIndex.get(address);

            if (ids != null) {
                List<Person> result = new ArrayList<>();

                for (Integer id : ids) {
                    Person person = persons.get(id);

                    if (person != null) {
                        result.add(person);
                    }
                }

                return result;
            } else {

                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        synchronized (this) {
            List<Integer> ids = phoneIndex.get(phone);

            if (ids != null) {
                List<Person> result = new ArrayList<>();

                for (Integer id : ids) {
                    Person person = persons.get(id);

                    if (person != null) {
                        result.add(person);
                    }
                }

                return result;
            } else {

                return new ArrayList<>();
            }
        }
    }

    private void addToIndex(Map<String, List<Integer>> index, String key, int id) {
        List<Integer> ids = index.getOrDefault(key, new ArrayList<>());
        ids.add(id);
        index.put(key, ids);
    }

    private void removeFromIndex(Map<String, List<Integer>> index, String key, int id) {
        List<Integer> ids = index.get(key);
        if (ids != null) {
            ids.remove(Integer.valueOf(id));

            if (ids.isEmpty()) {
                index.remove(key);
            }
        }
    }
}
