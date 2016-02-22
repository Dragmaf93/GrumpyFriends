package network.server;

import java.math.MathContext;
import java.util.List;
import java.util.Set;

import network.Message;
import character.Character;
import world.World;
import game.AbstractMatchManager;
import game.TurnPhaseType;

public class ServerMatchManager extends AbstractMatchManager {

	private MatchServer matchServer;
	private String damagedCharactersJson;

	public ServerMatchManager(World battlefield) {
		super(battlefield);
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

			if (currentTurnPhase == TurnPhaseType.STARTER_PHASE) {
				if (isMatchFinished())
					currentTurnPhase = TurnPhaseType.END_PHASE;
				else if (allCharacterAreSpleeping() && canStartNextTurn()) {
					startNextTurn();
					
					System.out.println("START NEW TURN "+ currentTeam.getName());
				}
			}
			if (getCurrentTurnPhase() == TurnPhaseType.MAIN_PHASE) {

				if (getMatchTimer().isTurnTimerEnded()) {
					getCurrentPlayer().endTurn();
					getMatchTimer().stopTurnTimer();
					setTurnPhase(TurnPhaseType.STARTER_PHASE);
					matchServer.sendMessage(Message.START_NEXT_TURN,
							null);
				}
//				else if (getCurrentPlayer().attacked()
//						&& !getMatchTimer().isTurnTimerStopped()) {
//					 System.out.println("MAIN PHASE");
//					getMatchTimer().startAttackTimer();
//					getMatchTimer().stopTurnTimer();
//
//				} else if (getMatchTimer().isTurnTimerStopped()
//						&& getMatchTimer().isAttackTimerEnded()) {
//					getCurrentPlayer().endTurn();
//					setTurnPhase(TurnPhaseType.DAMAGE_PHASE);
//					matchServer.sendMessage(Message.END_TURN,null);
//				}
			}
			
//			if (getCurrentTurnPhase() == TurnPhaseType.DAMAGE_PHASE) {
//
//				applyDamageToHitCharacter();
//				
//				if (isAppliedDamage() && allCharacterAreSpleeping()) {
//					matchServer.sendMessage(Message.DAMAGE_CHARACTER, damagedCharactersJson);
//					if (!getDamagedCharacters().isEmpty()) {
//						if (canClearDamageCharacter()) {
//							damagedCharacters.clear();
//							setCanClearDamageCharacter(false);
//							setTurnPhase(TurnPhaseType.DEATH_PHASE);
//						}
//					} else {
//						setTurnPhase(TurnPhaseType.DEATH_PHASE);
//					}
//				}
//			}
		}
	}
	@Override
	public void applyDamageToHitCharacter() {
		if (!appliedDamage) {
			if (!hitCharacters.isEmpty()) {
				Set<String> keys = hitCharacters.keySet();
				for (String name : keys) {
					Character c = battlefield.getCharacter(name);
					if (c != null) {
						damagedCharactersJson+=";"+c.getName()+":"+(int) hitCharacters.get(name)
								.floatValue();
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
		}	}
	public void setMatchServer(MatchServer matchServer) {
		this.matchServer = matchServer;
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
