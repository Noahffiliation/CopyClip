## Data Structure's in CopyClip

Contributors: Seth, Jake, Cole

Initially we implemented a vanilla straightforward approach to our data structure: an Array List. This quickly proved to be innefficient for our needs since functionality would have to be added to accomodate adding and deleting items from the buffer. It quickly became apparent that we needed a Steque (Stack-Queue) implementation.

A partial solution was written, but was proven to require too much overhead in testing, so we returned to a classic Array-List approach with added functionality outside the scope of the ArrayList data strcuture. These helper functions and logic were added into the Shared class. This was a much better approach given our project and team time constraints. 

The most important operations are the ability to reverse the list so it appears like a stack, but operates similar to a queue in removing old items. It also has the ability to grab the i index (native ArrayListFunction). 

Almost all of our operations involve saving the ArrayList in its un-reversed form. This prevents errors when reading in the ArrayList later and dealing with index off by one errors (which continued to plague early design and testing). After the ArrayList is read in from the Shared Preferences, it is reversed and delivered to the correct corrosponding fron-end list. In regards to dequeue operations, it simply removes the first item of the unreversed list by memory. 

In general, our ability to index the right location followed this general pattern: (listFromMemory.size() - 1 ) - indexFromView.

This allowed for us to access the right array location in esentially every scenario besides a view edge cases involving unpinning.