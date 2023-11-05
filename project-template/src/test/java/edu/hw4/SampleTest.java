package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static edu.hw4.Main.countByType;
import static edu.hw4.Main.getAnimalWithLongestName;
import static edu.hw4.Main.getAnimalsWithErrors;
import static edu.hw4.Main.getAnimalsWithErrorsReadable;
import static edu.hw4.Main.getBitingAnimals;
import static edu.hw4.Main.getCustomSortedAnimals;
import static edu.hw4.Main.getDifferentPawsAndAgeCountAnimals;
import static edu.hw4.Main.getDominantSex;
import static edu.hw4.Main.getFewWordsNamesAnimals;
import static edu.hw4.Main.getHeaviestAnimals;
import static edu.hw4.Main.getHeaviestAnimalsByType;
import static edu.hw4.Main.getHeaviestFish;
import static edu.hw4.Main.getKOldestAnimals;
import static edu.hw4.Main.getPawsCount;
import static edu.hw4.Main.getWeightAboveHeightCount;
import static edu.hw4.Main.getWeightSumByType;
import static edu.hw4.Main.isExistsDogHigherThanK;
import static edu.hw4.Main.isSpidersBiteMoreThanDogs;
import static edu.hw4.Main.sortByHeight;
import static edu.hw4.Main.sortByWeightDescending;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    private static final List<Animal> animals =
        Arrays.asList(
            new Animal("Кошка", Animal.Type.CAT, Animal.Sex.F, 10, 60, 5, true),
            new Animal("Собака", Animal.Type.DOG, Animal.Sex.M, 7, 80, 12, true),
            new Animal("Птица", Animal.Type.BIRD, Animal.Sex.F, 2, 15, 1, false),
            new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 3, 30, 2, true),
            new Animal("Паук", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true)
        );

    private static final List<Animal> customAnimals =
        Arrays.asList(
            new Animal("Кошка 1", Animal.Type.CAT, Animal.Sex.F, 10, 60, 5, true),
            new Animal("Собака1", Animal.Type.DOG, Animal.Sex.M, 7, 80, 12, true),
            new Animal("Кошка 2", Animal.Type.CAT, Animal.Sex.F, 2, 15, 1, false),
            new Animal("Собака2", Animal.Type.DOG, Animal.Sex.F, 3, 30, 2, true)
        );

    private static final List<Animal> animalsWithErrors =
        Arrays.asList(
            new Animal("Кошка 1", Animal.Type.CAT, Animal.Sex.F, -10, 60, 5, true),
            new Animal("Собака1", Animal.Type.DOG, Animal.Sex.M, 7, -80, 12, true),
            new Animal("Кошка 2", Animal.Type.CAT, Animal.Sex.F, 2, 15, 1, false),
            new Animal("Собака2", Animal.Type.DOG, Animal.Sex.F, 3, 30, 2, true)
        );

    @DisplayName("Задача 1")
    @Test
    public void sortByHeightTest() {
        var expected = Arrays.asList(
            new Animal("Паук", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Птица", Animal.Type.BIRD, Animal.Sex.F, 2, 15, 1, false),
            new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 3, 30, 2, true),
            new Animal("Кошка", Animal.Type.CAT, Animal.Sex.F, 10, 60, 5, true),
            new Animal("Собака", Animal.Type.DOG, Animal.Sex.M, 7, 80, 12, true)
        );

        var actual = sortByHeight(animals);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @DisplayName("Задача 2")
    @Test
    public void sortByWeightDescendingTest() {
        var expected = Arrays.asList(
            new Animal("Собака", Animal.Type.DOG, Animal.Sex.M, 7, 80, 12, true),
            new Animal("Кошка", Animal.Type.CAT, Animal.Sex.F, 10, 60, 5, true),
            new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 3, 30, 2, true)
        );

        var actual = sortByWeightDescending(animals, 3);

        for (int i = 0; i < 3; i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @DisplayName("Задача 3")
    @Test
    public void countByTypeTest() {
        var actual = countByType(animals);

        for (var item : actual.keySet()) {
            assertEquals(1, actual.get(item));
        }
    }

    @DisplayName("Задача 4")
    @Test
    public void getAnimalWithLongestNameTest() {
        var actual = getAnimalWithLongestName(animals);

        assertEquals("Собака", actual.name());
    }

    @DisplayName("Задача 5")
    @Test
    public void getDominantSexTest() {
        var actual = getDominantSex(animals);

        assertEquals(Animal.Sex.F, actual);
    }

    @DisplayName("Задача 6")
    @Test
    public void getHeaviestAnimalsByTypeTest() {
        var actual = getHeaviestAnimalsByType(customAnimals);

        assertEquals(
            new Animal("Кошка 1", Animal.Type.CAT, Animal.Sex.F, 10, 60, 5, true),
            actual.get(Animal.Type.CAT)
        );
        assertEquals(
            new Animal("Собака1", Animal.Type.DOG, Animal.Sex.M, 7, 80, 12, true),
            actual.get(Animal.Type.DOG)
        );

    }

    @DisplayName("Задача 7")
    @Test
    public void getKOldestAnimalTest() {
        var actual = getKOldestAnimals(animals, 3);

        assertEquals(new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 3, 30, 2, true), actual);
    }

    @DisplayName("Задача 8")
    @Test
    public void getHeaviestAnimalsTest() {
        var actual = getHeaviestAnimals(animals, 31);

        assertEquals(new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 3, 30, 2, true), actual.get());
    }

    @DisplayName("Задача 9")
    @Test
    public void getPawsCountTest() {

        assertEquals(18, getPawsCount(animals));
    }

    @DisplayName("Задача 10")
    @Test
    public void getDifferentPawsAndAgeCountAnimalsTest() {
        var actual = getDifferentPawsAndAgeCountAnimals(animals);

        assertEquals(4, actual.size());
    }

    @DisplayName("Задача 11")
    @Test
    public void getBitingAnimalsTest() {
        var actual = getBitingAnimals(animals);

        assertEquals(0, actual.size());
    }

    @DisplayName("Задача 12")
    @Test
    public void getWeightAboveHeightCountTest() {
        var actual = getWeightAboveHeightCount(animals);

        assertEquals(0, actual);
    }

    @DisplayName("Задача 13")
    @Test
    public void getFewWordsNamesAnimalsTest() {
        var actual = getFewWordsNamesAnimals(customAnimals);

        assertEquals(2, actual.size());
    }

    @DisplayName("Задача 14")
    @Test
    public void isExistsDogHigherThanKTest() {
        var actual = isExistsDogHigherThanK(animals, 70);

        assertTrue(actual);
    }

    @DisplayName("Задача 15")
    @Test
    public void getWeightSumByTypeTest() {
        var actual = getWeightSumByType(customAnimals, 0, 100);

        assertEquals(14, actual.get(Animal.Type.DOG));
        assertEquals(6, actual.get(Animal.Type.CAT));
    }

    @DisplayName("Задача 16")
    @Test
    public void getCustomSortedAnimalsTest() {
        var expected = Arrays.asList(
            new Animal("Кошка", Animal.Type.CAT, Animal.Sex.F, 10, 60, 5, true),
            new Animal("Собака", Animal.Type.DOG, Animal.Sex.M, 7, 80, 12, true),
            new Animal("Птица", Animal.Type.BIRD, Animal.Sex.F, 2, 15, 1, false),
            new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 3, 30, 2, true),
            new Animal("Паук", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true)
        );

        var actual = getCustomSortedAnimals(animals);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @DisplayName("Задача 17")
    @Test
    public void isSpidersBiteMoreThanDogsTest() {
        boolean actual = isSpidersBiteMoreThanDogs(animals);

        assertFalse(actual);

        actual = isSpidersBiteMoreThanDogs(customAnimals);

        assertFalse(actual);
    }

    @DisplayName("Задача 18")
    @Test
    public void getHeaviestFishTest() {
        List<List<Animal>> animalList = new ArrayList<>();
        animalList.add(animals);
        animalList.add(customAnimals);
        animalList.add(animalsWithErrors);

        var actual = getHeaviestFish(animalList);

        assertEquals(new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 3, 30, 2, true), actual);
    }

    @DisplayName("Задача 19")
    @Test
    public void getAnimalsWithErrorsTest() {
        var actual = getAnimalsWithErrors(animalsWithErrors);

        assertEquals(2, actual.size());
    }

    @DisplayName("Задача 20")
    @Test
    public void getAnimalsWithErrorsReadableTest() {
        var actual = getAnimalsWithErrorsReadable(animalsWithErrors);

        assertEquals("Invalid age", actual.get("Кошка 1"));
        assertEquals("Invalid height", actual.get("Собака1"));
    }
}
