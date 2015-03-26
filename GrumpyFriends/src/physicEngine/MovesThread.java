package physicEngine;



public class MovesThread extends Thread {
	
	private PhysicEngine physicEngine = PhysicEngine.getInstace();
	
	public MovesThread() {
		
	}

	@Override
	public void run() {
		
		while(true){
			for (int i = 0; i < physicEngine.getElementsToMove().size();i++) {
				physicEngine.movesElement(i);
			}
			physicEngine.stopElementsMove();
	
		}
	}
}
