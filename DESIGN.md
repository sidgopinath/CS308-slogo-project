
##    Introduction

##    Overview
### Components: 
* Main:
	* instantiates front-/backend and controller
* SLogoController:
	* monitors user input
	* passes user commands to SLogoModel
*	SLogoModel:
	* Backend.
* SLogoView:
	* Frontend.
* Some utility classes:
	* Instruction: contains instructions to update the turtles. It is always instantiated by SLogoModel, and passed to SLogoView.
	* Polar: contains a pair of polar coordinates.
	* Turtle:
	* Drawer: translate Instruction to a PolyLine object. Returns false if the Instruction is to turn the turtle (distance == 0).
##    User Interface

##   Design Details
When Main instantiates the components, it follows this specific order: SLogoView, SLogoModel(SLogoView), SLogoController(SLogoModel). Then Main calls the setController method in SLogoView to bind the lamda functions in SLogoController to UI elements. 
When the user types in some commands and clicks run, SLogoController will send the commands as a single String to SLogoModel. SLogoModel then interprets the commands, and translate them into a List of Instruction objects. The Instruction class contains the information that SLogoView needs to update the display, for example, an integer to specify the ID of the turtle to be updated, a set of polar coordinates to specify the movement of the turtle, a boolean variable to specify pen-up or pen-down. 
The polar coordinates will be stored in instances of the Polar class, which has two variables: distance and angle. Angle varies from 0 to 360, corresponding to the angle from the vector to north (always CCW). If distance is 0, then the turtle turns around its own axis to the angle specified by Angle; otherwise, the turtle moves without turning. For example, (60,90) means move the turtle 60 pixels east. (0,180) means to turn the turtle so that it faces south.
SLogoModel then calls SLogoView.update(List<Instruction> instructions), which loops through the List of Instructions, and move the specified turtle accordingly. 
Besides update(), SLogoView has the following public methods:
* init(): initialize the view and create all UI elements.
* clear(): re-initialize the view. All current drawings will be cleared, and one turtle will start at the origin.
* setController(Controller controller): bind the controller to UI elements.
* addTurtle(int ID): create a new turtle using a user specified image.
    
##   API Example Code

*The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail.*    

After the users finish typing the command in the command window, they click a button/press enter in the window. This button's onClick() method is called, which is a lambda expression that calls the Controller Object's .parse(String s) with the string typed into the box by the user as the argument s. The controller then calls the model's .parse(String s) method, which uses a regular expression to split the string into a command format. From here, a specific Command object will be instantiated depending on which command is called - commands that do not need to update the turtle/turtle's position will be treated differently than commands which update the model, i.e. creating variables. In this case, a Forward object will be instantiated, whose .execute() method will be called to update the turtle's position. The .execute method returns a Instruction object, which contains the specific turtle's unique id, a Polar object, and a boolean to specify whether the pen was down. This Instruction object is returned to model.parse(), which adds the Instruction object to a list of Instruction objects. The model.parse() method calls view.update(list<Instruction>) with the newly created list.  

##    Design Considerations

##    Team Responsibilities

