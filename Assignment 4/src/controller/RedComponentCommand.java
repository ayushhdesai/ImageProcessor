package controller;

import model.Image;

/**
 * A command that visualizes the Red Component of the image.
 */
public class RedComponentCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new RedComponentCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public RedComponentCommand(ImageController controller, String inputImageKey,
                             String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToRedTransform = controller.imageMap.get(inputImageKey);
    Image redComponent = imageToRedTransform.visualizeRedComponent();
    controller.imageMap.put(outputImageKey, redComponent);
  }
}


