import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.ColorPixel;

/**
 * JUnit test file for ColorPixel class.
 */
public class ColorPixelTest {

  @Test
  public void testColorPixelCreation() {
    ColorPixel pixel = new ColorPixel(100, 150, 200);

    assertEquals(100, pixel.getRedValue());
    assertEquals(150, pixel.getGreenValue());
    assertEquals(200, pixel.getBlueValue());
  }

  @Test
  public void testRedValue() {
    ColorPixel pixel = new ColorPixel(255, 0, 0);

    assertEquals(255, pixel.getRedValue());
    assertEquals(0, pixel.getGreenValue());
    assertEquals(0, pixel.getBlueValue());
  }

  @Test
  public void testGreenValue() {
    ColorPixel pixel = new ColorPixel(0, 255, 0);

    assertEquals(0, pixel.getRedValue());
    assertEquals(255, pixel.getGreenValue());
    assertEquals(0, pixel.getBlueValue());
  }

  @Test
  public void testBlueValue() {
    ColorPixel pixel = new ColorPixel(0, 0, 255);

    assertEquals(0, pixel.getRedValue());
    assertEquals(0, pixel.getGreenValue());
    assertEquals(255, pixel.getBlueValue());
  }

}
