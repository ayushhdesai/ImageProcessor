package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ColorImage;
import model.ColorPixel;
import model.GreyscaleImage;
import model.Image;
import model.Pixel;

/**
 * This class acts as the controller tp run script commands.
 */
public class ImageController {

  Map<String, model.Image> imageMap = new HashMap<>();

  /**
   * Load an image from the given path.
   *
   * @param path the path where the image is located.
   * @return BufferedImage of the loaded image.
   * @throws IOException if the image can't be loaded.
   */
  public static BufferedImage loadImage(String path) throws IOException {
    String extension = path.substring(path.lastIndexOf(".") + 1);
    if (extension.equalsIgnoreCase("ppm")) {
      return ImageUtil.readPPM(path);
    } else {
      BufferedImage image = ImageIO.read(new File(path));
      if (image == null) {
        throw new IOException("Failed to load image from path: " + path);
      }
      return image;
    }
  }

  /**
   * Save a given BufferedImage to a file.
   *
   * @param image  the image to save.
   * @param format the format in which to save the image.
   * @param path   the location where to save the image.
   * @throws IOException if the image can't be saved.
   */
  public static void saveImage(BufferedImage image, String format, String path) throws IOException {
    if (format.equalsIgnoreCase("ppm")) {
      ImageUtil.writePPM(image, path);
    } else {
      ImageIO.write(image, format, new File(path));
    }
  }

