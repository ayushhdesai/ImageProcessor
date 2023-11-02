import org.junit.Before;
import org.junit.Test;


import model.ColorImage;
import model.ColorPixel;
import model.GreyscaleImage;
import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Junit test file for ColorImage class.
 */
public class ColorImageTest {

  private ColorImage testImage;
  private ColorPixel[][] samplePixels;

  @Before
  public void setUp() {
    samplePixels = new ColorPixel[][]{
            {new ColorPixel(255, 0, 0), new ColorPixel(0, 255,
                    0), new ColorPixel(0, 0, 255)},
            {new ColorPixel(128, 128, 128), new ColorPixel(255, 255,
                    255), new ColorPixel(0, 0, 0)},
            {new ColorPixel(85, 170, 255), new ColorPixel(170, 85,
                    0), new ColorPixel(255, 255, 0)}
    };
    testImage = new ColorImage(samplePixels);
  }

  @Test(expected = IllegalStateException.class)
  public void testVisualizeRedComponentWithNullPixels() {
    ColorImage imageWithNull = new ColorImage(null);
    imageWithNull.visualizeRedComponent();
  }

  @Test
  public void testVisualizeRedComponent() {
    ColorImage redComponent = (ColorImage) testImage.visualizeRedComponent();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(samplePixels[i][j].getRedValue(),
                redComponent.getPixels()[i][j].getRedValue());
        assertEquals(0, redComponent.getPixels()[i][j].getGreenValue());
        assertEquals(0, redComponent.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testVisualizeGreenComponentWithNullPixels() {
    ColorImage imageWithNull = new ColorImage(null);
    imageWithNull.visualizeGreenComponent();
  }

  @Test
  public void testVisualizeGreenComponent() {
    ColorImage greenComponent = (ColorImage) testImage.visualizeGreenComponent();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(0, greenComponent.getPixels()[i][j].getRedValue());
        assertEquals(samplePixels[i][j].getGreenValue(),
                greenComponent.getPixels()[i][j].getGreenValue());
        assertEquals(0, greenComponent.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testVisualizeBlueComponentWithNullPixels() {
    ColorImage imageWithNull = new ColorImage(null);
    imageWithNull.visualizeBlueComponent();
  }

  @Test
  public void testVisualizeBlueComponent() {
    ColorImage blueComponent = (ColorImage) testImage.visualizeBlueComponent();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(0, blueComponent.getPixels()[i][j].getRedValue());
        assertEquals(0, blueComponent.getPixels()[i][j].getGreenValue());
        assertEquals(samplePixels[i][j].getBlueValue(),
                blueComponent.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testGetValue() {
    Image greyImage = testImage.getValue();
    Pixel[][] pixels = greyImage.getPixels();
    assertEquals(255, pixels[0][0].getRedValue());
    assertEquals(255, pixels[0][1].getRedValue());
    assertEquals(255, pixels[0][2].getRedValue());
    assertEquals(128, pixels[1][0].getRedValue());
    assertEquals(255, pixels[1][1].getRedValue());
  }

  @Test
  public void testGetIntensity() {
    Image greyImage = testImage.getIntensity();
    Pixel[][] pixels = greyImage.getPixels();
    assertEquals(85, pixels[0][0].getRedValue());
  }

  @Test
  public void testGetLuma() {
    Image greyImage = testImage.getLuma();
    Pixel[][] pixels = greyImage.getPixels();
    assertEquals(54, pixels[0][0].getRedValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCombineChannelWithInvalidInput() {
    testImage.combineChannel(testImage, testImage, testImage);
  }

  @Test
  public void testCombineChannel() {
    Image redChannel = testImage.getRedChannel();
    Image greenChannel = testImage.getGreenChannel();
    Image blueChannel = testImage.getBlueChannel();
    Image combined = testImage.combineChannel(redChannel, greenChannel, blueChannel);
    Pixel[][] pixels = combined.getPixels();
    assertEquals(255, pixels[0][0].getRedValue());
    assertEquals(0, pixels[0][0].getGreenValue());
    assertEquals(0, pixels[0][0].getBlueValue());
  }

  @Test
  public void testVerticalFlip() {
    Image flippedImage = testImage.verticalFlip();
    assertEquals(samplePixels[0][2], flippedImage.getPixels()[0][0]);
    assertEquals(samplePixels[1][1], flippedImage.getPixels()[1][1]);
    assertEquals(samplePixels[2][0], flippedImage.getPixels()[2][2]);
  }

  @Test(expected = NullPointerException.class)
  public void testVerticalFlipWithNullPixels() {
    ColorImage imageWithNull = new ColorImage(null);
    imageWithNull.verticalFlip();
  }

  @Test
  public void testHorizontalFlip() {
    Image flippedImage = testImage.horizontalFlip();
    assertEquals(samplePixels[2][0], flippedImage.getPixels()[0][0]);
    assertEquals(samplePixels[1][1], flippedImage.getPixels()[1][1]);
    assertEquals(samplePixels[0][2], flippedImage.getPixels()[2][2]);
  }

  @Test(expected = NullPointerException.class)
  public void testHorizontalFlipWithNullPixels() {
    ColorImage imageWithNull = new ColorImage(null);
    imageWithNull.horizontalFlip();
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
        assertEquals(expectedRedValues[i][j], brightenedImage.getPixels()[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j], brightenedImage.getPixels()[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j], brightenedImage.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testBrightenVertical() {
    int alpha = 50;
    ColorImage brightenedImage = (ColorImage) testImage.brighten(alpha);
    ColorImage verticalBrightenImg = (ColorImage) brightenedImage.verticalFlip();

    int[][] expectedRedValues = {
            {50, 50, 255},
            {50, 255, 178},
            {255, 220, 135}
    };

    int[][] expectedGreenValues = {
            {50, 255, 50},
            {50, 255, 178},
            {255, 135, 220}
    };

    int[][] expectedBlueValues = {
            {255, 50, 50},
            {50, 255, 178},
            {50, 50, 255}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedRedValues[i][j],
                verticalBrightenImg.getPixels()[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j],
                verticalBrightenImg.getPixels()[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j],
                verticalBrightenImg.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testBrightenVerticalHorizontal() {
    int alpha = 50;
    ColorImage brightenedImage = (ColorImage) testImage.brighten(alpha);
    ColorImage verticalBrightenImg = (ColorImage) brightenedImage.verticalFlip();
    ColorImage vbhImg = (ColorImage) verticalBrightenImg.horizontalFlip();

    int[][] expectedRedValues = {
            {255, 220, 135},
            {50, 255, 178},
            {50, 50, 255}
    };

    int[][] expectedGreenValues = {
            {255, 135, 220},
            {50, 255, 178},
            {50, 255, 50}
    };

    int[][] expectedBlueValues = {
            {50, 50, 255},
            {50, 255, 178},
            {255, 50, 50}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedRedValues[i][j], vbhImg.getPixels()[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j], vbhImg.getPixels()[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j], vbhImg.getPixels()[i][j].getBlueValue());
      }
    }
  }


  @Test
  public void testLinearTransform() {
    float[][] mat = {
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

        expectedRedValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[0][0] * oldRed + mat[0][1] * oldGreen + mat[0][2] * oldBlue));
        expectedGreenValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[1][0] * oldRed + mat[1][1] * oldGreen + mat[1][2] * oldBlue));
        expectedBlueValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[2][0] * oldRed + mat[2][1] * oldGreen + mat[2][2] * oldBlue));
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedRedValues[i][j], transformedImage.getPixels()[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j], transformedImage.getPixels()[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j], transformedImage.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testLinearTransformSepia() {
    float[][] mat = {
            {0.393F, 0.769F, 0.189F},
            {0.349F, 0.686F, 0.186F},
            {0.272F, 0.534F, 0.131F}
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

        expectedRedValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[0][0] * oldRed + mat[0][1] * oldGreen + mat[0][2] * oldBlue));
        expectedGreenValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[1][0] * oldRed + mat[1][1] * oldGreen + mat[1][2] * oldBlue));
        expectedBlueValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[2][0] * oldRed + mat[2][1] * oldGreen + mat[2][2] * oldBlue));
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedRedValues[i][j], transformedImage.getPixels()[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j], transformedImage.getPixels()[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j], transformedImage.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testLinearTransformGreyscale() {
    float[][] mat = {
            {0.2126F, 0.7152F, 0.0722F},
            {0.2126F, 0.7152F, 0.0722F},
            {0.2126F, 0.7152F, 0.0722F}
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

        expectedRedValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[0][0] * oldRed + mat[0][1] * oldGreen + mat[0][2] * oldBlue));
        expectedGreenValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[1][0] * oldRed + mat[1][1] * oldGreen + mat[1][2] * oldBlue));
        expectedBlueValues[i][j] = (int) Math.min(255,
                Math.max(0, mat[2][0] * oldRed + mat[2][1] * oldGreen + mat[2][2] * oldBlue));
      }
    }

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedRedValues[i][j], transformedImage.getPixels()[i][j].getRedValue());
        assertEquals(expectedGreenValues[i][j], transformedImage.getPixels()[i][j].getGreenValue());
        assertEquals(expectedBlueValues[i][j], transformedImage.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterWithEvenKernel() {
    float[][] kernel = {
            {1, 1},
            {1, 1}
    };
    testImage.filter(kernel);
  }

  @Test
  public void testFilter() {
    float[][] identityKernel = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };

    ColorImage resultImage = (ColorImage) testImage.filter(identityKernel);

    Pixel[][] expectedPixels = {
            {new ColorPixel(255, 0, 0), new ColorPixel(0, 255, 0), new ColorPixel(0, 0, 255)},
            {new ColorPixel(128, 128, 128), new ColorPixel(255, 255, 255), new ColorPixel(0, 0, 0)},
            {new ColorPixel(85, 170, 255), new ColorPixel(170, 85, 0), new ColorPixel(255, 255, 0)}
    };

    for (int i = 0; i < expectedPixels.length; i++) {
      for (int j = 0; j < expectedPixels[i].length; j++) {
        assertEquals(expectedPixels[i][j].getRedValue(),
                resultImage.getPixels()[i][j].getRedValue());
        assertEquals(expectedPixels[i][j].getGreenValue(),
                resultImage.getPixels()[i][j].getGreenValue());
        assertEquals(expectedPixels[i][j].getBlueValue(),
                resultImage.getPixels()[i][j].getBlueValue());
      }
    }
  }

  @Test
  public void testFilterWithBlurKernel() {
    float[][] blurKernel = {
            {1f / 9f, 1f / 9f, 1f / 9f},
            {1f / 9f, 1f / 9f, 1f / 9f},
            {1f / 9f, 1f / 9f, 1f / 9f}
    };

    ColorImage resultImage = (ColorImage) testImage.filter(blurKernel);

    Pixel middlePixel = resultImage.getPixels()[1][1];

    assertEquals(125, middlePixel.getRedValue());
    assertEquals(125, middlePixel.getGreenValue());
    assertEquals(98, middlePixel.getBlueValue());
  }

  @Test
  public void testFilterWithSharpenKernel() {
    float[][] kernel1 = {
            {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F},
            {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
            {-0.125F, 0.25F, 1F, 0.25F, -0.125F},
            {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
            {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F}
    };

    ColorImage resultImage = (ColorImage) testImage.filter(kernel1);

    Pixel middlePixel = resultImage.getPixels()[1][1];

    assertEquals(476, middlePixel.getRedValue());
    assertEquals(476, middlePixel.getGreenValue());
    assertEquals(413, middlePixel.getBlueValue());
  }

  @Test
  public void testGetRedChannel() {
    Pixel[][] pixels = {
            {new ColorPixel(255, 100, 50), new ColorPixel(10, 20, 30)},
            {new ColorPixel(40, 50, 60), new ColorPixel(70, 80, 90)}
    };

    ColorImage img = new ColorImage(pixels);

    Image redChannel = img.getRedChannel();

    assertTrue(redChannel instanceof GreyscaleImage);

    int[][] expectedRedValues = {
            {255, 10},
            {40, 70}
    };
    Pixel[][] redPixels = redChannel.getPixels();
    for (int i = 0; i < redPixels.length; i++) {
      for (int j = 0; j < redPixels[i].length; j++) {
        assertEquals(expectedRedValues[i][j], redPixels[i][j].getGreenValue());
      }
    }
  }

  @Test
  public void testGetBlueChannel() {
    Pixel[][] pixels = {
            {new ColorPixel(255, 100, 50), new ColorPixel(10, 20, 30)},
            {new ColorPixel(40, 50, 60), new ColorPixel(70, 80, 90)}
    };

    ColorImage img = new ColorImage(pixels);

    Image blueChannel = img.getBlueChannel();

    assertTrue(blueChannel instanceof GreyscaleImage);

    int[][] expectedBlueValues = {
            {50, 30},
            {60, 90}
    };
    Pixel[][] bluePixels = blueChannel.getPixels();
    for (int i = 0; i < bluePixels.length; i++) {
      for (int j = 0; j < bluePixels[i].length; j++) {
        assertEquals(expectedBlueValues[i][j], bluePixels[i][j].getGreenValue());
      }
    }
  }

  @Test
  public void testGetGreenChannel() {
    Pixel[][] pixels = {
            {new ColorPixel(255, 100, 50), new ColorPixel(10, 20, 30)},
            {new ColorPixel(40, 50, 60), new ColorPixel(70, 80, 90)}
    };

    ColorImage img = new ColorImage(pixels);

    Image greenChannel = img.getGreenChannel();

    assertTrue(greenChannel instanceof GreyscaleImage);

    int[][] expectedGreenValues = {
            {100, 20},
            {50, 80}
    };
    Pixel[][] greenPixels = greenChannel.getPixels();
    for (int i = 0; i < greenPixels.length; i++) {
      for (int j = 0; j < greenPixels[i].length; j++) {
        assertEquals(expectedGreenValues[i][j], greenPixels[i][j].getGreenValue());
      }
    }
  }

}
