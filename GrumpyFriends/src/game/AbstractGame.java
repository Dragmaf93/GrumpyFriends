package game;

import gui.Popup;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.xml.internal.messaging.saaj.util.CharReader;

import world.GameWorldBuilder;
import world.World;
import world.WorldBuilder;
import world.WorldDirector;
import character.Character;
import character.Team;
import menu.GameBean;
import menu.MenuManager;
import menu.SequencePage;

public abstract class AbstractGame implements Game {

	protected SequencePage sequencePages;
	protected List<Team> teams;

	protected List<Character> characters;
	protected World battlefield;

	protected MatchManager matchManager;
	protected WorldBuilder worldBuilder;
	protected List<GameBean> beans;

	private Popup exceptionMap;
	private Popup exceptionCharacter;

	public AbstractGame() {
		teams = new ArrayList<Team>();
		characters = new ArrayList<Character>();
		beans = new ArrayList<>();

		exceptionMap = new Popup(500, 180, "File map not Supported", null, "Ok");
		exceptionCharacter = new Popup(400, 180, "Error", null, "Ok");
	}

	@Override
	public SequencePage getSequencePages() {
		return sequencePages;
	}

	protected void positionCharacter() {
		int x = 10;
		for (Character character : characters) {
			x += 10;
			character.setStartedPosition(x, 100);
			character.createPhysicObject();
		}
		worldBuilder.lastSettings();
	}

	@Override
	public void addBean(GameBean bean) {
		beans.add(bean);
	}

	@Override
	public MatchManager getMatchManager() {
		return matchManager;
	}

	protected void extractData(GameBean bean) {

		String beanName = bean.getNameBean();
		switch (beanName) {
		case "Team":
			Team team = new Team();
			teams.add(team);

			team.setName(bean.getFirstValue("TeamName"));
			String typePlayer = bean.getFirstValue("TypeTeam");

			while (bean.hasNext()) {
				if (bean.getNextNameData().contains("PlayerName")) {
					Character character = createCharacter(bean.getNextValue(),
							typePlayer, team);
					team.addCharacter(character);
					characters.add(character);
				}
			}
			break;
		case "World":
			battlefield = createWorld(bean.getFirstValue("WorldType"),
					bean.getFirstValue("WorldMap"));
			break;
		default:
			break;
		}
	}

	final protected Character createCharacter(String namePlayer, String type,
			Team team) {
		String name = PATH_PACKAGE + type;
		try {
			Class<?> classDefinition = Class.forName(name);
			Constructor constructor = classDefinition.getConstructor(
					String.class, Team.class);
			return (Character) constructor.newInstance(namePlayer, team);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException |
				SecurityException | IllegalArgumentException | InvocationTargetException e) {
			
			MenuManager.getInstance().addExceptionPopup(exceptionCharacter);
			exceptionCharacter.getRightButton().setOnMouseReleased(
					new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY)
								System.exit(-1);
						}
					});
			
		}
		return null;
	}

	final protected World createWorld(String typeWorld, String worldMap) {
		worldBuilder = new GameWorldBuilder();
		WorldDirector director = new WorldDirector(worldBuilder);
		try {
			director.createWorld("worldXML/" + worldMap + ".xml", typeWorld);
		} catch (SAXException | IOException | ParserConfigurationException e) {

			MenuManager.getInstance().addExceptionPopup(exceptionMap);
			exceptionMap.getRightButton().setOnMouseReleased(
					new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								MenuManager.getInstance().goToMainMenu();
								MenuManager.getInstance().removeExceptionPopup(
										exceptionMap);
							}
						}
					});

		}
		World world = worldBuilder.getWorld();
		return world;
	}
}
