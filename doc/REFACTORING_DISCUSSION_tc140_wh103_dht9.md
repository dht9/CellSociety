Ryan Chung:

Refactoring:

duplicate code: 
		frame = new KeyFrame(Duration.millis(1000 / MAX_FRAMES_PER_SECOND), e -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
duplicate code was used in constructor and start() method. Extracted to setNewTimeline method and called it in constructor and start() method. 