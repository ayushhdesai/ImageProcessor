package model;

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import controller.ImageController;

/**
 * This class represents a color image with pixels arranged in 2D array.
 */
public class ColorImage implements Image {

  protected Pixel[][] pixels;
  private int originalWidth;
  private int originalHeight;

  /**
   * Constructor that initialises the image with the give pixels.
   *
   * @param pixels the 2D array of pixels to set an image.
   */
  public ColorImage(Pixel[][] pixels) {
    this.pixels = pixels;
  }

  /**
   * Retrieves the pixels of the image.
   *
   * @return The 2D array of pixels.
   */
  public Pixel[][] getPixels() {
    return this.pixels;
  }

  private int[] getRedFrequencyMap() {
    Image redChannel = this.getRedChannel();
    Pixel[][] redPixels = redChannel.getPixels();

    int[] redChannelMap = new int[256];

    for (int i = 0; i < redPixels.length; i++) {
      for (int j = 0; j < redPixels[i].length; j++) {
        redChannelMap[redPixels[i][j].getRedValue()]++;
      }
    }

    return redChannelMap;

  }

  private int[] getGreenFrequencyMap() {
    Image greenChannel = this.getGreenChannel();
    Pixel[][] greenPixels = greenChannel.getPixels();

    int[] greenChannelMap = new int[256];

    for (int i = 0; i < greenPixels.length; i++) {
      for (int j = 0; j < greenPixels[i].length; j++) {

        greenChannelMap[greenPixels[i][j].getGreenValue()]++;
      }
    }

    return greenChannelMap;

  }

  private int[] getBlueFrequencyMap() {
    Image blueChannel = this.getBlueChannel();
    Pixel[][] bluePixels = blueChannel.getPixels();

    int[] blueChannelMap = new int[256];

    for (int i = 0; i < bluePixels.length; i++) {
      for (int j = 0; j < bluePixels[i].length; j++) {


        blueChannelMap[bluePixels[i][j].getBlueValue()]++;
      }
    }

    return blueChannelMap;

  }

  @Override
  public Image getHistogram() {

    int[] redChannelMap = getRedFrequencyMap();
    int[] greenChannelMap = getGreenFrequencyMap();
    int[] blueChannelMap = getBlueFrequencyMap();

    int maxWidth = 256;
    int maxHeight = 256;

    double maxRedValue = getMaxValue(redChannelMap);
    double maxGreenValue = getMaxValue(greenChannelMap);
    double maxBlueValue = getMaxValue(blueChannelMap);

    double maxFrequency = Math.max(Math.max(maxRedValue, maxGreenValue), maxBlueValue);

    double xScale = (maxWidth - 50.0) / Math.max(redChannelMap.length, Math.max(greenChannelMap.length,
            blueChannelMap.length)) * 1.25;
    double yScale = (maxHeight - 50.0) / maxFrequency * 1.25;


    BufferedImage image = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = image.createGraphics();


    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, maxWidth, maxHeight);
    drawGrid(graphics, xScale, yScale);

    graphics.setColor(Color.RED);
    drawLineGraph(graphics, redChannelMap, xScale, yScale, maxWidth, maxHeight);


    graphics.setColor(Color.GREEN);
    drawLineGraph(graphics, greenChannelMap, xScale, yScale, maxWidth, maxHeight);


    graphics.setColor(Color.BLUE);
    drawLineGraph(graphics, blueChannelMap, xScale, yScale, maxWidth, maxHeight);


