###Estimation: 

The front-end changes to allow the user to view all turtles at once and edit them from a new view may take around 1-2 hours. I will need to review adding a new stage/scene using JavaFX (there is currently a SceneSetter class that may help speed up the process; this will require changes in SLogoView to display a new stage), update the “New Turtle” button to add to the new view upon click, populate the view (this may require a new class to be made to display and update the Viewer), and update turtles within the view when their images are changed (perhaps with creation of a new TurtleViewer class) .

###Review: 

Completing this feature took about 1 hour, which falls in line with my estimation. I did not get it completely right on the first try and had trouble getting the scene to display the turtles initially. Perhaps a better solution would have been to add the display to the right sidebar to allow for easier dynamic updating (otherwise refreshing will be required in some instances). I updated a total of 5 files: the properties file to add the “View Turtles” text, the CustomizationBar to change uploadTurtles to a public method (to avoid duplication as the method is already written), the MainView to display the new scene, SLogoView to setup and open a TurtleViewer, and a new TurtleViewer class to display the turtle additions. 

Note: Pressed the formatter hotkeys out of habit, and it appears that I made over 800 additions in the code. The actual number of additions was much less than this and was predominantly in the TurtleViewer class.

###Analysis:

The code itself could have been better documented; however, it was generally flexible enough to make the updates relatively easy and quick to make. Some small things that I noticed were that classes that functioned like utilizes like SceneSetters could have been static, while some hardcoded variables like setFitWidth(30) are not flexible to change.

Again, if I had not been familiar with the code, it would have been difficult to determine what some of the classes or methods were doing because of the low amount of documentation. In this situation, the small number of classes made it easy to find where each new method or change should be in, but I would not consider this a pro since there was minimal abstraction that could have become a detriment for larger changes.
