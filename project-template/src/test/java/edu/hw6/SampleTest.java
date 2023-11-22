package edu.hw6;

import edu.hw6.task1.DiskMap;
import edu.hw6.task3.AbstractFilter;
import edu.hw6.task5.HackerNews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static edu.hw6.Main.cloneFile;
import static edu.hw6.Main.streamCombinationWrite;
import static edu.hw6.task3.AbstractFilter.globMatches;
import static edu.hw6.task3.AbstractFilter.largerThan;
import static edu.hw6.task3.AbstractFilter.regexContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    public static final AbstractFilter regularFile = Files::isRegularFile;
    public static final AbstractFilter readable = Files::isReadable;

    @DisplayName("Задание 1")
    @Test
    public void diskMapTest() {
        String localDir = System.getProperty("user.dir");
        String path = localDir + "\\src\\test\\java\\edu\\hw6\\data.txt";
        DiskMap map = new DiskMap(path);
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        StringBuilder actual = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals("key1:value1key2:value2key3:value3", actual.toString());

        map.remove("key2");
        assertEquals(2, map.size());

        assertEquals(map.get("key3"), "value3");
    }

    @DisplayName("Задание 2")
    @Test
    public void copyFileTest() {
        String localDir = System.getProperty("user.dir");
        DiskMap map = new DiskMap(localDir + "\\src\\test\\java\\edu\\hw6\\data.txt");
        Path path = Path.of(localDir + "\\src\\test\\java\\edu\\hw6\\data.txt");

        cloneFile(path);
        File actualFirst = new File(localDir + "\\src\\test\\java\\edu\\hw6\\data - копия.txt");
        assertTrue(actualFirst.exists());
        cloneFile(path);
        File actualSecond = new File(localDir + "\\src\\test\\java\\edu\\hw6\\data - копия (2).txt");
        assertTrue(actualSecond.exists());

        actualFirst.delete();
        actualSecond.delete();
    }

    @DisplayName("Задание 3")
    @Test
    public void abstractFilterTest() throws IOException {
        Path dir = Path.of(
            "C:\\Users\\Egor\\IdeaProjects\\java-course-2023\\project-template\\src\\test\\java\\edu\\hw6");

        DirectoryStream.Filter<Path> filter = regularFile
            .and(readable)
            .and(largerThan(10))
            .and(globMatches("*.txt"))
            .and(regexContains("temp"));

        StringBuilder actual = new StringBuilder();

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            entries.forEach(f -> actual.append(f.getFileName()));
        }

        assertEquals("temp1.txttemp2.txt", actual.toString());
    }

    @DisplayName("Задание 4")
    @Test
    public void streamCombinationWriteTest() throws IOException {
        var expected = "Programming is learned by writing programs. ― Brian Kernighan";
        Path path = streamCombinationWrite(expected);
        StringBuilder actual = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(expected, actual.toString());
        Files.delete(path);
    }

    @DisplayName("Задание 5")
    @Test
    public void newsTest() throws IOException {
        var expected = "JDK 21 Release Notes";
        String actual = HackerNews.news(37570037);

        assertEquals(expected, actual);
    }
}
