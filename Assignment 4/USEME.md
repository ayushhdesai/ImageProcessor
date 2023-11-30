# Introduction

This document provides guidance on using our image processing application, which offers a variety of 
functionalities for image manipulation and analysis. The application can be operated in different modes, 
including GUI, file-based, and interactive command-line modes.

# Application Modes
1. GUI Mode
   Usage: Run the application without any command-line arguments.
   Description: Launches the graphical user interface, allowing users to interact with the application 
   through a user-friendly visual platform.
2. File-Based Mode
   Usage: java -jar ImageProcessing.jar -file [filePath]
   Example: java -jar ImageProcessing.jar -file commands.txt
   Description: Processes a list of commands from a specified file. Each line in the file should contain one command.
3. Interactive Command-Line Mode
   Usage: java -jar ImageProcessing.jar -text
   Description: Enters an interactive mode where commands can be typed and executed one at a time in the console.

# Command List and Usage Examples

1. Load Image
- Command: load [path] [imageKey]
- Example: load C:/Users/Ayush/res/test.jpg loadImg
- Description: This command loads an image from the specified path and assigns it a key for further reference.

2. Compress Image
- Command: compress [compressionRate] [sourceKey] [targetKey]
- Example: compress 20 loadImg tImg
- Description: Compresses the image associated with sourceKey at the specified rate and saves it with targetKey.

3. Save Image
- Command: save [path] [imageKey]
- Example: save C:/Users/Ayush/res/test-compress-20.png tImg
- Description: Saves the image associated with imageKey to the specified path.

4. Generate Histogram
- Command: histogram [sourceKey] [targetKey]
- Example: histogram loadImg hImg
- Description: Generates a histogram for the image linked to sourceKey and stores it with targetKey.

5. Color Correction
- Command: color-correct [sourceKey] [targetKey] / color-correct [sourceKey] [targetKey] split [percentage]
- Example: color-correct loadImg cImg / color-correct loadImg cImg split 50
- Description: Applies color correction to the whole or portion of image associated with sourceKey and assigns it to targetKey.

6. Adjust Levels
- Command: levels-adjust [low] [mid] [high] [sourceKey] [targetKey] / levels-adjust [low] [mid] [high] [sourceKey] [targetKey] split 30
- Example: levels-adjust 20 30 40 loadImg laImg / levels-adjust 20 30 40 loadImg laImg split 75
- Description: Adjusts the levels (low, mid, high) of the whole or portion of image linked to sourceKey and saves it with targetKey.

7. Apply Sepia Tone
- Command: sepia [sourceKey] [targetKey] / sepia [sourceKey] [targetKey] split [percentage]
- Example: sepia loadImg sepiaImg / sepia loadImg sepiaImg split 40
- Description: Applies a sepia tone to the whole image or a percentage of the image linked to sourceKey and stores it with targetKey.

8. Sharpen Image
- Command: sharpen [sourceKey] [targetKey] / sharpen [sourceKey] [targetKey] split [percentage]
- Example: sharpen loadImg sharpenImg / sharpen loadImg sharpenImg split 90
- Description: Sharpens the whole or a portion of the image associated with sourceKey as defined by the percentage and assigns it to targetKey.

9. Apply Blur Effect
- Command: blur [sourceKey] [targetKey] / blur [sourceKey] [targetKey] split [percentage]
- Example: blur loadImg blurImg / blur loadImg blurImg split 60
- Description: Blurs the whole or a portion of the image linked to sourceKey based on the specified percentage and saves it with targetKey.

10. Extract Color Components
- Commands:
-- red-component [sourceKey] [targetKey]
-- green-component [sourceKey] [targetKey]
-- blue-component [sourceKey] [targetKey]
- Examples:
-- red-component loadImg redImg
---green-component loadImg greenImg
-- blue-component loadImg blueImg
- Description: Extracts the respective color component from the image linked to sourceKey and assigns it to targetKey.

11. Flip Image
- Commands:
-- horizontal-flip [sourceKey] [targetKey]
-- vertical-flip [sourceKey] [targetKey]
- Examples:
-- horizontal-flip loadImg horizontalFlip
-- vertical-flip loadImg verticalFlip
- Description: Flips the image horizontally or vertically associated with sourceKey and saves it with targetKey.

12. Brighten Image
- Command: brighten [brightnessLevel] [sourceKey] [targetKey]
- Example: brighten 20 loadImg brighten
- Description: Increases the brightness of the image linked to sourceKey by the specified level and assigns it to targetKey.

13. Intensity Image
- Command: intensity [sourceKey] [targetKey]
- Example: intensity loadImg brighten
- Description: Intensity of the image linked to sourceKey and assigns it to targetKey.

14. Luma Image
- Command: luma [sourceKey] [targetKey]
- Example: luma loadImg brighten
- Description: Luma of the image linked to sourceKey and assigns it to targetKey.

15. Value Image
- Command: value [sourceKey] [targetKey]
- Example: value loadImg brighten
- Description: Value of the image linked to sourceKey and assigns it to targetKey.

16. Apply Greyscale
- Command: greyscale [sourceKey] [targetKey] / greyscale [sourceKey] [targetKey] split [percentage]
- Example: greyscale loadImg grey / greyscale loadImg grey split 40
- Description: Converts the whole or portion of image associated with sourceKey to greyscale and saves it with targetKey.

17. RGB Split and Combine
- Commands:
-- rgb-split [sourceKey] [redKey] [greenKey] [blueKey]
-- rgb-combine [targetKey] [redKey] [greenKey] [blueKey]
- Examples:
-- rgb-split loadImg test-red test-green test-blue
-- rgb-combine test-red-tint test-red test-green test-blue
- Description: Splits the image into RGB components and then combines them back. Each component is associated with its respective key.

# Notes on Command Usage
- The load command must be executed before any other command to ensure the image is available for processing.
- The save command should be the final step to persist the processed image.
- Ensure the target keys used in commands are unique to avoid overwriting previously processed images.
- The GUI mode provides a more intuitive experience, suitable for users who prefer a visual approach to image processing.