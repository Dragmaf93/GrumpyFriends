package network.server;

import world.World;
import game.AbstractMatchManager;

public class ServerMatchManager extends AbstractMatchManager{

	
	public ServerMatchManager(World battlefield) {
		super(battlefield);
	}
	
	@Override
	public void update() {
		battlefield.step();
		battlefield.update();
		battlefield.removeDestroyedElement();
	}

	@Override
	public void moveCurrentPlayer(int direction) {
		currentPlayer.move(direction);
	}

	@Override
	public void stopToMoveCurrentPlayer() {
		currentPlayer.stopToMove();
	}

	@Override
	public void jumpCurrentPlayer() {
		currentPlayer.jump();
	}

	@Override
	public void equipWeaponCurrentPlayer(String weaponName) {
		currentPlayer.equipWeapon(weaponName);
	}

	@Override
	public void changeAimCurrentPlayer(int direction) {
		currentPlayer.changeAim(direction);
	}

	@Override
	public void attackCurrentPlayer(float power) {
		currentPlayer.attack(power);
	}


}
