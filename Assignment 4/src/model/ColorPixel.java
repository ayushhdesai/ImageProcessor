package model;

/**
 * This class represents a color pixel with red, green, and blue components.
 */
public class ColorPixel implements Pixel {
  protected int red;
  protected int green;
  protected int blue;

  /**
   * Constructs a ColorPixel object with specified red, green, and blue values.
   *
   * @param red   the red component.
   * @param green the green component.
   * @param blue  the blue component.
   */
  public ColorPixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRedValue() {
    return red;
  }

  @Override
  public int getGreenValue() {
    return green;
  }

  @Override
  public int getBlueValue() {
    return blue;
  }
}
