public class GreyscaleImage implements Image {

  Pixel[][] pixels;

  public GreyscaleImage(Pixel[][] pixels){
    this.pixels = pixels;
  }

  @Override
  public Image visualizeRedComponent() {
    GreyPixel[][] redComp = new GreyPixel[this.pixels.length][this.pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[i].length; j++) {
        redComp[i][j] = new GreyPixel(this.pixels[i][j].getRedValue());
      }
    }
    return new GreyscaleImage(redComp);
  }

  // Grayscale image has only one channel, so return the same copy.
  @Override
  public Image visualizeBlueComponent() {
    return this.visualizeRedComponent();
  }

  // Grayscale image has only one channel, so return the same copy.
  @Override
  public Image visualizeGreenComponent() {
    return this.visualizeRedComponent();
  }

  @Override
  public Image getValue() {
    return null;
  }

  @Override
  public Image getIntensity() {
    return null;
  }

  @Override
  public Image getLuma() {
    return null;
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
