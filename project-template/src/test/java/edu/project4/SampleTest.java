package edu.project4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.LinkedList;

public class SampleTest {
    @DisplayName("Тест исключений")
    @Test
    void getTotalRequestCountTest() {
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
        var variations = new LinkedList<Transformation>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
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
        });
    }
}
