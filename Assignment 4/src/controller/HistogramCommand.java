package controller;

import model.Image;

/**
 * A command that creates Histogram of the image.
 */
public class HistogramCommand implements Command {
  private ImageController controller;
  private String inputImageKey;
  private String outputImageKey;

  /**
   * Constructs a new HistogramCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
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

