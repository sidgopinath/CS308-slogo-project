Sunjeev is working on the front-end, but he walked us through the back-end as best as he could.
For him, input is parased through a reader which generates a Token(?) using a TokenFactory. This includes things like variables, commands, lists, etc.
Token is an interface with subclasses of constants, variables, lists, commands, etc
The view gets those tokens and then updates it accordingly. 

Janan is working on the parser. There is a manager class that communicates between the view and the model.
The view takes in the string and then passes it to the manager. The manager passes it to the model.
The parser breaks apart the user input into commands and then asks the model to call commands.
For forward 50, you would read it, make a forward object, then call the model.
Then the manager would be called to pass it to the view.
