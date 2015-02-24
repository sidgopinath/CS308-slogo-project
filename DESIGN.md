
##    Introduction

The SLogo program will allow users to input commands into a graphical user interface to move and manipulate an on-screen turtle in a way very similar to the Logo programming language primarily used for educational purposes. As we proceed with the coding of the program, we have several design goals we want to keep in mind for the project. This is a program that should be able to be very easily extended. This is especially the case in terms of instructions and the Turtle itself. Our program needs to be very flexible in how instructions are handled and how the Turtle is represented on-screen. Our "parser" that reads in user input needs to allow for many variations and possible future extensions.

The primary architecture of the design is very directly inspired by those design goals. We want to keep the controller rather closed, as it handles interactions between the view and the model. The basic layout of an instruction and the parser should also stay relatively closed. However, we want to easily leave the program open to more additions in terms of instructions or how the user interacts with the turtle on the view side and the back-end. We also want the Turtle object to be somewhat open, in the sense that additional parameters could be added if need be. 

##    Overview
![Diagram](http://i.imgur.com/fPicHLX.jpg)

Our SLogo implementation is centered around a model-view-controller design. We create a Main class, which handles instantiation of the Stage, the View, and the Controller, and the Model. SLogoController is the controller that handles user input, SLogoModel is the model that translates user input into instructions on how to update the view, and SLogoView is the view to update the display. SLogoController will have access to the instance of SLogoModel. SLogoModel will have access to an instance of SLogoView, and SLogoView will have access to an instance of SLogoController. Therefore, the flow of the program is always user --> controller --> model --> view.  This helps each part of the design remain distinct while also helping the other parts, and it also creates the basic framework upon which our SLogo interpreter is built.

Specifically, the view is created first and will take in information from the user as text is input in the textbox and as selections in the history, variable, or customization option panels. This information will then be transferred to the controller. This prevents the view from needing direct access to the Model. The view also has an constructor that takes in a Stage object. Main will then call view.init(), which will create a new Scene with all the UI elements included and also set the scene to the stage.

From here, the model is instantiated and passed the view as an argument.  The model holds all data about the scene that is currently being displayed - the instance(s) of the Turtle object that describe the turtle, the functions and variables the user has defined, and the parser that is used to process commands.  This parser will work with an abstraction hierarchy of instructions, with each "type" of instruction having its own class - for example, forward and backward can be grouped as one instruction type, since backward is really just a reverse forward command.  All communication between the controller and the model happens through the parser method, which takes in a string and instantiates the correct instruction type.  All math operations/boolean values are evaluated by a pre-parser, which transforms these operands into primitive values (true, false, integers).  After complex expressions have been removed, this string is then passed to the parser and used to instantiate the correct command object.  All instructions contain a .execute method, which runs the instruction and either a) returns an Instruction object containing the new turtle state, b) adds an entry to the map representing variables, or c) adds an entry to the map representing functions.  This design is easily extendable - any new instruction can simply subclass command and return a new type, or can add a new field to the instruction class.  A list of all instruction objects executed returned in this series of instructions is then passed to the view, which uses the data it contains to draw the lines and populate the history.  This implementation is flexible, as it allows more than one instruction to be executed at a time by the user. It also allows for the potential for multiple turtles to be on screen and updated at once, since the instruction class specifies the turtle that will be updated through the id field.  

Then Main instantiate the controller. The controller has all the methods that handle user input. For example, the controller will have a method that passes the commands in the command window to model as a single String when the run button is clicked. After the controller is created, Main passes the controller back to the view, which accesses specific methods in the controller through corresponding lambda functions. This is the only time when the view has access to the controller.

This design ensures extensibility. When a new interactive UI element is added, one only needs to create a method that handles this element in SLogoController. This method will be automatically added to a lamda function in SLogoView, because both the UI element and the method will follow a strict naming scheme. This method will then use the SLogoModel APIs to pass information to SLogoModel. When one adds a new command to the model, he/she only has to update the SLogoModel class to have a new set of rules to translate the command into instructions to update the view or perform certain algorithms. How the model obtains information from the controller or how it communicates with the view remain the same.

### Components: 
* Main:
	* instantiates front-end, back-end, and controller
* SLogoController:
	* monitors user input
	* passes user commands to SLogoModel
* SLogoModel:
	* Contains all backend data, including instances of the turtle object.  Also contains the instruction parser. 
* SLogoView:
	* Initialize UI.
	* Receive a list of instructions from SLogoModel to update the display.
	* Reset the display when requested.
* Some utility classes:
	* Instruction: contains instructions to update the turtles. It is always instantiated by SLogoModel, and passed to SLogoView.
	* Polar: contains a pair of polar coordinates.
	* Turtle: A class which contains the state objects for a specific turtle - its current position, whether the pen is down, its heading, and whether the turtle is showing or not.  This class does not have many  
	* Drawer: translate Instruction to a PolyLine object. Returns false if the Instruction is to turn the turtle (distance == 0).

## User Interface
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

