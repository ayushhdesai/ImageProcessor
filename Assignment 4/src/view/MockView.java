package view;

import java.awt.image.BufferedImage;

import controller.Features;

/**
 * A mock object of View interface in order to test the Controller class.
 */
public class MockView implements View {
  private boolean displayImageFlag;
  private boolean getImageFlag;

  /**
   * Constructor to create the mock view object.
   */
  public MockView() {
    displayImageFlag = false;
    getImageFlag = false;
  }

  /**
   * Return the boolean value of display image to check whether the view method gets called.
   *
   * @return true or false
   */
  public boolean getDisplayImageFlag() {
    return this.displayImageFlag;
  }


  /**
   * Return the boolean value of get image flag to check whether the view method gets called.
   *
   * @return true or false
   */
  public boolean getImageFlag() {
    return this.getImageFlag;
  }

  @Override
  public void loadImage() {
    /**
     * No implementation required for mocking purpose.
     */
  }

  @Override
  public void displayImage(BufferedImage bufferedImage) {
    displayImageFlag = true;
  }

  @Override
  public void displayHistogram(BufferedImage bufferedImage) {
    /**
     * No implementation required for mocking purpose.
     */
  }

  @Override
  public void displayPreviewImage(BufferedImage image) {
    /**
     * No implementation required for mocking purpose.
     */
  }

  @Override
  public BufferedImage getImage() {
    getImageFlag = true;
    return new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  public void addFeatures(Features features) {
    /**
     * No implementation required for mocking purpose.
     */
  }

  @Override
  public float getCompressionPercentage() {
    return 0;
  }

  @Override
  public String[] getLevelAdjustments() {
    return new String[0];
  }

  @Override
  public void saveImage() {
    /**
     * No implementation required for mocking purpose.
     */
  }

  @Override
  public int getSplitPercentage() {
    return 0;
  }
}
