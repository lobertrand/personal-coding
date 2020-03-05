package core;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PImage {

    BufferedImage img;
    public int width, height;
    public int[] pixels;

    public PImage(int width, int height) {
        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.width = width;
        this.height = height;
    }

    public PImage(String filePath) {
        try {
            img = ImageIO.read(new File(filePath));
            width = img.getWidth();
            height = img.getHeight();
        } catch (IOException e) {
            PCanvas.error(e.getMessage());
        }
    }

    public PImage(BufferedImage bufferedImage) {
        img = bufferedImage;
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    public PImage resize(int width, int height) {
        if (width <= 0) {
            width = (int) (this.width * height / (float) this.height);
        } else if (height <= 0) {
            height = (int) (this.height * width / (float) this.width);
        }

        BufferedImage resized = new BufferedImage(width, height, img.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(),
                img.getHeight(), null);
        g.dispose();
        this.img = resized;
        this.width = img.getWidth();
        this.height = img.getHeight();
        return this;
    }

    public int get(int x, int y) {
        return img.getRGB(x, y);
    }

    public void loadPixels() {
        pixels = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x + y * width] = img.getRGB(x, y);
            }
        }
    }

    public void updatePixels() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(x, y, pixels[x + y * width]);
            }
        }
    }

    public PImage get(int x, int y, int width, int height) {
        return new PImage(img.getSubimage(x, y, width, height));
    }

    public void grayScale() {
        final WritableRaster raster = img.getRaster();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] rgba = raster.getPixel(x, y, new int[4]);
                int average = (rgba[0] + rgba[2] + rgba[3]) / 3;
                rgba[0] = average;
                rgba[1] = average;
                rgba[2] = average;
                raster.setPixel(x, y, rgba);
            }
        }
        img.setData(raster);
    }

    public PImage copy() {
        BufferedImage bfimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bfimg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return new PImage(bfimg);
    }

    @Override
    public String toString() {
        return "PImage [width=" + width + ", height=" + height + "]";
    }

}