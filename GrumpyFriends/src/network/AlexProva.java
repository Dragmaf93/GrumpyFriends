package network;

import gui.MatchPane;
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
import character.BlackStormtrooper;
import character.Character;
import character.Team;
import character.WhiteStormtrooper;

public class AlexProva extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		WorldBuilder builder = new GameWorldBuilder();
		WorldDirector director = new WorldDirector(builder);
		director.createWorld("worldXML/Map2.xml","Planet");
		World world = builder.getWorld();
		NetworkMatchManager matchManager = new NetworkMatchManager(world);
		
		
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
		Multiplayer multiplayer = matchManager.getMultiplayer();
		multiplayer.createMatch();
		
		
		primaryStage.setTitle("Grumpy Friends");
		
//		
		MatchPane matchPane = new MatchPane(matchManager);
		
		Scene scene = new Scene(matchPane,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
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
		
		
//		p1.start();
		
	}
}
