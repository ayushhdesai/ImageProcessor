public interface Image {
  Image visualizeRedComponent();
  Image visualizeBlueComponent();
  Image visualizeGreenComponent();

  Image getValue();

  Image getIntensity();

  Image getLuma();

  Image combineChannel(Image red, Image green, Image blue);

  Image horizontalFlip();

  Image verticalFlip();

  Image brighten(int alpha);


  Image filter(int[][] kernel);

  Image linearTransform(int[][] mat);

  Image getRedChannel();
  Image getBlueChannel();
  Image getGreenChannel();


}
