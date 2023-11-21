package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessingGUI {
  private JFrame frame;
  private JLabel imageLabel;
  private JButton loadButton;
  private JButton saveButton;
  private JButton flipVerticalButton;
  private JButton flipHorizontalButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton greyscaleButton;
  private JButton sepiaButton;
  private JButton compressButton;
  private JButton colorCorrectButton;
  private JButton adjustLevelsButton;
  private JSlider splitPercentageSlider;


  public ImageProcessingGUI() {
    frame = new JFrame("Image Processing Application");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLayout(new BorderLayout());

    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);

    loadButton = new JButton("Load Image");
    saveButton = new JButton("Save Image");
    flipVerticalButton = new JButton("Flip Vertical");
    flipHorizontalButton = new JButton("Flip Horizontal");
    blurButton = new JButton("Blur");
    sharpenButton = new JButton("Sharpen");
    greyscaleButton = new JButton("Convert to Greyscale");
    sepiaButton = new JButton("Convert to Sepia");
    compressButton = new JButton("Compress");
    colorCorrectButton = new JButton("Color Correct");
    adjustLevelsButton = new JButton("Adjust Levels");

    splitPercentageSlider = new JSlider(0, 100);
    splitPercentageSlider.setValue(50);
    splitPercentageSlider.setMajorTickSpacing(10);
    splitPercentageSlider.setMinorTickSpacing(1);
    splitPercentageSlider.setPaintTicks(true);
    splitPercentageSlider.setPaintLabels(true);

    frame.add(imageLabel, BorderLayout.CENTER);
    frame.add(createButtonPanel(), BorderLayout.WEST);
    frame.add(createOptionsPanel(), BorderLayout.EAST);


    frame.setVisible(true);
  }

  private JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(12, 1));
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(flipVerticalButton);
    buttonPanel.add(flipHorizontalButton);
    buttonPanel.add(blurButton);
    buttonPanel.add(sharpenButton);
    buttonPanel.add(greyscaleButton);
    buttonPanel.add(sepiaButton);
    buttonPanel.add(compressButton);
    buttonPanel.add(colorCorrectButton);
    buttonPanel.add(adjustLevelsButton);
    buttonPanel.add(new JLabel("Split View Percentage:"));
    buttonPanel.add(splitPercentageSlider);
    return buttonPanel;
  }

  private JPanel createOptionsPanel() {
    JPanel optionsPanel = new JPanel();
    optionsPanel.setLayout(new GridLayout(1, 1));
    return optionsPanel;
  }

  private void initializeImageProcessingOperations() {
    loadButton.addActionListener(e -> loadImage());
    saveButton.addActionListener(e -> saveImage());
    flipVerticalButton.addActionListener(e -> flipImage());
    flipHorizontalButton.addActionListener(e -> flipImage());
    blurButton.addActionListener(e -> applyBlur());
    sharpenButton.addActionListener(e -> applySharpen());
    greyscaleButton.addActionListener(e -> convertToGreyscale());
    sepiaButton.addActionListener(e -> convertToSepia());
    compressButton.addActionListener(e -> applyCompression());
    colorCorrectButton.addActionListener(e -> colorCorrect());
    adjustLevelsButton.addActionListener(e -> adjustLevels());
  }

  private void saveImage() {
  }

  private void flipImage() {
  }

  private void applyBlur() {
  }

  private void applySharpen() {
  }

  private void convertToGreyscale() {
  }

  private void convertToSepia() {
  }

  private void applyCompression() {
  }

  private void colorCorrect() {
  }

  private void adjustLevels() {
  }

  private void loadImage() {
    try {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Select an Image");
      fileChooser.setFileFilter(new
              FileNameExtensionFilter("Image files", "jpg", "png", "ppm"));

      int returnValue = fileChooser.showOpenDialog(frame);

      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();

        BufferedImage image = ImageIO.read(selectedFile);

        ImageIcon icon = new ImageIcon(image);

        imageLabel.setIcon(icon);

        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
      }
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(frame, "Error loading the image.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      ImageProcessingGUI gui = new ImageProcessingGUI();
      gui.initializeImageProcessingOperations();
    });
  }
}
