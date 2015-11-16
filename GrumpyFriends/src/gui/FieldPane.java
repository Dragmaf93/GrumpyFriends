package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


import character.Character;
import element.Ground;
import game.MatchManager;
import gui.drawer.CharacterDrawer;
import gui.drawer.DrawerObject;
import gui.drawer.LinearGroundDrawer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import world.World;

public class FieldPane extends Pane {

	private World world;
	private MatchManager matchManager;

	private HashMap<String, DrawerObject> drawers;
	private HashMap<String, CharacterDrawer> characterDrawers;

	private FieldScene scene;

	private Group field;
	private boolean addedCamerasMovement;
	private List<Character> damagedCharacters;
	private int currentDamagedCharacter;
	private boolean updateStarted;

	public FieldPane(MatchManager matchManager) {
		this.matchManager = matchManager;
		this.world = matchManager.getWorld();
		this.field = new Group();

		initializeDrawers();
		createWorld();

		this.getChildren().add(field);

		damagedCharacters = new ArrayList<Character>();
	}

	private void initializeDrawers() {

		List<Character> characters = world.getAllCharacters();
		characterDrawers = new HashMap();

		for (Character character : characters) {
			CharacterDrawer cd = new CharacterDrawer(field, character);
			characterDrawers.put(character.getName(), cd);
		}

		this.drawers = new HashMap<>();
		drawers.put("LinearGround", new LinearGroundDrawer(field));

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

		world.update();

		Collection<CharacterDrawer> collection = characterDrawers.values();

		for (CharacterDrawer characterDrawer : collection) {
			characterDrawer.draw();
		}
		if(!matchManager.isAppliedDamage() && matchManager.allCharacterAreSpleeping() && matchManager.isTheCurrentTurnEnded()){
			scene.focusNextPlayer();
		}
		if (matchManager.isAppliedDamage() && matchManager.allCharacterAreSpleeping()) {

			
			if (!matchManager.getDamagedCharacters().isEmpty()) {
				System.out.println("CI SONO PLAYER COLPITI");
				
				int cont=0;
				damagedCharacters = matchManager.getDamagedCharacters();
				for (Character c : damagedCharacters) {
					CharacterDrawer cd =	characterDrawers.get(c.getName());
					
					if(cd.canStartUpdate()){
						cd.startLifePointsUpdate();
					}
					
					if(!cd.finishedLifePointUpdate()){
						cont++;
					}
				}
				if(cont==damagedCharacters.size()){
					damagedCharacters.clear();
				}
				
//				if (!addedCamerasMovement) {
//					damagedCharacters = matchManager.getDamagedCharacters();
//					currentDamagedCharacter = 0;
//					for (Character character : damagedCharacters) {
//
//						if (character.sufferedDamage()) {
//							scene.addMovemetoToCamera(character.getX(), character.getY());
//						}
//					}
//					addedCamerasMovement = true;
//					scene.startCamerasMovements();
//
//				} else {
//					if (scene.cameraFocusAddedPoint()) {
//						Character dmgCharacter = damagedCharacters.get(currentDamagedCharacter);
//						CharacterDrawer cd = characterDrawers.get(dmgCharacter.getName());
//
//						if (!updateStarted) {
//							cd.startLifePointsUpdate();
//							updateStarted=true;
//
//						} else if (cd.finishedLifePointUpdate()) {
//							System.out.println("UPDATE FINITO");
//							updateStarted=false;
//							if (currentDamagedCharacter + 1 < damagedCharacters.size()) {
//								currentDamagedCharacter++;
//								scene.nextCameraMovement()
//							} else {
//								damagedCharacters.clear();
//							}
//
//						}
//					}
//				}
			} else {
				scene.focusNextPlayer();
				
			}
		}

	}

	public World getWorld() {
		return world;
	}

	public MatchManager getMatchManager() {
		return matchManager;
	}

}
