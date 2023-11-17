package controller;

import model.GreyscaleImage;
import model.Image;

/**
 * A command that splits the channels of the image.
 */
public class RgbSplitCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String redOutputKey;
  private String greenOutputKey;
  private String blueOutputKey;

  /**
   * Constructs a new RgbSplitCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to store the processed image.
   * @param redOutputKey   for the red channel.
   * @param greenOutputKey for the green channel.
   * @param blueOutputKey  for the blue channel.
   */
  public RgbSplitCommand(ImageController controller, String inputImageKey, String redOutputKey,
                         String greenOutputKey, String blueOutputKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.redOutputKey = redOutputKey;
    this.greenOutputKey = greenOutputKey;
    this.blueOutputKey = blueOutputKey;
  }

  @Override
  public void execute() {
    Image imageToSplit = controller.imageMap.get(inputImageKey);
    GreyscaleImage redSplit = (GreyscaleImage) imageToSplit.getRedChannel();
    GreyscaleImage greenSplit = (GreyscaleImage) imageToSplit.getGreenChannel();
    GreyscaleImage blueSplit = (GreyscaleImage) imageToSplit.getBlueChannel();
    controller.imageMap.put(redOutputKey, redSplit);
    controller.imageMap.put(greenOutputKey, greenSplit);
    controller.imageMap.put(blueOutputKey, blueSplit);
  }
}
