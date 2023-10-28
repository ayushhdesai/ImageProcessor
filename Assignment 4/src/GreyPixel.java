public class GreyPixel implements Pixel {
  private int color;

  public GreyPixel(int color){
    this.color = color;
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
