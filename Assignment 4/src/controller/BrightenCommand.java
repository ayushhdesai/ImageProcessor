package controller;

import model.ColorImage;
import model.Image;

/**
 * A command that brightens the image.
 */
public class BrightenCommand implements Command {
  private ImageController controller;
  private int alpha;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new BrightenCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   * @param alpha          amount of brightness required.
   */
  public BrightenCommand(ImageController controller, int alpha, String inputImageKey,
                         String outputImageKey) {
    this.controller = controller;
    this.alpha = alpha;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    ColorImage imageToBrighten = (ColorImage) controller.imageMap.get(inputImageKey);
    Image brightenedImage = imageToBrighten.brighten(alpha);
    controller.imageMap.put(outputImageKey, brightenedImage);
  }
}
