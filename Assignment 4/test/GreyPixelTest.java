import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.GreyPixel;

/**
 * JUnit test file for GreyPixel class.
 */
public class GreyPixelTest {

  @Test
  public void testColorPixelCreation() {
    GreyPixel pixel = new GreyPixel(100);

    assertEquals(100, pixel.getRedValue());
    assertEquals(100, pixel.getGreenValue());
    assertEquals(100, pixel.getBlueValue());
  }

}