package gui;

import java.util.ArrayList;

import character.Character;
import character.Chewbacca;
import character.Team;
import game.MatchManager;
import javafx.application.Application;
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
		director.createWorld("worldXML/"+mapChoosed);
		World world = builder.getWorld();
		MatchManager matchManager = new MatchManager(world);
//		Team teamA = new Team("TeamA", 1, matchManager);
//		matchManager.setTeamA(teamA);
//		Character playerA = new Chewbacca("PlayerA", 100, 20, teamA);
//		world.addCharacter(playerA);
//		teamA.addCharcter(playerA);
//		matchManager.startTest();
		
		Team teamA;
		Team teamB;
		if (typeA.equals("WHITE"))
			teamA = new Team(nameTeamA, 4, matchManager,1);
		else
			teamA = new Team(nameTeamA, 4, matchManager,2);
		
		if (typeB.equals("WHITE"))
			teamB = new Team(nameTeamB, 4, matchManager,1);
		else
			teamB = new Team(nameTeamB, 4, matchManager,2);
		
//		teamA = new Team(nameTeamA, 4, matchManager,1);
//		teamB = new Team(nameTeamB, 4, matchManager,2);
		
		teamA.setColorTeam(Color.CRIMSON);
		teamB.setColorTeam(Color.STEELBLUE);
		
		matchManager.setTeamA(teamA);
		matchManager.setTeamB(teamB);
		
		int x = 0;
		int y = 100;
		for (int i = 0; i < namesPlayerTeamA.size(); i++) {
			Character player = new Chewbacca(namesPlayerTeamA.get(i), x+=50, y, teamA, world);
			world.addCharacter(player);
			teamA.addCharacter(player);
		}
		
		int x1 = 30;
		for (int i = 0; i < namesPlayerTeamB.size(); i++) {
			Character player = new Chewbacca(namesPlayerTeamB.get(i), x1+=30, y, teamB, world);
			world.addCharacter(player);
			teamB.addCharacter(player);
		}
		
//		
//		Character playerA1 = new Chewbacca("Player A1", 50, 100, teamA,world);
//		Character playerB1 = new Chewbacca("Player B1", 30, 100, teamB,world);
//		
//		Character playerA2 = new Chewbacca("Player A2", 90, 100, teamA, world);
//		Character playerB2 = new Chewbacca("Player B2", 130, 100, teamB, world);
//
//		Character playerA3 = new Chewbacca("Player A3", 120, 100, teamA,world);
//		Character playerB3 = new Chewbacca("Player B3", 90, 100, teamB,world);
//		
//		Character playerA4 = new Chewbacca("Player A4", 160, 100, teamA,world);
//		Character playerB4 = new Chewbacca("Player B4", 150, 100, teamB,world);
////		
//		world.addCharacter(playerA1);
//		world.addCharacter(playerB1);
//		world.addCharacter(playerA2);
//		world.addCharacter(playerB2);
//		world.addCharacter(playerA3);
//		world.addCharacter(playerB3);
//		world.addCharacter(playerA4);
//		world.addCharacter(playerB4);
//////		
//		teamA.addCharcter(playerA1);
//		teamA.addCharcter(playerA2);
//		teamA.addCharcter(playerA3);
//		teamA.addCharcter(playerA4);
////		
//		teamB.addCharcter(playerB1);
//		teamB.addCharcter(playerB2);
//		teamB.addCharcter(playerB3);
//		teamB.addCharcter(playerB4);

		builder.lastSettings();
		// ui
		primaryStage.setTitle("Grumpy Friends");

		matchManager.startMatch();
//		
		MatchPane matchPane = new MatchPane(matchManager);
		MatchScene scene = new MatchScene(matchPane, matchManager,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
//		MatchScene scene = new MatchScene(matchPane, matchManager,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		primaryStage.setScene(scene);
		primaryStage.show();
		
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
