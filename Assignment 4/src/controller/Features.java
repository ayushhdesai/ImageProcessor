package controller;

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
}
