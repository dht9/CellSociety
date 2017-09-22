# Interitance Review Questions

#### Discussion Participants:
  * David Tran [dht9], Paulo Flecha [pf51], Estelle He [wh103], Ryan Chung [tc140]

## Part 1

1. The XML configuration implementation defines all of the parameters of the simulation and the initial states of the cells.
2.  I cannot think of any inheritance hierarchies for the XML reader.
3. The XML file is open because the parameters can be changed. The XML reader is closed because it finds the same tags for each file, even if some XML files don't have the same parameters.
4. NullPointerException, BadFilePath, empty file. We will throw an exception and open a window that says the file has an error.
5. A good design would be one that can read all of the four XML file cases and extract data even if the data changes. 

## Part 2
1. My area extracts and supplies information used by the simulation and visualization.
2. These dependencies are based on the other  class's behaviors or implementation because the other class's will need to request information of a certain type and format.

(Could not finish the rest)


