package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Phaser;

import character.Character;
import character.Team;

import com.sun.org.apache.bcel.internal.generic.DMUL;

import javafx.scene.paint.Color;
import physic.PhysicalCharacter;
import utils.ObjectWithTimer;
import world.World;

public abstract class AbstractMatchManager implements MatchManager{

	protected World battlefield;
	protected Team teamA;
	protected Team teamB;

	protected Character currentPlayer;
	protected Team currentTeam;

	protected int turn;

	boolean started;
	boolean pause;

	protected boolean appliedDamage;
	protected MatchTimer timer;

	protected boolean endTurn;
	
	protected boolean canStartNextTurn;
	
	protected HashMap<String, Float> hitCharacters;
	protected List<Character> damagedCharacters;

	protected List<Character> diedCharactersOfTheCurrentTurn;

	protected TurnPhaseType currentTurnPhase;
	protected boolean characterSufferedDamage;
	protected boolean canRemoveCharacter;
	protected boolean canClearDamageCharacter;
	
	public AbstractMatchManager() {
		this.battlefield = null;
		this.teamA = null;
		this.teamB = null;
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
		this.teamA = teamA;
		this.teamB = teamB;
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
		this.teamA = null;
		this.teamB = null;
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
		if (started || teamA == null || teamB == null
				|| battlefield == null)
			return false;
		
		
		
//		currentTeam = getFirstTeam();

		teamA.setUpForTheMatch();
		teamB.setUpForTheMatch();

		
		currentTeam = teamA;
		
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
		List<Character> characters = teamA.getCharactersInGame();

		for (Character character : characters) {
			if (!character.isSleeping()) {
				return false;
			}
		}

		characters = teamB.getCharactersInGame();
		for (Character character : characters) {
			if (!character.isSleeping()) {
				return false;
			}
		}

		return true;
	}
	@Override
	public void setCanStartNextTurn(boolean b) {
		canStartNextTurn=b;
	}
	@Override
	public boolean canStartNextTurn() {
		return canStartNextTurn;
	}
	
	@Override
	public boolean canRemoveDeathCharacter() {
		return canRemoveCharacter;
	}
	
	@Override
	public void setCanRemoveDeathCharacter(boolean can) {
		canRemoveCharacter = can;
	}
	public void startNextTurn() {
		if (started) {
			if (currentTeam == teamA)
				currentTeam = teamB;
			else if (currentTeam == teamB)
				currentTeam = teamA;
			canStartNextTurn=false;
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
		
		teamA.reset();
		teamB.reset();
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

	protected Team getFirstTeam() {
		if (Math.random() <= 0.5)
			return teamA;
		return teamB;
	}

	public int getTurn() {
		return turn;
	}

	public boolean isCurrentTurnEnded() {
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
		currentTeam = teamA;
		currentPlayer = teamA.get(0);

	}

	public World getWorld() {
		return battlefield;
	}

	public MatchTimer getMatchTimer() {
		return timer;
	}

	public boolean isMatchFinished() {

		return teamB.isLose() || teamA.isLose()
				|| timer.isMatchTimerEnded();
	}

	public void swapTeam() {
		if (currentTeam == teamA)
			currentTeam = teamB;
		else if (currentTeam == teamB)
			currentTeam = teamA;
	}

	public void checkDiedCharacters() {
		teamB.checkDiedCharacter(diedCharactersOfTheCurrentTurn);
		teamA.checkDiedCharacter(diedCharactersOfTheCurrentTurn);
	}

	public Team getTeamWithMaxLifePoints() {

		int teamRedLF = teamA.getTeamLifePoint();
		int teamBlueLF = teamB.getTeamLifePoint();

		if (teamRedLF < teamBlueLF)
			return teamB;
		else if (teamRedLF > teamBlueLF)
			return teamA;
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
		if (currentTeam == teamA)
			team = teamB;
		else if (currentTeam == teamB)
			team = teamA;

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
		if (teamB.isLose() && !teamA.isLose())
			return teamA;
		if (teamA.isLose() && !teamB.isLose())
			return teamB;
		return null;
	}
	
	public Team getLoserTeam() {
		if (teamB.isLose() && !teamA.isLose())
			return teamB;
		if (teamA.isLose() && !teamB.isLose())
			return teamA;
		return null;
	}

	public boolean hasWinnerTeam() {
		return teamB.isLose() ^ teamA.isLose();
	}

	public void checkCharactersOutOfWorld() {
		List<Character> charactersOutOfWorld = battlefield.getCharacatersOutOfWorld();
		
		for (Character character : charactersOutOfWorld) {
			character.getTeam().removeCharacterInGame(character);
		}
		charactersOutOfWorld.clear();
	}
	
	@Override
	public boolean canClearDamageCharacter() {
		return canClearDamageCharacter;
	}
	
	@Override
	public void setCanClearDamageCharacter(boolean b) {
		canClearDamageCharacter=b;
	}

}