package controller;

import model.ColorImage;
import model.Image;

public class BrightenCommand implements Command {
  private ImageController controller;
  private int alpha;
  private String inputImageKey;
  private String outputImageKey;

  public BrightenCommand(ImageController controller, int alpha, String inputImageKey, String outputImageKey) {
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
