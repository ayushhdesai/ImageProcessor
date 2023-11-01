package model;

/**
 * This interface represents a Pixel in an Image.
 */
public interface Pixel {

  /**
   * Retrieves the red component value of the pixel.
   *
   * @return the red component value.
   */
  public int getRedValue();

  /**
   * Retrieves the green component value of the pixel.
   *
   * @return the green component value.
   */
  public int getGreenValue();

  /**
   * Retrieves the blue component value of the pixel.
   *
   * @return the blue component value.
   */
  public int getBlueValue();
}
