package controller;

import model.ColorImage;
import model.Image;

public class VerticalFlipCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public VerticalFlipCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    ColorImage imageToVFlip = (ColorImage) controller.imageMap.get(inputImageKey);
    Image flipImage = imageToVFlip.verticalFlip();
    controller.imageMap.put(outputImageKey, flipImage);
  }
}
