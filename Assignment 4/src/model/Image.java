package model;

/**
 * This interface represents functionalities provided for Image Processing.
 */
public interface Image {

  /**
   * Visualize the red component of the image.
   *
   * @return the image with only the red component.
   */
  Image visualizeRedComponent();

  /**
   * Visualize the blue component of the image.
   *
   * @return the image with only the blue component.
   */
  Image visualizeBlueComponent();

  /**
   * Visualize the green component of the image.
   *
   * @return the image with only the green component.
   */
  Image visualizeGreenComponent();

  /**
   * Retrieves the value of the image.
   *
   * @return image with the given value.
   */
  Image getValue();

  /**
   * Retrieves the intensity of the image.
   *
   * @return image representing the intensity.
   */
  Image getIntensity();

  /**
   * Retrieves the luma of the image.
   *
   * @return image representing the luma.
   */
  Image getLuma();

  /**
   * Combines the specified red, green, and blue images into a single image.
   *
   * @param red   the red channel image.
   * @param green the green channel image.
   * @param blue  the blue channel image.
   * @return resulting combined image.
   */
  Image combineChannel(Image red, Image green, Image blue);

  /**
   * Performs horizontal flip of the image.
   *
   * @return image that is horizontally flipped.
   */
  Image horizontalFlip();

  /**
   * Performs vertical flip of the image.
   *
   * @return image that is vertically flipped.
   */
  Image verticalFlip();

  /**
   * Brightens the image by the given alpha value.
   *
   * @return image that is brightened.
   */
  Image brighten(int alpha);

  /**
   * Applied filter to the image via the given kernel.
   *
   * @param kernel the kernel to apply filter.
   * @return image that is filtered.
   */
  Image filter(float[][] kernel);

  /**
   * Applied linear transform to the image via the given matrix.
   *
   * @param mat the matrix to apply.
   * @return image that is transformed.
   */
  Image linearTransform(float[][] mat);

  /**
   * Retrieves the red channel of the image.
   *
   * @return image representing the red channel.
   */
  Image getRedChannel();

  /**
   * Retrieves the blue channel of the image.
   *
   * @return image representing the blue channel.
   */
  Image getBlueChannel();

  /**
   * Retrieves the green channel of the image.
   *
   * @return image representing the green channel.
   */
  Image getGreenChannel();

  /**
   * Gets the pixel values of the image.
   *
   * @return 2D array of pixels representing the image.
   */
  Pixel[][] getPixels();

  Image getHistogram();

  Image colorCorrect();

  Image adjustLevels(String black, String mid, String white );

}
