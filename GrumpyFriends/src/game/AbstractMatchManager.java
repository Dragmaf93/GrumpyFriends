package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Phaser;

import com.sun.org.apache.bcel.internal.generic.DMUL;

import character.Character;
import character.Team;
import javafx.scene.paint.Color;
import physic.PhysicalCharacter;
import utils.ObjectWithTimer;
import world.World;

public abstract class AbstractMatchManager implements MatchManager{

	protected World battlefield;
	protected Team teamRed;
	protected Team teamBlue;

	protected Character currentPlayer;
	protected Team currentTeam;

	protected int turn;

	boolean started;
	boolean pause;

	boolean appliedDamage;
	protected MatchTimer timer;

	protected boolean endTurn;
	protected HashMap<String, Float> hitCharacters;
	protected List<Character> damagedCharacters;

	protected List<Character> diedCharactersOfTheCurrentTurn;

	protected TurnPhaseType currentTurnPhase;
	protected boolean characterSufferedDamage;
	
	public AbstractMatchManager() {
		this.battlefield = null;
		this.teamRed = null;
		this.teamBlue = null;
		this.currentPlayer = null;
		this.turn = 0;
		this.started = false;
		appliedDamage = false;
		timer = new MatchTimer();
		damagedCharacters = new ArrayList<Character>();
		diedCharactersOfTheCurrentTurn = new ArrayList<Character>();
	}

	public AbstractMatchManager(World battlefield, Team teamA, Team teamB) {
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
		diedCharactersOfTheCurrentTurn = new ArrayList<Character>();
		hitCharacters = battlefield.getHitCharacter();
		damagedCharacters = new ArrayList<Character>();
	}

	public AbstractMatchManager(World battlefield) {
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
		diedCharactersOfTheCurrentTurn = new ArrayList<Character>();
	}
	
	public boolean startMatch() {
		if (started || teamRed == null || teamBlue == null
				|| battlefield == null)
			return false;
		
		
		
//		currentTeam = getFirstTeam();

		teamRed.setUpForTheMatch();
		teamBlue.setUpForTheMatch();

		
		currentTeam = teamRed;
		
		currentPlayer = currentTeam.nextPlayer();

		turn = 1;

		timer.startMatchTimer();
		timer.startTurnTimer();

		started = true;
		endTurn = false;

		currentTurnPhase = TurnPhaseType.MAIN_PHASE;
		
		return true;
	}

	public void endMainPhase() {
		if (endTurn)
			return;
		currentPlayer.endTurn();
		endTurn = true;
	}

	public void applyDamageToHitCharacter() {
		if (!appliedDamage) {
			if (!hitCharacters.isEmpty()) {
				Set<String> keys = hitCharacters.keySet();
				for (String name : keys) {
					Character c = battlefield.getCharacter(name);
					if (c != null) {
						c.decreaseLifePoints((int) hitCharacters.get(name)
								.floatValue());
						damagedCharacters.add(c);
					}
				}
				hitCharacters.clear();
				appliedDamage = true;
				characterSufferedDamage = true;
			} else
				characterSufferedDamage = false;
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
			diedCharactersOfTheCurrentTurn.clear();
			damagedCharacters.clear();
			turn++;
			timer.startTurnTimer();
			endTurn = false;
			currentTurnPhase = TurnPhaseType.MAIN_PHASE;
			appliedDamage = false;
			characterSufferedDamage = false;
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
		
		teamRed.reset();
		teamBlue.reset();
		battlefield.reset();
		
		currentTeam = getFirstTeam();

		currentPlayer = currentTeam.nextPlayer();
		
		turn = 1;

		timer.startMatchTimer();
		timer.startTurnTimer();

		started = true;
		endTurn = false;
		pause=false;

		currentTurnPhase = TurnPhaseType.MAIN_PHASE;
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

	protected Team getFirstTeam() {
		if (Math.random() <= 0.5)
			return teamRed;
		return teamBlue;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isTheCurrentTurnEnded() {
		return currentTurnPhase != TurnPhaseType.MAIN_PHASE;
	}

	public Team getCurrentTeam() {
		return currentTeam;
	}

	public void stopTurnTimer() {
		timer.stopTurnTimer();
	}

	public void startTest() {
		// timer.startMatchTimer();
		// timer.startTurnTimer();
		currentTeam = teamRed;
		currentPlayer = teamRed.get(0);

	}

	public World getWorld() {
		return battlefield;
	}

	public MatchTimer getMatchTimer() {
		return timer;
	}

	public boolean isMatchFinished() {

		return teamBlue.isLose() || teamRed.isLose()
				|| timer.isMatchTimerEnded();
	}

	public void swapTeam() {
		if (currentTeam == teamRed)
			currentTeam = teamBlue;
		else if (currentTeam == teamBlue)
			currentTeam = teamRed;
	}

	public void checkDiedCharacters() {
		teamBlue.checkDiedCharacter(diedCharactersOfTheCurrentTurn);
		teamRed.checkDiedCharacter(diedCharactersOfTheCurrentTurn);
	}

	public Team getTeamWithMaxLifePoints() {

		int teamRedLF = teamRed.getTeamLifePoint();
		int teamBlueLF = teamBlue.getTeamLifePoint();

		if (teamRedLF < teamBlueLF)
			return teamBlue;
		else if (teamRedLF > teamBlueLF)
			return teamRed;
		//in caso di pareggio null
		return null;

	}
	
	public List<Character> getDiedCharacters() {
		return diedCharactersOfTheCurrentTurn;
	}

	protected void endMatch() {
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

	public TurnPhaseType getCurrentTurnPhase() {
		return currentTurnPhase;
	}

	public void setTurnPhase(TurnPhaseType phase) {
		currentTurnPhase = phase;
//		System.out.println(currentTurnPhase);
	}

	public Team getWinnerTeam() {
		if (teamBlue.isLose() && !teamRed.isLose())
			return teamRed;
		if (teamRed.isLose() && !teamBlue.isLose())
			return teamBlue;
		return null;
	}
	
	public Team getLoserTeam() {
		if (teamBlue.isLose() && !teamRed.isLose())
			return teamBlue;
		if (teamRed.isLose() && !teamBlue.isLose())
			return teamRed;
		return null;
	}

	public boolean hasWinnerTeam() {
		return teamBlue.isLose() ^ teamRed.isLose();
	}

	public void checkCharactersOutOfWorld() {
		List<Character> charactersOutOfWorld = battlefield.getCharacatersOutOfWorld();
		
		for (Character character : charactersOutOfWorld) {
			character.getTeam().removeCharacterInGame(character);
//			if(character.getTeam()==teamBlue)
//				teamBlue.removeCharacterInGame(character);
//			else if(character.getTeam()==teamRed)
//				teamRed.removeCharacterInGame(character);
		}
		charactersOutOfWorld.clear();
	}

}