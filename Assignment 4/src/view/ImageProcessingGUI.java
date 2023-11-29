package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import controller.Features;
import controller.ImageUtil;

public class ImageProcessingGUI implements View {
  private final JScrollPane scrollPane;
  private JFrame frame;
  private JLabel imageLabel;
  private JLabel histogramLabel;
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
  private JButton visualizeRedButton;
  private JButton visualizeGreenButton;
  private JButton visualizeBlueButton;
  private JButton adjustLevelsButton;
  private JSlider splitPercentageSlider;
  private JCheckBox splitViewCheckbox;
  private JLabel previewLabel;


  public ImageProcessingGUI() {
    frame = new JFrame("Image Processing Application");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLayout(new BorderLayout());

    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    scrollPane = new JScrollPane(imageLabel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.SOUTH_EAST);

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
    visualizeRedButton = new JButton("Visualize Red Component");
    visualizeGreenButton = new JButton("Visualize Green Component");
    visualizeBlueButton = new JButton("Visualize Blue Component");

    splitViewCheckbox = new JCheckBox("Split View");
    splitPercentageSlider = new JSlider(0, 100); // Assuming 0-100% for split
    splitPercentageSlider.setValue(50); // Default value
    splitPercentageSlider.setMajorTickSpacing(10);
    splitPercentageSlider.setPaintTicks(true);
    splitPercentageSlider.setPaintLabels(true);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    frame.add(mainPanel, BorderLayout.CENTER);
    frame.add(histogramLabel, BorderLayout.SOUTH);
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
    buttonPanel.add(visualizeRedButton);
    buttonPanel.add(visualizeGreenButton);
    buttonPanel.add(visualizeBlueButton);


    return buttonPanel;
  }

