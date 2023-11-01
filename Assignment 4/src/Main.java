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

import controller.ImageController;
import controller.ImageUtil;
import model.ColorImage;
import model.ColorPixel;
import model.GreyscaleImage;
import model.Image;
import model.Pixel;

import static controller.ImageController.convertToBufferedImage;
import static controller.ImageController.convertToPixels;
import static controller.ImageController.loadImage;
import static controller.ImageController.saveImage;

public class Main {

  public static void main(String[] args) throws IOException {
    ImageController imageController = new ImageController();

    if (args.length > 0) {
      String filePath = args[0];
      try {
        List<String> fileCommands = Files.readAllLines(Paths.get(filePath));
        imageController.processCommands(fileCommands);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      imageController.interactiveMode();
    }

  }


}


