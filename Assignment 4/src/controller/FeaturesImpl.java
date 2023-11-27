package controller;

import java.awt.image.BufferedImage;
import java.util.Map;

import model.ColorImage;
import model.GreyscaleImage;
import model.Image;
import view.View;

public class FeaturesImpl implements Features{
  private Map<String, model.Image> imageMap;

  private Image image;
  private View view;


  public FeaturesImpl(){}

  public FeaturesImpl(Map<String, Image> imageMap, View view){
    this.imageMap = imageMap;
    this.view = view;
  }

  @Override
  public void loadImage() {
    view.loadImage();
    this.loadHistogram();
  }

  @Override
  public void saveImage() {
    view.saveImage();
  }

  @Override
  public void verticalFlip() {
      BufferedImage bufferedImage = view.getImage();
      Image imageToFlip = new ColorImage(ImageController.convertToPixels(bufferedImage));
      Image flipped = imageToFlip.verticalFlip();
      view.displayImage(ImageController.convertToBufferedImage(flipped.getPixels()));
      this.loadHistogram();
  }

  @Override
  public void horizontalFlip() {
    BufferedImage bufferedImage = view.getImage();
    Image imageToFlip = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image flipped = imageToFlip.horizontalFlip();
    view.displayImage(ImageController.convertToBufferedImage(flipped.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void visualizeRedComponent() {
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image redComponent = image.visualizeRedComponent();
    view.displayImage(ImageController.convertToBufferedImage(redComponent.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void visualizeGreenComponent() {
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image greenComponent = image.visualizeGreenComponent();
    view.displayImage(ImageController.convertToBufferedImage(greenComponent.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void visualizeBlueComponent() {
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image blueComponent = image.visualizeBlueComponent();
    view.displayImage(ImageController.convertToBufferedImage(blueComponent.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void loadHistogram() {
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image histogram = image.getHistogram();
    view.displayHistogram(ImageController.convertToBufferedImage(histogram.getPixels()));
  }

  @Override
  public void blurImage() {
    float[][] blurKernel = {
            {0.0625F, 0.125F, 0.0625F},
            {0.125F, 0.25F, 0.125F},
            {0.0625F, 0.125F, 0.0625F}
    };
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image blurImg = image.filter(blurKernel);
    view.displayImage(ImageController.convertToBufferedImage(blurImg.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void applySharpen() {
    float[][] sharpenKernel = {
            {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F},
            {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
            {-0.125F, 0.25F, 1F, 0.25F, -0.125F},
            {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
            {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F}
    };
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image sharpenImg = image.filter(sharpenKernel);
    view.displayImage(ImageController.convertToBufferedImage(sharpenImg.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void convertToGreyscale() {
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    GreyscaleImage lumaComponent = (GreyscaleImage) image.getLuma();
    view.displayImage(ImageController.convertToBufferedImage(lumaComponent.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void convertToSepia() {
    float[][] sepiaMatrix = {
            {0.393F, 0.769F, 0.189F},
            {0.349F, 0.686F, 0.186F},
            {0.272F, 0.534F, 0.131F}
    };
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image sepiaImg = image.filter(sepiaMatrix);
    view.displayImage(ImageController.convertToBufferedImage(sepiaImg.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void applyCompression() {
    float compressionPercentage = view.getCompressionPercentage();
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image compressedImage = image.compress(compressionPercentage);
    view.displayImage(ImageController.convertToBufferedImage(compressedImage.getPixels()));
    this.loadHistogram();
  }
  @Override
  public void colorCorrect() {
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image colorCorrectImg = image.colorCorrect();
    view.displayImage(ImageController.convertToBufferedImage(colorCorrectImg.getPixels()));
    this.loadHistogram();
  }

  @Override
  public void adjustLevels() {
    String[] levelValues = view.getLevelAdjustments();
    BufferedImage bufferedImage = view.getImage();
    Image image = new ColorImage(ImageController.convertToPixels(bufferedImage));
    Image adjustLevelsImg = image.adjustLevels(levelValues[0], levelValues[1], levelValues[2]);
    view.displayImage(ImageController.convertToBufferedImage(adjustLevelsImg.getPixels()));
    this.loadHistogram();
  }


}
