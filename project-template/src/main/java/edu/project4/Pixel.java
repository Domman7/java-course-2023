package edu.project4;

public class Pixel {

    private int r;
    private int g;
    private int b;
    private int hitCount;
    private double brightness;

    public Pixel(int r, int g, int b, int hitCount) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = hitCount;
        this.brightness = 0;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getHitCount() {
        return hitCount;
    }

    public double getBrightness() {
        return brightness;
    }
}
