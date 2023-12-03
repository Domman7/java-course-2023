package edu.hw7;

import edu.hw6.task1.DiskMap;
import edu.hw7.task3.InMemoryPersonDatabase;
import edu.hw7.task3.Person;
import edu.hw7.task3_5.RWLInMemoryPersonDatabase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Path;
import static edu.hw6.Main.cloneFile;
import static edu.hw7.Main.factorial;
import static edu.hw7.Main.getCount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @DisplayName("Задание 1")
    @Test
    public void getCountTest() throws InterruptedException {

        assertEquals(30, getCount(10, 3));
    }

    @DisplayName("Задание 2")
    @Test
    public void factorialTest() {
        assertEquals(120, factorial(5));
    }

    @DisplayName("Задание 3")
    @Test
    public void personDatabaseTest() {
        var db = new InMemoryPersonDatabase();
        db.add(new Person(1, "First", "f", "1"));
        db.add(new Person(2, "Second", "s", "2"));
        db.add(new Person(3, "Third", "t", "3"));

        assertEquals(1, db.findByName("First").size());
        assertEquals(1, db.findByAddress("s").size());
        assertEquals(1, db.findByPhone("3").size());
    }

    @DisplayName("Задание 3_5")
    @Test
    public void personDatabaseRWLTest() {
        var db = new RWLInMemoryPersonDatabase();
        db.add(new Person(1, "First", "f", "1"));
        db.add(new Person(2, "Second", "s", "2"));
        db.add(new Person(3, "Third", "t", "3"));

        assertEquals(1, db.findByName("First").size());
        assertEquals(1, db.findByAddress("s").size());
        assertEquals(1, db.findByPhone("3").size());
    }
}
