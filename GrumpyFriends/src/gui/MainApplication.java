package gui;

import character.Character;
import character.Chewbacca;
import character.Team;
import game.MatchManager;
import gui.hud.MatchPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import world.GameWorldBuilder;
import world.World;
import world.WorldBuilder;
import world.WorldDirector;

public class MainApplication extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//match config
		WorldBuilder builder = new GameWorldBuilder();
		WorldDirector director = new WorldDirector(builder);
		director.createWorld("worldXML/world.xml");
		World world = builder.getWorld();
		MatchManager matchManager = new MatchManager(world);
//		Team teamA = new Team("TeamA", 1, matchManager);
//		matchManager.setTeamA(teamA);
//		Character playerA = new Chewbacca("PlayerA", 100, 20, teamA);
//		world.addCharacter(playerA);
//		teamA.addCharcter(playerA);
//		matchManager.startTest();
		
		Team teamA = new Team("TeamA", 1, matchManager);
		Team teamB = new Team("TeamB", 1, matchManager);
		
		matchManager.setTeamA(teamA);
		matchManager.setTeamB(teamB);
		
		Character playerA = new Chewbacca("PlayerA", 100, 20, teamA);
		Character playerB = new Chewbacca("PlayerB", 110, 20, teamB);
		
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
		primaryStage.setScene(scene);
//		
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
