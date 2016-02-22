package network.client;

import network.Message;
import world.World;
import character.Character;
import game.AbstractMatchManager;

public class ClientMatchManager extends AbstractMatchManager {

	private Multiplayer multiplayer;

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
			System.out.println("muovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovimuovi");
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
		battlefield.update();
	}

	private boolean isMyTurn() {
		System.out.println("Current team   "+ currentTeam.getName() +"  teamB "+ teamB.getName()+" teamA "+teamA.getName());
		if (multiplayer.isAChooser() && currentTeam == teamB)
			return true;
		else if (!multiplayer.isAChooser() && currentTeam == teamA)
			return true;
		return false;
	}
}
