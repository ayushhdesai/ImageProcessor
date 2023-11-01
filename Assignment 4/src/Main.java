import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import controller.ImageController;

/**
 * The starting point of the program, the MVC architecture.
 */
public class Main {

  public static void main(String[] args) throws IOException {
    ImageController imageController = new ImageController();

    if (args.length > 0) {
      String filePath = args[0];
      try {
        List<String> fileCommands = Files.readAllLines(Paths.get(filePath));
        imageController.processCommands(fileCommands);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      imageController.interactiveMode();
    }

  }
}


