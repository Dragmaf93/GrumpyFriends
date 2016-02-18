package game;

import utils.ObjectWithTimer;

public class MatchTimer {

	private final static long MATCH_DURATION_MILLIS = 1200000;
	private final static long TURN_DURATION_MILLIS = 60000;
	private final static long ATTACK_TIMER_DURATION = 5000;

	private static long initialPause;
	private static long endPause;

	private long matchPauseTime;
	private long turnPauseTime;
	private long attackPauseTime;

	private long initialMatchTime;
	private long initialTurnTime;

	private boolean endTurn;
	private long currentTurnTime;
	
	private long initialAttackTimer;
	private boolean stopTurn;

	private boolean pause;
	private static long initialObjectTimer;
	private static long pauseObjectTime;
	
	private static ObjectWithTimer currentObjectWithTimer;
	
	public void startMatchTimer() {
		initialMatchTime = System.currentTimeMillis();
		matchPauseTime = 0;
	}

	public void pauseTimers() {
		initialPause = System.currentTimeMillis();
		pause=true;
	}
	public boolean isPaused(){
		return pause;
	}
	public void restartPausedTimers() {
		pause=false;
		endPause = System.currentTimeMillis();
		matchPauseTime += endPause - initialPause;
		turnPauseTime += endPause - initialPause;
		attackPauseTime+=endPause -initialPause;
		
		pauseObjectTime+=endPause - initialPause;
		endPause = initialPause = 0;
	}

	public void restartTimers() {
	}
	
	public void startAttackTimer(){
		initialAttackTimer=System.currentTimeMillis();
		System.out.println(initialAttackTimer);
		attackPauseTime=0;
	}
	
	public void startTurnTimer() {
		initialTurnTime = System.currentTimeMillis();
		turnPauseTime = 0;
		endTurn= stopTurn=false;
		currentTurnTime=0;
	}

	public void stopTurnTimer() {
		endTurn = true;
		stopTurn = true;
		currentTurnTime = TURN_DURATION_MILLIS - (currentTurnTime - initialTurnTime - turnPauseTime);

	}

	public int endTurnIn() {
		if (!endTurn) {
			long currentTurnTime = System.currentTimeMillis();

			return toSeconds(TURN_DURATION_MILLIS - (currentTurnTime - initialTurnTime - turnPauseTime));
		}
		else return toSeconds(currentTurnTime);
	}

	public int endMatchIn() {
		long currentMatchTime = System.currentTimeMillis();

		return toSeconds(MATCH_DURATION_MILLIS - (currentMatchTime - initialMatchTime - matchPauseTime));
	}
	
	public boolean isTurnTimerEnded(){
		return endTurnIn()<=0;
	}
	
	public boolean isMatchTimerEnded(){
		return endMatchIn()<=0;
	}
	
	public int endAttackTimerIn(){
		long currentTime = System.currentTimeMillis();
		int t = toSeconds(ATTACK_TIMER_DURATION - (currentTime - initialAttackTimer - attackPauseTime));
		
		if (t<=0) return 0;
		return t;
	}
	
	public String attackTimerStringFormat(){
		String tmp=Integer.toString(endAttackTimerIn());
		return tmp;
	}
	public int minutesToEndMatch() {
		return endMatchIn() / 60;
	}

	public int secondsToEndMatch() {
		return endMatchIn() % 60;
	}

	public String matchTimerStringFormat() {
		String tmp = "";
		tmp += minutesToEndMatch();
		tmp += ":";
		tmp += secondsToEndMatch();
		return tmp;
	}

	public String turnTimerStringFormat() {
		String tmp = Integer.toString(endTurnIn());
		return tmp;
	}

	public static int toSeconds(long millis) {
		return (int) (millis / 1000);
	}

	public boolean isTurnTimerStopped() {
		return stopTurn;
	}
	
	public static void startTimer(ObjectWithTimer objectWithTimer){
		currentObjectWithTimer = objectWithTimer;
		initialObjectTimer=System.currentTimeMillis();
		pauseObjectTime=0;
	}
	
	public static int endObjectTimerIn(){
		long currentTime = System.currentTimeMillis();
		long endTimer = currentObjectWithTimer.getSecondToStopTimer();
		
		return toSeconds(endTimer - (currentTime -initialObjectTimer -pauseObjectTime));
	}

	public boolean isAttackTimerEnded() {
		return endAttackTimerIn()<=0;
	}
	
}
	
