import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

  public static BufferedImage loadImage(String path) throws IOException {
    BufferedImage image = ImageIO.read(new File(path));
    if (image == null) {
      throw new IOException("Failed to load image from path: " + path);
    }
    return image;
  }


  public static void saveImage(BufferedImage image, String format, String path) throws IOException {
    ImageIO.write(image, format, new File(path));
  }

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

  public static BufferedImage convertToBufferedImage(Pixel[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        ColorPixel pixel = (ColorPixel) pixels[y][x];
        Color color = new Color(pixel.getRedValue(), pixel.getGreenValue(), pixel.getBlueValue());
        image.setRGB(x, y, color.getRGB());
      }
    }
    return image;
  }

  public static void main(String[] args) {
    try {
      Map<String, Image> imageMap = new HashMap<>();
      List<String> commands = Files.readAllLines(Paths.get("C:/Users/Kavish Desai/OneDrive/Desktop/ProgramDesignParadigm/Group/Assignment 4/src/output.txt"));

      for (String command : commands) {
        String[] parts = command.split(" ");


        switch (parts[0]) {
          case "load":
            BufferedImage loadedImage = loadImage(parts[1]);
            Pixel[][] pixels = convertToPixels(loadedImage);
            imageMap.put(parts[2], new ColorImage(pixels));
            break;

          case "linear-transform":
            ColorImage imageToTransform = (ColorImage) imageMap.get(parts[1]);
            float[][] mat = {
                    {0.393F, 0.769F, 0.189F},
                    {0.349F, 0.686F, 0.186F},
                    {0.272F, 0.534F, 0.131F}
            };
            Image transformedImage = imageToTransform.linearTransform(mat);
            imageMap.put(parts[2], transformedImage);
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

          case "save":
            BufferedImage imgToSave = convertToBufferedImage(((ColorImage) imageMap.get(parts[3])).pixels);
            saveImage(imgToSave, parts[1], parts[2]);
            break;

          case "brighten":
            ColorImage imageToBrighten = (ColorImage) imageMap.get(parts[2]);
            int alpha = Integer.parseInt(parts[1]);
            Image brightenedImage = imageToBrighten.brighten(alpha);
            imageMap.put(parts[3], brightenedImage);
            break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}


