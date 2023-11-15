package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import model.ColorImage;
import model.Pixel;

public class LoadCommand implements Command {
  private ImageController controller;
  private String path;
  private String imageKey;

  public LoadCommand(ImageController controller, String path, String imageKey) {
    this.controller = controller;
    this.path = path;
    this.imageKey = imageKey;
  }

  @Override
  public void execute() throws IOException {
    BufferedImage loadedImage = ImageController.loadImage(path);
    Pixel[][] pixels = ImageController.convertToPixels(loadedImage);
    controller.imageMap.put(imageKey, new ColorImage(pixels));
  }
}

