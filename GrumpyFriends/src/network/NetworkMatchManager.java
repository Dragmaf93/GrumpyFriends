package network;

import character.Character;
import world.World;
import game.AbstractMatchManager;

public class NetworkMatchManager extends AbstractMatchManager {

	private Multiplayer multiplayer;
	private int left,right,stop;
	public NetworkMatchManager(World battlefield) {
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
		currentPlayer.move(direction);

		if (direction == Character.LEFT){
			multiplayer.sendOperationMessage(Message.OP_MOVE_LEFT, null);
		}
		else
			
			multiplayer.sendOperationMessage(Message.OP_MOVE_RIGHT, null);

	}

	@Override
	public void stopToMoveCurrentPlayer() {
		currentPlayer.stopToMove();
		multiplayer.sendOperationMessage(Message.OP_STOP_MOVE, null);
	}

	@Override
	public void jumpCurrentPlayer() {
		currentPlayer.jump();
		multiplayer.sendOperationMessage(Message.OP_JUMP, null);
	}

	@Override
	public void equipWeaponCurrentPlayer(String weaponName) {
		currentPlayer.equipWeapon(weaponName);
		multiplayer.sendOperationMessage(Message.OP_EQUIP_WEAPON,
				weaponName);
	}

	@Override
	public void changeAimCurrentPlayer(int direction) {
		currentPlayer.changeAim(direction);

		if (direction == Character.INCREASE_AIM)
			multiplayer.sendOperationMessage(Message.OP_INCREASE_AIM, null);
		else
			multiplayer.sendOperationMessage(Message.OP_DECREASE_AIM, null);
	}

	@Override
	public void attackCurrentPlayer(float power) {
		currentPlayer.attack(power);

		multiplayer.sendOperationMessage(Message.OP_ATTACK,
				Float.toString(power));

	}
}
