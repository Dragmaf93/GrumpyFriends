package game;

import character.Character;
import character.Team;
import utils.ObjectWithTimer;
import utils.Timer;
import world.World;

public class MatchManager implements ObjectWithTimer {

	private World battlefield;
	private Team teamA;
	private Team teamB;

	private Character currentPlayer;
	private Team currentTeam;

	private Timer turnTimer;

	private int turn;

	boolean started;

	public MatchManager() {
		this.battlefield = null;
		this.teamA = null;
		this.teamB = null;
		this.currentPlayer = null;
		this.turn = 0;
		this.started = false;
	}

	public MatchManager(World battlefield, Team teamA, Team teamB) {
		this.battlefield = battlefield;
		this.teamA = teamA;
		this.teamB = teamB;
		this.turn = 0;
		this.currentPlayer = null;
		this.started = false;
	}

	public MatchManager(World battlefield) {
		this.battlefield = battlefield;
		this.teamA = null;
		this.teamB = null;
		this.turn = 0;
		this.currentPlayer = null;
		this.started = false;
	}

	public boolean startMatch() {
		if (started || teamA == null || teamB == null || battlefield == null)
			return false;

		currentTeam = getFirstTeam();

		teamA.setUpForTheMatch();
		teamB.setUpForTheMatch();

		currentPlayer = currentTeam.nextPlayer();

		turn = 1;
		turnTimer = new Timer(this);
		turnTimer.start();

		started = true;

		return true;
	}

	public void nextTurn() {
		if (started) {
			if (currentTeam == teamA)
				currentTeam = teamB;
			else if (currentTeam == teamB)
				currentTeam = teamA;

			currentPlayer = currentTeam.nextPlayer();
			turn++;
			turnTimer = new Timer(this);
			turnTimer.start();
		}
	}

	public void restartMatch() {

	}

	public Character getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Character currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public World getBattlefield() {
		return battlefield;
	}

	public void setBattlefield(World battlefield) {
		this.battlefield = battlefield;
	}

	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	private Team getFirstTeam() {
		if (Math.random() <= 0.5)
			return teamA;
		return teamB;
	}

	public int getTurn() {
		return turn;
	}

	public Team getCurrentTeam() {
		return currentTeam;
	}

	public void stopTurnTimer() {
		if (turnTimer != null)
			turnTimer.forceToStop();
		turnTimer = null;
	}

	public int getTimer() {
		if (turnTimer != null)
			return turnTimer.getSeconds();
		return 0;
	}

	@Override
	public long getSecondToStopTimer() {
		return 60;
	}

	@Override
	public void afterCountDown() {
		nextTurn();
	}

	public void startTest() {
		currentTeam = teamA;
		currentPlayer = teamA.get(0);
	}

	public World getWorld() {
		return battlefield;
	}

}
