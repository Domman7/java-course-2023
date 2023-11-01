package edu.hw3.task6;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;

public class Stock implements Comparable<Stock> {
    private String name;
    private double price;

    public Stock(String name, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price should be positive");
        }

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price should be positive");
        }

        this.price = price;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return Double.compare(stock.price, price) == 0 && Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, price);
    }

    @Override public String toString() {

        return "Stock: " + name + "; Price: " + price;
    }

    @Override
    public int compareTo(@NotNull Stock o) {

        return Double.compare(o.price, price);
    }
}
