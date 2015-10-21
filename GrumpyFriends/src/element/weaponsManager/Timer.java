package element.weaponsManager;


public class Timer extends Thread {

	private ObjectWithTimer objectWithTimer;
	private long time0, currentTime, endTimer;
	
	public Timer(ObjectWithTimer objectWithTimer) {
		this.objectWithTimer=objectWithTimer;
		currentTime = 0;
		time0 = System.currentTimeMillis()/1000;
		endTimer = objectWithTimer.getSecondToStopTimer();
	}
	
	@Override
	public void run() {
		while(currentTime<endTimer){
			currentTime=System.currentTimeMillis()/1000-time0;
		}
		System.out.println("BOOOOOOOOOOOOOOOOOOOOOOMMMMMMMMMMMMMMM");
		objectWithTimer.afterCountDown();
	}
}
