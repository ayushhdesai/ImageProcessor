package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import model.ColorImage;
import model.Pixel;

/**
 * A command that loads the image.
 */
public class LoadCommand implements Command {
  private ImageController controller;
  private String path;
  private String imageKey;

  /**
   * Constructs a new LoadCommand with the specified parameters.
   *
   * @param controller to facilitate image processing operations.
   * @param imageKey   used to assign the input image.
   * @param path       path of the image.
   */
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

