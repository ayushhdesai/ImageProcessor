package controller;

import model.ColorImage;
import model.Image;

/**
 * A command that compresses the image.
 */
public class CompressCommand implements Command {
  private ImageController controller;
  private float percentage;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new CompressCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   * @param percentage     percentage of compression required.
   */
  public CompressCommand(ImageController controller, float percentage,
                         String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.percentage = percentage;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    ColorImage imageToCompress = (ColorImage) controller.imageMap.get(inputImageKey);
    Image compressedImage = imageToCompress.compress(percentage);
    controller.imageMap.put(outputImageKey, compressedImage);
  }
}
