package controller;

import model.ColorImage;
import model.Image;

/**
 * A command that blurs the image.
 */
public class BlurCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage;

  /**
   * Constructs a new BlurCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   * @param percentage     percentage used for split.
   */
  public BlurCommand(ImageController controller, String inputImageKey,
                     String outputImageKey, Integer percentage) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    ColorImage imageToTransform1 = (ColorImage) controller.imageMap.get(inputImageKey);
    float[][] blurKernel = {
            {0.0625F, 0.125F, 0.0625F},
            {0.125F, 0.25F, 0.125F},
            {0.0625F, 0.125F, 0.0625F}
    };
    Image transformedImage1;
    if (percentage != null) {
      transformedImage1 = imageToTransform1.filterSplit(blurKernel, percentage);
    } else {
      transformedImage1 = imageToTransform1.filter(blurKernel);
    }
    controller.imageMap.put(outputImageKey, transformedImage1);
  }
}