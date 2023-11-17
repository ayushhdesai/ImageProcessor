package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import model.ColorImage;

/**
 * A command that saves the image.
 */
public class SaveCommand implements Command {
  private ImageController controller;
  private String filePath;
  private String imageKey;

  /**
   * Constructs a new SaveCommand with the specified parameters.
   *
   * @param controller to facilitate image processing operations.
   * @param imageKey   used to assign the input image.
   * @param filePath   path of the image.
   */
  public SaveCommand(ImageController controller, String filePath, String imageKey) {
    this.controller = controller;
    this.filePath = filePath;
    this.imageKey = imageKey;
  }

  @Override
  public void execute() throws IOException {
    String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
    BufferedImage imgToSave = ImageController.convertToBufferedImage(((ColorImage)
            controller.imageMap.get(imageKey)).getPixels());
    ImageController.saveImage(imgToSave, extension, filePath);
  }
}
