package physicEngine;

import world.Vector;
import element.Element;

public class MovesThread extends Thread {
	
	private PhysicEngine physicEngine = PhysicEngine.getInstace();
	private MovableElement elementToMove;
	private long time0,time;
	private Vector speed0;
	private Vector position0;
	
	public MovesThread(MovableElement element) {
		elementToMove = element;
		speed0 = new Vector(elementToMove.getSpeed());
		position0= new Vector(element.getPosition());
		time0 = System.currentTimeMillis();
	}

	@Override
	public void run() {
		
	}
}
