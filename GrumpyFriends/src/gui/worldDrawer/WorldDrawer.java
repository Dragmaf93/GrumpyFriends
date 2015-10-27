package gui.worldDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import character.Character;
import element.Element;
import element.Ground;
import game.MatchManager;
import gui.MatchPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import world.World;

public class WorldDrawer {

	private Pane pane;

	private World world;

	private HashMap<String, DrawerObject> drawers;
	private List<CharacterDrawer> characterDrawers;

	public WorldDrawer(Pane pane) {
		this.pane = pane;
		this.world = ((MatchPane) pane).getWorld();
		
		List<Character> characters = world.getAllCharacters();
		characterDrawers = new ArrayList<CharacterDrawer>();
		
		for (Character character : characters) {
			CharacterDrawer c= new CharacterDrawer(character);
			characterDrawers.add(c);
		}
		
		this.drawers = new HashMap<>();
		initializeDrawers();
	}

	public void drawWorld() {

		List<Ground> grounds = world.getGrounds();
		
		for (Ground ground : grounds) {
			Shape s = drawers.get(ground.getClass().getSimpleName()).getShape(ground);
			pane.getChildren().add(s);
		}
		
		for (CharacterDrawer cd : characterDrawers) {
			pane.getChildren().add(cd.getShape());
		}
	}

	public void relocateCharacters() {

		for (CharacterDrawer cd : characterDrawers) {
			cd.relocate();
		}
	}

	private void initializeDrawers() {
		drawers.put("LinearGround", new LinearGroundDrawer());

	}
}
