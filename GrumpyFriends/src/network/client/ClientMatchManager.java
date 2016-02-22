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
			System.out
					.println("muovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovi");
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
				System.out.println("STARTER "+ canStartNextTurn +" sleep "+allCharacterAreSpleeping()
						+" mex "+sendMex);
//				if (isMatchFinished())
//					currentTurnPhase = TurnPhaseType.END_PHASE;
//				else 
					if (allCharacterAreSpleeping() && canStartNextTurn()
						) {
					multiplayer.sendOperationMessage(Message.START_NEXT_TURN,
							null);
					if(isMyTurn())
						multiplayer.sendOperationMessage(Message.CHANGE_TURN, null);
					System.out.println("ciao");
					startNextTurn();
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
			// // System.out.println("MAIN PHASE");
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

	private boolean isMyTurn() {
		System.out.println("Current team   " + currentTeam.getName()
				+ "  teamB " + teamB.getName() + " teamA " + teamA.getName());
		if (multiplayer.isAChooser() && currentTeam == teamB)
			return true;
		else if (!multiplayer.isAChooser() && currentTeam == teamA)
			return true;
		return false;
	}
}
