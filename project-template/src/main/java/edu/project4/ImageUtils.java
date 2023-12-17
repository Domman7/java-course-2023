package edu.project4;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        var height = image.height();
        var width = image.width();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] colorArray = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var pixel = image.pixel(x, y);
                colorArray[x + y * width] = new Color(pixel.getR(), pixel.getG(), pixel.getB()).getRGB();
            }
        }

        bufferedImage.setRGB(0, 0, width, height, colorArray, 0, width);

        try {
            ImageIO.write(bufferedImage, format.name(), filename.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
