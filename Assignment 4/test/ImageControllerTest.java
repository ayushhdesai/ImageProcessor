import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import controller.ImageController;

import static org.junit.Assert.assertTrue;

/**
 * JUnit test class for ImageController class.
 */
public class ImageControllerTest {

  ImageController imageController = new ImageController();

  @Test(expected = IllegalArgumentException.class)
  public void testProcessCommandsFromAFileError() throws IOException {
    String filePath = "C:/Users/Ayush/res/test.txt";
    List<String> fileCommands = Files.readAllLines(Paths.get(filePath));
    imageController.processCommands(fileCommands);
  }

  @Test
  public void testProcessCommandsFromAFile() throws IOException {
    String filePath = "C:/Users/Ayush/res/test.txt";
    List<String> fileCommands = Files.readAllLines(Paths.get(filePath));
    imageController.processCommands(fileCommands);
    String expectedOutputFilePath = "C:/Users/Ayush/res/back.jpg";
    File outputFile = new File(expectedOutputFilePath);
    assertTrue("Output file should exist after processing commands", outputFile.exists());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProcessCommandsForScriptsError() throws IOException {
    imageController.processCommand("xyz");
  }

  @Test
  public void testProcessCommandsForScripts() throws IOException {
    imageController.processCommand("load C:/Users/Ayush/res/back.jpg a");
    imageController.processCommand("compress 50 a b");
    imageController.processCommand("save C:/Users/Ayush/res/back.png b");
    String expectedOutputFilePath = "C:/Users/Ayush/res/back.png";
    File outputFile = new File(expectedOutputFilePath);
    assertTrue("Output file should exist after processing commands", outputFile.exists());
  }

  @Test
  public void testProcessCommandsForScriptsForPPM() throws IOException {
    imageController.processCommand("load C:/Users/Ayush/res/back.jpg a");
    imageController.processCommand("brighten 50 a b");
    imageController.processCommand("save C:/Users/Ayush/res/test-brighten.png b");
    imageController.processCommand("save C:/Users/Ayush/res/test-brighten.ppm b");
    String expectedOutputFilePath = "C:/Users/Ayush/res/test-brighten.png";
    File outputFile = new File(expectedOutputFilePath);
    assertTrue("Output file should exist after processing commands", outputFile.exists());
  }
}