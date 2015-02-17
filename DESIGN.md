
##    Introduction

##    Overview

##    User Interface

##   Design Details
    
##   API Example Code
	
*The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail.*    

After the user finishes typing the command in the command window, they click a button/press enter in the window.  This button's onClick() method is called, which is a lambda expression that calls the Controller Object's .parse(String s) with the string typed into the box by the user as the argument s.  The controller then calls the model's .parse(String s) method, which uses a regular expression to split the string into a command format.  From here, a specific Command object will be instantiated depending on which command is called - commands that do not need to update the turtle/turtle's position will be treated differently than commands which update the model, i.e. creating variables.  In this case, a Forward object will be instantiated, whose .execute() method will be called to update the turtle's position.  The .execute method returns a TurtleInfo object, which contains the specific turtle's unique id, old x-coordinate, old y-coordinate, old heading, new x-coordinate, new y-coordinate, new heading, and whether the pen was down.  This TurtleInfo object is returned to model.parse(), which adds the TurtleInfo object to a list of TurtleInfo objects.  The model.parse() method calls view.update(list<TurtleInfo>) with the newly created list.  

##    Design Considerations

##    Team Responsibilities

