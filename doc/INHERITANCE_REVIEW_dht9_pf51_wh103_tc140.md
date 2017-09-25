# Inheritance Review Questions

#### Discussion Participants:
  * David Tran [dht9], Paulo Flecha [pf51], Estelle He [wh103], Ryan Chung [tc140]

## Part 1

1. The XML configuration implementation defines all of the parameters of the simulation and the initial states of the cells.
2.  I cannot think of any inheritance hierarchies for the XML reader other than creating a different parser subclass for each simulation type. However, that would be less efficient.
3. The XML file is open because the parameters can be changed. The XML reader is closed because it finds the same tags for each file, even if some XML files don't have the same parameters.
4. NullPointerException, BadFilePath, empty file. We will throw an exception and open a window that says the file has an error.
5. A good design would be one that can read all of the four XML file cases and extract data even if the data changes. 

## Part 2
1. My area extracts and supplies information used by the simulation and visualization.
2. These dependencies are based on the other class's behaviors or implementation because the other class's will need to request information of a certain type and format.
3. To minimize these dependencies, I will need to parse information into the most flexible structures, such as getting all the parameter values and storing them into a map, rather than use an if-else statement to parse the parameters differently for each simulation type. 
4. A pair or super/sub classes is the super Cell and the GameOfLife cell. There is room for improvement to be flexible for toroidal vs. straight edges. Right now, the cell parameter for straight edge is hard-coded in specific to GameOfLife cell. We will implement the parameter into the XML and parse it for the cell. What the classes have in common are getter methods for location and state. Also, they have an `isNeighbor` method to check if another cell is a neighbor. What varies between these two classes is the update and updateInfo classes. Updating info is specific to each subclass because they have different states and update differently.

## Part 3
