package controller;

import model.Image;

public class ColorCorrectCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage; // Nullable to handle cases where percentage is not provided

  public ColorCorrectCommand(ImageController controller, String inputImageKey, String outputImageKey, Integer percentage) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    Image imageForColorCorrect = controller.imageMap.get(inputImageKey);
    Image correctedImage;
    if (percentage != null) {
      correctedImage = imageForColorCorrect.colorCorrectWithSplit(percentage);
    } else {
      correctedImage = imageForColorCorrect.colorCorrect();
    }
    controller.imageMap.put(outputImageKey, correctedImage);
  }
}

