# Overview

This assignment aims to represent the various functionalities that can be applied to an input image.
The architecture separates the representation of an image and its pixels from the utility
functions and controllers that manipulate and manage these images.

# Classes & Interfaces :

1. Pixel (Interface)- Represents the most basic properties of a pixel.
   This interface is the foundation for any specific pixel representation, be it color or greyscale.

2. Image (Interface)- Provides a structure for any image representation, be it color or greyscale.
   It ensures that any type of image has basic methods that are common across different image
   representations.

3. ColorPixel (Class)- Represents a colored pixel with Red, Green, and Blue values.
   Implements the Pixel interface and adds color-specific properties and methods.

4. GreyPixel (Class)- Represents a greyscale pixel with a singular grey value.
   Implements the Pixel interface with greyscale-specific properties and methods.

5. ColorImage (Class)- Represents a RGB color image.
   Implements the Image interface and holds a collection of ColorPixel objects.
   Provides methods to manipulate and interact with the colored image.

6. GreyscaleImage (Class)- Represents a greyscale image.
   Implements the Image interface and holds a collection of GreyPixel objects.
   Provides methods to manipulate and interact with the greyscale image.

7. ImageUtil (Class)- A utility class that provides various static methods to manipulate
   and manage images, including format conversion, scaling, and other image processing tasks.

8. ImageController (Class)- Acts as a controller to manage the workflow of the application.
   Interacts with the Model classes (like ColorImage and GreyscaleImage)
   and uses ImageUtil for any necessary image operations.

9. Main Method - Provides the entry point to the application, creating a user interface
   and facilitating interactions between the user and the underlying image processing capabilities.

# Design changes and Justification:

Integration of New Functionalities
1. Enhanced Image Interface:
- The Image interface was extended to include new functionalities. 
- The ColorImage class, implementing this interface, was updated to support these new functionalities, ensuring a seamless integration with existing structures.
2. Refactoring for Command Design Pattern:
- The processCommand method in the ImageController class was refactored to adhere to the Command Design Pattern. This change was made to improve code maintainability, readability, and scalability.
- By encapsulating each image processing command into its own class, we've made the system more modular and easier to extend with new commands in the future.
3. Command Line Interface (CLI) Enhancements:
- The CLI was enhanced to provide users with a choice between interactive command line input and file-based input.

Justification for Design Choices
1. Adherence to Open-Closed Principle:
- By extending the Image interface for new functionalities, we adhere to the Open-Closed Principle, allowing our appliation entities to be open for extension but closed for modification. This design choice minimizes the risk of introducing bugs into existing code and eases the addition of new features.
2. Command Design Pattern Benefits:
- Implementing the Command Design Pattern in our ImageController enhances the separation of concerns by decoupling the command execution process from the actual command logic.

# Running the program

The application allows us to run the image processing in both the ways :

Running the Program
The application supports two modes of running image processing tasks: through a Command Line Script and by using a File as a command. Below are the steps for each method:

1. Command Line Script Mode
- When you run the program, you will be presented with two options.
- Choose option 1 for Command Line Script mode.
- Once selected, the interactive mode will start, and you can input script commands directly on the command line.
- Refer to the USEME file for a list of supported script commands and usage examples.

2. File as Command Mode
- When you run the program, choose option 2 for File as Command mode.
- You will be prompted to enter the file path. Input the full path to the text file containing the script commands (e.g., C:/Users/Ayush/res/output.txt).
- Ensure that the file contains valid script commands as outlined in the USEME file.
- The program will execute all the commands in the text file.
- Check the output directory specified in your text file to view the processed images.

Steps to Run the Program
- Open Main.java.
- Run the program using the run button in your IDE.
- Select the desired mode (1 or 2) as per the instructions above.

Additional Notes
- Ensure that the input and output paths of the images in the script commands (whether entered via command line or specified in the file) are correctly set according to your file locations.

Example :

- load "image/path" loadImg
- sepia loadImg load_sepia
- save "output_path/sepia.jpg" load_sepia

# Running the JAR file

- Open a command prompt.
- Use the cd command to navigate to the directory where ImageProcessing.jar is located.
- Type java -jar ImageProcessing.jar and press Enter. This command will start the application.
- After launch of the application, follow the steps in "Running the program".

# Text files submitted

- output.txt has all the commands of Assignment 4 and Assignment 5.
- assign5.txt/assign5-back.txt has all commands of Assignment 5.
- test.txt is used for testing.

# Test Image

The image in use is clicked by us and we give legal rights to the professor amd the teaching
assistant to use it for testing purpose.