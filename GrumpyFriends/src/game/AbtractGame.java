package game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import world.GameWorldBuilder;
import world.World;
import world.WorldBuilder;
import world.WorldDirector;
import character.Character;
import character.Team;
import menu.GameBean;
import menu.SequencePage;

public abstract class AbtractGame implements Game {

	protected SequencePage sequencePages;
	protected List<Team> teams;
	
	protected List<Character> characters;
	protected World battlefield;
	
	protected MatchManager matchManager;
	protected WorldBuilder worldBuilder;
	
	public AbtractGame() {
		teams = new ArrayList<Team>();
		characters = new ArrayList<Character>();
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
		}
		worldBuilder.lastSettings();
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
				if (bean.getNextNameData().equals("PlayerName")) {
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
		System.out.println(type);
		String name = PATH_PACKAGE + type;
		try {
			Class<?> classDefinition = Class.forName(name);
			Constructor constructor = classDefinition.getConstructor(String.class,Team.class);
			return  (Character) constructor.newInstance(namePlayer,team);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	final protected World createWorld(String typeWorld, String worldMap) {
		worldBuilder = new GameWorldBuilder();
		WorldDirector director = new WorldDirector(worldBuilder);
		director.createWorld("worldXML/" + worldMap + ".xml", typeWorld);
		World world = worldBuilder.getWorld();
		return world;
	}
}
