package edu.hw2.task2;

public class Rectangle {
    private int width;
    private int height;

    public Rectangle setWidth(int width) {

        Rectangle temp = new Rectangle();
        temp.width = width;
        temp.height = height;

        return temp;
    }

    public Rectangle setHeight(int height) {
        Rectangle temp = new Rectangle();
        temp.width = width;
        temp.height = height;

        return temp;
    }

    public double area() {
        return width * height;
    }
}
