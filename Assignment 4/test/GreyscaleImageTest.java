import org.junit.Before;
import org.junit.Test;

import model.GreyPixel;
import model.GreyscaleImage;
import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Junit test file for GreyscaleImage class.
 */
public class GreyscaleImageTest {

  private GreyscaleImage greyscaleImage;
  private Pixel[][] samplePixels;

  @Before
  public void setUp() {
    samplePixels = new Pixel[][]{
            {new GreyPixel(10), new GreyPixel(20), new GreyPixel(30)},
            {new GreyPixel(40), new GreyPixel(50), new GreyPixel(60)},
            {new GreyPixel(70), new GreyPixel(80), new GreyPixel(90)}
    };
    greyscaleImage = new GreyscaleImage(samplePixels);
  }

  @Test
  public void testVisualizeRedComponent() {
    Image redCompImage = greyscaleImage.visualizeRedComponent();
    for (int i = 0; i < samplePixels.length; i++) {
      for (int j = 0; j < samplePixels[i].length; j++) {
        assertEquals(samplePixels[i][j].getRedValue(), redCompImage.getPixels()[i][j].getRedValue());
      }
    }
  }


}