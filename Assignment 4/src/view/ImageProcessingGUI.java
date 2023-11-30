package view;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import controller.Features;
import controller.ImageUtil;

/**
 * GUI implementation of an image processing application.
 * This class provides graphical user interface for image manipulation.
 */
public class ImageProcessingGUI implements View {
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

  /**
   * Constructor for a ImageProcessingGUI and initializes the GUI components.
   */
  public ImageProcessingGUI() {
    frame = new JFrame("Image Processing Application");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1024, 768);
    frame.setLayout(new BorderLayout());

    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    JScrollPane scrollPane = new JScrollPane(imageLabel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

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
    setButtonsEnabled(false);

    splitViewCheckbox = new JCheckBox("Split View");
    previewLabel = new JLabel("50");
    splitPercentageSlider = new JSlider(0, 100);
    splitPercentageSlider.setValue(50);
    splitPercentageSlider.setMajorTickSpacing(10);
    splitPercentageSlider.setPaintTicks(true);
    splitPercentageSlider.setPaintLabels(true);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    frame.add(mainPanel, BorderLayout.CENTER);
    frame.add(createButtonPanel(), BorderLayout.NORTH);
    frame.add(createOptionsPanel(), BorderLayout.WEST);


    frame.setVisible(true);
  }


  private JPanel createButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2, 7));

    loadButton.setBackground(new Color(153, 204, 255));
    buttonPanel.add(loadButton);

    saveButton.setBackground(new Color(204, 255, 204));
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

    visualizeRedButton.setBackground(new Color(255, 0, 0));
    buttonPanel.add(visualizeRedButton);

    visualizeGreenButton.setBackground(new Color(0, 255, 0));
    buttonPanel.add(visualizeGreenButton);

    visualizeBlueButton.setBackground(new Color(0, 0, 255));
    buttonPanel.add(visualizeBlueButton);

    return buttonPanel;
  }


  private JPanel createOptionsPanel() {
    JPanel optionsPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(2, 2, 2, 2);

    optionsPanel.add(splitViewCheckbox, gbc);
    JPanel splitPercentagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
    splitPercentagePanel.add(new JLabel("Preview Split Percentage:"));
    splitPercentagePanel.add(previewLabel);
    optionsPanel.add(splitPercentagePanel, gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weighty = 0;
    optionsPanel.add(splitPercentageSlider, gbc);

    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER);
    optionsPanel.add(histogramLabel, gbc);

    splitPercentageSlider.addChangeListener(e -> {
      int value = splitPercentageSlider.getValue();
      previewLabel.setText(String.valueOf(value));
      splitPercentageSlider.setEnabled(splitViewCheckbox.isSelected());
    });

    splitViewCheckbox.addItemListener(e -> {
      splitPercentageSlider.setEnabled(splitViewCheckbox.isSelected());
      previewLabel.setVisible(splitViewCheckbox.isSelected());
    });

    return optionsPanel;
  }

  private void setButtonsEnabled(boolean enabled) {
    saveButton.setEnabled(enabled);
    flipVerticalButton.setEnabled(enabled);
    flipHorizontalButton.setEnabled(enabled);
    blurButton.setEnabled(enabled);
    sharpenButton.setEnabled(enabled);
    greyscaleButton.setEnabled(enabled);
    sepiaButton.setEnabled(enabled);
    compressButton.setEnabled(enabled);
    colorCorrectButton.setEnabled(enabled);
    adjustLevelsButton.setEnabled(enabled);
    visualizeRedButton.setEnabled(enabled);
    visualizeGreenButton.setEnabled(enabled);
    visualizeBlueButton.setEnabled(enabled);
  }

  @Override
  public void saveImage() {
    BufferedImage image = getImage();
    if (image == null) {
      JOptionPane.showMessageDialog(frame,
              "No image to save.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");
    fileChooser.addChoosableFileFilter(
            new FileNameExtensionFilter("PNG Images", "png"));
    fileChooser.addChoosableFileFilter(
            new FileNameExtensionFilter("JPEG Images", "jpg", "jpeg"));
    fileChooser.addChoosableFileFilter(
            new FileNameExtensionFilter("PPM Images", "ppm"));
    fileChooser.setAcceptAllFileFilterUsed(false);

    int userSelection = fileChooser.showSaveDialog(frame);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fileChooser.getSelectedFile();
      String ext = getFileExtension(fileToSave.getName());
      if (ext.isEmpty()) {
        JOptionPane.showMessageDialog(
                frame, "Invalid file format.", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(
                    frame, "Unsupported file format: " + ext, "Error", JOptionPane.ERROR_MESSAGE);
        }
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(frame,
                "Error saving the image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private String getFileExtension(String fileName) {
    int i = fileName.lastIndexOf('.');
    return (i > 0) ? fileName.substring(i + 1) : "";
  }

  @Override
  public void loadImage() {
    try {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Select an Image");
      fileChooser.setFileFilter(
              new FileNameExtensionFilter("Image files", "jpg", "png", "ppm"));

      int returnValue = fileChooser.showOpenDialog(frame);

      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String ext = getFileExtension(selectedFile.getName());

        BufferedImage image;

        if ("ppm".equalsIgnoreCase(ext)) {
          image = ImageUtil.readPPM(selectedFile.getAbsolutePath());
        } else {
          image = ImageIO.read(selectedFile);
        }

        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        setButtonsEnabled(true);
      }
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(
              frame, "Error loading the image: " + e.getMessage(),
              "Error", JOptionPane.ERROR_MESSAGE);
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
    String input = JOptionPane.showInputDialog(
            frame, "Enter compression percentage(1-99):",
            "Compression", JOptionPane.QUESTION_MESSAGE);
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(
              frame, "Invalid input. Please enter a number.",
              "Error", JOptionPane.ERROR_MESSAGE);
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

    int result = JOptionPane.showConfirmDialog(
            null, myPanel, "Please Enter Black, Mid and White Values",
            JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      return new String[]{blackField.getText(), midField.getText(), whiteField.getText()};
    }
    return null;
  }

  @Override
  public int getSplitPercentage() {
    int n = splitPercentageSlider.getValue();
    if (n == 0 || n == 100) {
      JOptionPane.showMessageDialog(
              frame, "Invalid input. Please enter percentage between 1 to 99.",
              "Percentage Error", JOptionPane.ERROR_MESSAGE);
      return -1;
    }
    return n;
  }

  @Override
  public void displayPreviewImage(BufferedImage image) {
    int newWidth = image.getWidth();
    int newHeight = image.getHeight();

    Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    ImageIcon previewIcon = new ImageIcon(scaledImage);

    JLabel previewImageLabel = new JLabel(previewIcon);
    JOptionPane.showMessageDialog(frame, previewImageLabel, "Preview", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void addFeatures(Features features) {
    loadButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.loadImage();
      }
    });
    saveButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.saveImage();
      }
    });
    flipVerticalButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.verticalFlip();
      }
    });
    flipHorizontalButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.horizontalFlip();
      }
    });
    visualizeRedButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.visualizeRedComponent();
      }
    });
    visualizeGreenButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.visualizeGreenComponent();
      }
    });
    visualizeBlueButton.addActionListener(e -> {
      if (splitViewCheckbox.isSelected()) {
        JOptionPane.showMessageDialog(
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
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
                frame, "This functionality doesn't support split view.",
                "Feature Not Supported", JOptionPane.INFORMATION_MESSAGE);
        splitViewCheckbox.setSelected(false);
        splitPercentageSlider.setEnabled(false);
      } else {
        features.applyCompression();
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
