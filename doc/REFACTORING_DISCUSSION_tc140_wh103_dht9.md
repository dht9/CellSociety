# INHERITANCE REVIEW

## PART 1

1. Visualization implementation (front-end) uses parameters given from XML Reader and Cell Manager classes and displays the cells and their changes on grid.
2. I do not have any inheritance hierarchies in visualization
3. All are closed within my area which are simulation and visualization. My area only receives parameters from XML Reader and Cell Manager.
4, Null Pointer Exceptions (when using nodes for visualization). Will have to fix bug.
5. I have multiple classes, each classes designated per concept. Inside classes, code and method are concise, and variable names follow naming convention. No duplicate code. Tried to minimize number of global variables by just passing them as parameters.

## PART 2

1. My area receives information from XML Reader class to initialize grid using parameters from the loaded XML. It also receives information from Cell Manager Class on cell's updated row, column, and state. 
2. Yes. The Cell Manager is a class out of my area which gives information about updated cell according to the type of game.XML Reader is also another class which gives information about initial cell position and other parameters such as colormaps. 
3. These are necessary dependencies, but by using multiple classes, I can only use dependency only on a class which requires it. 
4. I do not have a super/sub class in my area of implementation.

## PART 3

1. slider to change speed of animation, changing color of cell, button to start/pause/step, putting initial states on grid, XML file loader.
2. creating slider to change speed of animation.
3. changing color of cell.