import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import controller.ImageController;
import view.ImageProcessingGUI;
import view.View;

/**
 * The starting point of the program, the MVC architecture.
 */
public class Main {

  /**
   * The main method to run the application.
   *
   * @param args to run a file or via command line.
   * @throws IOException in case of an issue.
   */
  public static void main(String[] args) {
    ImageController imageController = new ImageController();
    Scanner scanner = new Scanner(System.in);

    try{
      if(args.length>0){
        String choice = args[0];

        switch(choice){
          case "-file":
            String filePath = args[1];
            List<String> fileCommands = Files.readAllLines(Paths.get(filePath));
            imageController.processCommands(fileCommands);
            break;

          case "-text":
            imageController.interactiveMode();
            break;

          default:
            throw new IOException("Invalid command");
        }
      }
      else {
        View view = new ImageProcessingGUI();
        imageController.setView(view);
      }
    } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
    } finally {
      scanner.close();
    }

//    Scanner scanner = new Scanner(System.in);
//
//    System.out.println("1. Command Line Script");
//    System.out.println("2. File as command");
//    System.out.println("3. GUI");
//    System.out.println("Select an option (1, 2 or 3):");
//
//    String choice = scanner.nextLine();
//
//    try {
//      if ("1".equals(choice)) {
//        imageController.interactiveMode();
//      } else if ("2".equals(choice)) {
//        System.out.print("Enter file path: ");
//        String filePath = scanner.nextLine().trim();
//        List<String> fileCommands = Files.readAllLines(Paths.get(filePath));
//        imageController.processCommands(fileCommands);
//      } else if ("3".equals(choice)) {
//        View view = new ImageProcessingGUI();
//        imageController.setView(view);
//      } else {
//        imageController.interactiveMode();
//      }
//    } catch (IOException e) {
//      System.err.println("An error occurred: " + e.getMessage());
//      e.printStackTrace();
//    } finally {
//      scanner.close();
//    }
  }

}


