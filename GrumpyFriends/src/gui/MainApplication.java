package gui;

import java.util.ArrayList;

import character.Character;
import character.Chewbacca;
import character.Team;
import game.MatchManager;
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
	
	String mapChoosed="Map1.xml";

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

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//match config
		WorldBuilder builder = new GameWorldBuilder();
		WorldDirector director = new WorldDirector(builder);
		director.createWorld("worldXML/"+mapChoosed);
		World world = builder.getWorld();
		MatchManager matchManager = new MatchManager(world);
//		Team teamA = new Team("TeamA", 1, matchManager);
//		matchManager.setTeamA(teamA);
//		Character playerA = new Chewbacca("PlayerA", 100, 20, teamA);
//		world.addCharacter(playerA);
//		teamA.addCharcter(playerA);
//		matchManager.startTest();
		
		Team teamA = new Team(nameTeamA, 1, matchManager);
		Team teamB = new Team(nameTeamB, 1, matchManager);
		
		teamA.setColorTeam(Color.CRIMSON);
		teamB.setColorTeam(Color.STEELBLUE);
		
		matchManager.setTeamA(teamA);
		matchManager.setTeamB(teamB);
		
//		Character playerA = new Chewbacca(namesPlayerTeamA.get(0), 50, 100, teamA);
//		Character playerB = new Chewbacca(namesPlayerTeamB.get(0), 30, 100, teamB);
//		
		Character playerA = new Chewbacca("PlayerA", 50, 100, teamA);
		Character playerB = new Chewbacca("PlayerB", 30, 100, teamB);
		
		world.addCharacter(playerA);
		world.addCharacter(playerB);
		
		teamA.addCharcter(playerA);
		teamB.addCharcter(playerB);

		builder.lastSettings();
		// ui
		primaryStage.setTitle("Grumpy Friends");

		matchManager.startMatch();
//		
		MatchPane matchPane = new MatchPane(matchManager);
		MatchScene scene = new MatchScene(matchPane, matchManager,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
//		MatchScene scene = new MatchScene(matchPane, matchManager,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		primaryStage.setScene(scene);
//		
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
