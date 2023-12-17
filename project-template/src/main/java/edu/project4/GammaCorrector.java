package edu.project4;

public class GammaCorrector implements ImageProcessor {
    private final double gamma;
    private double max = 0;

    public GammaCorrector(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                var pixel = image.pixel(x, y);

                if (pixel.getHitCount() != 0) {
                    pixel.setBrightness(Math.log10(pixel.getHitCount()));

                    if (max < pixel.getBrightness()) {
                        max = pixel.getBrightness();
                    }
                }
            }
        }

        var ginv = 1. / gamma;
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                var pixel = image.pixel(x, y);
                pixel.setBrightness(pixel.getBrightness() / max);
                var brightness = pixel.getBrightness();

                pixel.setR((int) (pixel.getR() * Math.pow(brightness, ginv)));
                pixel.setG((int) (pixel.getG() * Math.pow(brightness, ginv)));
                pixel.setB((int) (pixel.getB() * Math.pow(brightness, ginv)));
            }
        }
    }
}
