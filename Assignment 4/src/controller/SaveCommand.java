package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import model.ColorImage;

public class SaveCommand implements Command {
  private ImageController controller;
  private String filePath;
  private String imageKey;

  public SaveCommand(ImageController controller, String filePath, String imageKey) {
    this.controller = controller;
    this.filePath = filePath;
    this.imageKey = imageKey;
  }

  @Override
  public void execute() throws IOException {
    String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
    BufferedImage imgToSave = ImageController.convertToBufferedImage(((ColorImage) controller.imageMap.get(imageKey)).getPixels());
    ImageController.saveImage(imgToSave, extension, filePath);
  }
}
