# INHERITANCE REVIEW

## PART 1

1. All the movements of each cell and their state changes are hidden from any thing outside the specific cell class. Some protected fields are accessible inside the cell package, but cannot be accessed by visualization/configuration.

2. I am building a cell super class with different implementation/subclass based on rules from different simulations. This is based on the common properties of cells like their positions, states, checking-neighbor behavior and updating behavior. 

3. The closed part would be the cell manager class and neighborcell class where all methods are meant to be general cell-related methods that could be utilized by each cell subclass. The cell subclass would be open so that whenever we need to add a new implementation/simulation, we can just create a new subclass without changing the cell manager and neighborcell classes. 

4. There should be error cases when the other classes trying to create cells with incorrect parameters, it will be handled by try/catch and default settings.

5. I think a good design should be flexible to any new rules given to the simulation. For my design, I created manager class and neighborclass as utilities class that can be used as building blocks for new rules given. 

## PART 2

1. My area is linked to visualization part through the update method that returns a list of cell to visualization part to update the colors on the grid. For dependency on configuration part, I need to get initial position and state for each cell and other simulation-specific parameter from the configuration XML reader. 

2. Visualization part depends on my package(cell simulation) to calculate the current position and state for all cells for visualization. My package depends on configuration to read in information from XML reader. 

3. The dependency can be minimize through good planning and clear task delegation. If we can clearly divide all works into these three areas, then most of the information would be inside each package/area, thus cut the unnecessary interaction between different areas. 

4. We analyzed cell super class and gameOfLife subclass. All cells should be able to give information about its own state and position. It should also be able to tell whether a cell is its neighbor or not, whether it is on the edge or not. It should also be able to update its own state and position when given a list of neighborcells and empty spaces on the grid. The method/behavior that is unique to gameOfLife subclass is the way it changes each cell's state. In order words, how the rules govern what a cell should do. 

## PART 3

1. 
- In GameofLife implementation, when a cell is updated, it first check whether it is a live cell or not; then it gets neighborcell list from manager, who loop through every cell to check whether is a neughborcell or not and gives back the neighborcell list to the cell; using the neighborcell lists, the cell would be able to set its nextstate according to rules and state of cells around it. 

- In GameofLife implementation, when a cell manager is created and set to initialize, it receives necessary parameters and array of cell state and create cells according to the simulation types and cell states; then it stores those cells in a list; when the update method is called, the manager will loop through the cell list to get updated information stored in each cell; then it will loop through those cells again and execute the changes. 

- In GameOfLife implementation, when a neighborcell instance is created in each cell, it helps the cell to handle any neighbor-related issue. When a cell needs to know whether a cell is its neighbor, neighborcell instance would first generate a list of adjacent position based on the rules passed to it when the instance is created; then it will check whether a certain cell's position is in this list; then it would tell the cell whether a certain cell is its neighbor.

- In PredatorPrey implementation, when a cell is updated, it goes into different update methods based on its own type(fish/shark); then it would determine whether there is spaces around it for it to do any action; it will also checks whether it is time for it to breed or die and store that information; then it will execute those actions when called.

- In PredatorPrey implementation, when a shark/fish reaches the time for breed, it will turn itself into breedable state; then when it has empty space around it that allows it to move, it will move and leave a baby behind; after a new baby cell is created and added to the celllist, it will reset all its breed-related state to initial setting. 

2. I am most excited to summarize the common properties of all types of cell and implement them in the cell super class. 

3. I am most worried about the actual testing and debugging process for each simulation due to their differences in rules. 