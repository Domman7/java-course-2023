package edu.project3;

import edu.project2.Cell;
import edu.project2.ConsoleRenderer;
import edu.project2.Maze;
import edu.project2.PrimGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {
    static final String path =
        "C:\\Users\\Egor\\IdeaProjects\\java-course-2023\\project-template\\src\\test\\java\\edu\\project3" +
            "\\log_reports\\report";

    @DisplayName("Тест общего количества запросов")
    @Test
    void getTotalRequestCountTest() {
        var actual = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "0.md"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(
            "#### Общее количество запросов|       Метрика        | Значение ||----------------------|----" +
                "------|| Общее число запросов |   139    |",
            actual.toString()
        );
    }

    @DisplayName("Тест самых популярных запросов")
    @Test
    void getMostPopularResourcesTest() {
        var actual = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "1.md"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(
            "#### Запрашиваемые ресурсы|        Ресурс        | Количество ||----------------------|---------" +
                "---|| /downloads/product_1 |     70     || /downloads/product_2 |     65     || /downloads/product_3" +
                " |     2      |",
            actual.toString()
        );
    }

    @DisplayName("Тест самых популярных статусов")
    @Test
    void getMostPopularStatusesTest() {
        var actual = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "2.md"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(
            "#### Коды ответа| Код | Количество ||-----|------------|| 304 |     81     || 404 |     52   " +
                "  || 200 |     6      |",
            actual.toString()
        );
    }

    @DisplayName("Тест среднего количества байт")
    @Test
    void getAvgBytesSentTest() {
        var actual = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "3.md"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(
            "#### Ответы сервера|        Метрика        | Значение ||-----------------------|----------|" +
                "| Средний размер ответа |   170    |",
            actual.toString()
        );
    }

    @DisplayName("Тест самых популярных ip")
    @Test
    void getMostPopularRemoteAddressesTest() {
        var actual = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "4.md"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(
            "#### Удаленные адреса|    Адрес     | Количество ||--------------|------------|| 80.91.33.133 |" +
                "     38     || 5.83.131.103 |     8      || 50.57.209.92 |     8      || 93.180.71.3  |     7      " +
                "|| 217.168.17.5 |     7      |",
            actual.toString()
        );
    }

    @DisplayName("Тест самых популярных кодов ответа")
    @Test
    void getMostPopularRequestTypesTest() {
        var actual = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "5.md"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(
            "#### Типы запросов| Запрос | Количество ||--------|------------||  GET   |    138     ||  HEAD" +
                "  |     1      |",
            actual.toString()
        );
    }
}
