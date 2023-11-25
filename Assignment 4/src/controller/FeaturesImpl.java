package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.ColorImage;
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


}
