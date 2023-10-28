package edu.hw2.task2;

public class Square extends Rectangle {
    @Override
    public Rectangle setWidth(int width) {

        return super.setWidth(width).setHeight(width);
    }

    @Override
    public Rectangle setHeight(int height) {

        return super.setWidth(height).setHeight(height);
    }
}