    return new ColorImage(ImageController.convertToPixels(image));
  }

  @Override
  public Image colorCorrect() {
    int[] redChannelMap = getRedFrequencyMap();
    int[] greenChannelMap = getGreenFrequencyMap();
    int[] blueChannelMap = getBlueFrequencyMap();

    Image redChannel = this.getRedChannel();
    Image greenChannel = this.getGreenChannel();
    Image blueChannel = this.getBlueChannel();

    int redValueForMaxFrequency = findMaxFrequencyValue(redChannelMap);
    int greenValueForMaxFrequency = findMaxFrequencyValue(greenChannelMap);
    int blueValueForMaxFrequency = findMaxFrequencyValue(blueChannelMap);

    int avgValue = (redValueForMaxFrequency + greenValueForMaxFrequency + blueValueForMaxFrequency) / 3;

    int redOffset = avgValue - redValueForMaxFrequency;
    int greenOffset = avgValue - greenValueForMaxFrequency;
    int blueOffset = avgValue - blueValueForMaxFrequency;

    Pixel[][] redPixels = redChannel.getPixels();
    Pixel[][] greenPixels = greenChannel.getPixels();
    Pixel[][] bluePixels = blueChannel.getPixels();

    adjustChannel(redPixels, redOffset);
    adjustChannel(greenPixels, greenOffset);
    adjustChannel(bluePixels, blueOffset);

    return combineChannel(new GreyscaleImage(redPixels),
            new GreyscaleImage(greenPixels), new GreyscaleImage(bluePixels));
  }

  @Override
  public Image adjustLevels(String black, String mid, String white) {
    int b = Integer.parseInt(black);
    int m = Integer.parseInt(mid);
    int w = Integer.parseInt(white);

    if (b >= m || m >= w || b < 0 || b > 255 || m < 0 || m > 255 || w < 0 || w > 255) {
      throw new IllegalArgumentException("b m w values invalid!");
    }

    Image redChannel = this.getRedChannel();
    Image greenChannel = this.getGreenChannel();
    Image blueChannel = this.getBlueChannel();
    Pixel[][] redPixels = redChannel.getPixels();
    Pixel[][] greenPixels = greenChannel.getPixels();
    Pixel[][] bluePixels = blueChannel.getPixels();

    int A = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    int Aa = -b * (-127) + 128 * w - 255 * m;
    int Ab = b * b * (-127) + 255 * m * m - 128 * w * w;
    int Ac = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);

    float a = (float) Aa / A;
    float bb = (float) Ab / A;
    float c = (float) Ac / A;

    for (int i = 0; i < redPixels.length; i++) {
      for (int j = 0; j < redPixels[i].length; j++) {
        int xr = redPixels[i][j].getRedValue();
        int xg = greenPixels[i][j].getGreenValue();
        int xb = bluePixels[i][j].getBlueValue();
        redPixels[i][j] = new GreyPixel((int) Math.max(0, Math.min(255, (a * xr * xr + bb * xr + c))));
        greenPixels[i][j] = new GreyPixel((int) Math.max(0, Math.min(255, (a * xg * xg + bb * xg + c))));
        bluePixels[i][j] = new GreyPixel((int) Math.max(0, Math.min(255, (a * xb * xb + bb * xb + c))));
      }
    }

    return combineChannel(new GreyscaleImage(redPixels),
            new GreyscaleImage(greenPixels), new GreyscaleImage(bluePixels));

  }

  private int findMaxFrequencyValue(int[] channelMap) {
    int maxFrequency = Integer.MIN_VALUE;
    int valueForMaxFrequency = -1;

    for (int i = 0; i < channelMap.length; i++) {
      if (channelMap[i] > maxFrequency) {
        maxFrequency = channelMap[i];
        valueForMaxFrequency = i;
      }
    }

    return valueForMaxFrequency;
  }

  private void adjustChannel(Pixel[][] pixels, int offset) {
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        pixels[i][j] = new GreyPixel(
                Math.min(255, Math.max(pixels[i][j].getRedValue() + offset, 0)));
      }
    }
  }

  private void drawLineGraph(Graphics2D graphics, int[] channelMap,
                             double xScale, double yScale, int maxWidth, int maxHeight) {

    for (int i = 0; i < channelMap.length - 1; i++) {
      double y1 = channelMap[i] * yScale;
      double y2 = channelMap[i + 1] * yScale;

      graphics.drawLine(i, (int) (256 - y1), i + 1, (int) (256 - y2));
    }
  }


  private void drawGrid(Graphics2D graphics, double xScale, double yScale) {
    graphics.setColor(Color.LIGHT_GRAY);
    int maxWidth = 256;
    int maxHeight = 256;


    for (int i = 0; i < maxWidth; i += 10) {
      graphics.drawLine(i, 0, i, maxHeight);
    }

    for (int i = 0; i < maxHeight; i += 10) {
      graphics.drawLine(0, i, maxWidth, i);
    }

    graphics.setColor(Color.BLACK);
    graphics.drawLine(0, maxHeight, maxWidth, maxHeight);
    graphics.drawLine(0, 0, 0, maxHeight);
  }

  private static int getMaxValue(int[] data) {
    int maxValue = Integer.MIN_VALUE;
    for (int value : data) {
      if (value > maxValue) {
        maxValue = value;
      }
    }
    return maxValue;
  }


  @Override
  public Image visualizeRedComponent() {
    if (this.pixels == null) {
      throw new IllegalStateException("Pixels array is null");
    }
    Pixel[][] redComponentPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        redComponentPixels[i][j] =
                new ColorPixel(this.pixels[i][j].getRedValue(), 0, 0);
      }
    }

    return new ColorImage(redComponentPixels);
  }

  @Override
  public Image visualizeGreenComponent() {
    if (this.pixels == null) {
      throw new IllegalStateException("Pixels array is null");
    }
    Pixel[][] greenComponentPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        greenComponentPixels[i][j] =
                new ColorPixel(0, this.pixels[i][j].getGreenValue(), 0);
      }
    }

    return new ColorImage(greenComponentPixels);
  }

  @Override
  public Image visualizeBlueComponent() {
    if (this.pixels == null) {
      throw new IllegalStateException("Pixels array is null");
    }
    Pixel[][] blueComponentPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        blueComponentPixels[i][j] =
                new ColorPixel(0, 0, this.pixels[i][j].getBlueValue());
      }
    }

    return new ColorImage(blueComponentPixels);
  }

  @Override
  public Image getValue() {
    Pixel[][] valuePixels = new GreyPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int maxColorValue = Math.max(pixels[i][j].getRedValue(),
                Math.max(pixels[i][j].getGreenValue(), pixels[i][j].getBlueValue()));
        valuePixels[i][j] = new GreyPixel(maxColorValue);
      }
    }

    return new GreyscaleImage(valuePixels);
  }

  @Override
  public Image getIntensity() {
    Pixel[][] intensityPixels = new GreyPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int sumValue = this.pixels[i][j].getRedValue()
                + this.pixels[i][j].getGreenValue() + this.pixels[i][j].getBlueValue();

        int avgColorValue = sumValue / 3;
        intensityPixels[i][j] = new GreyPixel(avgColorValue);
      }
    }

    return new GreyscaleImage(intensityPixels);
  }

  @Override
  public Image getLuma() {
    Pixel[][] lumaPixels = new GreyPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int weightedSum = (int) (this.pixels[i][j].getRedValue() * 0.2126)
                + (int) (this.pixels[i][j].getGreenValue() * 0.7152)
                + (int) (this.pixels[i][j].getBlueValue() * 0.0722);


        lumaPixels[i][j] = new GreyPixel(weightedSum);
      }
    }

    return new GreyscaleImage(lumaPixels);
  }

  @Override
  public Image combineChannel(Image redImage, Image greenImage, Image blueImage) {
    if (!(redImage instanceof GreyscaleImage) || !(greenImage instanceof GreyscaleImage)
            || !(blueImage instanceof GreyscaleImage)) {
      throw new IllegalArgumentException("Component type is not similar.");
    }

    Pixel[][] combinedPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int redValue = redImage.getPixels()[i][j].getRedValue();
        int greenValue = greenImage.getPixels()[i][j].getGreenValue();
        int blueValue = blueImage.getPixels()[i][j].getBlueValue();

        combinedPixels[i][j] = new ColorPixel(redValue, greenValue, blueValue);
      }
    }

    return new ColorImage(combinedPixels);
  }


  @Override
  public Image verticalFlip() {
    Pixel[][] flippedPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        // Horizontal flip we switch the columns
        flippedPixels[i][j] = this.pixels[i][pixels[i].length - 1 - j];
      }
    }

    return new ColorImage(flippedPixels);
  }

  @Override
  public Image horizontalFlip() {
    Pixel[][] flippedPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        // Vertical flip we switch the rows
        flippedPixels[i][j] = this.pixels[pixels.length - 1 - i][j];
      }
    }

    return new ColorImage(flippedPixels);
  }

  @Override
  public Image brighten(int alpha) {
    Pixel[][] brightenedPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        // Cannot go beyond 255 because of the range
        int redValue = Math.min(255, Math.max(0, this.pixels[i][j].getRedValue() + alpha));
        int greenValue = Math.min(255, Math.max(0, this.pixels[i][j].getGreenValue() + alpha));
        int blueValue = Math.min(255, Math.max(0, this.pixels[i][j].getBlueValue() + alpha));

        brightenedPixels[i][j] = new ColorPixel(redValue, greenValue, blueValue);
      }
    }

    return new ColorImage(brightenedPixels);
  }


  @Override
  public Image filter(float[][] kernel) {

    if (kernel.length % 2 == 0 || kernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("Kernel cannot have event length");
    }

    int rows = this.getRedChannel().getPixels().length;
    int cols = this.getRedChannel().getPixels()[0].length;

    Pixel[][] resultantRedChannelPixels = this.getRedChannel().getPixels();
    Pixel[][] resultantGreenChannelPixels = this.getGreenChannel().getPixels();
    Pixel[][] resultantBlueChannelPixels = this.getBlueChannel().getPixels();

    Pixel[][] currRedChannelPixels = this.getRedChannel().getPixels();
    Pixel[][] currGreenChannelPixels = this.getGreenChannel().getPixels();
    Pixel[][] currBlueChannelPixels = this.getBlueChannel().getPixels();

    int midrow = kernel.length / 2;
    int midcol = kernel[0].length / 2;

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int sumred = 0;
        int sumgreen = 0;
        int sumblue = 0;
        for (int x = 0; x < kernel.length; x++) {
          for (int y = 0; y < kernel[x].length; y++) {

            int rowIndex = i - midrow + x;
            int colIndex = j - midcol + y;

            if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
              sumred += (int) (currRedChannelPixels[rowIndex][colIndex].getRedValue() * kernel[x][y]);
              sumgreen += (int) (currGreenChannelPixels[rowIndex][colIndex].getGreenValue() * kernel[x][y]);
              sumblue += (int) (currBlueChannelPixels[rowIndex][colIndex].getBlueValue() * kernel[x][y]);
            }
          }
        }

        resultantRedChannelPixels[i][j] = new GreyPixel(sumred);
        resultantGreenChannelPixels[i][j] = new GreyPixel(sumgreen);
        resultantBlueChannelPixels[i][j] = new GreyPixel(sumblue);

      }
    }


    return combineChannel(new GreyscaleImage(resultantRedChannelPixels),
            new GreyscaleImage(resultantGreenChannelPixels),
            new GreyscaleImage(resultantBlueChannelPixels));
  }

  @Override
  public Image linearTransform(float[][] mat) {
    if (mat.length != 3 || mat[0].length != 3) {
      throw new IllegalArgumentException("Improper kernel matrix size");
    }
    Pixel[][] transformedPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int oldRed = this.pixels[i][j].getRedValue();
        int oldGreen = this.pixels[i][j].getGreenValue();
        int oldBlue = this.pixels[i][j].getBlueValue();

        int newRed = (int) (mat[0][0] * oldRed + mat[0][1] * oldGreen + mat[0][2] * oldBlue);
        int newGreen = (int) (mat[1][0] * oldRed + mat[1][1] * oldGreen + mat[1][2] * oldBlue);
        int newBlue = (int) (mat[2][0] * oldRed + mat[2][1] * oldGreen + mat[2][2] * oldBlue);

        // Keep in range
        newRed = Math.min(255, Math.max(0, newRed));
        newGreen = Math.min(255, Math.max(0, newGreen));
        newBlue = Math.min(255, Math.max(0, newBlue));

        transformedPixels[i][j] = new ColorPixel(newRed, newGreen, newBlue);
      }
    }

    return new ColorImage(transformedPixels);
  }


  @Override
  public Image getRedChannel() {
    Pixel[][] greyPixelsForRedChannel = new GreyPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {

        int redValue = this.pixels[i][j].getRedValue();
        greyPixelsForRedChannel[i][j] = new GreyPixel(redValue);
      }
    }

    return new GreyscaleImage(greyPixelsForRedChannel);
  }

  @Override
  public Image getBlueChannel() {
    Pixel[][] greyPixelsForBlueChannel = new GreyPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int blueValue = this.pixels[i][j].getBlueValue();
        greyPixelsForBlueChannel[i][j] = new GreyPixel(blueValue);
      }
    }

    return new GreyscaleImage(greyPixelsForBlueChannel);
  }

  @Override
  public Image getGreenChannel() {
    Pixel[][] greyPixelsForGreenChannel = new GreyPixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        int greenValue = this.pixels[i][j].getGreenValue();
        greyPixelsForGreenChannel[i][j] = new GreyPixel(greenValue);
      }
    }

    return new GreyscaleImage(greyPixelsForGreenChannel);
  }

  @Override
  public Image compress(float percentage) {
    if (percentage < 1 || percentage > 99)  {
      throw new IllegalArgumentException("Percentage should be between 1-99");
    }
    this.padImageToPowerOfTwo();

    this.applyHaarTransform();

    this.applyThresholding(percentage);

    this.invertHaarTransform();

    this.undoImage();

    return this;
  }

  private void padImageToPowerOfTwo() {
    originalWidth = this.pixels[0].length;
    originalHeight = this.pixels.length;

    int maxSize = Math.max(originalWidth, originalHeight);
    int targetSize = nextPowerOfTwo(maxSize);

    Pixel[][] paddedPixels = new Pixel[targetSize][targetSize];

    for (int i = 0; i < targetSize; i++) {
      for (int j = 0; j < targetSize; j++) {
        paddedPixels[i][j] = new ColorPixel(0, 0, 0);
      }
    }

    // Copy original pixels to the new padded array
    for (int i = 0; i < originalHeight; i++) {
      for (int j = 0; j < originalWidth; j++) {
        paddedPixels[i][j] = this.pixels[i][j];
      }
    }

    this.pixels = paddedPixels;
  }

  private int nextPowerOfTwo(int number) {
    int power = 1;
    while (power < number) {
      power *= 2;
    }
    return power;
  }

  private void applyHaarTransform() {
    int n = this.pixels.length;

    // Apply transform to each row
    for (int i = 0; i < n; i++) {
      this.pixels[i] = transformRowOrColumn(this.pixels[i]);
    }

    // Apply transform to each column
    for (int j = 0; j < n; j++) {
      Pixel[] column = new Pixel[n];
      for (int i = 0; i < n; i++) {
        column[i] = this.pixels[i][j];
      }
      column = transformRowOrColumn(column);
      for (int i = 0; i < n; i++) {
        this.pixels[i][j] = column[i];
      }
    }
  }

  private Pixel[] transformRowOrColumn(Pixel[] sequence) {
    int n = sequence.length;
    Pixel[] transformed = new Pixel[n];
    int halfN = n / 2;

    for (int i = 0; i < halfN; i++) {
      Pixel avg = average(sequence[2 * i], sequence[2 * i + 1]);
      Pixel diff = difference(sequence[2 * i], sequence[2 * i + 1]);
      transformed[i] = avg;
      transformed[i + halfN] = diff;
    }

    return transformed;
  }

  private Pixel average(Pixel a, Pixel b) {
    int avgRed = (a.getRedValue() + b.getRedValue()) / 2;
    int avgGreen = (a.getGreenValue() + b.getGreenValue()) / 2;
    int avgBlue = (a.getBlueValue() + b.getBlueValue()) / 2;
    return new ColorPixel(avgRed, avgGreen, avgBlue);
  }

  private Pixel difference(Pixel a, Pixel b) {
    int diffRed = (a.getRedValue() - b.getRedValue()) / 2;
    int diffGreen = (a.getGreenValue() - b.getGreenValue()) / 2;
    int diffBlue = (a.getBlueValue() - b.getBlueValue()) / 2;
    return new ColorPixel(diffRed, diffGreen, diffBlue);
  }

  private void applyThresholding(float percentage) {
    List<Integer> redValues = new ArrayList<>();
    List<Integer> greenValues = new ArrayList<>();
    List<Integer> blueValues = new ArrayList<>();

    // Flatten and store absolute values for each channel
    for (Pixel[] row : this.pixels) {
      for (Pixel pixel : row) {
        redValues.add(Math.abs(pixel.getRedValue()));
        greenValues.add(Math.abs(pixel.getGreenValue()));
        blueValues.add(Math.abs(pixel.getBlueValue()));
      }
    }

    // Calculate thresholds for each channel
    int redThreshold = calculateThreshold(redValues, percentage);
    int greenThreshold = calculateThreshold(greenValues, percentage);
    int blueThreshold = calculateThreshold(blueValues, percentage);

    // Apply thresholding
    for (int i = 0; i < this.pixels.length; i++) {
      for (int j = 0; j < this.pixels[i].length; j++) {
        Pixel currentPixel = this.pixels[i][j];
        int redValue = Math.abs(currentPixel.getRedValue())
                < redThreshold ? 0 : currentPixel.getRedValue();
        int greenValue = Math.abs(currentPixel.getGreenValue())
                < greenThreshold ? 0 : currentPixel.getGreenValue();
        int blueValue = Math.abs(currentPixel.getBlueValue())
                < blueThreshold ? 0 : currentPixel.getBlueValue();
        this.pixels[i][j] = new ColorPixel(redValue, greenValue, blueValue);
      }
    }
  }

  private int calculateThreshold(List<Integer> values, float percentage) {
    int sum = 0;
    for (int value : values) {
      sum += value;
    }

    double mean = sum / (double) values.size();

    double sumOfSquares = 0.0;
    for (int value : values) {
      sumOfSquares += Math.pow(value - mean, 2);
    }

    double stdDev = Math.sqrt(sumOfSquares / values.size());

    double threshold = mean + stdDev * (percentage / 100.0);

    return (int) threshold;
  }


  private void invertHaarTransform() {
    int n = this.pixels.length;

    // Apply inverse transform to each column
    for (int j = 0; j < n; j++) {
      Pixel[] column = new Pixel[n];
      for (int i = 0; i < n; i++) {
        column[i] = this.pixels[i][j];
      }
      column = inverseTransformRowOrColumn(column);
      for (int i = 0; i < n; i++) {
        this.pixels[i][j] = column[i];
      }
    }

    // Apply inverse transform to each row
    for (int i = 0; i < n; i++) {
      this.pixels[i] = inverseTransformRowOrColumn(this.pixels[i]);
    }
  }

  private Pixel[] inverseTransformRowOrColumn(Pixel[] sequence) {
    int n = sequence.length;
    Pixel[] original = new Pixel[n];
    int halfN = n / 2;

    for (int i = 0; i < halfN; i++) {
      Pixel a = sequence[i];
      Pixel d = sequence[i + halfN];
      original[2 * i] = reconstruct(a, d, true);
      original[2 * i + 1] = reconstruct(a, d, false);
    }

    return original;
  }

  private Pixel reconstruct(Pixel avg, Pixel diff, boolean firstElement) {
    int redValue = firstElement
            ? avg.getRedValue() + diff.getRedValue()
            : avg.getRedValue() - diff.getRedValue();
    int greenValue = firstElement
            ? avg.getGreenValue() + diff.getGreenValue()
            : avg.getGreenValue() - diff.getGreenValue();
    int blueValue = firstElement
            ? avg.getBlueValue() + diff.getBlueValue()
            : avg.getBlueValue() - diff.getBlueValue();

    // Clamp the data
    redValue = Math.max(0, Math.min(255, redValue));
    greenValue = Math.max(0, Math.min(255, greenValue));
    blueValue = Math.max(0, Math.min(255, blueValue));

    return new ColorPixel(redValue, greenValue, blueValue);
  }

  private void undoImage() {
    Pixel[][] undoPixels = new Pixel[originalHeight][originalWidth];

    for (int i = 0; i < originalHeight; i++) {
      for (int j = 0; j < originalWidth; j++) {
        undoPixels[i][j] = this.pixels[i][j];
      }
    }

    this.pixels = undoPixels;
  }

  @Override
  public Image filterSplit(float[][] kernel, Integer splitPercentage) {
    Pixel[][] entireFilteredPixels = this.filter(kernel).getPixels();
    return getImage(splitPercentage, entireFilteredPixels);
  }

  @Override
  public Image linearTransformWithSplit(float[][] mat, Integer splitPercentage) {
    if (mat.length != 3 || mat[0].length != 3) {
      throw new IllegalArgumentException("Improper transformation matrix size");
    }

    Pixel[][] allTransformedPixels = this.linearTransform(mat).getPixels();
    return getImage(splitPercentage, allTransformedPixels);
  }

  @Override
  public Image colorCorrectWithSplit(Integer splitPercentage) {
    Pixel[][] allTransformedPixels = this.colorCorrect().getPixels();
    return getImage(splitPercentage, allTransformedPixels);
  }

  @Override
  public Image levelAdjustWithSplit(String black, String mid, String white, Integer splitPercentage) {
    Pixel[][] allTransformedPixels = this.adjustLevels(black, mid, white).getPixels();
    return getImage(splitPercentage, allTransformedPixels);
  }

  private Image getImage(@Nullable Integer splitPercentage, Pixel[][] allTransformedPixels) {
    Pixel[][] resultPixels = new Pixel[this.pixels.length][this.pixels[0].length];

    int splitColumn = (splitPercentage != null) ? (int) (this.pixels[0].length * (splitPercentage / 100.0)) : this.pixels[0].length;

    for (int i = 0; i < this.pixels.length; i++) {
      for (int j = 0; j < this.pixels[i].length; j++) {
        if (splitPercentage != null && j >= splitColumn) {
          resultPixels[i][j] = this.pixels[i][j];
        } else {
          resultPixels[i][j] = allTransformedPixels[i][j];
        }
      }
    }

    if (splitPercentage != null) {
      for (int i = 0; i < this.pixels.length; i++) {
        resultPixels[i][splitColumn] = new ColorPixel(255, 0, 0);
      }
    }

    return new ColorImage(resultPixels);
  }

}
