package controller;

import model.Image;

/**
 * A command that visualizes the Blue Component of the image.
 */
public class BlueComponentCommand implements Command {

  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new BlueComponentCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public BlueComponentCommand(ImageController controller, String inputImageKey,
                              String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToBlueTransform = controller.imageMap.get(inputImageKey);
    Image blueComponent = imageToBlueTransform.visualizeBlueComponent();
    controller.imageMap.put(outputImageKey, blueComponent);
  }

}
