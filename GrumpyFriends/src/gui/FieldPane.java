package gui;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import character.Character;
import element.Ground;
import game.MatchManager;
import game.TurnPhaseType;
import gui.drawer.CharacterDrawer;
import gui.drawer.DrawerObject;
import gui.drawer.PolygonGroundDrawer;
import gui.drawer.RoundGroundDrawer;
import gui.drawer.WeaponDrawer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import world.World;

public class FieldPane extends Pane {

	private World world;
	private MatchManager matchManager;

	private HashMap<String, DrawerObject> drawers;
	private HashMap<String, CharacterDrawer> characterDrawers;
	private WeaponDrawer weaponDrawer;
	
	private FieldScene scene;

	private Group field;
	// private boolean addedCamerasMovement;
	// private int currentDamagedCharacter;
	// private boolean updateStarted;

	private List<Character> damagedCharacters;

	private List<Character> diedCharacters;
	private CharacterDrawer currentDiedCD;
	private int currentTurn;
	private ImageLoader imageLoader;

	public FieldPane(MatchManager matchManager) {
		this.matchManager = matchManager;
		this.world = matchManager.getWorld();
		this.field = new Group();

		this.setWidth(world.getWidth());
		this.setHeight(world.getHeight());

		weaponDrawer = new WeaponDrawer();
		initializeDrawers();
		createWorld();

//		this.setStyle("-fx-background: #6b5d5d; -fx-background-color: #6b5d5d; ");
		this.getChildren().add(field);

		damagedCharacters = new ArrayList<Character>();
		diedCharacters = matchManager.getDiedCharacters();
		

	}

	private void initializeDrawers() {

		imageLoader = new ImageLoader();
		List<Character> characters = world.getAllCharacters();
		characterDrawers = new HashMap();

		for (Character character : characters) {
			CharacterDrawer cd = new CharacterDrawer(field, character,weaponDrawer,matchManager);
			characterDrawers.put(character.getName(), cd);
		}

		this.drawers = new HashMap<>();
		drawers.put("GenericGround", new PolygonGroundDrawer(field, imageLoader));
		drawers.put("LinearGround", new PolygonGroundDrawer(field, imageLoader));
		drawers.put("InclinedGround", new PolygonGroundDrawer(field, imageLoader));
		drawers.put("RoundGround", new RoundGroundDrawer(field));

	}

	public void setScene(FieldScene scene) {
		this.scene = scene;
	}

	private void createWorld() {

		List<Ground> grounds = world.getGrounds();

		for (Ground ground : grounds) {
			drawers.get(ground.getClass().getSimpleName()).draw(ground);
		}
	}

	public void update() {
		if (!matchManager.isPaused()) {

			if (matchManager.isMatchFinished() && matchManager.getCurrentTurnPhase() == TurnPhaseType.END_PHASE) {

			} else {

				world.update();

				if (matchManager.getCurrentPlayer().getX() < 0 || matchManager.getCurrentPlayer().getX() > world.getWidth())
				{
					matchManager.setTurnPhase(TurnPhaseType.DEATH_PHASE);
					matchManager.getCurrentPlayer().setDied(true);
				}
				
				Collection<CharacterDrawer> collection = characterDrawers.values();

				for (CharacterDrawer characterDrawer : collection) {
					characterDrawer.draw();
				}
				
				if(matchManager.getCurrentTurnPhase()==TurnPhaseType.STARTER_PHASE){
					
					if (matchManager.allCharacterAreSpleeping())
						scene.focusNextPlayer();
				}
				 if (matchManager.getCurrentTurnPhase() == TurnPhaseType.MAIN_PHASE) {
					if (matchManager.getMatchTimer().isTurnTimerEnded()) {
						matchManager.getCurrentPlayer().endTurn();
						matchManager.getMatchTimer().stopTurnTimer();
						matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);	
					} else if (matchManager.getCurrentPlayer().attacked()
							&& !matchManager.getMatchTimer().isTurnTimerStopped()) {
//						System.out.println("MAIN PHASE");
						matchManager.getMatchTimer().startAttackTimer();
						matchManager.getMatchTimer().stopTurnTimer();

					} else if (matchManager.getMatchTimer().isTurnTimerStopped()
							&& matchManager.getMatchTimer().isAttackTimerEnded()) {
						matchManager.getCurrentPlayer().endTurn();
						matchManager.setTurnPhase(TurnPhaseType.DAMAGE_PHASE);
						matchManager.applyDamageToHitCharacter();
					}
				}

				if (matchManager.getCurrentTurnPhase() == TurnPhaseType.DEATH_PHASE) {

					matchManager.checkDiedCharacters();

					if (matchManager.allCharacterAreSpleeping()) {

						if (!diedCharacters.isEmpty()) {

							if (currentDiedCD == null) {
								currentDiedCD = characterDrawers.get(diedCharacters.get(0).getName());
							}

							currentDiedCD.startDeathAnimation();

							if (currentDiedCD.isDeathAnimationFinished()) {
//								System.out.println("ciao");
								diedCharacters.remove(0).afterDeath();
								currentDiedCD = null;
							}

						} else {

							if (matchManager.isMatchFinished()) {
								matchManager.setTurnPhase(TurnPhaseType.END_PHASE);
							} else
								matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);
						}
					}
					if (matchManager.getCurrentPlayer().isOutWorld())
					{
						matchManager.getCurrentPlayer().endTurn();
						matchManager.getMatchTimer().stopTurnTimer();
						matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);
						scene.focusNextPlayer();
					}
				}

