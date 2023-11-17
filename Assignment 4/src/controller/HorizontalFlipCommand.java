package controller;

import model.ColorImage;
import model.Image;

/**
 * A command that Horizontally Flips the image.
 */
public class HorizontalFlipCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new HorizontalFlipCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public HorizontalFlipCommand(ImageController controller, String inputImageKey,
                               String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    ColorImage imageToHFlip = (ColorImage) controller.imageMap.get(inputImageKey);
    Image flipImage = imageToHFlip.horizontalFlip();
    controller.imageMap.put(outputImageKey, flipImage);
  }
}
