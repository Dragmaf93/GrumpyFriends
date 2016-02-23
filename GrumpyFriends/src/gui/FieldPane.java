package gui;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import world.World;
import character.Character;
import game.AbstractMatchManager;
import game.MatchManager;
import game.TurnPhaseType;
import gui.drawer.BackgroundPane;
import gui.drawer.CharacterDrawer;
import gui.drawer.DrawerObject;
import gui.drawer.WeaponDrawer;
import gui.drawer.WorldDrawer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class FieldPane extends Pane {

	private World world;
	private MatchManager matchManager;

	private Map<String, DrawerObject> drawers;
	private Map<String, CharacterDrawer> characterDrawers;
	private WeaponDrawer weaponDrawer;
	private WorldDrawer worldDrawer;

	private FieldScene scene;
	private Node worldNode;

	private Group field;
	private boolean focusPlayer;
	
	private List<Character> damagedCharacters;

	private List<Character> diedCharacters;
	private CharacterDrawer currentDiedCD;
	private int currentTurn;
	private ImageLoader imageLoader;

	private int contdeathAnimation;

	public FieldPane(MatchManager matchManager) {
		this.matchManager = matchManager;
		this.world = matchManager.getWorld();
		this.field = new Group();

		this.setWidth(world.getWidth());
		this.setHeight(world.getHeight() + 100);

		weaponDrawer = new WeaponDrawer();
		imageLoader = new ImageLoader();
		imageLoader.loadImage(world.getType());
		worldDrawer = new WorldDrawer(world, imageLoader);
		initializeDrawers();
		createWorld();
		// this.setBackground(new Background(new
		// BackgroundImage(getImageLoader().getImageBackgrounds("Planet"),BackgroundRepeat.ROUND,
		// BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT,
		// new BackgroundSize(100,100,true,true,true,false))));
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
			CharacterDrawer cd = new CharacterDrawer(field, character,
					weaponDrawer, matchManager);
			characterDrawers.put(character.getName(), cd);
		}

		this.drawers = new HashMap<>();
		// drawers.put("GenericGround", new PolygonGroundDrawer(field,
		// imageLoader));
		// drawers.put("LinearGround", new PolygonGroundDrawer(field,
		// imageLoader));
		// drawers.put("InclinedGround", new PolygonGroundDrawer(field,
		// imageLoader));
		// drawers.put("RoundGround", new RoundGroundDrawer(field,
		// imageLoader));

	}

	public void setScene(FieldScene scene) {
		this.scene = scene;
	}

	public FieldScene getFieldScene() {
		return scene;
	}

	private void createWorld() {

		field.getChildren().add(worldNode = worldDrawer.createWorld());
		// List<Ground> grounds = world.getGrounds();
		//
		// for (Ground ground : grounds) {
		// drawers.get(ground.getClass().getSimpleName()).draw(ground);
		// // new PolygonGroundDrawer(field, imageLoader).draw(ground);
		// }
	}

	public void reset() {

		Collection<CharacterDrawer> collection = characterDrawers.values();

		field.getChildren().remove(worldNode);

		for (CharacterDrawer characterDrawer : collection) {
			characterDrawer.reset();
		}

		field.getChildren().add(worldNode);
	}

	public void update() {

			matchManager.update();

			Collection<CharacterDrawer> collection = characterDrawers.values();

			for (CharacterDrawer characterDrawer : collection) {
				characterDrawer.draw();
			}
			//
			// if (matchManager.getCurrentPlayer().isOutWorld()) {
			// matchManager.getMatchTimer().stopTurnTimer();
			// matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);
			// }

			if (matchManager.getCurrentTurnPhase() == TurnPhaseType.STARTER_PHASE) {

				if (!matchManager.isMatchFinished()
						&& matchManager.allCharacterAreSpleeping() && !focusPlayer){
					scene.focusNextPlayer();
					focusPlayer=true;
				}
			}else{
				focusPlayer=false;
			}

			// if (matchManager.getCurrentTurnPhase() ==
			// TurnPhaseType.MAIN_PHASE) {
			//
			// if (matchManager.getMatchTimer().isTurnTimerEnded()) {
			// matchManager.getCurrentPlayer().endTurn();
			// matchManager.getMatchTimer().stopTurnTimer();
			// matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);
			// } else if (matchManager.getCurrentPlayer().attacked()
			// && !matchManager.getMatchTimer().isTurnTimerStopped()) {
			// // System.out.println("MAIN PHASE");
			// matchManager.getMatchTimer().startAttackTimer();
			// matchManager.getMatchTimer().stopTurnTimer();
			//
			// } else if (matchManager.getMatchTimer().isTurnTimerStopped()
			// && matchManager.getMatchTimer().isAttackTimerEnded()) {
			// matchManager.getCurrentPlayer().endTurn();
			// matchManager.setTurnPhase(TurnPhaseType.DAMAGE_PHASE);
			// }
			// }

			if (matchManager.getCurrentTurnPhase() == TurnPhaseType.DEATH_PHASE) {

				// matchManager.checkDiedCharacters();

				if (matchManager.allCharacterAreSpleeping()) {

					if (!diedCharacters.isEmpty()) {

						if (currentDiedCD == null) {
							currentDiedCD = characterDrawers.get(diedCharacters
									.get(0).getName());
						}

						currentDiedCD.startDeathAnimation();

						if (currentDiedCD.isDeathAnimationFinished()) {
							currentDiedCD = null;
							contdeathAnimation++;
						}

						if (contdeathAnimation == diedCharacters.size()) {
							matchManager.setCanRemoveDeathCharacter(true);
							contdeathAnimation = 0;
						}
					}
					// else {
					// matchManager.setTurnPhase(TurnPhaseType.STARTER_PHASE);
					// }
				}
			}

			if (matchManager.getCurrentTurnPhase() == TurnPhaseType.DAMAGE_PHASE) {

				// matchManager.applyDamageToHitCharacter();
				if (matchManager.isAppliedDamage()
						&& matchManager.allCharacterAreSpleeping()) {

					if (!matchManager.getDamagedCharacters().isEmpty()) {

						int cont = 0;
						damagedCharacters = matchManager.getDamagedCharacters();
						for (Character c : damagedCharacters) {

							CharacterDrawer cd = characterDrawers.get(c
									.getName());

							if (cd.canStartUpdate()) {
								cd.startLifePointsUpdate();
							}

							if (cd.finishedLifePointUpdate()) {
								cont++;
							}
						}
						if (cont == damagedCharacters.size()) {
							matchManager.setCanClearDamageCharacter(true);
						}
					}
					// else {
					// matchManager.setTurnPhase(TurnPhaseType.DEATH_PHASE);
					//
					// }
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
