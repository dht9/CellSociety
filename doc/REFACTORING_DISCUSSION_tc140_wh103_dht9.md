## REFACTORING DISCUSSION

**Since we didn't have a lot of refactoring that need to be discussed (changing from arraylist to list and extracting into new method), we spent our time mostly on planing ahead for the complete implmenetation**

------
Ryan Chung:

Refactoring:

duplicate code in SimulationLoop Class: 
		frame = new KeyFrame(Duration.millis(1000 / MAX_FRAMES_PER_SECOND), e -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
Duplicate code was used in constructor and start() method. Extracted to setNewTimeline method and called it in constructor and start() method. 

------
Estelle He:

Refactoring:

We decide not to change the duplicate codes because to change those codes in the cell package would involve changing the way we decide the neighbortype and edge type, which would be implemented later.

List:
We are going to change the type from arraylist to genearl list when creating the arraylist, not done yet. 

