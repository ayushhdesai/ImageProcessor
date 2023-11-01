package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


/**
 * This class contains utility methods to read and write a PPM image.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format from a given path.
   *
   * @param filename the path of the file.
   */
  public static BufferedImage readPPM(String filename) throws IOException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IOException("File " + filename + " not found!", e);
    }
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token = sc.next();

    if (!token.equals("P3")) {
      throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    if (maxValue > 255) {
      throw new IOException("Unsupported range.");
    }

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        image.setRGB(j, i, new Color(r, g, b).getRGB());
      }
    }

    return image;
  }

  /**
   * Write an image file in the PPM format to the given path.
   *
   * @param filename the path of the file.
   */
  public static void writePPM(BufferedImage image, String filename) throws IOException {
    try (FileWriter writer = new FileWriter(filename)) {
      int width = image.getWidth();
      int height = image.getHeight();

      writer.write("P3\n");
      writer.write(width + " " + height + "\n");
      writer.write("255\n");

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          Color color = new Color(image.getRGB(x, y));
          writer.write(color.getRed() + " ");
          writer.write(color.getGreen() + " ");
          writer.write(color.getBlue() + " ");
        }
        writer.write("\n");
      }
    }
  }
}

