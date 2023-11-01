package edu.hw3.task5;

import org.jetbrains.annotations.NotNull;

public class Person implements Comparable<Person> {
    private final String firstName;
    private final String lastName;

    public Person(String firstName, String lastName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getValue() {
        if (lastName == null) {

            return firstName;
        }

        return lastName;
    }

    @Override
    public int compareTo(@NotNull Person o) {
        String first = this.lastName;
        String second = o.lastName;

        if (first == null) {
            first = this.firstName;
        }
        if (second == null) {
            second = o.firstName;
        }

        return first.compareTo(second);
    }

    @Override public String toString() {
        if (lastName != null) {

            return firstName + " " + lastName;
        }

        return firstName;
    }
}
