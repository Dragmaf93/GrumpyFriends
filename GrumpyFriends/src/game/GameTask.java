package game;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public abstract class GameTask{
	
	private Task<Void> task;
	
	public GameTask() {
	}

	public void startToWork(){
		task = new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				work();
				return null;
			}
		};
		
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent arg0) {
				afterWork();
			}
		});
		
		new Thread(task).start();
	}
	
	abstract protected void work();
	abstract protected void afterWork();
}
