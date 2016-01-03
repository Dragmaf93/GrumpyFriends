package game;

import java.util.ArrayList;

import gui.MainApplication;
import javafx.stage.Stage;

public class GameManager {

	private Stage stage;
	private MainApplication mainApplication;
	
	private static GameManager instance;
	
	private PlayGame playGame;
	
	public static GameManager getIstance(){
    	if(instance == null)
    		instance = new GameManager();
    	return instance;
    }
	
	private GameManager() {
		stage = new Stage();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void startMainApplication() {
		try {
			if (mainApplication == null)
				mainApplication = new MainApplication();

			mainApplication.start(mainApplication.getStage());
			playGame.closeStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startMainApplication(String string, ArrayList<String> arrayList, String typeA, String string2,
			ArrayList<String> arrayList2, String typeB, String string3) {
		try {
			if (mainApplication == null)
				mainApplication = new MainApplication(string,arrayList, typeA,string2,arrayList2, typeB,string3);

			mainApplication.start(mainApplication.getStage());
			playGame.closeStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startMenu() {
		try {
			playGame.start(playGame.getStage());
			playGame.setFirstScene();
			mainApplication.closeStage();
			mainApplication = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPlayGame(PlayGame playGame) {
		this.playGame = playGame;
	}
}
