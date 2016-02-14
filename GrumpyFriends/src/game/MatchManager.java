package game;

import java.util.List;

import javafx.geometry.Point2D;
import world.World;
import character.Character;
import character.Team;

public interface MatchManager {

	abstract public boolean startMatch();

	abstract public void endMainPhase();

	abstract public void applyDamageToHitCharacter();

	abstract public List<Character> getDamagedCharacters();

	abstract public boolean allCharacterAreSpleeping();

	abstract public void startNextTurn();

	abstract public void pauseMatch();

	abstract public void restartMatch();

	abstract public Character getCurrentPlayer();

	abstract public void setCurrentPlayer(Character currentPlayer);

	abstract public World getBattlefield();

	abstract public void setBattlefield(World battlefield);

	abstract public Team getTeamA();

	abstract public void setTeamA(Team teamA);

	abstract public Team getTeamB();

	abstract public void setTeamB(Team teamB);

	abstract public int getTurn();

	abstract public boolean isTheCurrentTurnEnded();

	abstract public Team getCurrentTeam();

	abstract public void stopTurnTimer();

	abstract public void startTest();

	abstract public World getWorld();

	abstract public MatchTimer getMatchTimer();

	abstract public boolean isMatchFinished();

	abstract public void swapTeam();

	abstract public void checkDiedCharacters();

	abstract public Team getTeamWithMaxLifePoints();

	abstract public List<Character> getDiedCharacters();

	abstract public boolean isAppliedDamage();

	abstract public TurnPhaseType getCurrentTurnPhase();

	abstract public void setTurnPhase(TurnPhaseType phase);

	abstract public Team getWinnerTeam();

	abstract public Team getLoserTeam();

	abstract boolean isPaused();
	abstract public boolean hasWinnerTeam();

	abstract public void checkCharactersOutOfWorld();
	
	abstract public void moveCurrentPlayer(int direction);
	abstract public void stopToMoveCurrentPlayer();
	abstract public void jumpCurrentPlayer();
	abstract public void equipWeaponCurrentPlayer(String weaponName);
	abstract public void changeAimCurrentPlayer(int direction);
	abstract public void attackCurrentPlayer(float power);

	public abstract Character nextPlayer();

	public abstract void restartPausedMatch();
}
