package edu.hw10.task1;

public class MyClass {
    private final String name;
    private final int value;

    public MyClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static MyClass create(
        @NotNull String name,
        @Min(0) @Max(100) int value
    ) {

        return new MyClass(name, value);
    }

    @Override public String toString() {
        return "MyClass{" +
            "name=" + name +
            ", value=" + value +
            '}';
    }

    public String getName() {

        return name;
    }

    public int getValue() {

        return value;
    }
}
