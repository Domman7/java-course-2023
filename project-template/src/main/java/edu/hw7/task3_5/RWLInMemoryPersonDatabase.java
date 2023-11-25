package edu.hw7.task3_5;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLInMemoryPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> persons;
    private final Map<String, List<Integer>> nameIndex;
    private final Map<String, List<Integer>> addressIndex;
    private final Map<String, List<Integer>> phoneIndex;
    private final ReadWriteLock lock;

    public RWLInMemoryPersonDatabase() {
        this.persons = new HashMap<>();
        this.nameIndex = new HashMap<>();
        this.addressIndex = new HashMap<>();
        this.phoneIndex = new HashMap<>();
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            persons.put(person.id(), person);
            addToIndex(nameIndex, person.name(), person.id());
            addToIndex(addressIndex, person.address(), person.id());
            addToIndex(phoneIndex, person.phoneNumber(), person.id());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person person = persons.remove(id);

            if (person != null) {
                removeFromIndex(nameIndex, person.name(), person.id());
                removeFromIndex(addressIndex, person.address(), person.id());
                removeFromIndex(phoneIndex, person.phoneNumber(), person.id());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
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
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
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
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
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
        } finally {
            lock.readLock().unlock();
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
