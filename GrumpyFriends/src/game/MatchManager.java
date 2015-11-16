package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.DMUL;

import character.Character;
import character.Team;
import javafx.scene.paint.Color;
import physic.PhysicalCharacter;
import utils.ObjectWithTimer;
import world.World;

public class MatchManager {

	private World battlefield;
	private Team teamRed;
	private Team teamBlue;

	private Character currentPlayer;
	private Team currentTeam;

	private int turn;

	boolean started;
	boolean pause;

	boolean appliedDamage;
	private MatchTimer timer;

	private boolean endTurn;
	private HashMap<String, Float> hitCharacters;
	private List<Character> damagedCharacters;

	public MatchManager() {
		this.battlefield = null;
		this.teamRed = null;
		this.teamBlue = null;
		this.currentPlayer = null;
		this.turn = 0;
		this.started = false;
		appliedDamage = false;
		timer = new MatchTimer();
		damagedCharacters = new ArrayList<Character>();
	}

	public MatchManager(World battlefield, Team teamA, Team teamB) {
		this.battlefield = battlefield;
		this.teamRed = teamA;
		this.teamBlue = teamB;
		this.turn = 0;
		this.currentPlayer = null;
		this.started = false;
		appliedDamage = false;
		timer = new MatchTimer();
		// teamBlue.setColorTeam(Color.BLUE);
		// teamRed.setColorTeam(Color.RED);
		hitCharacters = battlefield.getHitCharacter();
		damagedCharacters = new ArrayList<Character>();
	}

	public MatchManager(World battlefield) {
		this.battlefield = battlefield;
		this.teamRed = null;
		this.teamBlue = null;
		this.turn = 0;
		this.currentPlayer = null;
		hitCharacters = battlefield.getHitCharacter();
		this.started = false;
		timer = new MatchTimer();
		hitCharacters = battlefield.getHitCharacter();
		appliedDamage = false;
		damagedCharacters = new ArrayList<Character>();
	}

	public boolean startMatch() {
		if (started || teamRed == null || teamBlue == null || battlefield == null)
			return false;

		currentTeam = getFirstTeam();

		teamRed.setUpForTheMatch();
		teamBlue.setUpForTheMatch();

		currentPlayer = currentTeam.nextPlayer();

		turn = 1;

		timer.startMatchTimer();
		timer.startTurnTimer();

		started = true;
		endTurn = false;

		return true;
	}

	public void endTurn() {
		if (endTurn)
			return;
		currentPlayer.endTurn();
		endTurn = true;
		appliedDamage = false;
	}

	public void applyDamageToHitCharacter() {
		if (!appliedDamage) {
			if (!hitCharacters.isEmpty()) {
				Set<String> keys = hitCharacters.keySet();
				for (String name : keys) {
					Character c = battlefield.getCharacter(name);
					if (c != null) {
						c.decreaseLifePoints((int) hitCharacters.get(name).floatValue());
						damagedCharacters.add(c);
					}
				}
				hitCharacters.clear();
			}
			appliedDamage = true;
		}
	}

	public List<Character> getDamagedCharacters() {
		return damagedCharacters;
	}

	public boolean allCharacterAreSpleeping() {
		List<Character> characters = teamRed.getCharactersInGame();

		for (Character character : characters) {
			if (!character.isSleeping()) {
				return false;
			}
		}

		characters = teamBlue.getCharactersInGame();
		for (Character character : characters) {
			if (!character.isSleeping()) {
				return false;
			}
		}

		return true;
	}

	public void startNextTurn() {
		if (started) {
			if (currentTeam == teamRed)
				currentTeam = teamBlue;
			else if (currentTeam == teamBlue)
				currentTeam = teamRed;

			
			currentPlayer = currentTeam.nextPlayer();
			currentPlayer.prepareForTurn();
			damagedCharacters.clear();
			turn++;
			timer.startTurnTimer();
			endTurn = false;
			appliedDamage = false;
			
			
		}
	}

	public void pauseMatch() {
		pause = true;
		timer.pauseTimers();
	}

	public void restartPausedMatch() {
		pause = false;
		timer.restartPausedTimers();
	}

	public void restartMatch() {
		timer.restartTimers();
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
		return teamRed;
	}

	public void setTeamA(Team teamA) {
		this.teamRed = teamA;
	}

	public Team getTeamB() {
		return teamBlue;
	}

	public void setTeamB(Team teamB) {
		this.teamBlue = teamB;
	}

	private Team getFirstTeam() {
		if (Math.random() <= 0.5)
			return teamRed;
		return teamBlue;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isTheCurrentTurnEnded() {
		return endTurn;
	}

	public Team getCurrentTeam() {
		return currentTeam;
	}

	public void stopTurnTimer() {
		timer.stopTurnTimer();
	}

	public void startTest() {
//		timer.startMatchTimer();
//		timer.startTurnTimer();
		currentTeam = teamRed;
		currentPlayer = teamRed.get(0);

	}

	public World getWorld() {
		return battlefield;
	}

	public MatchTimer getMatchTimer() {
		return timer;
	}

	public void update() {
		if (!pause) {

			if (timer.endTurnIn() <= 0) {
				endTurn();
//				timer.pauseTimers();
			}

			if (timer.endMatchIn() <= 0) {
				endMatch();
			}

			if (currentPlayer.attacked() && !timer.isTurnTimerStopped()) {
				timer.startAttackTimer();
				timer.stopTurnTimer();
//				timer.pauseTimers();
			}
			if (timer.isTurnTimerStopped() && timer.endAttackTimerIn() <= 0) {
				endTurn();
				applyDamageToHitCharacter();
			}

			// if (currentPlayer.sufferedDamage()) {
			// endTurn();
			// }

		}
	}

	private void endMatch() {
		// TODO Auto-generated method stub

	}

	public boolean isAppliedDamage() {
		return appliedDamage;
	}

	public Character nextPlayer() {
		Team team = null;
		if (currentTeam == teamRed)
			team = teamBlue;
		else if (currentTeam == teamBlue)
			team = teamRed;

		return team.whoIsNextPlayer();
	}

	public boolean isPaused() {
		return pause;
	}

}
