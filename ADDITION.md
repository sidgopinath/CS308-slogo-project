SLOGO addition - grm19
======================

### Estimation

I think this feature will take me a little less than an hour to implement.  I will definitely need to modify the properties file that contains the command names, English.properties.  Further, I will need to aadd a field and getters/setters to the executionenvironment class, and then modify the ViewUpdater and Drawer class to handle the new window bounds.  FrontEndInstruction will also need a new case within the switch statement to handle the new commands.  I considered just adding a method to the ViewUpdater class and calling it directly from the switch case, but since it seems like bad design to have to pass the ViewUpdater into the individual instruction class in the first pace (poor encapsulation between front and back end) I chose to use the executionenvironment to handle updates instead.  This has the added advantage of enabling the backend to know what state the front end is in, and thus potentially enable future commands that require this knowledge.

### Review

The feature actually took me about an hour and fifteen minutes to implement.  I did not get it completely right the first time - the hardest part was working with the Drawer class, which has about 8 different variables that all relate to the turtle's position with very ambiguous names.  I needed to update exactly the files I thought, for a total of 5 classes changed.  However, the changes to each class were relatively minor and straightforward, outside of the Drawer class changes.

### Analysis

I feel that this exercise demonstrated my previous feelings about our code: that the backend is implemented in a very flexible way, that requires few modifications to add an instruction, that the front-end's Drawer is poorly designed and documented, and that having the turtle's in the front end introduces complexities in passing the calculated location to the front end.  On the last point, had all calculations occurred in the backend as I suggested, there would be no need to pass an additional variable indicating the window type to the front end, as the backend could simply track which window type was selected and generate coordinates for the front end based on that.
	
I believe that my update to the code that implemented the observer pattern would be very helpful for the code's design - the backend would no longer need an instance of the viewupdater passed into every class, and there would be a better separation of the front and backend.
	
If I had no idea what to do with the code, I think I would have gathered that I needed to add an instruction to the instruction class, and somehow notify the front of that change.  I actually did have minimal knowledge of the front end, and found the front-end's drawing easy to locate but hard to understand.  The actual changes were very straightforward, but changing variable and class names, adding javadoc, and changing the complex way the backend passes information to the front end would all be big steps toward making someone who has minimal familiarity with the code able to make changes easily.
