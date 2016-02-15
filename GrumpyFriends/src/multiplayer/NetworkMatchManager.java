package multiplayer;

import character.Character;
import world.World;
import game.AbstractMatchManager;

public class NetworkMatchManager extends AbstractMatchManager {

	private Multiplayer multiplayer;
	private int left,right,stop;
	public NetworkMatchManager(World battlefield) {
		super(battlefield);
		this.multiplayer = new Multiplayer(this);
	}

	public Multiplayer getMultiplayer() {
		return multiplayer;
	}

	@Override
	public void moveCurrentPlayer(int direction) {
		currentPlayer.move(direction);

		if (direction == Character.LEFT){
			multiplayer.sendOperationMessage(Multiplayer.OP_MOVE_LEFT, null);
		}
		else
			
			multiplayer.sendOperationMessage(Multiplayer.OP_MOVE_RIGHT, null);

	}

	@Override
	public void stopToMoveCurrentPlayer() {
		currentPlayer.stopToMove();
		multiplayer.sendOperationMessage(Multiplayer.OP_STOP_MOVE, null);
	}

	@Override
	public void jumpCurrentPlayer() {
		currentPlayer.jump();
		multiplayer.sendOperationMessage(Multiplayer.OP_JUMP, null);
	}

	@Override
	public void equipWeaponCurrentPlayer(String weaponName) {
		currentPlayer.equipWeapon(weaponName);
		multiplayer.sendOperationMessage(Multiplayer.OP_EQUIP_WEAPON,
				weaponName);
	}

	@Override
	public void changeAimCurrentPlayer(int direction) {
		currentPlayer.changeAim(direction);

		if (direction == Character.INCREASE_AIM)
			multiplayer.sendOperationMessage(Multiplayer.OP_INCREASE_AIM, null);
		else
			multiplayer.sendOperationMessage(Multiplayer.OP_DECREASE_AIM, null);
	}

	@Override
	public void attackCurrentPlayer(float power) {
		currentPlayer.attack(power);

		multiplayer.sendOperationMessage(Multiplayer.OP_ATTACK,
				Float.toString(power));

	}
}
