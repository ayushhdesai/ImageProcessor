package controller;

import model.Image;

public class BlueComponentCommand implements Command{

  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public BlueComponentCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToBlueTransform = controller.imageMap.get(inputImageKey);
    Image blueComponent = imageToBlueTransform.visualizeBlueComponent();
    controller.imageMap.put(outputImageKey, blueComponent);
  }

}
