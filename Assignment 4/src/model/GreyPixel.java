package model;

/**
 * This class represents a grey pixel.
 */
public class GreyPixel extends ColorPixel {

  /**
   * Constructs a GreyPixel object with specified red, green, and blue values.
   *
   * @param color the red, blue and green component.
   */
  public GreyPixel(int color) {
    super(color, color, color);
  }


}
