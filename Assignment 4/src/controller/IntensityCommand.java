package controller;

import model.Image;
import model.GreyscaleImage;

public class IntensityCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public IntensityCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageToIntensityTransform = controller.imageMap.get(inputImageKey);
    GreyscaleImage intensityComponent = (GreyscaleImage) imageToIntensityTransform.getIntensity();
    controller.imageMap.put(outputImageKey, intensityComponent);
  }
}
