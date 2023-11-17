package controller;

import model.ColorImage;
import model.Image;

/**
 * A command that GreyScales the image.
 */
public class GreyscaleCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage; // Nullable for cases without a percentage

  /**
   * Constructs a new GreyscaleCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   * @param percentage     used for splitting.
   */
  public GreyscaleCommand(ImageController controller, String inputImageKey,
                          String outputImageKey, Integer percentage) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    ColorImage imageToTransform3 = (ColorImage) controller.imageMap.get(inputImageKey);
    float[][] greyscaleMatrix = {
            {0.2126F, 0.7152F, 0.0722F},
            {0.2126F, 0.7152F, 0.0722F},
            {0.2126F, 0.7152F, 0.0722F}
    };
    Image transformedImage3;
    if (percentage != null) {
      transformedImage3 = imageToTransform3.linearTransformWithSplit(greyscaleMatrix, percentage);
    } else {
      transformedImage3 = imageToTransform3.linearTransform(greyscaleMatrix);
    }
    controller.imageMap.put(outputImageKey, transformedImage3);
  }
}
