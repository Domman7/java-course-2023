package edu.project4;

import java.util.List;

public class MultiThreadRenderer implements Renderer {
    private static final int NUM_THREADS = 8;

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
        Thread[] threads = new Thread[NUM_THREADS];
        int samplesPerThread = samples / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                var renderer = new SingleThreadRenderer();
                renderer.render(canvas, world, variations, samplesPerThread, iterPerSample, symmetry, seed);
            });

            threads[i].start();
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return canvas;
    }
}
