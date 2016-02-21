package network;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import menu.GameBean;
import menu.SequencePage;
import network.client.Client;
import network.client.Multiplayer;
import network.client.ClientMatchManager;
import world.GameWorldBuilder;
import world.World;
import world.WorldBuilder;
import world.WorldDirector;
import character.BlackStormtrooper;
import character.Character;
import character.Team;
import character.WhiteStormtrooper;
import game.MatchManager;
import gui.MatchPane;
import gui.event.KeyboardPressedEventHandler;
import gui.event.KeyboardReleaseEventHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AlexProva extends Application {
	static final String PATH_PACKAGE="character.";

	protected List<Team> teams;
	
	protected List<Character> characters;
	protected World battlefield;
	
	protected MatchManager matchManager;
	protected WorldBuilder worldBuilder;
	protected List<GameBean> beans;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		teams = new ArrayList<Team>();
		characters = new ArrayList<Character>();
		beans = new ArrayList<>();
		
		Client client = new Client();
		client.connectToServer();
		client.setImAChooser(false);
		InfoMatch infoMatch = new InfoMatch("Ciao", "CIAE", 1, false, "Planet");
		infoMatch.setStatusMatch(StatusMatch.WAITING);
		client.createMatch(infoMatch);

		Multiplayer multiplayer = new Multiplayer();
		multiplayer.createMatch(client.getMatchServerIp(),
				client.getServerPortNumber());

		GameBean beanWorld = new GameBean("World");

		beanWorld.addData("WorldType", "Planet");
		beanWorld.addData("WorldMap", "Map2");
		
		multiplayer.sendOperationMessage(Message.OP_SEND_INFO_WORLD,
				beanWorld.toJSON());
		GameBean beanTeam = new GameBean("Team");


		
		beanTeam.addData("TeamName", "Alex");
		beanTeam.addData("PlayerName" + 0, "Alex");
		beanTeam.addData("TypeTeam", "WhiteStormtrooper");
		multiplayer.sendOperationMessage(Message.OP_SEND_INFO_TEAM_TO_CHOOSER,
				beanTeam.toJSON());

		List<GameBean> gamebeans = multiplayer.getGameBean();
		gamebeans.add(beanWorld);
		gamebeans.add(beanTeam);
		
		multiplayer.readyToStart();

		for (int i = 0; i < gamebeans.size(); i++) {
			extractData(gamebeans.get(i));
		}

		matchManager = new ClientMatchManager(battlefield);
		for (Character character : characters) {
			character.setWorld(battlefield);
			battlefield.addCharacter(character);
		}
		multiplayer.setMatchManager(matchManager);
		
		((ClientMatchManager) matchManager).setMultiplayer(multiplayer);
		teams.get(0).setMatchManager(matchManager);
		teams.get(0).setColorTeam(Color.CRIMSON);
		teams.get(1).setMatchManager(matchManager);
		teams.get(1).setColorTeam(Color.STEELBLUE);

		if(teams.get(0).getName().equals("Alex")){
			matchManager.setTeamA(teams.get(0));
			matchManager.setTeamB(teams.get(1));
		}else{
			matchManager.setTeamA(teams.get(1));
			matchManager.setTeamB(teams.get(0));
		}


		positionCharacter();
		
		multiplayer.sendOperationMessage(Message.SERVER_READY, null);
		matchManager.startMatch();
		// multiplayer.setIps("127.0.0.1", "127.0.0.1");
		// multiplayer.createMatch();

		primaryStage.setTitle("Alex Creator");

		//
		MatchPane matchPane = new MatchPane(matchManager);

		Scene scene = new Scene(matchPane, Screen.getPrimary().getBounds()
				.getWidth(), Screen.getPrimary().getBounds().getHeight());
		primaryStage.setScene(scene);
		scene.setOnKeyPressed(new KeyboardPressedEventHandler(matchPane,
				matchManager));
		scene.setOnKeyReleased(new KeyboardReleaseEventHandler(matchPane,
				matchManager));

		primaryStage.show();

		new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				matchPane.update();
			}
		}.start();

		// p1.start();

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
	protected void positionCharacter() {
		int x = 10;
		for (Character character : characters) {
			x += 10;
			character.setStartedPosition(x, 100);
		}
		worldBuilder.lastSettings();
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
