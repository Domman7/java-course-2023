package edu.project4;

import java.nio.file.Path;
import java.util.List;

public class FractalFlameGenerator {
    public static FractalImage generateSingleThread(
        int width,
        int height,
        int symmetry,
        int samples,
        int iterPerSample,
        long seed,
        Rect world,
        List<Transformation> variations,
        double gamma,
        Path path,
        ImageFormat format
    ) {

        if (width <= 0 || height <= 0 || symmetry <= 0 || samples <= 0 || iterPerSample <= 0 || gamma <= 0) {

            throw new IllegalArgumentException("Invalid arguments");
        }

        if (variations.isEmpty()) {
            throw new IllegalArgumentException("Invalid variations list");
        }

        var canvas = FractalImage.create(width, height);
        var renderer = new SingleThreadRenderer();
        var image = renderer.render(canvas, world, variations, samples, iterPerSample, symmetry, seed);
        var processor = new GammaCorrector(gamma);
        processor.process(image);
        ImageUtils.save(image, path, format);

        return image;
    }

    public static FractalImage generateMultiThread(
        int width,
        int height,
        int symmetry,
        int samples,
        int iterPerSample,
        long seed,
        Rect world,
        List<Transformation> variations,
        double gamma,
        Path path,
        ImageFormat format
    ) {

        if (width <= 0 || height <= 0 || symmetry <= 0 || samples <= 0 || iterPerSample <= 0 || gamma <= 0) {

            throw new IllegalArgumentException("Invalid arguments");
        }

        if (variations.isEmpty()) {
            throw new IllegalArgumentException("Invalid variations list");
        }

        var canvas = FractalImage.create(width, height);
        var renderer = new MultiThreadRenderer();
        var image = renderer.render(canvas, world, variations, samples, iterPerSample, symmetry, seed);
        var processor = new GammaCorrector(gamma);
        processor.process(image);
        ImageUtils.save(image, path, format);

        return image;
    }
}
