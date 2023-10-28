public class GreyscaleImage implements Image {

  private Pixel[][] pixels;

  public GreyscaleImage(Pixel[][] pixels){
    this.pixels = pixels;
  }
  @Override
  public Image visualizeRedComponent() {
    return null;
  }
  @Override
  public Image visualizeBlueComponent() {
    return null;
  }

  @Override
  public Image visualizeGreenComponent() {
    return null;
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
