package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import character.Character;
import game.MatchManager;
import game.TurnPhaseType;
import gui.drawer.BackgroundPane;
import gui.drawer.CharacterDrawer;
import gui.drawer.DrawerObject;
import gui.drawer.WeaponDrawer;
import gui.drawer.WorldDrawer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import world.World;

public class FieldPane extends Pane {

	private World world;
	private MatchManager matchManager;

	private HashMap<String, DrawerObject> drawers;
	private HashMap<String, CharacterDrawer> characterDrawers;
	private WeaponDrawer weaponDrawer;
	private WorldDrawer worldDrawer;
	
	private FieldScene scene;
	
	private BackgroundPane background;

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
		this.setHeight(world.getHeight()+100);

		weaponDrawer = new WeaponDrawer();
		imageLoader = new ImageLoader();
		imageLoader.loadImage(world.getType());
		background = new BackgroundPane();
		worldDrawer = new WorldDrawer(world,imageLoader);
		initializeDrawers();
		createWorld();
//		this.setBackground(new Background(new BackgroundImage(getImageLoader().getImageBackgrounds("Planet"),BackgroundRepeat.ROUND,
//				BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT,
//				new BackgroundSize(100,100,true,true,true,false))));
		this.setStyle("-fx-background: null; -fx-background-color: null; ");
		this.getChildren().add(field);

		damagedCharacters = new ArrayList<Character>();
		diedCharacters = matchManager.getDiedCharacters();
		

	}
	
	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	private void initializeDrawers() {

		List<Character> characters = world.getAllCharacters();
		characterDrawers = new HashMap();

		for (Character character : characters) {
			CharacterDrawer cd = new CharacterDrawer(field, character,weaponDrawer,matchManager);
			characterDrawers.put(character.getName(), cd);
		}

		this.drawers = new HashMap<>();
//		drawers.put("GenericGround", new PolygonGroundDrawer(field, imageLoader));
//		drawers.put("LinearGround", new PolygonGroundDrawer(field, imageLoader));
//		drawers.put("InclinedGround", new PolygonGroundDrawer(field, imageLoader));
//		drawers.put("RoundGround", new RoundGroundDrawer(field, imageLoader));

	}

	public void setScene(FieldScene scene) {
		this.scene = scene;
	}

	public FieldScene getFieldScene(){
		return scene;
	}
	private void createWorld() {
		
		field.getChildren().add(worldDrawer.createWorld());
//		List<Ground> grounds = world.getGrounds();
//
//		for (Ground ground : grounds) {
//			drawers.get(ground.getClass().getSimpleName()).draw(ground);
////			new PolygonGroundDrawer(field, imageLoader).draw(ground);
//		}
	}

	public void update() {
		if (!matchManager.isPaused() && 
				!(matchManager.isMatchFinished() && matchManager.getCurrentTurnPhase() == TurnPhaseType.END_PHASE)) {

				world.update();
				
				matchManager.checkCharactersOutOfWorld();
				
				
				Collection<CharacterDrawer> collection = characterDrawers.values();

				for (CharacterDrawer characterDrawer : collection) {
					characterDrawer.draw();
				}
				
				
				if (matchManager.getCurrentPlayer().isOutWorld())
				{
					matchManager.getMatchTimer().stopTurnTimer();
					matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);
				}
				
				if(matchManager.getCurrentTurnPhase()==TurnPhaseType.STARTER_PHASE){
					
					if (matchManager.isMatchFinished())
						matchManager.setTurnPhase(TurnPhaseType.END_PHASE);
					
					else if (matchManager.allCharacterAreSpleeping())
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
								diedCharacters.remove(0).afterDeath();
								currentDiedCD = null;
							}

						} else {
								matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);
						}
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

								if (cd.finishedLifePointUpdate()) {
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
