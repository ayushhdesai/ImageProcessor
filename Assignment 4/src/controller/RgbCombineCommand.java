package controller;

import model.Image;

/**
 * A command that combines the channels of the image.
 */
public class RgbCombineCommand implements Command {
  private ImageController controller;
  private String outputImageKey;
  private String redImageKey;
  private String greenImageKey;
  private String blueImageKey;

  /**
   * Constructs a new RgbCombineCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param outputImageKey used to store the processed image.
   * @param redImageKey    for the red channel.
   * @param greenImageKey  for the green channel.
   * @param blueImageKey   for the blue channel.
   */
  public RgbCombineCommand(ImageController controller, String outputImageKey, String redImageKey,
                           String greenImageKey, String blueImageKey) {
    this.controller = controller;
    this.outputImageKey = outputImageKey;
    this.redImageKey = redImageKey;
    this.greenImageKey = greenImageKey;
    this.blueImageKey = blueImageKey;
  }

  @Override
  public void execute() {
    Image redGreyImage = controller.imageMap.get(redImageKey);
    Image greenGreyImage = controller.imageMap.get(greenImageKey);
    Image blueGreyImage = controller.imageMap.get(blueImageKey);
    Image combinedImage = redGreyImage.combineChannel(redGreyImage, greenGreyImage, blueGreyImage);
    controller.imageMap.put(outputImageKey, combinedImage);
  }
}
