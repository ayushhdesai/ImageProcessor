package controller;

import model.Image;

/**
 * A command that color corrects the image.
 */
public class ColorCorrectCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage; // Nullable to handle cases where percentage is not provided

  /**
   * Constructs a new ColorCorrectCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   * @param percentage     percentage used for split.
   */
  public ColorCorrectCommand(ImageController controller, String inputImageKey,
                             String outputImageKey, Integer percentage) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    Image imageForColorCorrect = controller.imageMap.get(inputImageKey);
    Image correctedImage;
    if (percentage != null) {
      correctedImage = imageForColorCorrect.colorCorrectWithSplit(percentage);
    } else {
      correctedImage = imageForColorCorrect.colorCorrect();
    }
    controller.imageMap.put(outputImageKey, correctedImage);
  }
}