				if (matchManager.getCurrentTurnPhase() == TurnPhaseType.DAMAGE_PHASE) {
					
					matchManager.applyDamageToHitCharacter();
					
					if (matchManager.isAppliedDamage() && matchManager.allCharacterAreSpleeping()) {

						if (!matchManager.getDamagedCharacters().isEmpty()) {

							int cont = 0;
							damagedCharacters = matchManager.getDamagedCharacters();
							for (Character c : damagedCharacters) {
								CharacterDrawer cd = characterDrawers.get(c.getName());

								if (cd.canStartUpdate()) {
									cd.startLifePointsUpdate();
								}

								if (!cd.finishedLifePointUpdate()) {
									cont++;
								}
							}
							if (cont == damagedCharacters.size()) {
								damagedCharacters.clear();
							}
							/*
							 * if (!addedCamerasMovement) { damagedCharacters =
							 * matchManager.getDamagedCharacters();
							 * currentDamagedCharacter = 0; for (Character
							 * character : damagedCharacters) {
							 * 
							 * if (character.sufferedDamage()) {
							 * scene.addMovemetoToCamera(character.getX(),
							 * character.getY()); } } addedCamerasMovement =
							 * true; scene.startCamerasMovements();
							 * 
							 * } else { if (scene.cameraFocusAddedPoint()) {
							 * Character dmgCharacter =
							 * damagedCharacters.get(currentDamagedCharacter);
							 * CharacterDrawer cd =
							 * characterDrawers.get(dmgCharacter.getName());
							 * 
							 * if (!updateStarted) { cd.startLifePointsUpdate();
							 * updateStarted=true;
							 * 
							 * } else if (cd.finishedLifePointUpdate()) {
							 * System.out.println("UPDATE FINITO");
							 * updateStarted=false; if (currentDamagedCharacter
							 * + 1 < damagedCharacters.size()) {
							 * currentDamagedCharacter++;
							 * scene.nextCameraMovement() } else {
							 * damagedCharacters.clear(); }
							 * 
							 * } } }
							 */
						} else {
							matchManager.setTurnPhase(TurnPhaseType.DEATH_PHASE);

						}
					}
				}

			}
		}
	}
	// public void update() {
	//
	// world.update();
	//
	// Collection<CharacterDrawer> collection = characterDrawers.values();
	//
	// for (CharacterDrawer characterDrawer : collection) {
	// characterDrawer.draw();
	// }
	//
	// if (matchManager.getCurrentTurnPhase() == TurnPhaseType.MAIN_PHASE &&
	// matchManager.allCharacterAreSpleeping()
	// && matchManager.isTheCurrentTurnEnded()) {
	// scene.focusNextPlayer();
	// }
	//
	// if (matchManager.getCurrentTurnPhase() == TurnPhaseType.DEATH_PHASE
	// && matchManager.allCharacterAreSpleeping()) {
	//
	// if (!diedCharacters.isEmpty()) {
	//
	// if (currentDiedCD == null) {
	// currentDiedCD = characterDrawers.get(diedCharacters.get(0).getName());
	// }
	//
	// currentDiedCD.startDeathAnimation();
	//
	// if (currentDiedCD.isDeathAnimationFinished()) {
	// System.out.println("ciao");
	// diedCharacters.remove(0).afterDeath();
	// currentDiedCD = null;
	// }
	//
	// } else {
	//
	// if (matchManager.isMatchFinished()) {
	// matchManager.setTurnPhase(TurnPhaseType.END_PHASE);
	// } else
	// scene.focusNextPlayer();
	// }
	// }
	//
	// if (matchManager.getCurrentTurnPhase() == TurnPhaseType.DAMAGE_PHASE &&
	// matchManager.isAppliedDamage()
	// && matchManager.allCharacterAreSpleeping()) {
	//
	// if (!matchManager.getDamagedCharacters().isEmpty()) {
	//
	// int cont = 0;
	// damagedCharacters = matchManager.getDamagedCharacters();
	// for (Character c : damagedCharacters) {
	// CharacterDrawer cd = characterDrawers.get(c.getName());
	//
	// if (cd.canStartUpdate()) {
	// cd.startLifePointsUpdate();
	// }
	//
	// if (!cd.finishedLifePointUpdate()) {
	// cont++;
	// }
	// }
	// if (cont == damagedCharacters.size()) {
	// damagedCharacters.clear();
	// }
	// /*
	// * if (!addedCamerasMovement) { damagedCharacters =
	// * matchManager.getDamagedCharacters(); currentDamagedCharacter
	// * = 0; for (Character character : damagedCharacters) {
	// *
	// * if (character.sufferedDamage()) {
	// * scene.addMovemetoToCamera(character.getX(),
	// * character.getY()); } } addedCamerasMovement = true;
	// * scene.startCamerasMovements();
	// *
	// * } else { if (scene.cameraFocusAddedPoint()) { Character
	// * dmgCharacter =
	// * damagedCharacters.get(currentDamagedCharacter);
	// * CharacterDrawer cd =
	// * characterDrawers.get(dmgCharacter.getName());
	// *
	// * if (!updateStarted) { cd.startLifePointsUpdate();
	// * updateStarted=true;
	// *
	// * } else if (cd.finishedLifePointUpdate()) {
	// * System.out.println("UPDATE FINITO"); updateStarted=false; if
	// * (currentDamagedCharacter + 1 < damagedCharacters.size()) {
	// * currentDamagedCharacter++; scene.nextCameraMovement() } else
	// * { damagedCharacters.clear(); }
	// *
	// * } } }
	// */
	// } else {
	// System.out.println("cia");
	// matchManager.setTurnPhase(TurnPhaseType.DEATH_PHASE);
	//
	// }
	// }
	//
	// }

	public World getWorld() {
		return world;
	}

	public MatchManager getMatchManager() {
		return matchManager;
	}

}
