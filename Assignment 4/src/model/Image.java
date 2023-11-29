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

  /**
   * Retrieves a histogram of the image.
   *
   * @return an image representing the histogram.
   */
  Image getHistogram();

  /**
   * Performs color correction on the image.
   *
   * @return a color corrected image.
   */
  Image colorCorrect();

  /**
   * Adjust levels of the image.
   *
   * @param black the black point value for adjustment.
   * @param mid   the midtone point value for adjustment.
   * @param white the white point value for adjustment.
   * @return an adjusted levels image.
   */
  Image adjustLevels(String black, String mid, String white);

  /**
   * Compress the image with the given percentage.
   *
   * @param percentage the percentage to determine the threshold.
   * @return the compressed image.
   */
  Image compress(float percentage);

  /**
   * Applies a filter to a portion of the image.
   *
   * @param kernel          the kernel to apply as filter.
   * @param splitPercentage the percentage of image to apply the filter on.
   * @return an image after filter applied to a portion.
   */
  Image filterSplit(float[][] kernel, Integer splitPercentage);

  /**
   * Applies a linear transformation to a portion of the image.
   *
   * @param mat             the matrix used for transformation.
   * @param splitPercentage the percentage of image to apply the transformation on.
   * @return an image after transformation applied to a portion.
   */
  Image linearTransformWithSplit(float[][] mat, Integer splitPercentage);

  /**
   * Applies a color correction to a portion of the image.
   *
   * @param splitPercentage the percentage of image to apply the color correction on.
   * @return an image after color correction applied to a portion.
   */
  Image colorCorrectWithSplit(Integer splitPercentage);

  /**
   * Applies an adjust levels to a portion of the image.
   *
   * @param black           the black point value for adjustment.
   * @param mid             the midtone point value for adjustment.
   * @param white           the white point value for adjustment.
   * @param splitPercentage the percentage of image to apply the adjust levels on.
   * @return an image after levels adjust correction applied to a portion.
   */
  Image levelAdjustWithSplit(String black, String mid, String white, Integer splitPercentage);

  Image lumaWithSplit(Integer splitPercentage);
}
