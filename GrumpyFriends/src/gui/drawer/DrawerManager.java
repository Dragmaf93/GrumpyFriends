package gui.drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import character.Character;
import element.Element;
import element.Ground;
import game.MatchManager;
import gui.FieldPane;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import world.World;

public class DrawerManager {

	private Group pane;
	private World world;

	private HashMap<String, DrawerObject> drawers;
	private List<CharacterDrawer> characterDrawers;
	private WeaponDrawer weaponDrawer;
	

	public DrawerManager(Group p, World world) {
		this.world = world;
		this.pane = p;
		
		initializeDrawers();
	
	}

	public void createWorld() {
		
		List<Ground> grounds = world.getGrounds();
		
		for (Ground ground : grounds) {
			drawers.get(ground.getClass().getSimpleName()).draw(ground);
		}
	}

	public void draw() {

		for (CharacterDrawer cd : characterDrawers) {
			cd.draw();
		}
		
	}

	private void initializeDrawers() {

		List<Character> characters = world.getAllCharacters();
		characterDrawers = new ArrayList<CharacterDrawer>();
		
		for (Character character : characters) {
			System.out.println(character.getName());
			CharacterDrawer c= new CharacterDrawer(pane,character);
			characterDrawers.add(c);
		}
		
		this.drawers = new HashMap<>();
		drawers.put("LinearGround", new LinearGroundDrawer(pane));
		

	}
}
