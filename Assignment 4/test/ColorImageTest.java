import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ColorImageTest {

  private ColorImage testImage;
  private ColorPixel[][] samplePixels;

  @Before
  public void setUp() {
    samplePixels = new ColorPixel[][]{
            {new ColorPixel(255, 0, 0), new ColorPixel(0, 255, 0), new ColorPixel(0, 0, 255)},
            {new ColorPixel(128, 128, 128), new ColorPixel(255, 255, 255), new ColorPixel(0, 0, 0)},
            {new ColorPixel(85, 170, 255), new ColorPixel(170, 85, 0), new ColorPixel(255, 255, 0)}
    };
    testImage = new ColorImage(samplePixels);
  }

  @Test
  public void testVisualizeRedComponent() {
    ColorImage redComponent = (ColorImage) testImage.visualizeRedComponent();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(samplePixels[i][j].getRedValue(), redComponent.pixels[i][j].getRedValue());
        assertEquals(0, redComponent.pixels[i][j].getGreenValue());
        assertEquals(0, redComponent.pixels[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testVisualizeGreenComponent() {
    ColorImage greenComponent = (ColorImage) testImage.visualizeGreenComponent();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(0, greenComponent.pixels[i][j].getRedValue());
        assertEquals(samplePixels[i][j].getGreenValue(), greenComponent.pixels[i][j].getGreenValue());
        assertEquals(0, greenComponent.pixels[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testVisualizeBlueComponent() {
    ColorImage blueComponent = (ColorImage) testImage.visualizeBlueComponent();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(0, blueComponent.pixels[i][j].getRedValue());
        assertEquals(0, blueComponent.pixels[i][j].getGreenValue());
        assertEquals(samplePixels[i][j].getBlueValue(), blueComponent.pixels[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testCombineChannel() {
    ColorImage redComponent = (ColorImage) testImage.visualizeRedComponent();
    ColorImage greenComponent = (ColorImage) testImage.visualizeGreenComponent();
    ColorImage blueComponent = (ColorImage) testImage.visualizeBlueComponent();

    ColorImage combinedImage = (ColorImage) testImage.combineChannel(redComponent, greenComponent, blueComponent);

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(samplePixels[i][j].getRedValue(), combinedImage.pixels[i][j].getRedValue());
        assertEquals(samplePixels[i][j].getGreenValue(), combinedImage.pixels[i][j].getGreenValue());
        assertEquals(samplePixels[i][j].getBlueValue(), combinedImage.pixels[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testHorizontalFlip() {
    ColorImage flippedImage = (ColorImage) testImage.horizontalFlip();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(samplePixels[i][2 - j].getRedValue(), flippedImage.pixels[i][j].getRedValue());
        assertEquals(samplePixels[i][2 - j].getGreenValue(), flippedImage.pixels[i][j].getGreenValue());
        assertEquals(samplePixels[i][2 - j].getBlueValue(), flippedImage.pixels[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testVerticalFlip() {
    ColorImage flippedImage = (ColorImage) testImage.verticalFlip();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(samplePixels[2 - i][j].getRedValue(), flippedImage.pixels[i][j].getRedValue());
        assertEquals(samplePixels[2 - i][j].getGreenValue(), flippedImage.pixels[i][j].getGreenValue());
        assertEquals(samplePixels[2 - i][j].getBlueValue(), flippedImage.pixels[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testBrighten() {
    int alpha = 50;
    ColorImage brightenedImage = (ColorImage) testImage.brighten(alpha);

    int[][] expectedRedValues = {
            {255, 50, 50},
            {178, 255, 50},
            {135, 220, 255}
    };

    int[][] expectedGreenValues = {
            {50, 255, 50},
            {178, 255, 50},
            {220, 135, 255}
    };

    int[][] expectedBlueValues = {
            {50, 50, 255},
            {178, 255, 50},
            {255, 50, 50}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedRedValues[i][j], brightenedImage.pixels[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j], brightenedImage.pixels[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j], brightenedImage.pixels[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testLinearTransform() {
    int[][] mat = {
            {2, 1, 0},
            {0, 0, 1},
            {1, 0, 2}
    };

    ColorImage transformedImage = (ColorImage) testImage.linearTransform(mat);

    int[][] expectedRedValues = new int[3][3];
    int[][] expectedGreenValues = new int[3][3];
    int[][] expectedBlueValues = new int[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int oldRed = samplePixels[i][j].getRedValue();
        int oldGreen = samplePixels[i][j].getGreenValue();
        int oldBlue = samplePixels[i][j].getBlueValue();

        expectedRedValues[i][j] = Math.min(255, Math.max(0, mat[0][0] * oldRed + mat[0][1] * oldGreen + mat[0][2] * oldBlue));
        expectedGreenValues[i][j] = Math.min(255, Math.max(0, mat[1][0] * oldRed + mat[1][1] * oldGreen + mat[1][2] * oldBlue));
        expectedBlueValues[i][j] = Math.min(255, Math.max(0, mat[2][0] * oldRed + mat[2][1] * oldGreen + mat[2][2] * oldBlue));
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedRedValues[i][j], transformedImage.pixels[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j], transformedImage.pixels[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j], transformedImage.pixels[i][j].getBlueValue());
      }
    }
  }

}
