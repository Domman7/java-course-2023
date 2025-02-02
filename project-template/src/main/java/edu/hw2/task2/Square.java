package edu.hw2.task2;

public class Square extends Rectangle {
    public Square() {
        super();
    }

    public Square(int side) {
        super(side, side);
    }

    @Override
    public Rectangle setWidth(int width) {

        return super.setWidth(width).setHeight(width);
    }

    @Override
    public Rectangle setHeight(int height) {

        return super.setWidth(height).setHeight(height);
    }
}
