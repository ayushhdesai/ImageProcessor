package view;

import java.awt.image.BufferedImage;

import controller.Features;

public interface View {

    void loadImage();

    void displayImage(BufferedImage bufferedImage);
    void displayHistogram(BufferedImage bufferedImage);

    BufferedImage getImage();
    void addFeatures(Features features);

    float getCompressionPercentage();

    String[] getLevelAdjustments();

    void saveImage();
    int getSplitPercentage();
}
