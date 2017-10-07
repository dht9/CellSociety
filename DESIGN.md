# UPDATED DESIGN

## High Level Design

* The simulation is mostly organized under the structure of a cell super class, which is extended by several subclasses for each specific simulation, and some utility class like neighborcell class and organizer class like cell manager class. 


* The visualization would request information contained in xml file from configuration and pass those parameters into the simulation for initialization. Then in each step loop, the visualization asks simulation to update and retrieves updated information. With those information, visualization updated the display. 


## Add new feature

* For the simulation part, adding new feature would be to create new implementation of a simulation with a new sets of rules. We would need to override the updateInfo or even the update methods that controls how the cell behaves. 

## Design Choices

* One design decision we had to make is how to treat empty spaces. We can either store them as position or create a cell instance for it. If the empty cell is a passive object that only holds the location information, it would make sense to have a position instead of a cell. It also enables the real cell to move easily since we don't need to create a new empty cell at the current spot of the cell and delete the empty cell that the cell is about to move to. However, when there are some behaviors that need to be implemented by the background cell, it does make sense to make an empty cell for the empty spaces for behavior implementation. 


* We also had to decide whether to extends the cell class to rectangle object. We actually went back and forth when we are making that decision, and at the end we decided to go with the more abstract one: the cell class that only contains information. The rectangle object would have some built-in methods that we do not need to implement on our own, but it also limits the flexibility of the whole program. 
