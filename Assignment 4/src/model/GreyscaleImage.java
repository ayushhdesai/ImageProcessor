package model;

/**
 * This class represents a Greyscale Image.
 * It is a special case where red, blue and green components are the same.
 */
public class GreyscaleImage extends ColorImage {

  /**
   * Constructs a GreyscaleImage object using a 2D array of pixels.
   *
   * @param pixels a 2D array representing the pixels of the image.
   */
  public GreyscaleImage(Pixel[][] pixels) {
    super(pixels);
  }
}
