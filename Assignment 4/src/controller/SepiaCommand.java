package controller;

import model.ColorImage;
import model.Image;

public class SepiaCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage; // Nullable for cases without a percentage

  public SepiaCommand(ImageController controller, String inputImageKey, String outputImageKey, Integer percentage) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    ColorImage imageToTransform = (ColorImage) controller.imageMap.get(inputImageKey);
    float[][] sepiaMatrix = {
            {0.393F, 0.769F, 0.189F},
            {0.349F, 0.686F, 0.186F},
            {0.272F, 0.534F, 0.131F}
    };
    Image transformedImage;
    if (percentage != null) {
      transformedImage = imageToTransform.linearTransformWithSplit(sepiaMatrix, percentage);
    } else {
      transformedImage = imageToTransform.linearTransform(sepiaMatrix);
    }
    controller.imageMap.put(outputImageKey, transformedImage);
  }
}

