public class ColorImage implements Image {

  protected Pixel[][] pixels;

  public ColorImage(Pixel[][] pixels) {
    this.pixels = pixels;
  }

  public Pixel[][] getPixels(){
    return this.pixels;
  }


  @Override
  public Image visualizeRedComponent() {
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
    Pixel[][] blueComponentPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        blueComponentPixels[i][j] =
                new ColorPixel(0, 0, this.pixels[i][j].getBlueValue());
      }
    }

    return new ColorImage(blueComponentPixels);
  }

  // Not tested
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

  // Not tested
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

  // Not tested
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
    if (!(redImage instanceof GreyscaleImage) || !(greenImage instanceof GreyscaleImage) ||
            !(blueImage instanceof GreyscaleImage)) {
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

    if(kernel.length%2==0 || kernel[0].length%2==0){
      throw new IllegalArgumentException("Kernel cannot have event length");
    }

    int rows = this.getRedChannel().getPixels().length;
    int cols = this.getRedChannel().getPixels()[0].length;

    Pixel[][] resultantRedChannelPixels = this.getRedChannel().getPixels();
    Pixel[][] resultantGreenChannelPixels = this.getGreenChannel().getPixels();
    Pixel[][] resultantBlueChannelPixels = this.getBlueChannel().getPixels();

    Pixel[][] currentRedChannelPixels = this.getRedChannel().getPixels();
    Pixel[][] currentGreenChannelPixels = this.getGreenChannel().getPixels();
    Pixel[][] currentBlueChannelPixels = this.getBlueChannel().getPixels();

    int midrow = kernel.length/2;
    int midcol = kernel[0].length/2;

    for(int i=0; i<pixels.length; i++){
      for(int j=0; j<pixels[i].length; j++){
        int sumred =0, sumgreen=0, sumblue=0;
        for(int x=0; x< kernel.length; x++){
          for(int y=0; y<kernel[x].length; y++){

            int rowIndex = i - midrow + x;
            int colIndex = j - midcol + y;

            if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
              sumred += currentRedChannelPixels[rowIndex][colIndex].getRedValue() * kernel[x][y];
              sumgreen += currentGreenChannelPixels[rowIndex][colIndex].getGreenValue() * kernel[x][y];
              sumblue += currentBlueChannelPixels[rowIndex][colIndex].getBlueValue() * kernel[x][y];
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
    if(mat.length!=3 || mat[0].length!=3) {
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
