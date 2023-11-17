package controller;

import model.ColorImage;
import model.Image;

/**
 * A command that applies sepia to the image.
 */
public class SepiaCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage;

  /**
   * Constructs a new SepiaCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to assign the input image.
   * @param outputImageKey used to assign the output image.
   * @param percentage     used for splitting.
   */
  public SepiaCommand(ImageController controller, String inputImageKey, String outputImageKey,
                      Integer percentage) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    ColorImage imageToTransform = (ColorImage) controller.imageMap.get(inputImageKey);
    float[][] sepiaMatrix = {
            {0.393F, 0.769F, 0.189F},
            {0.349F, 0.686F, 0.186F},
            {0.272F, 0.534F, 0.131F}
    };
    Image transformedImage;
    if (percentage != null) {
      transformedImage = imageToTransform.linearTransformWithSplit(sepiaMatrix, percentage);
    } else {
      transformedImage = imageToTransform.linearTransform(sepiaMatrix);
    }
    controller.imageMap.put(outputImageKey, transformedImage);
  }
}

