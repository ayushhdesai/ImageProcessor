public class ColorPixel implements Pixel{
  private int red;
  private int green;
  private int blue;

  public ColorPixel(int red, int green, int blue){
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRow() {
    return 0;
  }

  @Override
  public int getCol() {
    return 0;
  }

  @Override
  public int getRedValue() {
    return 0;
  }

  @Override
  public int getGreenValue() {
    return 0;
  }

  @Override
  public int getBlueValue() {
    return 0;
  }


}
