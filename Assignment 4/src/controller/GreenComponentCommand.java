package controller;

import model.Image;

/**
 * A command that visualizes the Green Component of the image.
 */
public class GreenComponentCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new GreenComponentCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public GreenComponentCommand(ImageController controller, String inputImageKey,
                               String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToGreenTransform = controller.imageMap.get(inputImageKey);
    Image greenComponent = imageToGreenTransform.visualizeGreenComponent();
    controller.imageMap.put(outputImageKey, greenComponent);
  }
}