  /**
   * Convert a BufferedImage into a 2D array of Pixels.
   *
   * @param image the image to convert.
   * @return 2D array of Pixels.
   */
  public static Pixel[][] convertToPixels(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] pixels = new ColorPixel[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color color = new Color(image.getRGB(x, y));
        pixels[y][x] = new ColorPixel(color.getRed(), color.getGreen(), color.getBlue());
      }
    }
    return pixels;
  }

  /**
   * Convert a 2D array of Pixels into a BufferedImage.
   *
   * @param pixels the 2D array of Pixels to convert.
   * @return BufferedImage of the Pixels.
   */
  public static BufferedImage convertToBufferedImage(Pixel[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        ColorPixel pixel = (ColorPixel) pixels[y][x];
        int red = pixel.getRedValue();
        red = Math.min(255, Math.max(0, red));

        int green = pixel.getGreenValue();
        green = Math.min(255, Math.max(0, green));

        int blue = pixel.getBlueValue();
        blue = Math.min(255, Math.max(0, blue));

        Color color = new Color(red, green, blue);
        image.setRGB(x, y, color.getRGB());
      }
    }
    return image;
  }

  /**
   * Process a list of script commands.
   *
   * @param commands list of commands to be processed.
   * @throws IOException if there's an error while command segregation.
   */
  public void processCommands(List<String> commands) throws IOException {
    for (String command : commands) {
      processCommand(command);
    }
  }

  /**
   * Interactively process image commands entered by the user.
   *
   * @throws IOException if there's an error while command processing.
   */
  public void interactiveMode() throws IOException {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("Enter a command (e.g., load image.ppm image1 or type 'exit' to quit): ");
      String userInput = scanner.nextLine();
      if (userInput.equalsIgnoreCase("exit")) {
        System.out.println("Exiting the program.");
        break;
      }
      processCommand(userInput);
    }
  }

  /**
   * Process a script command using a switch case.
   *
   * @param command the command given.
   * @throws IOException if there's an error while processing the command.
   */
  public void processCommand(String command) throws IOException {

    String[] parts = command.split(" ");


    switch (parts[0]) {
      case "load":
        BufferedImage loadedImage = loadImage(parts[1]);
        Pixel[][] pixels = convertToPixels(loadedImage);
        imageMap.put(parts[2], new ColorImage(pixels));
        break;

      case "sepia":
        ColorImage imageToTransform = (ColorImage) imageMap.get(parts[1]);
        float[][] mat = {
                {0.393F, 0.769F, 0.189F},
                {0.349F, 0.686F, 0.186F},
                {0.272F, 0.534F, 0.131F}
        };
        Image transformedImage = imageToTransform.linearTransform(mat);
        imageMap.put(parts[2], transformedImage);
        break;

      case "red-component":
        Image imageToRedTransform = imageMap.get(parts[1]);
        Image redComponent = imageToRedTransform.visualizeRedComponent();
        imageMap.put(parts[2], redComponent);
        break;

      case "blue-component":
        Image imageToBlueTransform = imageMap.get(parts[1]);
        Image blueComponent = imageToBlueTransform.visualizeBlueComponent();
        imageMap.put(parts[2], blueComponent);
        break;

      case "green-component":
        Image imageToGreenTransform = imageMap.get(parts[1]);
        Image greenComponent = imageToGreenTransform.visualizeGreenComponent();
        imageMap.put(parts[2], greenComponent);
        break;

      case "value":
        Image imageToValueTransform = imageMap.get(parts[1]);
        GreyscaleImage valueComponent = (GreyscaleImage) imageToValueTransform.getValue();
        imageMap.put(parts[2], valueComponent);
        break;

      case "luma":
        Image imageToLumaTransform = imageMap.get(parts[1]);
        GreyscaleImage lumaComponent = (GreyscaleImage) imageToLumaTransform.getLuma();
        imageMap.put(parts[2], lumaComponent);
        break;

      case "intensity":
        Image imageToIntensityTransform = imageMap.get(parts[1]);
        GreyscaleImage intensityComponent = (GreyscaleImage) imageToIntensityTransform.getIntensity();
        imageMap.put(parts[2], intensityComponent);
        break;

      case "rgb-split":
        Image imageToSplit = imageMap.get(parts[1]);
        GreyscaleImage redSplit = (GreyscaleImage) imageToSplit.getRedChannel();
        GreyscaleImage greenSplit = (GreyscaleImage) imageToSplit.getGreenChannel();
        GreyscaleImage blueSplit = (GreyscaleImage) imageToSplit.getBlueChannel();
        imageMap.put(parts[2], redSplit);
        imageMap.put(parts[3], greenSplit);
        imageMap.put(parts[4], blueSplit);
        break;

      case "rgb-combine":
        Image redGreyImage = imageMap.get(parts[2]);
        Image greenGreyImage = imageMap.get(parts[3]);
        Image blueGreyImage = imageMap.get(parts[4]);
        Image combinedImage = redGreyImage.combineChannel(redGreyImage, greenGreyImage, blueGreyImage);
        imageMap.put(parts[1], combinedImage);
        break;

      case "greyscale":
        ColorImage imageToTransform3 = (ColorImage) imageMap.get(parts[1]);
        float[][] mat3 = {
                {0.2126F, 0.7152F, 0.0722F},
                {0.2126F, 0.7152F, 0.0722F},
                {0.2126F, 0.7152F, 0.0722F}
        };
        Image transformedImage3 = imageToTransform3.linearTransform(mat3);
        imageMap.put(parts[2], transformedImage3);
        break;

      case "vertical-flip":
        ColorImage imageToVFlip = (ColorImage) imageMap.get(parts[1]);
        Image flipImage = imageToVFlip.verticalFlip();
        imageMap.put(parts[2], flipImage);
        break;

      case "horizontal-flip":
        ColorImage imageToHFlip = (ColorImage) imageMap.get(parts[1]);
        Image flipImage1 = imageToHFlip.horizontalFlip();
        imageMap.put(parts[2], flipImage1);
        break;

      case "blur":
        ColorImage imageToTransform1 = (ColorImage) imageMap.get(parts[1]);
        float[][] kernel = {
                {0.0625F, 0.125F, 0.0625F},
                {0.125F, 0.25F, 0.125F},
                {0.0625F, 0.125F, 0.0625F}
        };
        Image transformedImage1 = imageToTransform1.filter(kernel);
        imageMap.put(parts[2], transformedImage1);
        break;

      case "sharpen":
        ColorImage imageToTransform2 = (ColorImage) imageMap.get(parts[1]);
        float[][] kernel1 = {
                {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F},
                {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
                {-0.125F, 0.25F, 1F, 0.25F, -0.125F},
                {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
                {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F}
        };
        Image transformedImage2 = imageToTransform2.filter(kernel1);
        imageMap.put(parts[2], transformedImage2);
        break;

      case "save":
        String filePath = parts[1];
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
        BufferedImage imgToSave = convertToBufferedImage(((ColorImage) imageMap.get(parts[2])).getPixels());
        saveImage(imgToSave, extension, parts[1]);
        break;

      case "brighten":
        ColorImage imageToBrighten = (ColorImage) imageMap.get(parts[2]);
        int alpha = Integer.parseInt(parts[1]);
        Image brightenedImage = imageToBrighten.brighten(alpha);
        imageMap.put(parts[3], brightenedImage);
        break;
    }

  }
}
