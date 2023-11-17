package controller;

import model.GreyscaleImage;
import model.Image;

/**
 * A command that shows luma of the image.
 */
public class LumaCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new LumaCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public LumaCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToLumaTransform = controller.imageMap.get(inputImageKey);
    GreyscaleImage lumaComponent = (GreyscaleImage) imageToLumaTransform.getLuma();
    controller.imageMap.put(outputImageKey, lumaComponent);
  }
}
