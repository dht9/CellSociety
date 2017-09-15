# DESIGN_EXERCISE


## Inheritance Review

Brick:
* public brick(int xpos, int ypos, double width, double height) {}
* public void effect() {}

Power-ups:
* public power-up(Image image, int xpos, int ypos, double width, double height) {}
* public boolean checkingConsumed() {}
* public void implementation() {}
* public void consume() {}


## High Level Design

1. We will have different cell classes for each simulation under the same cell superclass, and each cell class will have different implementation according to different rules. Therefore, when a cell is created, it already contains the rule information in the method inside its class. 

2. A cell will get the list of its neighbors from the manager class that keeps track of all the cells created. In order to update its own state without affecting its neighbors' update, set up an variable private to each cell that store the update information for each step size. After every cell gets its update information, we will then execute the update and enter into the next step size. 

3. We will use gridplane from javafx to implement the grid in our program. It allows one node inside each block. The gridplane should be created in the setup class and handled by the setup class. 

4. The configuration file should contains the name/type of the simulation, the parameters set specifically for each simulation, and the initial state of the cells in the grid. 

5. We will adjust the step size so that the change in display would be obvious after each update. 


## CRC cards
superclass cell with four class implementation --- relates to manager
* constructor
* status return method
* rules implementation methods


manager class --- relates to manager and setup classes
* constructor
* find neighbors and return a list of neighboring cells
* move the cell inside the grid
* create and add cells to the list. 

setup class --- relates to userInput and manager
* setup scene
* setup timeline

userInput class --- relates to setup class
* handle buttons
* loading the configuration file
* reading the configuration file

main class --- relates to setup
* start method
* launch method


