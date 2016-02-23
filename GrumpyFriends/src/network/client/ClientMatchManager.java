package network.client;

import network.Message;
import world.World;
import character.Character;
import game.AbstractMatchManager;
import game.TurnPhaseType;


public class ClientMatchManager extends AbstractMatchManager {

    
    private Multiplayer multiplayer;
    private boolean sendMex;

    public ClientMatchManager(World battlefield) {
	super(battlefield);
    }

    
    public Multiplayer getMultiplayer() {
	return multiplayer;
    }

    
    public void setMultiplayer(Multiplayer multiplayer) {
	this.multiplayer = multiplayer;
    }

    @Override
    public void moveCurrentPlayer(int direction) {
	// currentPlayer.move(direction);
	if (isMyTurn()) {
	    // System.out
	    // .println("muovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovi");
	    if (direction == Character.LEFT)
		multiplayer.sendOperationMessage(Message.OP_MOVE_LEFT, null);
	    else
		multiplayer.sendOperationMessage(Message.OP_MOVE_RIGHT, null);
	}
    }

    @Override
    public void stopToMoveCurrentPlayer() {
	// currentPlayer.stopToMove();
	if (isMyTurn()) {
	    multiplayer.sendOperationMessage(Message.OP_STOP_MOVE, null);
	}
    }

    @Override
    public void jumpCurrentPlayer() {
	// currentPlayer.jump();
	if (isMyTurn()) {
	    multiplayer.sendOperationMessage(Message.OP_JUMP, null);
	}
    }

    @Override
    public void equipWeaponCurrentPlayer(String weaponName) {
	// currentPlayer.equipWeapon(weaponName);
	if (isMyTurn()) {
	    multiplayer.sendOperationMessage(Message.OP_EQUIP_WEAPON,
		    weaponName);
	}
    }

    @Override
    public void changeAimCurrentPlayer(int direction) {
	// currentPlayer.changeAim(direction);
	if (isMyTurn()) {
	    if (direction == Character.INCREASE_AIM)
		multiplayer.sendOperationMessage(Message.OP_INCREASE_AIM, null);
	    else if (direction == Character.DECREASE_AIM)
		multiplayer.sendOperationMessage(Message.OP_DECREASE_AIM, null);
	    else
		multiplayer.sendOperationMessage(Message.OP_STOP_AIM, null);
	}
    }

    @Override
    public void attackCurrentPlayer(float power) {
	// currentPlayer.attack(power);
	if (isMyTurn()) {
	    multiplayer.sendOperationMessage(Message.OP_ATTACK,
		    Float.toString(power));
	    currentPlayer.attack(power);
	}
    }

    @Override
    public void update() {

	if (!isPaused()
		&& !(isMatchFinished() && getCurrentTurnPhase() == TurnPhaseType.END_PHASE)) {

	    battlefield.update();

	    checkCharactersOutOfWorld();

	    if (currentPlayer.isOutWorld()) {
		timer.stopTurnTimer();
		setTurnPhase(TurnPhaseType.STARTER_PHASE);
	    }

	    if (currentTurnPhase == TurnPhaseType.STARTER_PHASE) {
		 if (isMatchFinished())
		     currentTurnPhase = TurnPhaseType.END_PHASE;
		 else
        		if (allCharacterAreSpleeping() && canStartNextTurn()) {
        		    multiplayer.sendOperationMessage(Message.SET_STATER_PHASE,null);
        		    if (isMyTurn())
        			multiplayer.sendOperationMessage(Message.CHANGE_TURN,null);
        		    startNextTurn();
		}
	    }

	    if (currentTurnPhase == TurnPhaseType.DAMAGE_PHASE) {
		// if (isAppliedDamage() && allCharacterAreSpleeping()) {
		if (!getDamagedCharacters().isEmpty()) {
		    if (canClearDamageCharacter()) {
			damagedCharacters.clear();
			setCanClearDamageCharacter(false);
			multiplayer.sendOperationMessage(
				Message.SET_DAMAGE_PHASE, null);
		    }
		} else {
		    multiplayer.sendOperationMessage(Message.SET_DAMAGE_PHASE,
			    null);
		}
		// }
	    }

	    if (currentTurnPhase == TurnPhaseType.DEATH_PHASE) {
		checkDiedCharacters();

		if (allCharacterAreSpleeping()) {

		    if (!diedCharactersOfTheCurrentTurn.isEmpty()
			    && canRemoveDeathCharacter()) {
			for (Character character : diedCharactersOfTheCurrentTurn) {
			    character.afterDeath();
			}
			// setTurnPhase(TurnPhaseType.STARTER_PHASE);
			multiplayer.sendOperationMessage(
				Message.SET_DEATH_PHASE, null);
		    } else {
			multiplayer.sendOperationMessage(
				Message.SET_DEATH_PHASE, null);
			// setTurnPhase(TurnPhaseType.STARTER_PHASE);
		    }
		}
	    }
	    // if (getCurrentTurnPhase() == TurnPhaseType.MAIN_PHASE) {
	    //
	    // if (getMatchTimer().isTurnTimerEnded()) {
	    // getCurrentPlayer().endTurn();
	    // getMatchTimer().stopTurnTimer();
	    // setTurnPhase(TurnPhaseType.STARTER_PHASE);
	    // } else if (getCurrentPlayer().attacked()
	    // && !getMatchTimer().isTurnTimerStopped()) {
	    // getMatchTimer().startAttackTimer();
	    // getMatchTimer().stopTurnTimer();
	    //
	    // } else if (getMatchTimer().isTurnTimerStopped()
	    // && getMatchTimer().isAttackTimerEnded()) {
	    // getCurrentPlayer().endTurn();
	    // setTurnPhase(TurnPhaseType.DAMAGE_PHASE);
	    // }
	    // }
	}

    }
    
    @Override
    public String matchTimerLeft() {
        return matchTimerString;
    }
    @Override
    public String turnTimerLeft() {
        return turnTimerString;
    }
    
    private boolean isMyTurn() {
	if (multiplayer.isAChooser() && currentTeam == teamB)
	    return true;
	else if (!multiplayer.isAChooser() && currentTeam == teamA)
	    return true;
	return false;
    }
}
