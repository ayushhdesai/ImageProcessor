package controller;

import model.ColorImage;
import model.Image;

/**
 * A command that Vertically Flips the image.
 */
public class VerticalFlipCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new VerticalFlipCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public VerticalFlipCommand(ImageController controller, String inputImageKey,
                             String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    ColorImage imageToVFlip = (ColorImage) controller.imageMap.get(inputImageKey);
    Image flipImage = imageToVFlip.verticalFlip();
    controller.imageMap.put(outputImageKey, flipImage);
  }
}
