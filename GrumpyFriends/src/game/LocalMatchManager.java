package game;

import gui.drawer.CharacterDrawer;
import character.Character;
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
		if (!isPaused()
				&& !(isMatchFinished() && getCurrentTurnPhase() == TurnPhaseType.END_PHASE)) {

			battlefield.step();
			battlefield.update();
			battlefield.removeDestroyedElement();

			checkCharactersOutOfWorld();

			if (currentPlayer.isOutWorld()) {
				timer.stopTurnTimer();
				setTurnPhase(TurnPhaseType.STARTER_PHASE);
			}

			if (getCurrentTurnPhase() == TurnPhaseType.STARTER_PHASE) {
				if (isMatchFinished())
					setTurnPhase(TurnPhaseType.END_PHASE);
				else if (allCharacterAreSpleeping() && canStartNextTurn())
					startNextTurn();
			}

			if (getCurrentTurnPhase() == TurnPhaseType.MAIN_PHASE) {
			    System.out.println(getCurrentPlayer().attacked() + " " + getMatchTimer().isTurnTimerStopped());
				if (getMatchTimer().isTurnTimerEnded()) {
					getCurrentPlayer().endTurn();
					getMatchTimer().stopTurnTimer();
					setTurnPhase(TurnPhaseType.STARTER_PHASE);
				} else if (getCurrentPlayer().attacked()
						&& !getMatchTimer().isTurnTimerStopped()) {
					// System.out.println("MAIN PHASE");
					getMatchTimer().startAttackTimer();
					getMatchTimer().stopTurnTimer();

				} else if (getMatchTimer().isTurnTimerStopped()
						&& getMatchTimer().isAttackTimerEnded()) {
					getCurrentPlayer().endTurn();
					setTurnPhase(TurnPhaseType.DAMAGE_PHASE);
				}
			}
			if (getCurrentTurnPhase() == TurnPhaseType.DEATH_PHASE) {

				checkDiedCharacters();

				if (allCharacterAreSpleeping()) {

					if (!diedCharactersOfTheCurrentTurn.isEmpty()
							&& canRemoveDeathCharacter()) {
						for (Character character : diedCharactersOfTheCurrentTurn) {
							character.afterDeath();
						}
						setTurnPhase(TurnPhaseType.STARTER_PHASE);
					}
						else
					    setTurnPhase(TurnPhaseType.STARTER_PHASE);
//					    
				}
			}
			if (getCurrentTurnPhase() == TurnPhaseType.DAMAGE_PHASE) {

				applyDamageToHitCharacter();
				if (isAppliedDamage() && allCharacterAreSpleeping()) {

					if (!getDamagedCharacters().isEmpty()) {
						if (canClearDamageCharacter()) {
							damagedCharacters.clear();
							setCanClearDamageCharacter(false);
							setTurnPhase(TurnPhaseType.DEATH_PHASE);
						}
					} else {
						setTurnPhase(TurnPhaseType.DEATH_PHASE);
					}
				}
			}
		}

	}

}
