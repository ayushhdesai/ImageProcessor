import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

    GreyscaleImage redCompGreyscale = (GreyscaleImage) redCompImage;
    for (int i = 0; i < samplePixels.length; i++) {
      for (int j = 0; j < samplePixels[i].length; j++) {
        assertEquals(samplePixels[i][j].getRedValue(), ((GreyPixel) redCompGreyscale.pixels[i][j]).getRedValue());
      }
    }
  }

  @Test
  public void testVisualizeBlueComponent() {
    Image blueCompImage = greyscaleImage.visualizeBlueComponent();

    GreyscaleImage blueCompGreyscale = (GreyscaleImage) blueCompImage;
    for (int i = 0; i < samplePixels.length; i++) {
      for (int j = 0; j < samplePixels[i].length; j++) {
        assertEquals(samplePixels[i][j].getRedValue(), ((GreyPixel) blueCompGreyscale.pixels[i][j]).getRedValue());
      }
    }
  }

  @Test
  public void testVisualizeGreenComponent() {
    Image greenCompImage = greyscaleImage.visualizeGreenComponent();

    GreyscaleImage greenCompGreyscale = (GreyscaleImage) greenCompImage;
    for (int i = 0; i < samplePixels.length; i++) {
      for (int j = 0; j < samplePixels[i].length; j++) {
        assertEquals(samplePixels[i][j].getRedValue(), ((GreyPixel) greenCompGreyscale.pixels[i][j]).getRedValue());
      }
    }
  }

}