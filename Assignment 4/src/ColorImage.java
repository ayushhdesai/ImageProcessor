public class ColorImage implements Image{

  private Pixel[][] pixels;

  public ColorImage(Pixel[][] pixels){
    this.pixels = pixels;
  }
  @Override
  public Image visualizeRedComponent() {
    Pixel[][] redComponentPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];
    for(int i=0; i<pixels.length; i++){
      for(int j=0; j<pixels[i].length; j++){
          redComponentPixels[i][j] =
                  new ColorPixel(this.pixels[i][j].getRedValue(),0,0);
      }
    }

    return new ColorImage(redComponentPixels);
  }

  @Override
  public Image visualizeGreenComponent() {
    Pixel[][] greenComponentPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];
    for(int i=0; i<pixels.length; i++){
      for(int j=0; j<pixels[i].length; j++){
        greenComponentPixels[i][j] =
                new ColorPixel(0,this.pixels[i][j].getGreenValue(),0);
      }
    }

    return new ColorImage(greenComponentPixels);
  }

  @Override
  public Image visualizeBlueComponent() {
    Pixel[][] blueComponentPixels = new ColorPixel[this.pixels.length][this.pixels[0].length];
    for(int i=0; i<pixels.length; i++){
      for(int j=0; j<pixels[i].length; j++){
        blueComponentPixels[i][j] =
                new ColorPixel(0,0,this.pixels[i][j].getBlueValue());
      }
    }

    return new ColorImage(blueComponentPixels);
  }

  @Override
  public Image getValue() {
    Pixel[][] valuePixels = new GreyPixel[this.pixels.length][this.pixels[0].length];
    for(int i=0; i<pixels.length; i++){
      for(int j=0; j<pixels[i].length; j++){
        int maxColorValue = Math.max(pixels[i][j].getRedValue(),
                Math.max(pixels[i][j].getGreenValue(),pixels[i][j].getBlueValue() ));
        valuePixels[i][j] = new GreyPixel(maxColorValue);
      }
    }

    return new GreyscaleImage(valuePixels);
  }

  @Override
  public Image getIntensity() {
    Pixel[][] intensityPixels = new GreyPixel[this.pixels.length][this.pixels[0].length];
    for(int i=0; i<pixels.length; i++){
      for(int j=0; j<pixels[i].length; j++){
        int sumValue = this.pixels[i][j].getRedValue()
                + this.pixels[i][j].getGreenValue() + this.pixels[i][j].getBlueValue();

        int avgColorValue = sumValue/3;
        intensityPixels[i][j] = new GreyPixel(avgColorValue);
      }
    }

    return new GreyscaleImage(intensityPixels);
  }

  @Override
  public Image getLuma() {
    Pixel[][] lumaPixels = new GreyPixel[this.pixels.length][this.pixels[0].length];
    for(int i=0; i<pixels.length; i++){
      for(int j=0; j<pixels[i].length; j++){
        int weightedSum = (int) (this.pixels[i][j].getRedValue()*0.2126)
                + (int) (this.pixels[i][j].getGreenValue()*0.7152)
                + (int) (this.pixels[i][j].getBlueValue()*0.0722);


        lumaPixels[i][j] = new GreyPixel(weightedSum);
      }
    }

    return new GreyscaleImage(lumaPixels);
  }

  @Override
  public Image combineChannel(Image red, Image green, Image blue) {
    return null;
  }

  @Override
  public Image horizontalFlip() {
    return null;
  }

  @Override
  public Image verticalFlip() {
    return null;
  }

  @Override
  public Image brighten(int alpha) {
    return null;
  }

  @Override
  public Image filter(int[][] kernel) {
    return null;
  }

  @Override
  public Image linearTransform(int[][] mat) {
    return null;
  }

  @Override
  public Image getRedChannel() {
    return null;
  }

  @Override
  public Image getBlueChannel() {
    return null;
  }

  @Override
  public Image getGreenChannel() {
    return null;
  }
}
