package controller;

import model.Image;

/**
 * A command that Levels Adjust the image.
 */
public class LevelsAdjustCommand implements Command {
  private ImageController controller;
  private String blackLevel;
  private String midLevel;
  private String whiteLevel;
  private String inputImageKey;
  private String outputImageKey;
  private Integer percentage;

  /**
   * Constructs a new LevelsAdjustCommand with the specified parameters.
   *
   * @param controller     to facilitate image processing operations.
   * @param inputImageKey  used to retrieve the input image.
   * @param outputImageKey used to store the processed image.
   */
  public LevelsAdjustCommand(ImageController controller, String blackLevel, String midLevel,
                             String whiteLevel, String inputImageKey, String outputImageKey,
                             Integer percentage) {
    this.controller = controller;
    this.blackLevel = blackLevel;
    this.midLevel = midLevel;
    this.whiteLevel = whiteLevel;
    this.inputImageKey = inputImageKey;
    this.outputImageKey = outputImageKey;
    this.percentage = percentage;
  }

  @Override
  public void execute() {
    Image imageForLevelsAdjust = controller.imageMap.get(inputImageKey);
    Image adjustedLevelImg;
    if (percentage != null) {
      adjustedLevelImg = imageForLevelsAdjust.levelAdjustWithSplit(blackLevel,
              midLevel, whiteLevel, percentage);
    } else {
      adjustedLevelImg = imageForLevelsAdjust.adjustLevels(blackLevel, midLevel, whiteLevel);
    }
    controller.imageMap.put(outputImageKey, adjustedLevelImg);
  }
}

