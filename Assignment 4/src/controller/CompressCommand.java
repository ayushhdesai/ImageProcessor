package controller;

import model.ColorImage;
import model.Image;

public class CompressCommand implements Command {
  private ImageController controller;
  private float percentage;
  private String inputImageKey;
  private String outputImageKey;

  public CompressCommand(ImageController controller, float percentage, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.percentage = percentage;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    ColorImage imageToCompress = (ColorImage) controller.imageMap.get(inputImageKey);
    Image compressedImage = imageToCompress.compress(percentage);
    controller.imageMap.put(outputImageKey, compressedImage);
  }
}
