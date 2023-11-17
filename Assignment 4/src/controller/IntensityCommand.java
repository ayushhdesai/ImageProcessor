package controller;

import model.Image;
import model.GreyscaleImage;

/**
 * A command that shows intensity of the image.
 */
public class IntensityCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new IntensityCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public IntensityCommand(ImageController controller, String inputImageKey,
                          String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToIntensityTransform = controller.imageMap.get(inputImageKey);
    GreyscaleImage intensityComponent = (GreyscaleImage) imageToIntensityTransform.getIntensity();
    controller.imageMap.put(outputImageKey, intensityComponent);
  }
}
