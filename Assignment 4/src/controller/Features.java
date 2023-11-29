package controller;

import java.awt.image.BufferedImage;

public interface Features {

    void loadImage();

    void saveImage();


    void verticalFlip();
    void horizontalFlip();

    void visualizeRedComponent();

    void visualizeGreenComponent();

    void visualizeBlueComponent();

    void loadHistogram();

    void blurImage();
    void applySharpen();
    void convertToGreyscale();
    void convertToSepia();
    void applyCompression();
    void colorCorrect();
    void adjustLevels();

    BufferedImage blurImageWithSplit(int splitPercentage);
    BufferedImage sharpenImageWithSplit(int splitPercentage);
    BufferedImage sepiaImageWithSplit(int splitPercentage);
    BufferedImage levelsAdjustImageWithSplit(int splitPercentage);
    BufferedImage greyscaleImageWithSplit(int splitPercentage);
    BufferedImage colorCorrectionImageWithSplit(int splitPercentage);
}
