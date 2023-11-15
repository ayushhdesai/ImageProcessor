package controller;

import model.Image;

public class HistogramCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  public HistogramCommand(ImageController controller, String inputImageKey, String outputImageKey) {
    this.controller = controller;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
  }

  @Override
  public void execute() {
    Image imageForHistogram = controller.imageMap.get(inputImageKey);
    Image histogramImage = imageForHistogram.getHistogram();
    controller.imageMap.put(outputImageKey, histogramImage);
  }
}

