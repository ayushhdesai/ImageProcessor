package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import controller.ImageController;

/**
 * This class represents a color image with pixels arranged in 2D array.
 */
public class ColorImage implements Image {

  protected Pixel[][] pixels;

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

  private HashMap<Integer,Integer> getRedFrequencyMap(){
    Image redChannel = this.getRedChannel();
    Pixel[][] redPixels = redChannel.getPixels();

    HashMap<Integer, Integer> redChannelMap = new HashMap<>();

    for(int i=0; i<redPixels.length; i++){
      for(int j=0; j<redPixels[i].length; j++){
        redChannelMap.put(redPixels[i][j].getRedValue(),
                redChannelMap.getOrDefault(redPixels[i][j].getRedValue(),0)+1);
      }
    }

    return redChannelMap;

  }

  private HashMap<Integer,Integer> getGreenFrequencyMap(){
    Image greenChannel = this.getGreenChannel();
    Pixel[][] greenPixels = greenChannel.getPixels();

    HashMap<Integer, Integer> greenChannelMap = new HashMap<>();

    for(int i=0; i<greenPixels.length; i++){
      for(int j=0; j<greenPixels[i].length; j++){

        greenChannelMap.put(greenPixels[i][j].getGreenValue(),
                greenChannelMap.getOrDefault(greenPixels[i][j].getGreenValue(),0)+1);
      }
    }

    return greenChannelMap;

  }

  private HashMap<Integer,Integer> getBlueFrequencyMap(){
    Image blueChannel = this.getBlueChannel();
    Pixel[][] bluePixels = blueChannel.getPixels();

    HashMap<Integer, Integer> blueChannelMap = new HashMap<>();

    for(int i=0; i<bluePixels.length; i++){
      for(int j=0; j<bluePixels[i].length; j++){


        blueChannelMap.put(bluePixels[i][j].getBlueValue(),
                blueChannelMap.getOrDefault(bluePixels[i][j].getBlueValue(),0)+1);
      }
    }

    return blueChannelMap;

  }

  @Override
  public Image getHistogram() {

    HashMap<Integer, Integer> redChannelMap = getRedFrequencyMap();
    HashMap<Integer, Integer> greenChannelMap = getGreenFrequencyMap();
    HashMap<Integer, Integer> blueChannelMap = getBlueFrequencyMap();

    int maxWidth = 256;
    int maxHeight = 256;

    double maxRedValue = getMaxValue(redChannelMap);
    double maxGreenValue = getMaxValue(greenChannelMap);
    double maxBlueValue = getMaxValue(blueChannelMap);

    double maxFrequency = Math.max(Math.max(maxRedValue, maxGreenValue), maxBlueValue);

    double xScale = (maxWidth - 50.0) / Math.max(redChannelMap.size(), Math.max(greenChannelMap.size(), blueChannelMap.size()))*1.25;
    double yScale = (maxHeight - 50.0) / maxFrequency*1.25;


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
    HashMap<Integer, Integer> redChannelMap = getRedFrequencyMap();
    HashMap<Integer, Integer> greenChannelMap = getGreenFrequencyMap();
    HashMap<Integer, Integer> blueChannelMap = getBlueFrequencyMap();

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

  private int findMaxFrequencyValue(HashMap<Integer, Integer> channelMap) {
    int maxFrequency = Integer.MIN_VALUE;
    int valueForMaxFrequency = -1;

    for (Integer value : channelMap.keySet()) {
      if (channelMap.get(value) > maxFrequency && value > 10 && value < 245) {
        maxFrequency = channelMap.get(value);
        valueForMaxFrequency = value;
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

  private void drawLineGraph(Graphics2D graphics, HashMap<Integer, Integer> channelMap,
                             double xScale, double yScale, int maxWidth, int maxHeight) {
    int prevX = 0;
    int prevY = maxHeight;


    for (int x : channelMap.keySet()) {
      int y = (int) (maxHeight - channelMap.get(x) * yScale);
      graphics.drawLine(prevX, prevY, (int) (x * xScale), y);
      prevX = (int) (x * xScale);
      prevY = y;
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

  private static int getMaxValue(Map<Integer, Integer> data) {
    int maxValue = Integer.MIN_VALUE;
    for (int value : data.values()) {
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
              sumred += currRedChannelPixels[rowIndex][colIndex].getRedValue() * kernel[x][y];
              sumgreen += currGreenChannelPixels[rowIndex][colIndex].getGreenValue() * kernel[x][y];
              sumblue += currBlueChannelPixels[rowIndex][colIndex].getBlueValue() * kernel[x][y];
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
}