  private JPanel createOptionsPanel() {
    JPanel optionsPanel = new JPanel();
    optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS)); // Use BoxLayout for vertical stacking

    optionsPanel.add(splitViewCheckbox);

    optionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    optionsPanel.add(new JLabel("Split Percentage:"));
    optionsPanel.add(splitPercentageSlider);
    splitPercentageSlider.setEnabled(splitViewCheckbox.isSelected());
    splitViewCheckbox.addItemListener(e -> splitPercentageSlider.setEnabled(splitViewCheckbox.isSelected()));

    return optionsPanel;
  }


  public void saveImage() {
    BufferedImage image = getImage();
    if (image == null) {
      JOptionPane.showMessageDialog(frame, "No image to save.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Images", "jpg", "jpeg"));
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PPM Images", "ppm"));
    fileChooser.setAcceptAllFileFilterUsed(false);

    int userSelection = fileChooser.showSaveDialog(frame);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fileChooser.getSelectedFile();
      String ext = getFileExtension(fileToSave.getName());
      if (ext.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Invalid file format.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        switch (ext.toLowerCase()) {
          case "ppm":
            ImageUtil.writePPM(image, fileToSave.getAbsolutePath());
            break;
          case "png":
            ImageIO.write(image, "PNG", fileToSave);
            break;
          case "jpg":
          case "jpeg":
            ImageIO.write(image, "JPEG", fileToSave);
            break;
          default:
            JOptionPane.showMessageDialog(frame, "Unsupported file format: " + ext, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(frame, "Error saving the image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private String getFileExtension(String fileName) {
    int i = fileName.lastIndexOf('.');
    return (i > 0) ? fileName.substring(i + 1) : "";
  }


  public void loadImage() {
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

  @Override
  public void displayImage(BufferedImage bufferedImage) {
    ImageIcon icon = new ImageIcon(bufferedImage);
    imageLabel.setIcon(icon);
    imageLabel.revalidate();
  }

  @Override
  public void displayHistogram(BufferedImage bufferedImage) {
    ImageIcon icon = new ImageIcon(bufferedImage);
    histogramLabel.setIcon(icon);
    histogramLabel.setHorizontalAlignment(JLabel.CENTER);
    histogramLabel.setVerticalAlignment(JLabel.CENTER);
  }


  @Override
  public BufferedImage getImage() {
    ImageIcon imageIcon = (ImageIcon) imageLabel.getIcon();
    return (BufferedImage) imageIcon.getImage();
  }

  @Override
  public float getCompressionPercentage() {
    String input = JOptionPane.showInputDialog(frame, "Enter compression percentage(1-99):", "Compression", JOptionPane.QUESTION_MESSAGE);
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
      return -1;
    }
  }

  @Override
  public String[] getLevelAdjustments() {
    JTextField blackField = new JTextField(5);
    JTextField midField = new JTextField(5);
    JTextField whiteField = new JTextField(5);

    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel("Black:"));
    myPanel.add(blackField);
    myPanel.add(Box.createHorizontalStrut(15));
    myPanel.add(new JLabel("Mid:"));
    myPanel.add(midField);
    myPanel.add(Box.createHorizontalStrut(15));
    myPanel.add(new JLabel("White:"));
    myPanel.add(whiteField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
            "Please Enter Black, Mid and White Values", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      return new String[]{blackField.getText(), midField.getText(), whiteField.getText()};
    }
    return null;
  }

  @Override
  public int getSplitPercentage() {
    return splitPercentageSlider.getValue();
  }

  public void displayPreviewImage(BufferedImage image) {
    ImageIcon previewIcon = new ImageIcon(image);
    JLabel previewImageLabel = new JLabel(previewIcon);
    JOptionPane.showMessageDialog(frame, previewImageLabel, "Preview", JOptionPane.PLAIN_MESSAGE);
  }


//  public static void main(String[] args) {
//    SwingUtilities.invokeLater(() -> {
//      ImageProcessingGUI gui = new ImageProcessingGUI();
//    });
//  }

  @Override
  public void addFeatures(Features features) {
    loadButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.loadImage();
      }
    });
    saveButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.saveImage();
      }
    });
    flipVerticalButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.verticalFlip();
      }
    });
    flipHorizontalButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.horizontalFlip();
      }
    });
    visualizeRedButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.visualizeRedComponent();
      }
    });
    visualizeGreenButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.visualizeGreenComponent();
      }
    });
    visualizeBlueButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.visualizeBlueComponent();
      }
    });
    blurButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        int splitPercentage = getSplitPercentage();
        BufferedImage previewImage = features.blurImageWithSplit(splitPercentage);
        displayPreviewImage(previewImage);
      } else {
        features.blurImage();
      }
    });

    sharpenButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        int splitPercentage = getSplitPercentage();
        BufferedImage previewImage = features.sharpenImageWithSplit(splitPercentage);
        displayPreviewImage(previewImage);
      } else {
        features.applySharpen();
      }
    });
    greyscaleButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        int splitPercentage = getSplitPercentage();
        BufferedImage previewImage = features.greyscaleImageWithSplit(splitPercentage);
        displayPreviewImage(previewImage);
      } else {
        features.convertToGreyscale();
      }
    });
    sepiaButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        int splitPercentage = getSplitPercentage();
        BufferedImage previewImage = features.sepiaImageWithSplit(splitPercentage);
        displayPreviewImage(previewImage);
      } else {
        features.convertToSepia();
      }
    });
    compressButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame,
                "This functionality doesn't support split view.",
                "Feature Not Supported",
                JOptionPane.INFORMATION_MESSAGE
        );
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.loadImage();
      }
    });
    colorCorrectButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        int splitPercentage = getSplitPercentage();
        BufferedImage previewImage = features.colorCorrectionImageWithSplit(splitPercentage);
        displayPreviewImage(previewImage);
      } else {
        features.colorCorrect();
      }
    });
    adjustLevelsButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        int splitPercentage = getSplitPercentage();
        BufferedImage previewImage = features.levelsAdjustImageWithSplit(splitPercentage);
        displayPreviewImage(previewImage);
      } else {
        features.adjustLevels();
      }
    });
  }


}
