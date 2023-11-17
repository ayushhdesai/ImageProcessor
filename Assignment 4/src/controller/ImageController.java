package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ColorPixel;
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
   * @throws IOException if there's an error while processing the command.
   */
  public void processCommand(String commandLine) throws IOException {
    String[] parts = commandLine.split(" ");
    controller.Command command;

    switch (parts[0]) {
      case "load":
        command = new LoadCommand(this, parts[1], parts[2]);
        break;

      case "histogram":
        command = new HistogramCommand(this, parts[1], parts[2]);
        break;

      case "color-correct":
        Integer percentage = null;
        if (parts.length == 5 && "split".equals(parts[3])) {
          percentage = Integer.parseInt(parts[4]);
        }
        command = new ColorCorrectCommand(this, parts[1], parts[2], percentage);
        break;

      case "levels-adjust":
        Integer per = null;
        if (parts.length == 8 && "split".equals(parts[6])) {
          per = Integer.parseInt(parts[7]);
        }
        command = new LevelsAdjustCommand(this, parts[1], parts[2], parts[3],
                parts[4], parts[5], per);
        break;

      case "sepia":
        Integer percen = null;
        if (parts.length == 5 && "split".equals(parts[3])) {
          percen = Integer.parseInt(parts[4]);
        }
        command = new SepiaCommand(this, parts[1], parts[2], percen);
        break;

      case "red-component":
        command = new RedComponentCommand(this, parts[1], parts[2]);
        break;

      case "blue-component":
        command = new BlueComponentCommand(this, parts[1], parts[2]);
        break;

      case "green-component":
        command = new GreenComponentCommand(this, parts[1], parts[2]);
        break;

      case "value":
        command = new ValueCommand(this, parts[1], parts[2]);
        break;

      case "luma":
        command = new LumaCommand(this, parts[1], parts[2]);
        break;

      case "intensity":
        command = new IntensityCommand(this, parts[1], parts[2]);
        break;

      case "rgb-split":
        command = new RgbSplitCommand(this, parts[1], parts[2], parts[3], parts[4]);
        break;

      case "rgb-combine":
        command = new RgbCombineCommand(this, parts[1], parts[2], parts[3], parts[4]);
        break;

      case "greyscale":
        Integer percentag = null;
        if (parts.length == 5 && "split".equals(parts[3])) {
          percentag = Integer.parseInt(parts[4]);
        }
        command = new GreyscaleCommand(this, parts[1], parts[2], percentag);
        break;

      case "vertical-flip":
        command = new VerticalFlipCommand(this, parts[1], parts[2]);
        break;

      case "horizontal-flip":
        command = new HorizontalFlipCommand(this, parts[1], parts[2]);
        break;

      case "blur":
        Integer blurPercentage = null;
        if (parts.length == 5 && "split".equals(parts[3])) {
          blurPercentage = Integer.parseInt(parts[4]);
        }
        command = new BlurCommand(this, parts[1], parts[2], blurPercentage);
        break;

      case "sharpen":
        Integer sharpenPercentage = null;
        if (parts.length == 5 && "split".equals(parts[3])) {
          sharpenPercentage = Integer.parseInt(parts[4]);
        }
        command = new SharpenCommand(this, parts[1], parts[2], sharpenPercentage);
        break;

      case "save":
        command = new SaveCommand(this, parts[1], parts[2]);
        break;

      case "brighten":
        int alpha = Integer.parseInt(parts[1]);
        command = new BrightenCommand(this, alpha, parts[2], parts[3]);
        break;

      case "compress":
        float percent = Float.parseFloat(parts[1]);
        command = new CompressCommand(this, percent, parts[2], parts[3]);
        break;

      default:
        throw new IllegalArgumentException("Please enter a valid command!");
    }

    command.execute();
  }
}