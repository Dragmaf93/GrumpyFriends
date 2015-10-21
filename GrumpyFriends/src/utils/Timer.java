package utils;

public class Timer extends Thread {

	private ObjectWithTimer objectWithTimer;
	private long time0, currentTime, endTimer;
	private boolean stoped;

	public Timer(ObjectWithTimer objectWithTimer) {
		this.objectWithTimer = objectWithTimer;
		currentTime = 0;
		stoped = false;
		time0 = System.currentTimeMillis() / 1000;
		endTimer = objectWithTimer.getSecondToStopTimer();
	}

	final public void forceToStop() {
		stoped = true;
	}

	@Override
	public void run() {
		while (currentTime < endTimer) {
			currentTime = System.currentTimeMillis() / 1000 - time0;
			
			if(stoped) break;
		}
		if (!stoped)
			objectWithTimer.afterCountDown();
	}

	final public int getSeconds() {
		return (int )currentTime;
	}
}
