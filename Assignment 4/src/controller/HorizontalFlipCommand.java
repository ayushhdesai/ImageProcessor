package controller;

import model.ColorImage;
import model.Image;

public class HorizontalFlipCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public HorizontalFlipCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    ColorImage imageToHFlip = (ColorImage) controller.imageMap.get(inputImageKey);
    Image flipImage = imageToHFlip.horizontalFlip();
    controller.imageMap.put(outputImageKey, flipImage);
  }
}
