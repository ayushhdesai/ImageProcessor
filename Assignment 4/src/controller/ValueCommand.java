package controller;

import model.GreyscaleImage;
import model.Image;

public class ValueCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public ValueCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToValueTransform = controller.imageMap.get(inputImageKey);
    GreyscaleImage valueComponent = (GreyscaleImage) imageToValueTransform.getValue();
    controller.imageMap.put(outputImageKey, valueComponent);
  }
}
