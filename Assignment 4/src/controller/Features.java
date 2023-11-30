package controller;

import java.awt.image.BufferedImage;

/**
 * The interface provides methods for various image processing operations.
 * The operations are used to support functionalities in the GUI view.
 */
public interface Features {

  /**
   * Loads an image into the application.
   */
  void loadImage();

  /**
   * Saves the currently loaded image.
   */
  void saveImage();

  /**
   * Flips the image vertically.
   */
  void verticalFlip();

  /**
   * Flips the image horizontally.
   */
  void horizontalFlip();

  /**
   * Visualizes the red color component of the image.
   */
  void visualizeRedComponent();

  /**
   * Visualizes the green color component of the image.
   */
  void visualizeGreenComponent();

  /**
   * Visualizes the blue color component of the image.
   */
  void visualizeBlueComponent();

  /**
   * Loads and displays the histogram of the image.
   */
  void loadHistogram();

  /**
   * Applies a blur effect to the image.
   */
  void blurImage();

  /**
   * Applies a sharpening effect to the image.
   */
  void applySharpen();

  /**
   * Converts the image to greyscale.
   */
  void convertToGreyscale();

  /**
   * Converts the image to sepia tone.
   */
  void convertToSepia();

  /**
   * Applies compression to the image.
   */
  void applyCompression();

  /**
   * Performs color correction on the image.
   */
  void colorCorrect();

  /**
   * Adjusts the levels of the image.
   */
  void adjustLevels();

  /**
   * Applies a blur effect to a portion of the image.
   *
   * @param splitPercentage percentage of the image to apply the operation to.
   * @return an image with the operation effect applied to a portion of it.
   */
  BufferedImage blurImageWithSplit(int splitPercentage);

  /**
   * Applies a sharpen effect to a portion of the image.
   *
   * @param splitPercentage percentage of the image to apply the operation to.
   * @return an image with the operation effect applied to a portion of it.
   */
  BufferedImage sharpenImageWithSplit(int splitPercentage);

  /**
   * Converts a portion of the image to sepia tone.
   *
   * @param splitPercentage percentage of the image to apply the operation to.
   * @return an image with the operation effect applied to a portion of it.
   */
  BufferedImage sepiaImageWithSplit(int splitPercentage);

  /**
   * Adjusts the levels of a portion of the image.
   *
   * @param splitPercentage percentage of the image to apply the operation to.
   * @return an image with the operation effect applied to a portion of it.
   */
  BufferedImage levelsAdjustImageWithSplit(int splitPercentage);

  /**
   * Converts a portion of the image to greyscale.
   *
   * @param splitPercentage percentage of the image to apply the operation to.
   * @return an image with the operation effect applied to a portion of it.
   */
  BufferedImage greyscaleImageWithSplit(int splitPercentage);

  /**
   * Applies color correction to a portion of the image.
   *
   * @param splitPercentage percentage of the image to apply the operation to.
   * @return an image with the operation effect applied to a portion of it.
   */
  BufferedImage colorCorrectionImageWithSplit(int splitPercentage);
}
