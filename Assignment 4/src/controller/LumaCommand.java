package controller;

import model.GreyscaleImage;
import model.Image;

public class LumaCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

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
