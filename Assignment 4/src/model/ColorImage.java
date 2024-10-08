package model;


import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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

    double xScale = (maxWidth - 50.0) / Math.max(redChannelMap.length,
            Math.max(greenChannelMap.length, blueChannelMap.length)) * 1.25;
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

    int avgValue = (redValueForMaxFrequency + greenValueForMaxFrequency
            + blueValueForMaxFrequency) / 3;

    int redOffset = avgValue - redValueForMaxFrequency;
    int greenOffset = avgValue - greenValueForMaxFrequency;
    int blueOffset = avgValue - blueValueForMaxFrequency;

    Pixel[][] redPixels = redChannel.getPixels();
    Pixel[][] greenPixels = greenChannel.getPixels();
    Pixel[][] bluePixels = blueChannel.getPixels();

    adjustChannel(redPixels, redOffset);
    adjustChannel(greenPixels, greenOffset);
    adjustChannel(bluePixels, blueOffset);

    return combineChannel(new GreyscaleImage(redPixels), new GreyscaleImage(greenPixels),
            new GreyscaleImage(bluePixels));
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

    int aX = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    int aA = -b * (-127) + 128 * w - 255 * m;
    int bA = b * b * (-127) + 255 * m * m - 128 * w * w;
    int cA = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);

    float a = (float) aA / aX;
    float bb = (float) bA / aX;
    float c = (float) cA / aX;

    for (int i = 0; i < redPixels.length; i++) {
      for (int j = 0; j < redPixels[i].length; j++) {
        int xr = redPixels[i][j].getRedValue();
        int xg = greenPixels[i][j].getGreenValue();
        int xb = bluePixels[i][j].getBlueValue();
        redPixels[i][j] = new GreyPixel((int) Math.max(0,
                Math.min(255, (a * xr * xr + bb * xr + c))));
        greenPixels[i][j] = new GreyPixel((int) Math.max(0,
                Math.min(255, (a * xg * xg + bb * xg + c))));
        bluePixels[i][j] = new GreyPixel((int) Math.max(0,
                Math.min(255, (a * xb * xb + bb * xb + c))));
      }
    }

    return combineChannel(new GreyscaleImage(redPixels), new GreyscaleImage(greenPixels),
            new GreyscaleImage(bluePixels));

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
        pixels[i][j] = new GreyPixel(Math.min(255,
                Math.max(pixels[i][j].getRedValue() + offset, 0)));
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
        redComponentPixels[i][j] = new ColorPixel(this.pixels[i][j].getRedValue(), 0, 0);
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
        greenComponentPixels[i][j] = new ColorPixel(0, this.pixels[i][j].getGreenValue(), 0);
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
        blueComponentPixels[i][j] = new ColorPixel(0, 0, this.pixels[i][j].getBlueValue());
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
    if (!(redImage instanceof GreyscaleImage)
            || !(greenImage instanceof GreyscaleImage) || !(blueImage instanceof GreyscaleImage)) {
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
              sumred += (int) (currRedChannelPixels[rowIndex]
                      [colIndex].getRedValue() * kernel[x][y]);
              sumgreen += (int) (currGreenChannelPixels[rowIndex]
                      [colIndex].getGreenValue() * kernel[x][y]);
              sumblue += (int) (currBlueChannelPixels[rowIndex]
                      [colIndex].getBlueValue() * kernel[x][y]);
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
    if (percentage < 1 || percentage > 99) {
      throw new IllegalArgumentException("Percentage should be between 1-99");
    }

    Image paddedImg = this.padImageToPowerOfTwo();


    Image redChannel = paddedImg.getRedChannel();
    Pixel[][] redPixels = redChannel.getPixels();
    Image greenChannel = paddedImg.getGreenChannel();
    Pixel[][] greenPixels = greenChannel.getPixels();
    Image blueChannel = paddedImg.getBlueChannel();
    Pixel[][] bluePixels = blueChannel.getPixels();

    int n = paddedImg.getPixels().length;

    double[][] redValues = new double[n][n];
    double[][] greenValues = new double[n][n];
    double[][] blueValues = new double[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        redValues[i][j] = redPixels[i][j].getRedValue();
        greenValues[i][j] = greenPixels[i][j].getGreenValue();
        blueValues[i][j] = bluePixels[i][j].getBlueValue();
      }
    }

    double[][] transformedRed = this.haarTransform(redValues, n);
    double[][] transformedGreen = this.haarTransform(greenValues, n);
    double[][] transformedBlue = this.haarTransform(blueValues, n);

    applyThresholding(percentage, transformedRed, transformedGreen, transformedBlue);

    this.invertHaarTransform(transformedRed);
    this.invertHaarTransform(transformedGreen);
    this.invertHaarTransform(transformedBlue);

    return this.undoImage(transformedRed, transformedGreen, transformedBlue);


  }

  private Image padImageToPowerOfTwo() {
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

    for (int i = 0; i < originalHeight; i++) {
      for (int j = 0; j < originalWidth; j++) {
        paddedPixels[i][j] = new ColorPixel(this.pixels[i][j].getRedValue(),
                this.pixels[i][j].getGreenValue(), this.pixels[i][j].getBlueValue());
      }
    }

    return new ColorImage(paddedPixels);
  }

  private int nextPowerOfTwo(int number) {
    int power = 1;
    while (power < number) {
      power *= 2;
    }
    return power;
  }

  private double[][] haarTransform(double[][] inputMatrix, int dimension) {
    int currentDimension = dimension;
    while (currentDimension > 1) {
      for (int row = 0; row < currentDimension; row++) {
        double[] rowSegment = Arrays.copyOfRange(inputMatrix[row], 0, currentDimension);
        double[] processedRow = processRow(rowSegment, currentDimension);
        double[] remainingRow = new double[dimension - currentDimension];
        if (currentDimension != dimension) {
          remainingRow = Arrays.copyOfRange(inputMatrix[row], currentDimension, dimension);
        }
        inputMatrix[row] = arrayMerge(processedRow, currentDimension, remainingRow,
                dimension - currentDimension);
      }
      for (int col = 0; col < currentDimension; col++) {
        double[] columnData = new double[dimension];
        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
          columnData[rowIndex] = inputMatrix[rowIndex][col];
        }

        double[] columnSegment = Arrays.copyOfRange(columnData, 0, currentDimension);
        double[] processedColumn = processRow(columnSegment, currentDimension);
        double[] remainingColumn = new double[dimension - currentDimension];
        if (currentDimension != dimension) {
          remainingColumn = Arrays.copyOfRange(columnData, currentDimension, dimension);
        }
        columnData = arrayMerge(processedColumn, currentDimension, remainingColumn,
                dimension - currentDimension);
        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
          inputMatrix[rowIndex][col] = columnData[rowIndex];
        }
      }
      currentDimension /= 2;
    }
    return inputMatrix;
  }

  private double[] processRow(double[] dataArray, int length) {
    double[] average = new double[length / 2];
    double[] difference = new double[length / 2];

    for (int index = 0; index < length - 1; index += 2) {
      double avgValue = (dataArray[index] + dataArray[index + 1]) / Math.sqrt(2);
      double diffValue = (dataArray[index] - dataArray[index + 1]) / Math.sqrt(2);
      average[index / 2] = Math.round(avgValue * 1000.0) / 1000.0;

      difference[index / 2] = Math.round(diffValue * 1000.0) / 1000.0;
    }
    double[] transformedData = new double[length];

    System.arraycopy(average, 0, transformedData, 0, length / 2);
    System.arraycopy(difference, 0, transformedData, length / 2, length / 2);
    return transformedData;
  }


  private void applyThresholding(float percentage, double[][] transformedRed,
                                 double[][] transformedGreen, double[][] transformedBlue) {
    HashSet<Double> allUniqueValues = new HashSet<>();

    int n = transformedRed.length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        allUniqueValues.add(Math.abs(transformedRed[i][j]));
        allUniqueValues.add(Math.abs(transformedGreen[i][j]));
        allUniqueValues.add(Math.abs(transformedBlue[i][j]));
      }
    }

    List<Double> valuesList = new ArrayList<>(allUniqueValues);

    Collections.sort(valuesList);

    int thresholdIndex = (int) ((percentage / 100) * valuesList.size());
    double thresholdValue = valuesList.get(thresholdIndex - 1);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        transformedRed[i][j] = Math.abs(transformedRed[i][j]) <= thresholdValue
                ? 0 : transformedRed[i][j];
        transformedGreen[i][j] = Math.abs(transformedGreen[i][j]) <= thresholdValue
                ? 0 : transformedGreen[i][j];
        transformedBlue[i][j] = Math.abs(transformedBlue[i][j]) <= thresholdValue
                ? 0 : transformedBlue[i][j];
      }
    }

  }


  private void invertHaarTransform(double[][] matrixData) {
    int matrixSize = matrixData.length;
    int currentStep = 2;
    while (currentStep <= matrixSize) {

      for (int colIndex = 0; colIndex < currentStep; colIndex++) {
        double[] columnArray = new double[matrixSize];
        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
          columnArray[rowIndex] = matrixData[rowIndex][colIndex];
        }

        double[] segmentForInverse = Arrays.copyOfRange(columnArray, 0, currentStep);
        double[] reversedColumn = reverseTransform(segmentForInverse, currentStep);
        double[] remainingColumn = new double[matrixSize - currentStep];
        if (currentStep != matrixSize) {
          remainingColumn = Arrays.copyOfRange(columnArray, currentStep, matrixSize);
        }
        columnArray = arrayMerge(reversedColumn, currentStep, remainingColumn,
                matrixSize - currentStep);
        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
          matrixData[rowIndex][colIndex] = columnArray[rowIndex];
        }

      }
      for (int rowIndex = 0; rowIndex < currentStep; rowIndex++) {
        double[] segmentForInverse = Arrays.copyOfRange(matrixData[rowIndex], 0, currentStep);
        double[] reversedRow = reverseTransform(segmentForInverse, currentStep);
        double[] remainingRow = new double[matrixSize - currentStep];
        if (currentStep != matrixSize) {
          remainingRow = Arrays.copyOfRange(matrixData[rowIndex], currentStep, matrixSize);
        }
        matrixData[rowIndex] = arrayMerge(reversedRow, currentStep, remainingRow,
                matrixSize - currentStep);
      }

      currentStep *= 2;
    }
  }

  private static double[] reverseTransform(double[] dataArray, int length) {
    double[] avgComp = new double[length / 2];
    double[] diffComp = new double[length / 2];
    for (int index = 0; index < length / 2; index++) {
      double compA = dataArray[index];
      double compB = dataArray[length / 2 + index];
      double avgValue = (compA + compB) / Math.sqrt(2);
      double diffValue = (compA - compB) / Math.sqrt(2);
      avgComp[index] = Math.round(avgValue * 1000.0) / 1000.0;
      diffComp[index] = Math.round(diffValue * 1000.0) / 1000.0;
    }

    double[] reversedArray = new double[length];
    for (int index = 0; index < length; index += 2) {
      reversedArray[index] = avgComp[index / 2];
      reversedArray[index + 1] = diffComp[index / 2];
    }
    return reversedArray;
  }


  private double[] arrayMerge(double[] array1, int arraySize1, double[] array2,
                              int arraySize2) {
    double[] resultArray = new double[arraySize1 + arraySize2];
    System.arraycopy(array1, 0, resultArray, 0, arraySize1);
    System.arraycopy(array2, 0, resultArray, arraySize1, arraySize2);
    return resultArray;
  }

  private Image undoImage(double[][] transformedRed, double[][] transformedGreen,
                          double[][] transformedBlue) {
    Pixel[][] undoPixels = new Pixel[this.pixels.length][this.pixels[0].length];

    for (int i = 0; i < originalHeight; i++) {
      for (int j = 0; j < originalWidth; j++) {


        int redValue = Math.min(255, Math.max(0, (int) (transformedRed[i][j])));
        int greenValue = Math.min(255, Math.max(0, (int) (transformedGreen[i][j])));
        int blueValue = Math.min(255, Math.max(0, (int) (transformedBlue[i][j])));


        undoPixels[i][j] = new ColorPixel(redValue, greenValue, blueValue);
      }
    }

    return new ColorImage(undoPixels);
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
  public Image lumaWithSplit(Integer splitPercentage) {
    Pixel[][] allTransformedPixels = this.getLuma().getPixels();
    return getImage(splitPercentage, allTransformedPixels);
  }

  @Override
  public Image levelAdjustWithSplit(String black, String mid, String white,
                                    Integer splitPercentage) {
    Pixel[][] allTransformedPixels = this.adjustLevels(black, mid, white).getPixels();
    return getImage(splitPercentage, allTransformedPixels);
  }

  private Image getImage(Integer splitPercentage, Pixel[][] allTransformedPixels) {
    if (splitPercentage < 1 || splitPercentage > 99) {
      throw new IllegalArgumentException("Percentage should be between 1 to 99(numbers included)");
    }

    Pixel[][] resultPixels = new Pixel[this.pixels.length][this.pixels[0].length];

    int splitColumn = (splitPercentage != null) ? (int) (this.pixels[0].length
            * (splitPercentage / 100.0)) : this.pixels[0].length;

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
