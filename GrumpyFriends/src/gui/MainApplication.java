package gui;

import java.util.ArrayList;

import character.BlackStormtrooper;
import character.Character;
import character.WhiteStormtrooper;
import character.Team;
import game.AbstractMatchManager;
import game.LocalMatchManager;
import game.MatchManager;
import gui.event.KeyboardPressedEventHandler;
import gui.event.KeyboardReleaseEventHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import world.GameWorldBuilder;
import world.World;
import world.WorldBuilder;
import world.WorldDirector;

public class MainApplication extends Application{

	String nameTeamA = "TeamA";
	ArrayList<String> namesPlayerTeamA;
	String nameTeamB = "TeamB";
	ArrayList<String> namesPlayerTeamB;
	
	String mapChoosed="Map2.xml";

	private Stage stage;
	private String typeA;
	private String typeB;
	
	public MainApplication() {
	}
	
	public MainApplication(String nameTeamA, ArrayList<String> namesPlayerTeamA, String nameTeamB,
			ArrayList<String> namesPlayerTeamB, String mapChoosed) {
		super();
		this.nameTeamA = nameTeamA;
		this.namesPlayerTeamA = namesPlayerTeamA;
		this.nameTeamB = nameTeamB;
		this.namesPlayerTeamB = namesPlayerTeamB;
		this.mapChoosed = mapChoosed;
	}

	public MainApplication(String nameTeamA, ArrayList<String> namesPlayerTeamA, String typeA,
			String nameTeamB, ArrayList<String> namesPlayerTeamB, String typeB, 
			String mapChoosed) {
		super();
		this.nameTeamA = nameTeamA;
		this.namesPlayerTeamA = namesPlayerTeamA;
		this.typeA = typeA;
		this.nameTeamB = nameTeamB;
		this.namesPlayerTeamB = namesPlayerTeamB;
		this.typeB = typeB;
		this.mapChoosed = mapChoosed;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.stage = primaryStage;
		//match config
		WorldBuilder builder = new GameWorldBuilder();
		WorldDirector director = new WorldDirector(builder);
		director.createWorld("worldXML/"+mapChoosed,"Planet");
		World world = builder.getWorld();
		MatchManager matchManager = new LocalMatchManager(world);
//		Team teamA = new Team("TeamA", 1, matchManager);
//		matchManager.setTeamA(teamA);
//		Character playerA = new Chewbacca("PlayerA", 100, 20, teamA);
//		world.addCharacter(playerA);
//		teamA.addCharcter(playerA);
//		matchManager.startTest();

		Team teamA = new Team();
		Team teamB = new Team();
		
//		teamA = new Team(nameTeamA, 4, matchManager,1);
//		teamB = new Team(nameTeamB, 4, matchManager,2);
		
		
		teamA.setColorTeam(Color.CRIMSON);
		teamB.setColorTeam(Color.STEELBLUE);
		Character playerA = new WhiteStormtrooper("PlayerA",teamA);
		Character playerB = new BlackStormtrooper("PlayerB",teamB);

		playerA.setWorld(world);
		playerB.setWorld(world);
		
		matchManager.setTeamA(teamA);
		matchManager.setTeamB(teamB);
		
		teamA.setMatchManager(matchManager);
		teamB.setMatchManager(matchManager);
		
		teamA.addCharacter(playerA);
		teamB.addCharacter(playerB);
		
		world.addCharacter(playerA);
		world.addCharacter(playerB);

		
		builder.lastSettings();
		playerA.setStartedPosition(100,100);
		playerB.setStartedPosition(110,100);
		// ui
		primaryStage.setTitle("Grumpy Friends");
		
		matchManager.startMatch();
//		
		MatchPane matchPane = new MatchPane(matchManager);
		
		Scene scene = new Scene(matchPane,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
//		MatchScene scene = new MatchScene(matchPane, matchManager,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		primaryStage.setScene(scene);
		scene.setOnKeyPressed(new KeyboardPressedEventHandler(matchPane, matchManager));
		scene.setOnKeyReleased(new KeyboardReleaseEventHandler(matchPane, matchManager));
		
		primaryStage.show();
		
		new AnimationTimer() {
			
			@Override
			public void handle(long arg0) {
				matchPane.update();
			}
		}.start();
		
	}
	
	public void closeStage() {
		if (stage != null)
			stage.close();
		stage = null;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		if (stage != null)
			return stage;
		return new Stage();
	}
}
