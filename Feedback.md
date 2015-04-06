Feedback
====
* Some functionality missing such as being able to control turtle in multiple ways, a user-configurable workspace, and saving and loading sessions and preferences.
* Break functionality code out of view and put it into controller. Code interacting between model and view should not interact so deeply. View should be displaying information and passing information back to the model without caring how it is being processed or what is happening to that data.
* Remove System.out.println debug code before committing final submission.
* Avoid hard-coded strings, even when accessing Map values. They aren't compiler-checked, so a simple typo can blow everything up at Runtime, and if you want to change a name later it's nearly impossible to do so.
* I understand your reasoning behind the switch statements, but I think it could be done more cleanly. Also never hard-code strings, especially when they're used for control.


Hypothetical questions that don't impact grade
====
* Could your Views have inherited from each other? I don't know that they could have, but remember when designing even front ends that GUI components can share a lot of functionality.
* Would interfaces be useful? I again don't know that they would, but you guys used none and I find interfaces to be among the most helpful tools in Java. They are great for hiding data and testing (they really help with making Unit Tests!!).

Additional Feedback
====
* I'm kind of curious as to the thought process behind line 182 of SideBar.java.