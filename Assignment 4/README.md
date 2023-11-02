# Overview

This assignment aims to represent the various functionalities that can be applied to an input image.
The architecture separates the representation of an image and its pixels from the utility
functions and controllers that manipulate and manage these images.

# Classes & Interfaces :

1. Pixel (Interface)- Represents the most basic properties of a pixel.
   This interface is the foundation for any specific pixel representation, be it color or greyscale.

2. Image (Interface)- Provides a structure for any image representation, be it color or greyscale.
   It ensures that any type of image has basic methods that are common across different image representations.

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

# Running the program

The application allows us to run the image processing in both the ways :

1) Input from a file:(step-by-step guide)
- Open Main.java
- Click on dropdown besides run button.
- Click on edit configuration
- The prompt will open where you can select "Add new run configuration..."
- Name it anything (for clarity "Main with args")
- Under Build & Run fill the Main class as "Main" and Program Arguments as "/the path to the txt file"
  Example ("C:/Users/Ayush/OneDrive/Desktop/ProgramDesignParadigm/output.txt"), put it in quotes
- Click on Apply
- Click Ok
- Now run the program using the run button and all the commands in the txt file will run.
- Make sure the input and the ouput path of the image in the txt file commands is set according to your file locations.
- Finally, open the location which you set in the txt file to look at the new images formed.

2) Input as command Line Argument:
- You need to add a new configure the same as above, just this time leave the Program Argument Empty.
- Now click Apply and click Ok and run the file.
- The interactive mode will start running and now you can put in script commands on the command line.
- You can review the output.txt file provided to copy paste the commands.

Example :
- load "image/path" loadImg
- sepia loadImg load_sepia
- save "output_path/sepia.jpg" image1_sepia

# Test Image

The image in use is clicked by us and we give legal rights to the professor amd the teaching assistant to use it for testing purpose.