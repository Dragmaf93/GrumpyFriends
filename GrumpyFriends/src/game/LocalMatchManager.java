package game;

import world.World;

public class LocalMatchManager extends AbstractMatchManager {

	public LocalMatchManager(World battlefield) {
		super(battlefield);
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

	@Override
	public void update() {
		battlefield.step();
		battlefield.update();
		battlefield.removeDestroyedElement();
	}

}
