package controller;

import model.Image;

public class RedComponentCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public RedComponentCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToRedTransform = controller.imageMap.get(inputImageKey);
    Image redComponent = imageToRedTransform.visualizeRedComponent();
    controller.imageMap.put(outputImageKey, redComponent);
  }
}


