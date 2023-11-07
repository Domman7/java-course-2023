package edu.hw4;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ValidationError(Type type) {
    enum Type {
        INVALID_NAME, INVALID_AGE, INVALID_HEIGHT, INVALID_WEIGHT
    }

    public String message() {
        return switch (type) {
            case INVALID_NAME -> "Invalid name";
            case INVALID_AGE -> "Invalid age";
            case INVALID_HEIGHT -> "Invalid height";
            case INVALID_WEIGHT -> "Invalid weight";
        };
    }

    public static Boolean validateAnimal(Animal animal) {

        return animal.weight() >= 0 && animal.name().length() >= 1 && animal.age() >= 0 && animal.height() >= 0;
    }

    public static Set<ValidationError> getValidationErrors(Animal animal) {
        Set<ValidationError> res = new HashSet<>();

        if (animal.name().length() < 1) {

            res.add(new ValidationError(ValidationError.Type.INVALID_NAME));
        }

        if (animal.age() < 0) {

            res.add(new ValidationError(ValidationError.Type.INVALID_AGE));
        }

        if (animal.height() < 0) {

            res.add(new ValidationError(ValidationError.Type.INVALID_HEIGHT));
        }

        if (animal.weight() < 0) {

            res.add(new ValidationError(ValidationError.Type.INVALID_WEIGHT));
        }

        return res;
    }

    public static String getValidationErrorsAsString(Animal animal) {
        Set<ValidationError> validationErrors = getValidationErrors(animal);

        return validationErrors.stream()
            .map(ValidationError::message)
            .collect(Collectors.joining(", "));
    }
}
