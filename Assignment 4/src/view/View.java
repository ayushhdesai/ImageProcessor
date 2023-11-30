package view;

import java.awt.image.BufferedImage;

import controller.Features;

/**
 * This is an interface for an image processing application.
 * It defines methods required for image processing.
 */
public interface View {

  /**
   * Loads an image into the view.
   */
  void loadImage();

  /**
   * Displays a given BufferedImage in the view.
   *
   * @param bufferedImage image to be displayed.
   */
  void displayImage(BufferedImage bufferedImage);

  /**
   * Displays the histogram of a given BufferedImage.
   *
   * @param bufferedImage image for which the histogram is to be displayed.
   */
  void displayHistogram(BufferedImage bufferedImage);

  /**
   * Displays the preview of the split of the required operation.
   *
   * @param image split image to be displayed.
   */
  void displayPreviewImage(BufferedImage image);

  /**
   * Retrieves the current image displayed in the view.
   *
   * @return current BufferedImage.
   */
  BufferedImage getImage();

  /**
   * Adds feature callbacks to the view.
   *
   * @param features features to be added to the view.
   */
  void addFeatures(Features features);

  /**
   * Retrieves the compression percentage set by the user.
   *
   * @return compression percentage.
   */
  float getCompressionPercentage();

  /**
   * Retrieves the level adjustments set by the user.
   *
   * @return black, mid and white values of level adjustments.
   */
  String[] getLevelAdjustments();

  /**
   * Saves the image currently present on the screen.
   */
  void saveImage();

  /**
   * Retrieves the percentage value for splitting the image.
   *
   * @return split percentage.
   */
  int getSplitPercentage();
}
