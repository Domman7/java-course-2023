package edu.project4;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class SingleThreadRenderer implements Renderer {

    private static final int MAX_RGB_VALUE = 256;

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry,
        long seed
    ) {
        Random random = new Random(seed);

        for (int num = 0; num < samples; num++) {
            Point pw = randomPoint(world, random);

            for (int step = 0; step < iterPerSample; step++) {
                Transformation variation = getRandomVariation(variations, random);

                pw = variation.apply(pw);

                double theta2 = 0.0;
                for (int s = 0; s < symmetry; theta2 += Math.PI * 2 / symmetry, ++s) {
                    Point pwr = rotate(pw, theta2);

                    if (!world.contains(pwr)) {
                        continue;
                    }

                    Pixel pixel = mapRange(world, pwr, canvas);
                    if (pixel == null) {
                        continue;
                    }

                    synchronized (pixel) {
                        if (pixel.getHitCount() == 0) {
                            pixel.setR(random.nextInt(MAX_RGB_VALUE));
                            pixel.setG(random.nextInt(MAX_RGB_VALUE));
                            pixel.setB(random.nextInt(MAX_RGB_VALUE));
                        } else {
                            pixel.setR((pixel.getR() + random.nextInt(MAX_RGB_VALUE)) / 2);
                            pixel.setG((pixel.getG() + random.nextInt(MAX_RGB_VALUE)) / 2);
                            pixel.setB((pixel.getB() + random.nextInt(MAX_RGB_VALUE)) / 2);
                        }

                        pixel.setHitCount(pixel.getHitCount() + 1);
                    }
                }
            }
        }

        return canvas;
    }

    private Point randomPoint(Rect rect, Random random) {
        double x = rect.x() + random.nextDouble() * rect.width();
        double y = rect.y() + random.nextDouble() * rect.height();

        return new Point(x, y);
    }

    private Transformation getRandomVariation(List<Transformation> variations, Random random) {

        return variations.get(random.nextInt(variations.size()));
    }

    private Point rotate(Point point, double angle) {
        double x = point.x() * Math.cos(angle) - point.y() * Math.sin(angle);
        double y = point.x() * Math.sin(angle) + point.y() * Math.cos(angle);

        return new Point(x, y);
    }

    private Pixel mapRange(Rect world, Point point, FractalImage canvas) {
        int x = (int) ((point.x() - world.x()) / world.width() * canvas.width());
        int y = (int) ((point.y() - world.y()) / world.height() * canvas.height());

        if (!canvas.contains(x, y)) {

            return null;
        }

        return canvas.pixel(x, y);
    }
}