## Design Details
When Main instantiates the components, it follows this specific order: SLogoView, SLogoModel(SLogoView), SLogoController(SLogoModel). Then Main calls the setController method in SLogoView to bind the lambda functions in SLogoController to UI elements. 
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
The SLogo model contains all data needed to parse and run SLogo commands.  The instances of the turtle objects are contained here, as well as the code parser and all state information about the view (if the pen is down, etc.)  At a lower level, this class contains methods to parse the individual instructions, as well as an abstract class Command that all other commands inherit from.  To extend a command, one simply adds a new class that inherits from command and implements its .execute method to tell the parser what need to be updated.  In the .parse method, the command's string needs to be added, and a call to view.update() made if the command impacts the view.  Further, this design inherently supports multiple turtles.  When a turtle is added, the method .addNewTurtle() is called by the controller.  This then calls a method in the view which notifies the view of a new turtle, and prompts it to ask for an image file for the turtle.  No extension beyond new commands and support for multiple turtles is currently available - however, support could be added by adding methods to be called by the controller that do specific tasks.  Having the model contain all state information and only communicating changes to the Turtle to the view minimizes data passed between classes, and helps greatly with encapsulation.

### SLogoController:
The SLogoController has all the methods that handle user input. These methods are named according to the following scheme: handleUI[id]. For example, the handleUI1 is called when the UI element 1 is clicked.
    
##   API Example Code
*The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail.*    

After the users finish typing the command in the command window, they click a button/press enter in the window. This button's onClick() method is called, which is a lambda expression that calls the Controller Object's .parse(String s) with the string typed into the box by the user as the argument s. The controller then calls the model's .parse(String s) method, which uses a regular expression to split the string into a command format. From here, a specific Command object will be instantiated depending on which command is called - commands that do not need to update the turtle/turtle's position will be treated differently than commands which update the model, i.e. creating variables. In this case, a Forward object will be instantiated, whose .execute() method will be called to update the turtle's position. The .execute method returns a Instruction object, which contains the specific turtle's unique id, a Polar object, and a boolean to specify whether the pen was down. This Instruction object is returned to model.parse(), which adds the Instruction object to a list of Instruction objects. The model.parse() method calls view.update(list<Instruction>) with the newly created list.  

##    Design Considerations
In the initial design of Slogo, we considered several different possibilities of information flow between the back-end and the front-end. 

1. In one of the first possibilities that we considered, Main would call a controller which would call the front and back ends. To pass information between front and back, both sides would contain Turtle a turtle object containing the functionality and properties of a turtle. This method would allow us to be able to keep track of the turtle's location, header/direction, and other properties in both the front and back end and allow both to be able to move and change the objects. However, in order to transfer information between these, a Turtle object (or a TurtleState object containing almost the exact same information minus a few properties that may not be needed) would be passed through the controller to update both turtles. This flow of information seemed to work but also brought the problem  increased the number of places with the exact same information (duplicated code) and would also give both sides access to almost the exact same object (but initialized separately), which did not seem to be an ideal decision. A turtle would be updated once, then a turtle state would be created containing almost exactly the same information, and then it would be passed into the front end where yet another turtle would be updated.

2. We decided that it would be best to leave the turtle object in full control by either the front-end or back-end and simply pass information into another portion. We initially designed our project so that the turtles would remain in the front end; however, this was not an ideal decision because the Turtle would have access to data/information that the back-end utilized/updated. The front-end and back end would be too dependent and linked upon each other. 

3. As a solution to the above problem, we decided to place the Turtle into the back-end. In essence, the user would type a command, and the View would pass in the string to the Model via the controller. The Model would update the turtle object and store a list of Instruction objects containing start and end coordinates (so that a line could be drawn in the View), a boolean detailing whether the pen is up or down based on the current command, whether or not the turtle is showing, and any other information such as variables that the View may need to display. Doing so this way limited the amount of information the View received (it would only receive what was necessary) and limit its access to the Turtle or other Model components.

4. We still need to develop a better method for implementing commands in the backend.  Ideally, there will be no need for if-else structures to determine a command and call the appropriate command subclass.  Even if these structures are necessary, we can better develop the command abstraction hierarchy so that it is easier to extend.  Since commands seem to either impact the turtle or the model, this may be a logical distinction for how we divide our classes. 

5.  When deciding how to implement the model, we debated what sort of object should be passed to the view extensively.  We wanted to pass as little as possible while still allowing the view to work - we considered passing a completed polyline, but realized this was too complete and would not allow the view to not draw certain sections if the pen was not down.  Instead, we passed in a series of coordinates along with a turtle id (for multiple turtle cases) and a boolean for if the view should draw those coordinates.
##    Team Responsibilities

Greg - Work primarily on backend, implementing parser for instructions and Turtle object. Secondary - help Callie with GUI

Sid - Back-end work. Helps in implementation of Turtle object and instructions. Secondary role of helping Mengchao with the controller and Main, specifically with interactions between the Controller and Model.

Callie - The view and all user interface design

Mengchao - Controller and interaction between view and controller

