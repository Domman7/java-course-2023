package edu.hw4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
    }

    //Задача 1
    public static List<Animal> sortByHeight(List<Animal> animals) {

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .collect(Collectors.toList());
    }

    //Задача 2
    public static List<Animal> sortByWeightDescending(List<Animal> animals, int k) {
        if (k < 1) {

            throw new IllegalArgumentException("Invalid k");
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    //Задача 3
    public static Map<Animal.Type, Integer> countByType(List<Animal> animals) {

        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(e -> 1)));
    }

    //Задача 4
    public static Animal getAnimalWithLongestName(List<Animal> animals) {

        return animals.stream()
            .max(Comparator.comparingInt(e -> e.name().length()))
            .orElse(null);
    }

    //Задача 5
    public static Animal.Sex getDominantSex(List<Animal> animals) {
        long maleCount = animals.stream()
            .filter(e -> e.sex() == Animal.Sex.M)
            .count();

        long femaleCount = animals.stream()
            .filter(e -> e.sex() == Animal.Sex.F)
            .count();

        return (maleCount > femaleCount) ? Animal.Sex.M : Animal.Sex.F;
    }

    //Задача 6
    public static Map<Animal.Type, Animal> getHeaviestAnimalsByType(List<Animal> animals) {

        return animals.stream()
            .collect(Collectors.toMap(
                Animal::type,
                Function.identity(),
                BinaryOperator.maxBy(Comparator.comparingInt(Animal::weight))
            ));
    }

    //Задача 7
    public static Animal getKOldestAnimals(List<Animal> animals, int k) {
        if (k < 2) {

            throw new IllegalArgumentException("Invalid k");
        }

        return animals.stream()
            .sorted(Comparator.comparing(Animal::age))
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    //Задача 8
    public static Optional<Animal> getHeaviestAnimals(List<Animal> animals, int k) {
        if (k < 0) {

            throw new IllegalArgumentException("Invalid k");
        }

        return animals.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    //Задача 9
    public static Integer getPawsCount(List<Animal> animals) {

        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    //Задача 10
    public static List<Animal> getDifferentPawsAndAgeCountAnimals(List<Animal> animals) {

        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());
    }

    //Задача 11
    public static List<Animal> getBitingAnimals(List<Animal> animals) {

        return animals
            .stream()
            .filter(animal -> animal.bites() && animal.height() > 100)
            .collect(Collectors.toList());
    }

    //Задача 12
    public static Integer getWeightAboveHeightCount(List<Animal> animals) {

        return Math.toIntExact(animals
            .stream()
            .filter(animal -> animal.weight() > animal.height())
            .count());
    }

    //Задача 13
    public static List<Animal> getFewWordsNamesAnimals(List<Animal> animals) {

        return animals
            .stream()
            .filter(animal -> animal.name().split(" ").length >= 2)
            .collect(Collectors.toList());
    }

    //Задача 14
    public static Boolean isExistsDogHigherThanK(List<Animal> animals, int k) {
        if (k < 0) {

            throw new IllegalArgumentException("Invalid k");
        }

        return animals
            .stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    //Задача 15
    public static Map<Animal.Type, Integer> getWeightSumByType(List<Animal> animals, int k, int l) {
        if (k < 0 || k > l) {

            throw new IllegalArgumentException("Invalid k or l");
        }

        return animals
            .stream()
            .filter(animal -> animal.age() >= k && animal.age() <= l)
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    //Задача 16
    public static List<Animal> getCustomSortedAnimals(List<Animal> animals) {

        return animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .collect(Collectors.toList());
    }

    //Задача 17
    public static Boolean isSpidersBiteMoreThanDogs(List<Animal> animals) {

        return animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER || animal.type() == Animal.Type.DOG)
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()))
            .getOrDefault(Animal.Type.SPIDER, 0L) > animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER || animal.type() == Animal.Type.DOG)
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()))
            .getOrDefault(Animal.Type.DOG, 0L);
    }

    //Задача 18
    public static Animal getHeaviestFish(List<List<Animal>> animalsList) {

        return concatStreams(animalsList)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight))
            .orElse(null);
    }

    public static Stream<Animal> concatStreams(List<List<Animal>> animalList) {

        if (animalList.size() > 2) {

            return Stream.concat(animalList.get(0).stream(), concatStreams(animalList.subList(1, animalList.size())));
        } else if (animalList.size() == 2) {

            return Stream.concat(animalList.get(0).stream(), animalList.get(1).stream());
        } else if (animalList.size() == 1) {

            return animalList.get(0).stream();
        } else {

            throw new IllegalArgumentException("List are empty");
        }
    }

    //Задача 19
    public static Map<String, Set<ValidationError>> getAnimalsWithErrors(List<Animal> animals) {

        return animals.stream()
            .filter(animal -> !ValidationError.validateAnimal(animal))
            .collect(Collectors.toMap(Animal::name, ValidationError::getValidationErrors));
    }

    //Задача 20
    public static Map<String, String> getAnimalsWithErrorsReadable(List<Animal> animals) {

        return animals.stream()
            .filter(animal -> !ValidationError.validateAnimal(animal))
            .collect(Collectors.toMap(Animal::name, ValidationError::getValidationErrorsAsString));
    }
}
