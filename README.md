# Overview

This assignment provides an image processing application that facilitates a range of functionalities 
for image manipulation. The application's architecture distinctively separates image 
representation from image processing controllers and utilities, with a strong emphasis on user interaction through a 
graphical user interface, file-based or interactive mode style.

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

9. Features (Interface): Defines a set of callback methods for various image processing operations, supporting 
   functionalities in a view. These operations include loading and saving images, applying 
   different transformations and effects, and visualizing image components.

10. FeaturesImpl (Class): Implements the Features interface, handling various image transformations such as 
    flipping, color adjustments, filtering, and compression,etc. This class operates in conjunction with the 
    ImageController and View to provide a comprehensive image processing experience.

11. View (Interface): Defines essential methods for image processing, specifically tailored for GUI interaction. 
    This interface standardizes functions such as loading and displaying images, displaying histograms, and handling 
    user inputs for various image processing operations.

12. ImageProcessingGUI (Class): Implements the View interface, offering a graphical user interface for image manipulation. 
    It includes features like buttons for various image operations (e.g., flipping, blurring, color correction), 
    sliders for adjustable parameters, and interactive components for real-time image processing feedback.

13. MockView (Class): Creating a mock object of the View interface in order to isolate the testing of the Controller independently.

14. Main Method - Provides the entry point to the application, creating a user interface
    and facilitating interactions between the user and the underlying image processing capabilities.

# Design changes and Justification:

-- Integration of New Functionalities

1. Enhanced Command Line Interface (CLI):
The CLI has been upgraded to offer users a choice between GUI, file-based, or interactive command line input. This enhancement makes the application more flexible and accessible, catering to different user preferences and scenarios.

2. Introduction of Callback Functions:
Callback functions have been implemented to ensure that the ImageController and the View are not directly dependent on each other. This design approach enhances modularity and promotes the separation of concerns. It allows the View to notify the Controller of user actions without having a direct reference to the Controller, thereby reducing coupling between components.

3. Adoption of the Features Interface and its Implementation:
The Features interface and its implementation in FeaturesImpl class streamline the way image processing operations are handled. This design centralizes the image processing functionalities, making it easier to manage and extend.

4. Graphical User Interface Development:
The ImageProcessingGUI class provides a user-friendly graphical interface, making the application more intuitive and easier to interact with. The GUI includes various interactive elements like buttons, sliders, and visual feedback mechanisms, significantly enhancing the user experience.

-- Justification for Design Choices
1. Flexibility and User Accessibility:
The diversified modes of operation (GUI, file-based, interactive CLI) cater to a wide range of users, from those who prefer graphical interfaces to those who are more comfortable with command-line interactions.

2. Reduced Coupling through Callbacks:
The use of callback functions to facilitate communication between the View and the Controller aligns with modern software design principles, reducing coupling and increasing the robustness of the application. This approach allows for easier maintenance and scalability and decreases the coupling between the Controller and the View.

3. Enhanced User Experience with GUI:
The development of a comprehensive GUI addresses the needs of users who prefer visual interactions. It makes the application more accessible, especially to those who may not be familiar with command-line operations.
   
# Running the Program/JAR File
Open a command prompt.
Navigate to the directory containing ImageProcessing.jar.
Run java -jar ImageProcessing.jar [arguments].
Select the desired mode of operation as outlined in USEME.md for the arguments to be passed.
   
# Text Files Submitted
output.txt: Contains commands for Assignments 4 and 5.

# Test Image
The image in use is clicked by us and we give legal rights to the professor amd the teaching
assistant to use it for testing purpose.
