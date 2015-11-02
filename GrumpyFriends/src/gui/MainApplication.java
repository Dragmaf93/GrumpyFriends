package gui;

import character.Character;
import character.Chewbacca;
import character.Team;
import game.MatchManager;
import javafx.application.Application;
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
		Team teamA = new Team("TeamA", 1, matchManager);
		matchManager.setTeamA(teamA);
		Character playerA = new Chewbacca("PlayerA", 100, 20, teamA);
		world.addCharacter(playerA);
		teamA.addCharcter(playerA);
		builder.lastSettings();
		matchManager.startTest();

		// ui
		primaryStage.setTitle("Grumpy Friends");

		
		
//		MatchRootNode root = new MatchRootNode(matchManager);
		MatchPane pane = new MatchPane(matchManager);
//		ZoomingScrollPane scroll = new ZoomingScrollPane(pane);
		
		MatchScene scene = new MatchScene(pane, matchManager, 600, 600);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
