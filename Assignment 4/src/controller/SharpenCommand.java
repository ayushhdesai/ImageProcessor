package controller;

import model.ColorImage;
import model.Image;

public class SharpenCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage; // Nullable for cases without a percentage

  public SharpenCommand(ImageController controller, String inputImageKey, String outputImageKey, Integer percentage) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    ColorImage imageToTransform2 = (ColorImage) controller.imageMap.get(inputImageKey);
    float[][] sharpenKernel = {
            {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F},
            {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
            {-0.125F, 0.25F, 1F, 0.25F, -0.125F},
            {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
            {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F}
    };
    Image transformedImage2;
    if (percentage != null) {
      transformedImage2 = imageToTransform2.filterSplit(sharpenKernel, percentage);
    } else {
      transformedImage2 = imageToTransform2.filter(sharpenKernel);
    }
    controller.imageMap.put(outputImageKey, transformedImage2);
  }
}
