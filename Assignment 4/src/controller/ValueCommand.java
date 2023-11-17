package controller;

import model.GreyscaleImage;
import model.Image;

/**
 * A command that shows value operation of the image.
 */
public class ValueCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new ValueCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to assign the input image.
   * @param outputImageKey used to assign the output image.
   */
  public ValueCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToValueTransform = controller.imageMap.get(inputImageKey);
    GreyscaleImage valueComponent = (GreyscaleImage) imageToValueTransform.getValue();
    controller.imageMap.put(outputImageKey, valueComponent);
  }
}
