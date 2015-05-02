SLogo Addition
==============
by Sid Gopinath

#Estimation
I estimate it will take around two solid hours of work to complete this new feature. The new commands that are implemented are very specific to the view and how the view handles turtle movement in our program, and I am really unsure how that works. So, it will take me some time to get familiar with the view classes before I can do this properly. And, even then, if I remember correctly, some classes are rather messy, so looking at them again will be tough.

I imagine that I will be updating four or five files at most.

#Review
It took me right around two hours to complete this feature, as predicted. 

I did not get it completely right on the first try. It took me trying three separate approaches to make it work, eventually settling on something that followed our design but was quite messy in my opinion. It used some set methods in the View class that was public to the Model, but it still felt messy and like a workaround.

I changed five files, including the properties file. The change required that I make a few changees to the View classes and a few changes to our instruction classes. I also changed what a TurtleCommand looked for a bit before realizing that I was going about this the wrong way.

#Analysis
To be honest, the project was in worse shape than I remembered. It was a nightmare to navigate the Parser and View classes. Additionally, there was a massive lack of comments that did not help me at all. That would be a great improvement.

Simply styling the code and following coding conventions would be great. I remember that being something I did in many of the model classes, but I did not get the chance to do it for the view, so there is a lot of inconsistency between the two. Method names could be clearer and comments could be much more gratuitous. 

If I weren't familiar with the code at all, it would have likely taken me six plus hours. I would have spent a very, very long time just understanding how everything was connected and where to even begin. The number of stacked methods and .get calls was really hard to follow and a valuable lesson moving forward.