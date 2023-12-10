package edu.project4;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[] data = new Pixel[width * height];

        for (int i = 0; i < data.length; i++) {
            data[i] = new Pixel(0, 0, 0, 0);
        }

        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {

        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public Pixel pixel(int x, int y) {

        return data[x + y * width];
    }
}
