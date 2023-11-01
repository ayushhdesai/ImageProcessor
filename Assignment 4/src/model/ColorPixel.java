package model;

public class ColorPixel implements Pixel{
  protected int red;
  protected int green;
  protected int blue;

  public ColorPixel(int red, int green, int blue){
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRedValue() {
    return red;
  }

  @Override
  public int getGreenValue() {
    return green;
  }

  @Override
  public int getBlueValue() {
    return blue;
  }


}
