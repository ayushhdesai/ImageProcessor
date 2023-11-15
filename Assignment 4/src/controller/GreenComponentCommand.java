package controller;

import model.Image;

public class GreenComponentCommand implements Command{
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public GreenComponentCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToGreenTransform = controller.imageMap.get(inputImageKey);
    Image greenComponent = imageToGreenTransform.visualizeGreenComponent();
    controller.imageMap.put(outputImageKey, greenComponent);
  }
}
