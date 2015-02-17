
##    Introduction

##    Overview
Our SLogo implementation is centered around a model-view-controller design. We create a Main class, which handles instantiation of the Stage, the View, and the Controller, and the Model. SLogoController is the controller that handles user input, SLogoModel is the model that translates user input into instructions on how to update the view, and SLogoView is the view to update the display. SLogoController will have access to the instance of SLogoModel, SLogoModel has an instance of SLogoView, and SLogoView has an instance of SLogoController. Therefore, the flow of the program is always user --> controller --> model --> view.  This helps encapsulate each part of the design, and creates the basic framework upon which our SLogo interpreter is built.
Specifically, the view is created first ##### FILL IN DETAILS HERE
From here, the model is instantiated and passed the view as an argument. The model holds all data about the scene that is currently being displayed - the instance(s) of the Turtle object that describe the turtle, the functions and variables the user has defined, and the parser that is used to process commands. This parser is implemented as an abstraction hierarchy, with each "type" of instruction having its own class - for example, forward and backward can be grouped as one instruction type, since backward is really just a reverse forward command. All communication between the controller and the model happens through the parser method, which takes in a string and instantiates the correct instruction type. All instructions contain a .execute method, which runs the instruction and returns either a) an Instruction object containing the new turtle state, b) adds an entry to the map representing variables, or c) adds an entry to the map representing functions.
This design ensures extensibility. When a new interactive UI element is added, one only needs to create a method that handles this element in SLogoController. This method will be automatically added to a lamda function in SLogoView, because both the UI element and the method will follow a strict naming scheme. This method will then use the SLogoModel APIs to pass information to SLogoModel.
When one adds a new command to the model, he/she only has to update the SLogoModel class to have a new set of rules to translate the command into instructions to update the view or perform certain algorithms. How the model obtains information from the controller or how it communicates with the view remain the same.


### Components: 
* Main:
	* instantiates front-/backend and controller
* SLogoController:
	* monitors user input
	* passes user commands to SLogoModel
* SLogoModel:
	* Backend.
* SLogoView:
	* Frontend.
* Some utility classes:
	* Instruction: contains instructions to update the turtles. It is always instantiated by SLogoModel, and passed to SLogoView.
	* Polar: contains a pair of polar coordinates.
	* Turtle:
	* Drawer: translate Instruction to a PolyLine object. Returns false if the Instruction is to turn the turtle (distance == 0).
This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. As such, it should describe specific components you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each class's behavior, not its state. It should also include a picture of how the components are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). This section should be approximately 700-1000 words long and discuss specific classes, methods, and data structures, but not individual lines of code.
##    User Interface
In the scene, the user will be able to see the display containing the turtle in the main pane. Below it will be a textbox where the user can interactively enter a number of Logo commands. By clicking run, the IDE will run, parse and compile the code, and change the display by moving the turtle and drawing lines. After clicking run, the text in the box will automatically be erased and provide a blank slate for the user to enter in new commands. Additionally, any changed or inputted variables will appear on the right side bar with current values. A history of the strings written and run will remain in the History box also within the right side pane. 

The right side pane will function as a menu for the user, giving the user access to visualization customization and also to details on current variables and previous commands (history). In customization, the user will be able to select an image file for the turtle (which will be resized to fit a size preset by the IDE), select and change the pen color (via a choice box), and select and change the background color (via a choice box).

The turtle will be defaulted to a green triangle in the center (0,0) of the display. As it moves, lines will be drawn connecting it to its new and final values/points. 

An example of the user interface can be seen below 
![UI](http://i.imgur.com/YJuOLaO.jpg)

The user interface will also alert the user to general errors during compilation. Each error will appear as a popup box.

* If the user presses "run" without specifying commands, the user will receive an error "Please specify a command"
* If the user enters in a command that the parser does not recognize, the user will receive an error "Command not found: ______" with the specific erroneous command
* If the user enters in a command whose syntax the parser does not recognize, the user will receive an error of "Invalid syntax"
* If there is a math error (ie. user tries to divide by 0), the user will receive a "Math Exception" error
* 
##   Design Details
When Main instantiates the components, it follows this specific order: SLogoView, SLogoModel(SLogoView), SLogoController(SLogoModel). Then Main calls the setController method in SLogoView to bind the lamda functions in SLogoController to UI elements. 
When the user types in some commands and clicks run, SLogoController will send the commands as a single String to SLogoModel. SLogoModel then interprets the commands, and translate them into a List of Instruction objects. The Instruction class contains the information that SLogoView needs to update the display, for example, an integer to specify the ID of the turtle to be updated, a set of polar coordinates to specify the movement of the turtle, a boolean variable to specify pen-up or pen-down. 
The polar coordinates will be stored in instances of the Polar class, which has two variables: distance and angle. Angle varies from 0 to 360, corresponding to the angle from the vector to north (always CCW). If distance is 0, then the turtle turns around its own axis to the angle specified by Angle; otherwise, the turtle moves without turning. For example, (60,90) means move the turtle 60 pixels east. (0,180) means to turn the turtle so that it faces south.
SLogoModel then calls SLogoView.update(List<Instruction> instructions), which loops through the List of Instructions, and move the specified turtle accordingly.
	
### SLogoView:
Besides update(), SLogoView has the following public methods:
* init(): initialize the view and create all UI elements. Each element will have a unique ID. All interactive elements will be stoerd in a Map<Integer, Node> called interactives.
* clear(): re-initialize the view. All current drawings will be cleared, and one turtle will start at the origin.
* setController(Controller controller): bind the controller to UI elements.
* addTurtle(int ID): create a new turtle using a user specified image.

### SLogoModel:

### SLogoController:
The SLogoController has all the methods that handle user input. These methods are named according to the following scheme: handleUI[id]. For example, the handleUI1 is called when the UI element 1 is clicked.
    
##   API Example Code

*The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail.*    

After the users finish typing the command in the command window, they click a button/press enter in the window. This button's onClick() method is called, which is a lambda expression that calls the Controller Object's .parse(String s) with the string typed into the box by the user as the argument s. The controller then calls the model's .parse(String s) method, which uses a regular expression to split the string into a command format. From here, a specific Command object will be instantiated depending on which command is called - commands that do not need to update the turtle/turtle's position will be treated differently than commands which update the model, i.e. creating variables. In this case, a Forward object will be instantiated, whose .execute() method will be called to update the turtle's position. The .execute method returns a Instruction object, which contains the specific turtle's unique id, a Polar object, and a boolean to specify whether the pen was down. This Instruction object is returned to model.parse(), which adds the Instruction object to a list of Instruction objects. The model.parse() method calls view.update(list<Instruction>) with the newly created list.  

##    Design Considerations

##    Team Responsibilities

