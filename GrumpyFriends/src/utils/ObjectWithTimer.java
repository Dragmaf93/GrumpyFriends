package utils;

public interface ObjectWithTimer {

	public long getSecondToStopTimer();

	public void afterCountDown();
	
	public Timer getTimer();
}
