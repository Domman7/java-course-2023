package edu.project4;

public record Rect(double x, double y, double width, double height) {
    public boolean contains(Point p) {

        return p.x() >= x && p.y() >= y && p.x() < x + width && p.y() < y + height;
    }
}
