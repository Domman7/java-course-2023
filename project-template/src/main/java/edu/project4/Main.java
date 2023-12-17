package edu.project4;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int width = 960;
        int height = 960;
        int symmetry = 8;
        int samples = 800;
        int iterPerSample = 10000;
        long seed = System.currentTimeMillis();
        Rect world = new Rect(-2.5, -2.5, 5.0, 5.0);
        double gamma = 0.45;
        Path path = Path.of("result.jpeg");
        ImageFormat format = ImageFormat.JPEG;

        List<Transformation> variations = new ArrayList<>();
        variations.add(point -> new Point(point.x() * 0.2 + 0.1, point.y() * 0.5 + 0.5));
        variations.add(point -> new Point(point.x() * 0.5 + 0.3, point.y() * 0.5));
        variations.add(point -> new Point(point.x() * 0.1, point.y() * 0.5 + 0.3));
        variations.add(point -> new Point(point.x() * 0.9 + 0.3, point.y() * 0.9 + 0.5));

        System.out.println("Последовательная версия:");
        long startTime = System.nanoTime();
        FractalFlameGenerator.generateSingleThread(
            width,
            height,
            symmetry,
            samples,
            iterPerSample,
            seed,
            world,
            variations,
            gamma,
            path,
            format
        );
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Время: " + duration);
        var temp = duration;

        System.out.println("Параллельная версия:");
        startTime = System.nanoTime();
        FractalFlameGenerator.generateMultiThread(
            width,
            height,
            symmetry,
            samples,
            iterPerSample,
            seed,
            world,
            variations,
            gamma,
            path,
            format
        );
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("Время: " + duration);
        System.out.println("Результирующее ускорение: " + temp / duration);
    }
}
